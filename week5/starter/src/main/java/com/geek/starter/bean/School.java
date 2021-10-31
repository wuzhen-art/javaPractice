package com.geek.starter.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 吴振
 * @since 2021/11/1 上午12:57
 */
@Data
public class School implements ISchool {
    @Autowired
    Klass kclass;

    @Autowired
    Student student;

    @Override
    public void test(){

        System.out.println(this.kclass.getStudents().size() + " students and one is " + this.student);

    }

}

