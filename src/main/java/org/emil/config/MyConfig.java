package org.emil.config;

import org.emil.annotations.Bean;
import org.emil.annotations.Configuration;
import org.emil.pojo.Student;

@Configuration
public class MyConfig {

    @Bean
    public Student getStudent(){
        return new Student();
    }
}