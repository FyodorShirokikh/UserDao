package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }
    public void createUsersTable() throws Exception {
        PreparedStatement preparedStatement = null;
        String sql = "CREATE TABLE User (id int auto_increment primary key," +
                "name varchar(255) not null," +
                "lastName varchar(255) not null," +
                "age int not null)";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void dropUsersTable() throws Exception {
        PreparedStatement preparedStatement = null;
        String sql = "DROP TABLE User";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws Exception {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO User (name, lastName, age) "
                + " values (?, ?, ?) ";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void removeUserById(long id) throws Exception {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM User WHERE id=?";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public List<User> getAllUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User";
        Statement statement = null;
        try (Connection connection = Util.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        }
        catch(Exception exception){
            System.out.println(exception);
        }
        return userList;
    }

    public void cleanUsersTable() throws Exception {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM User";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
