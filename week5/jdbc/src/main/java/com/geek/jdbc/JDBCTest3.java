package com.geek.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴振
 * @since 2021/11/1 上午1:25
 */

@Component
public class JDBCTest3 {

    @Autowired
    private DataSource dataSource;

    public void insertData() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO student(id, `name`) VALUES (?, ?)";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, 2);
        statement.setString(2, "df");
        statement.execute();
        statement.close();
        connection.close();
    }

    public List<Student> getData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, `name` FROM student";

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

    public void updateData() throws SQLException, ClassNotFoundException {
        String sql = "UPDATE student SET name = ? WHERE name = ?";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "zz");
        statement.setString(2, "kk");
        statement.execute();
        statement.close();
        connection.close();
    }

    public void deleteData() throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM student WHERE name = ?";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "sg");
        statement.execute();
        statement.close();
        connection.close();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
