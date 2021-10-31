package com.geek.starter.bean;

import lombok.Data;

import java.util.List;

/**
 * @author 吴振
 * @since 2021/11/1 上午12:57
 */
@Data
public class Klass {

    List<Student> students;

    public void dong(){
        System.out.println(this.getStudents());
    }

}
