import org.cv.sf.core.annotation.MyAnnotationTracker;
import org.cv.sf.service.AnnotationService;
import org.cv.sf.service.AspectService;
import org.cv.sf.studyJpaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = studyJpaApplication.class)
public class AnnotationTest {

    @Autowired
    private AnnotationService annotationService;
    @Autowired
    private MyAnnotationTracker myAnnotationTracker;
    @Autowired
    private AspectService aspectService;

    @Test
    public void save(){
        //这样调用可以触发aop
        //aspectService.userAspect();
        //这样调用只能触发一个aop，就是上个对象直接调用的aop切点
        aspectService.save();
        //后面两种调用方式，触发不了aop
        //annotationService.userSave();
        //aspectService.userSave();
        //现象：后面两种调用，是对象里的方法，调用aop的切点，在同一个类中
        //第一种是对象的方法调用aop的切点，在两个对象中
        //总结aop是针对于方面，层，从一个对象到另一个对象，可以抽象成一个层到另一个层
        //但是如果只是一个对象，aop不认为它有层？不懂，只是猜测
        //myAnnotationTracker.tracker(AnnotationService.class);
    }
}
