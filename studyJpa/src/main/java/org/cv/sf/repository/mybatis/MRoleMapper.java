package org.cv.sf.repository.mybatis;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MRoleMapper {

    List<String> findRoleNamesByUserId(long userId);
}
