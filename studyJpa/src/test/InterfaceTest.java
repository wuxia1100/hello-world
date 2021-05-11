import org.cv.sf.core.model.interfaces.AnimalInterface;
import org.cv.sf.core.model.interfaces.BaseInterface;
import org.cv.sf.core.model.interfaces.DogInterface;
import org.cv.sf.core.model.interfaces.impl.HaShiQI;
import org.cv.sf.studyJpaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = studyJpaApplication.class)
public class InterfaceTest {

    @Test
    public void test1(){
        DogInterface d = new HaShiQI();
        AnimalInterface a = new HaShiQI();
        BaseInterface b = new HaShiQI();

        System.out.println(d.getString("哈士奇 DogInterface"));
        System.out.println(a.getString("哈士奇 AnimalInterface"));
        System.out.println(b.getString("哈士奇 BaseInterface"));

        System.out.println(d.getString("哈士奇 DogInterface",1));
        System.out.println(a.getString("哈士奇 AnimalInterface",2));

        System.out.println(d.getString("哈士奇 DogInterface",2,"傻子"));
    }
}
