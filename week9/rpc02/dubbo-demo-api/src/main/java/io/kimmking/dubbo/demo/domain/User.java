package io.kimmking.dubbo.demo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 *
 * @author <a href="mailto:chan@ittx.com.cn">韩超</a>
 * @version 2021.07.06
 */
@Data
public class User implements Serializable {

    private Long id;
    private String code;
    private String name;

}
