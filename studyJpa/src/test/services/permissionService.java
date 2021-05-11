package services;


import org.cv.sf.repository.mybatis.MPermissionMapper;
import org.cv.sf.repository.mybatis.MRoleMapper;
import org.cv.sf.studyJpaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = studyJpaApplication.class)
public class permissionService {
    @Autowired
    private MPermissionMapper permissionMapper;
    @Autowired
    private MRoleMapper mRoleMapper;

    @Test
    public void find(){
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(2l);
        roleIds.add(3l);
        for(String str:permissionMapper.findPermissionNamesByUserId(1)){
            System.out.println("permissionNames :"+str);
        }
        for(String str:mRoleMapper.findRoleNamesByUserId(1)){
            System.out.println("roleName :"+str);
        }
    }
}
