package rpc.user.service;

import io.kimmking.dubbo.demo.api.UserService;
import io.kimmking.dubbo.demo.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import rpc.user.autoconfigure.DataSourceContainer;

import java.util.List;

/**
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final JdbcTemplate jdbcTemplate;

    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getById(Long id) {
        final String sql = "select * from user where id = ?";
        DataSourceContainer.setId(id);
        List<User> userList =  this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class), id);
        if (CollectionUtils.isEmpty(userList))
            return null;

        return userList.get(0);
    }
}
