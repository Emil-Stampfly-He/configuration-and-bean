import org.emil.beanBuilder.BeanFactory;
import org.emil.pojo.Student;
import org.junit.Test;

public class BeanFactoryTest {
    @Test
    public void testBeanFactory() {
        // 创建 BeanFactory 实例
        BeanFactory beanFactory = new BeanFactory();

        // 通过 beanName "exampleBean" 获取实例
        Student studentBean = (Student) beanFactory.createBean("MyConfig#student");
        System.out.println("获取到的 ExampleBean 实例：" + studentBean);
    }
}
