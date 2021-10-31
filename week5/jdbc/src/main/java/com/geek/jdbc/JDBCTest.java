package com.geek.jdbc;

/**
 * @author 吴振
 * @since 2021/11/1 上午1:14
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCTest {

    public static void addData() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO student(id, `name`) VALUES (1, `zz`)";

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
        connection.close();
    }

    public static List<Student> getData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, `name`, age FROM student";

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Student> list = new ArrayList<>();
        while (resultSet.next()) {
            Student data = new Student();
            data.setId(resultSet.getLong("id"));
            data.setName(resultSet.getString("name"));
            list.add(data);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }

    public static void updateData() throws SQLException, ClassNotFoundException {
        String sql = "UPDATE student SET name = 'ss' WHERE name = 'zz'";

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
        connection.close();
    }

    public static void deleteData() throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM student WHERE name = 'zz'";

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
        connection.close();
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String address = "jdbc:mysql://localhost:3306/wz?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(address, user, password);
    }
}
