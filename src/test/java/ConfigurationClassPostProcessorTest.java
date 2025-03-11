import org.emil.processors.ConfigurationClassPostProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ConfigurationClassPostProcessorTest {
    @Test
    public void testGetClasses() {
        List<Class<?>> classes = ConfigurationClassPostProcessor.getClasses();

        boolean foundTestConfiguration = false;
        boolean foundNotAConfiguration = false;

        for  (Class<?> clazz : classes) {
            if (clazz.getName().equals("org.emil.config.MyConfig")) {
                foundTestConfiguration = true;
            }

            if (clazz.getName().equals("org.emil.pojo.MyNotConfig")) {
                foundNotAConfiguration = true;
            }
        }

        Assert.assertTrue("MyConfig should be found", foundTestConfiguration);
        Assert.assertFalse("MyNotConfig should not be found", foundNotAConfiguration);
    }
}
