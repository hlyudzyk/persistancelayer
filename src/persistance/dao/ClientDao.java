package persistance.dao;

import exception.NoResultException;
import exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import persistance.ConnectionPool;
import persistance.models.Client;
import persistance.models.Client.ClientBuilder;


public class ClientDao extends Dao<Client> {

    private static final String SAVE_SQL =
        """
  INSERT INTO clients(name,email)
  VALUES (?,?);
  """;

    private static final String UPDATE_SQL =
        """
  UPDATE clients
     SET name = ?,
     email = ?
   WHERE id = ?;
  """;

    private ClientDao(){

    }

    @Override
    public boolean update(Client client) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenceException(
                "При оновленні запису");
        }
    }

    @Override
    public Client save(Client client) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement =
                connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getInt("id"));
            }
            return client;
        } catch (SQLException e) {
            throw new PersistenceException(
                "При збереженні запису");
        }
    }

    @Override
    protected Client buildEntity(ResultSet resultSet) {
        try {
            return new ClientBuilder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .build();
        } catch (SQLException e) {
            throw new NoResultException(
                "Не вдалось отримати ResultSet");
        }
    }

    @Override
    protected String getTableName() {
        return "clients";
    }

    private static class ClientDaoHolder {
        public static final ClientDao HOLDER_INSTANCE = new ClientDao();
    }

    public static ClientDao getInstance() {
        return ClientDao.ClientDaoHolder.HOLDER_INSTANCE;
    }
}
