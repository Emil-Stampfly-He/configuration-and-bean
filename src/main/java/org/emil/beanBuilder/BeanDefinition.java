package org.emil.beanBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeanDefinition {
    private Object factoryBean; // 配置类的实例，即@Configuration类的实例
    private Method factoryMethod; // 创建Bean的工厂方法
    private Class<?> beanClass; // @Bean方法生成的返回的Bean类型，也就是方法的返回值类型
    private String beanName;
    private boolean singleton = false; // Bean是否是单例
}
