package com.geek.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 吴振
 * @since 2021/11/1 上午1:04
 */
@Data
@ConfigurationProperties("test")
public class TestProperties {
    private int id;

    private String value;
}
