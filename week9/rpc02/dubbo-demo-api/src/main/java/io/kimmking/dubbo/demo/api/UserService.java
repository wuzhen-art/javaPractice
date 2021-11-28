package io.kimmking.dubbo.demo.api;


import io.kimmking.dubbo.demo.domain.User;

/**
 */
public interface UserService {

    User getById(Long id);

}
