package org.emil.beanBuilder;

import org.emil.processors.ConfigurationClassBeanDefinitionReader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author EmilSt
 *
 * 基于BeanDefinition创建Bean的工厂类
 */
public class BeanFactory {
    private final Map<String, Object> singletonBeanContainer = new HashMap<>();

    /**
     * 根据BeanDefinition创建Bean
     * @param beanName Bean的名字
     * @return Bean
     */
    public Object createBean(String beanName) {
        ConfigurationClassBeanDefinitionReader reader = new ConfigurationClassBeanDefinitionReader();
        Map<String, BeanDefinition> beanDefinitionContainer = reader.getBeanDefinition();

        // 1. 检查缓存是否已经创建过Bean
        if (singletonBeanContainer.containsKey(beanName)) {
            // 2. 如果创建过，则直接返回这个Bean
            return singletonBeanContainer.get(beanName);
        }

        // 3. 进行Bean的实例化
        // 3.1 获取beanDefinition
        BeanDefinition beanDefinition = beanDefinitionContainer.get(beanName);
        if (beanDefinition == null) {
            throw new RuntimeException("No BeanDefinition found for " + beanName);
        }

        // 3.2 获取工厂方法和配置类实例
        Method factoryMethod = beanDefinition.getFactoryMethod();
        factoryMethod.setAccessible(true);
        Object configInstance = beanDefinition.getFactoryBean();

        // 3.3 处理参数解析
        Object beanInstance = null;
        try {
            Class<?>[] parameterTypes = factoryMethod.getParameterTypes();
            Object[] args = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                args[i] = createBean(parameterTypes[i].getName());
            }

            beanInstance = factoryMethod.invoke(configInstance, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. 缓存单例实例
        singletonBeanContainer.put(beanName, beanInstance);

        return beanInstance;
    }
}
