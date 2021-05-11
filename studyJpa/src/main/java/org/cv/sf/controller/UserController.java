package org.cv.sf.controller;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.cv.sf.dto.ResponseVo;
import org.cv.sf.dto.entity.MUserEntity;
import org.cv.sf.service.PermissionService;
import org.cv.sf.service.UserService;
import org.cv.sf.dto.vo.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 1
     * MissingPathVariableException:
     * Missing URI template variable 'id' for method parameter of type long]
     * <p>
     * 2
     * "message": "could not extract ResultSet; SQL [n/a];
     * nested exception is org.hibernate.exception.SQLGrammarException:
     * could not extract ResultSet",
     * 原因：数据库字段与实体bean属性对不上,user实体没有写完
     * 写完user实体还是报这个错，分析原因时不能映射到结果集
     * 然后看到实体没有set,get方法
     * 猜测：实体类私有化属性却没有提供set,get方法，那么别人怎么操作这个实体的属性
     * 就根本不可能映射查出来的结果
     * 引入lombok
     * 继续报这个错，然后再找问题
     *
     * @Table(name = "lin-user") 看到别名和数据库不一致，改完再试
     * 继续报错，但是换了一个错
     * Unknown column 'user0_.deleted_time' in 'field list'
     * private Date deletedTime; 改成 deleteTime，继续
     * 继续报错
     * Unknown column 'user0_.nick_name' in 'field list'
     * 再检查实体
     * private String nickName;改成 nickname
     * 因为数据库不是nick_name 这种形式，所以不能映射成驼峰式
     * 继续报错
     * Unknown column 'user0_.password' in 'field list'
     * 表里没有这个字段，我在实体内加了，项目启动的时候删了，貌似没用，再启动一次
     * <p>
     * 终于成功了！！！！
     * 太他妈不容易了
     */
    @RequestMapping(value = "/find/{id}")
    @RequiresUser
    public MUserEntity findById(@PathVariable int id) {
        return userService.findById(Long.valueOf(id)).get();
    }

    @RequestMapping(value = "/queryPage")
    public ResponseVo findPageUser(@RequestParam int start,
                                         @RequestParam int counts,
                                         @RequestParam String desc){
        Sort sort = Sort.by(desc);
        Pageable pageable = PageRequest.of(start/counts,counts,sort);
        Page<MUserEntity> pageUser = userService.getPageUser(pageable);
        PageResponse pageResponse = new PageResponse();
        pageResponse.setContent(pageUser.getContent());
        pageResponse.setStart(start);
        return new ResponseVo().success(pageResponse);
    }

    @RequestMapping(value = "/find/last")
    public  MUserEntity findLast(){
        return userService.findLast();
    }

    /**
     * Column 'create_time' cannot be null
     *
     */
    @RequestMapping(value = "/save")
    public String save(@RequestBody MUserEntity user){
        userService.save(user);
        return "保存成功";
    }

    /**
     * 问题出现的原因是使用@PathVariable注解而没有采用rest的写法
     * SPRINGMVC报错MISSING URI TEMPLATE VARIABLE 'ID' FOR METHOD PARAMETER OF TYPE LONG
     *
     */
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam long id){
        userService.deleteById(id);
        return "删除成功";
    }

    @RequestMapping(value = "/permission")
    public ResponseVo queryPermission(int page,int size){
        return new ResponseVo<>().success(permissionService.queryPage(page,size));
    }

}
