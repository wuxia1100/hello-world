package org.cv.sf.repository.mybatis;

import org.cv.sf.dto.entity.MPermissionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MPermissionMapper {

    /**
     */
    List<String> findPermissionNamesByRoleId(List<Long> roleIds);

    List<String> findPermissionNamesByUserId(long userId);

    List<MPermissionEntity> findPagePermission();
}
