package com.geektime.week8.sharding_sphere.xa_worker2.batchXAService;

import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @author 吴振
 * @since 2021/11/14 下午11:48
 */
@Service
public class XAService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public XAService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void process() {
        TransactionTypeHolder.set(TransactionType.XA);
        this.jdbcTemplate.execute(this::batchInsertAndPrint);
    }

    /**
     * 批量插入数据到多个数据库，然后全部查询出来
     * @return
     */
    public Boolean batchInsertAndPrint(Connection connection) {

        final String header_sql = "insert into order(`order_code`, `status`, `sku_list`, `price`, `create_time`) values(?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(header_sql)) {
            connection.setAutoCommit(false);
            for (int i = 0; i < 5 ; i++) {
                preparedStatement.setObject(1, "order_fd");
                preparedStatement.setObject(2, true);
                preparedStatement.setObject(3, "1243,2343,343");
                preparedStatement.setObject(4, BigDecimal.ONE);
                preparedStatement.setObject(5, LocalDateTime.now());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
        }

    }
}
