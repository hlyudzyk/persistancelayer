package persistance.dao;

import exception.NoResultException;
import exception.PersistenceException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import persistance.ConnectionPool;
import persistance.models.Sale;
import persistance.models.Sale.SaleBuilder;

public class SaleDao extends Dao<Sale>{
    private static final String SAVE_SQL =
        """
  INSERT INTO sales(product_id,client_id,quantity,sale_date)
  VALUES (?,?,?,?);
  """;


    private SaleDao(){

    }
    @Override
    public boolean update(Sale model) {
        return false;
    }

    @Override
    public Sale save(Sale sale) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement =
                connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, sale.getProduct().getId());
            statement.setInt(2, sale.getProduct().getId());
            statement.setInt(3, sale.getQuantity());
            statement.setDate(4, Date.valueOf(sale.getDate()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                sale.setId(generatedKeys.getInt("id"));
                sale.setDate(generatedKeys.getDate("sale_date").toLocalDate());
            }
            return sale;
        } catch (SQLException e) {
            throw new PersistenceException(
                "При збереженні запису");
        }
    }

    @Override
    protected Sale buildEntity(ResultSet resultSet) {
        ClientDao clientDao = ClientDao.getInstance();
        ProductDao productDao = ProductDao.getInstance();

        try {
            int clientId = resultSet.getInt("client_id");
            int productId = resultSet.getInt("product_id");
            return new SaleBuilder()
                .id(resultSet.getInt("id"))
                .product(productDao.findOneById(productId).orElseThrow())
                .client(clientDao.findOneById(clientId).orElseThrow())
                .quantity(resultSet.getInt("quantity"))
                .date(resultSet.getDate("sale_date").toLocalDate())
                .build();

        } catch (SQLException e) {
            throw new NoResultException(
                "Не вдалось отримати ResultSet");
        }
    }

    @Override
    protected String getTableName() {
        return "sales";
    }

    private static class SaleDaoHolder {
        public static final SaleDao HOLDER_INSTANCE = new SaleDao();
    }

    public static SaleDao getInstance() {
        return SaleDao.SaleDaoHolder.HOLDER_INSTANCE;
    }
}
