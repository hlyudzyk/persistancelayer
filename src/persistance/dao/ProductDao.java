package persistance.dao;

import exception.NoResultException;
import exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import persistance.ConnectionPool;
import persistance.models.Product;
import persistance.models.Product.ProductBuilder;

public class ProductDao extends Dao<Product>{
    private static final String SAVE_SQL =
        """
  INSERT INTO products(name,category_id,manufacturer_id, price)
  VALUES (?,?,?,?);
  """;

    private static final String UPDATE_SQL =
        """
  UPDATE products
     SET name = ?, category_id = ?,
     manufacturer_id = ?,
     price = ?
   WHERE id = ?;
  """;
    @Override
    public boolean update(Product product) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getCategory().getId());
            statement.setInt(3, product.getManufacturer().getId());
            statement.setDouble(4,product.getPrice());
            statement.setInt(5,product.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenceException(
                "При оновленні запису");
        }
    }

    @Override
    public Product save(Product product) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement =
                connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getCategory().getId());
            statement.setInt(3, product.getManufacturer().getId());
            statement.setDouble(4, product.getPrice());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt("id"));
            }
            return product;
        } catch (SQLException e) {
            throw new PersistenceException(
                "При збереженні запису");
        }
    }

    @Override
    protected Product buildEntity(ResultSet resultSet) {
        CategoryDao categoryDao = CategoryDao.getInstance();
        ManufacturerDao manufacturerDao = ManufacturerDao.getInstance();

        try {
            int categoryId = resultSet.getInt("category_id");
            int manufacturerId = resultSet.getInt("manufacturer_id");
            return new ProductBuilder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .category(categoryDao.findOneById(categoryId).orElseThrow())
                .manufacturer(manufacturerDao.findOneById(manufacturerId).orElseThrow())
                .price(resultSet.getInt("price"))
                .build();

        } catch (SQLException e) {
            throw new NoResultException(
                "Не вдалось отримати ResultSet");
        }
    }

    @Override
    protected String getTableName() {
        return "products";
    }

    private static class ProductDaoHolder {
        public static final ProductDao HOLDER_INSTANCE = new ProductDao();
    }

    public static ProductDao getInstance() {
        return ProductDao.ProductDaoHolder.HOLDER_INSTANCE;
    }
}
