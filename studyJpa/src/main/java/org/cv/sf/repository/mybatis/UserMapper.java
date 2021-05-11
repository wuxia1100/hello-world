package org.cv.sf.repository.mybatis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * mybatis 注解开发
 * 1 在类上打上mapper注解
 */
@Mapper
public interface UserMapper {

    @Delete("delete from lin_user where id = #{id}")
    int deleteByPrimaryKey(Integer id);

}
