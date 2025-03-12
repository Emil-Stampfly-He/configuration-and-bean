import org.emil.config.MyConfig;
import org.emil.beanBuilder.BeanDefinition;
import org.emil.pojo.Student;
import org.emil.processors.ConfigurationClassBeanDefinitionReader;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ConfigurationClassBeanDefinitionReaderTest {
    @Test
    public void testGetBeanDefinition() {
        ConfigurationClassBeanDefinitionReader reader = new ConfigurationClassBeanDefinitionReader();
        Map<String, BeanDefinition> beanDefinitions = reader.getBeanDefinition();

        // 验证返回的 Map 不为空
        assertNotNull(beanDefinitions);

        // 根据 MyConfig 中的 student() 方法构成的 beanName
        String expectedKey = "MyConfig#student";
        assertTrue("容器中应该包含 key 为 '" + expectedKey + "' 的 BeanDefinition", beanDefinitions.containsKey(expectedKey));

        BeanDefinition bd = beanDefinitions.get(expectedKey);
        // 验证 beanClass 应该是 student 方法的返回值类型，即 Student.class
        assertEquals("BeanDefinition 的 beanClass 应为 Student.class", Student.class, bd.getBeanClass());
        // 验证 factoryMethod 的名字
        assertEquals("Factory method 名称应为 student", "student", bd.getFactoryMethod().getName());
        // 验证 factoryBean 的类型
        assertEquals("Factory bean 应为 MyConfig 的实例", MyConfig.class, bd.getFactoryBean().getClass());
    }
}
