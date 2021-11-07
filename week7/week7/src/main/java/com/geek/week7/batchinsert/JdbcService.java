package com.geek.week7.batchinsert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

/**
 * @author 吴振
 * @since 2021/11/8 上午2:07
 */
public class JdbcService {

    private String url = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8";
    private String username = "root";
    private String password = "root";


    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return DriverManager.getConnection(url, username, password);
    }

    //100万行订单数据
    private int insertRows = 1000000;

    //订单数据
    private LocalDateTime created = LocalDateTime.now();
    private BigDecimal totalPrice = BigDecimal.ONE;

    private String prefix = "order_er";

    public long doInsert() {
        try (
                Connection connection = getConnection()
        ) {
            //写头
            final String header_sql = "insert into order(`order_code`, `status`, `sku_list`, `price`, `create_time`) values(?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            long begin = System.currentTimeMillis();
            for (int i = 0 ; i < insertRows; i++) {
                PreparedStatement statement = connection.prepareStatement(header_sql);
                statement.setString(1, prefix + i++);
                statement.setBoolean(2, true);
                statement.setString(3, "13434");
                statement.setBigDecimal(4, totalPrice);
                statement.setObject(5, created);
                statement.executeUpdate();
                statement.close();
            }
            connection.commit();
            return begin;

        } catch (Exception t) {
            t.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    public long doAddBatch() {
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {

            final String header_sql = "insert into order(`order_code`, `status`, `sku_list`, `price`, `create_time`) ";

            connection.setAutoCommit(false);
            long executeTime = System.currentTimeMillis();
            for (int i = 0 ; i < insertRows; i++) {
                StringBuffer sql = new StringBuffer();
                sql.append(header_sql);
                sql.append(" values ('")
                        .append(prefix + i++).append("','")
                        .append("1").append("','")
                        .append("12434").append("','")
                        .append(totalPrice.toString()).append("',")
                        .append("'2020-06-18 18:00:00'").append(");");
                statement.addBatch(sql.toString());
            }
            System.out.printf("prepare data use %d ms", System.currentTimeMillis() - executeTime);
            executeTime = System.currentTimeMillis();
            statement.executeBatch();
            connection.commit();
            return executeTime;
        } catch (Exception t) {
            t.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    public long doAddBatchByStep(int step) {
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            //写头
            final String header_sql = "insert into order(`order_code`, `status`, `sku_list`, `price`, `create_time`) ";

            connection.setAutoCommit(false);
            long executeTime = System.currentTimeMillis();
            for (int i = 0 ; i < insertRows; i = i + step) {
                StringBuffer sql = new StringBuffer();
                sql.append(header_sql);
                sql.append(" values ");
                for (int j = 0 ; j < step ; j++) {
                    sql.append(" ('")
                            .append(prefix + i++).append("','")
                            .append("1").append("','")
                            .append("12434").append("','")
                            .append(totalPrice.toString()).append("',")
                            .append("'2020-06-18 18:00:00'").append("),");

                }
                sql.deleteCharAt(sql.length() - 1);
                statement.addBatch(sql.toString());
            }
            System.out.printf("prepare data use %d ms\n", System.currentTimeMillis() - executeTime);
            executeTime = System.currentTimeMillis();
            statement.executeBatch();
            connection.commit();
            return executeTime;
        } catch (Exception t) {
            t.printStackTrace();
            return System.currentTimeMillis();
        }
    }
}
