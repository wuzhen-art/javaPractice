package com.geektime.week8.sharding_sphere.crud_homework_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 吴振
 * @since 2021/11/15 上午12:15
 */
@Service
public class OrderService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Object[] insertData = {"orderdf",true,"123,456", BigDecimal.ONE,  LocalDateTime.now()};

    public int insert() {

        final String header_sql = "insert into order(`order_code`, `status`, `sku_list`, `price`, `create_time`) values(?,?,?,?,?,?)";
        return this.jdbcTemplate.update(header_sql, insertData);
    }

    public List<Map<String, Object>> findById(long id) {
        return this.jdbcTemplate.queryForList("select * from order where id = ?", id);
    }

    public int update(Map<String, Object> updateValues, long id) {
        if (updateValues.isEmpty())
            return 0;
        StringBuffer sql = new StringBuffer();
        List<Object> objects = new LinkedList<>();
        sql.append("update order_header set ");
        updateValues.forEach(
                (key, value)->{
                    sql.append(key).append(" = ?,");
                    objects.add(value);
                }
        );
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" WHERE id = ?");
        objects.add(id);
        return this.jdbcTemplate.update(sql.toString(), objects.toArray());
    }

    public void delete(long id) {
        this.jdbcTemplate.update("delete from order where id = ?", id);
    }
}