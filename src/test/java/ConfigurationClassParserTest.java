import org.emil.config.MyConfig;
import org.emil.processors.ConfigurationClassParser;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfigurationClassParserTest {
    @Test
    public void testGetMethods() {
        ConfigurationClassParser parser = new ConfigurationClassParser();
        List<Method> beanMethods = parser.getMethods();

        // 断言返回的列表不为空
        assertFalse("The bean methods list should not be empty", beanMethods.isEmpty());

        // 遍历查找 TestConfiguration 中的@Bean方法
        boolean found = false;
        for (Method method : beanMethods) {
            if (method.getDeclaringClass().equals(MyConfig.class)) {
                found = true;
                break;
            }
        }
        assertTrue("MyConfig should contain a @Bean method", found);
    }
}
