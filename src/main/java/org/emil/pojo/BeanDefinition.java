package org.emil.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
public class BeanDefinition {
    private Object factoryBean;
    private Method factoryMethod;
    private Class<?> beanClass;
    private String beanName;
}
