package persistance.dao;

import exception.NoResultException;
import exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import persistance.ConnectionPool;
import persistance.models.Manufacturer;
import persistance.models.Manufacturer.ManufacturerBuilder;

public class ManufacturerDao extends Dao<Manufacturer> {
    private static final String SAVE_SQL =
        """
  INSERT INTO manufacturers(name,country)
  VALUES (?,?);
  """;

    private static final String UPDATE_SQL =
        """
  UPDATE manufacturers
     SET name = ?,
     coutry = ?
   WHERE id = ?;
  """;

    @Override
    public boolean update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setInt(3, manufacturer.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenceException(
                "При оновленні запису");
        }
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement =
                connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getInt("id"));
            }
            return manufacturer;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenceException(
                "При збереженні запису");
        }
    }

    @Override
    protected Manufacturer buildEntity(ResultSet resultSet) {
        try {
            return new ManufacturerBuilder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .country(resultSet.getString("country"))
                .build();
        } catch (SQLException e) {
            throw new NoResultException(
                "Не вдалось отримати ResultSet");
        }
    }

    @Override
    protected String getTableName() {
        return "manufacturers";
    }

    private static class ManufacturerDaoHolder {
        public static final ManufacturerDao HOLDER_INSTANCE = new ManufacturerDao();
    }

    public static ManufacturerDao getInstance() {
        return ManufacturerDaoHolder.HOLDER_INSTANCE;
    }
}
