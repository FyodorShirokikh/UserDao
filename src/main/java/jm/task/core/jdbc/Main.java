package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
       Util.getConnection();
       UserDao userDao = new UserDaoJDBCImpl();

       userDao.createUsersTable();

       userDao.saveUser("Name1", "LastName1", (byte) 20);
       userDao.saveUser("Name2", "LastName2", (byte) 25);
       userDao.saveUser("Name3", "LastName3", (byte) 31);
       userDao.saveUser("Name4", "LastName4", (byte) 38);

       userDao.removeUserById(1);
       List<User> userList = userDao.getAllUsers();
       for (int i = 0; i < userList.size(); i++) {
          System.out.println(userList.get(i).toString());
       }
       userDao.cleanUsersTable();
       userDao.dropUsersTable();
    }
}
