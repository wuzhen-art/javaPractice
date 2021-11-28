package io.kimmking.dubbo.demo.api;

import io.kimmking.dubbo.demo.domain.Account;

import java.math.BigDecimal;

/**
 * @version 2021.07.06
 */
public interface AccountService {
    boolean transfer(Long fromUserId, Long toUserId, BigDecimal totalQty);

    Account getByUserId(Long userId);

}
