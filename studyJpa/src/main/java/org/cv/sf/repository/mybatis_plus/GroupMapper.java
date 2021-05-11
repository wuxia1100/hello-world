package org.cv.sf.repository.mybatis_plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cv.sf.dto.entity.Group;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * mybatis-plus 集成
 * 1 集成BaseMapper
 * 2 实体类的规范
 * 3 调用BaseMapper内实现的方法
 */
@Repository
public interface GroupMapper extends BaseMapper<Group> {

    Group selectById(@Param("id") Long id);
}
