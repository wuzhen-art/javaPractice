package com.geek.mq;

import java.math.BigDecimal;

/**
 * @author 吴振
 * @since 2021/12/18 下午3:31
 */
public class TestMAth {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(1);
        System.out.println(bigDecimal.divide(new BigDecimal(100), 2, 1));
    }
}
