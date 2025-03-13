package org.emil.processors;

import org.emil.beanBuilder.BeanDefinition;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EmilSt
 *
 * 为@Bean方法生成Bean定义对象（BeanDefinition）
 * 并将这些BeanDefinition注册到一个容器中
 */
public class ConfigurationClassBeanDefinitionReader {
    private final Map<String, BeanDefinition> beanContainer = new HashMap<>();

    public Map<String, BeanDefinition> getBeanDefinition() {
        try {
            // 1. 获取所有@Bean方法
            ConfigurationClassParser configurationClassParser = new ConfigurationClassParser();
            List<Method> methods = configurationClassParser.getMethods();

            // 2. 获取所有@Configuration类
            List<Class<?>> classes = ConfigurationClassPostProcessor.getClasses();

            for (Class<?> clazz : classes) {
                // 3. 创建@Configuration类实例
                // 同一个@Configuration类的所有方法共享一个实例
                Object configInstance = clazz.getDeclaredConstructor().newInstance();

                // 4. 设置BeanDefinition的字段
                for (Method method : methods) {
                    // 4.1 如果@Bean方法不属于当前@Configuration类，则不予处理
                    if (!method.getDeclaringClass().equals(clazz)) {
                        continue;
                    }

                    BeanDefinition beanDefinition = new BeanDefinition();
                    beanDefinition.setBeanClass(method.getReturnType());
                    beanDefinition.setBeanName(clazz.getSimpleName() + "#" + method.getName());
                    beanDefinition.setFactoryMethod(method);
                    beanDefinition.setFactoryBean(configInstance);
                    beanDefinition.setSingleton(true);

                    // 5. 往容器中注册BeanDefinition
                    beanContainer.put(beanDefinition.getBeanName(), beanDefinition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return beanContainer;
    }
}
