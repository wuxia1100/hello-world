import org.cv.sf.repository.jdbc.LogDao;
import org.cv.sf.service.LogService;
import org.cv.sf.studyJpaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = studyJpaApplication.class)
public class JDBCTest {

    @Autowired
    private LogService logService;

    @Autowired
    private LogDao logDao;
    @Test
    public void test1(){
        System.out.println("1111");
        System.out.println(logService.findById(1));
        //logService.findById(1);
    }

    @Test
    public void insert(){
        logDao.insert();
    }

    @Test
    public void findByIdN(){
        System.out.println("findByIdN:");
        System.out.println(logDao.findByIdN());
    }


    @Test
    public void insertN(){
        //logDao.insertByNameTemplate();
    }
}
