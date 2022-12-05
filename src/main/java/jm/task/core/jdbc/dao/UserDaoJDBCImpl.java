package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS User (id int auto_increment primary key," +
                "name varchar(255) not null," +
                "lastName varchar(255) not null," +
                "age int not null)";
        try (PreparedStatement  preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println(exception);
        }
    }
    public void dropUsersTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS User";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println(exception);
        }
    }
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO User (name, lastName, age) "
                + " values (?, ?, ?) ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException exception) {
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println(exception);
        }
    }
    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM User WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println(exception);
        }
    }
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(sql);
            connection.commit();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        }
        catch(SQLException exception){
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println(exception);
        }
        return userList;
    }
    public void cleanUsersTable() throws SQLException {
        String sql = "DELETE FROM User";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println(exception);
        }
    }
}
