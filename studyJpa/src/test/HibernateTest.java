import org.cv.sf.dto.entity.Articles;
import org.cv.sf.repository.hibernate.ArticlesDao;
import org.cv.sf.studyJpaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = studyJpaApplication.class)
public class HibernateTest {

    @Autowired
    private ArticlesDao userHiDao;

    @Test
    public void save(){
        Articles u = new Articles();
        //u.setArticleId(5);
        u.setCategory("afas");
        u.setTitle("asdfasdf");
        userHiDao.save(u);
    }
}
