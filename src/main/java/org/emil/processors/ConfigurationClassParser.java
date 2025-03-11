package org.emil.processors;

import org.emil.annotations.Bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author EmilSt
 *
 * 解析带有@Configuration的类，找到@Bean方法
 */
public class ConfigurationClassParser {

    /**
     * 拿到所有加了@Bean的方法
     * @return 存储了所有@Bean方法的集合
     */
    public List<Method> getMethods() {
        List<Method> methods = new ArrayList<>();
        // 1. 加载所有标记了@Configuration的目标类
        List<Class<?>> classes = ConfigurationClassPostProcessor.getClasses();

        // 2. 获取方法列表
        for (Class<?> clazz : classes) {
            Method[] methodsArray = clazz.getDeclaredMethods();

            for (Method method : methodsArray) {
                if (method.isAnnotationPresent(Bean.class)) {
                    methods.add(method);
                }
            }
        }

        // 3. 筛选@Bean方法
        return methods;
    }
}
