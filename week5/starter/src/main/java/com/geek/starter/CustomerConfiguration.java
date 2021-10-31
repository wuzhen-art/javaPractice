package com.geek.starter;

import com.geek.starter.bean.Klass;
import com.geek.starter.bean.School;
import com.geek.starter.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴振
 * @since 2021/11/1 上午1:02
 */
@Configuration
@EnableConfigurationProperties(TestProperties.class)
public class CustomerConfiguration {

    @Autowired
    TestProperties testProperties;

    @ConditionalOnProperty(prefix = "test", name = "id")
    @Bean("testStudent")
    public Student student(){
        Student student = new Student();
        student.setId(testProperties.getId());
        student.setName(testProperties.getValue());
        return student;
    }

    @ConditionalOnBean(Student.class)
    @Bean
    @Primary
    public Klass klass(){
        Klass klass = new Klass();
        List<Student> students = new ArrayList<>();
        students.add(student());
        klass.setStudents(students);
        return klass;
    }

}