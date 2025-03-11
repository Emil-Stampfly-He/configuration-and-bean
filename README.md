## Learning `@Configuration` and `@Bean` from Spring framework by building the wheel from zero
1. 找到所有`@Configuration`类 -> `ConfigurationClassPostProcessor`
2. 解析`@Configuration`类，找到`@Bean`方法 -> `ConfigurationClassParser`
3. 为`@Bean`方法生成`Bean`定义对象（`BeanDefinition`） -> `ConfigurationClassBeanDefinitionReader`
4. `BeanFactory`实例化`Bean`对象


