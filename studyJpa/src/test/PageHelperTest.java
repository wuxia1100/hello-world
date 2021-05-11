
import com.github.pagehelper.PageInfo;
import org.cv.sf.dto.entity.MPermissionEntity;
import org.cv.sf.service.PermissionService;
import org.cv.sf.studyJpaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = studyJpaApplication.class)
public class PageHelperTest {
    @Autowired
    private PermissionService permissionService;

    @Test
    public void findPage() {
        //分页插件开启
        //PageHelper.startPage(1,2);
        //紧跟着的下一个list查询生效，下面还有list查询分页不再生效
        PageInfo<MPermissionEntity> list =  permissionService.queryPage(1,2);
        /*
        new PageInfo<MPermissionEntity>(list);
        for(MPermissionEntity o:list){
            System.out.println("permission :"+o.toString());
        }*/
    }
}
