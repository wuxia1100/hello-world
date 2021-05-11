package org.cv.sf.service;

import org.cv.sf.repository.mybatis.MRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private MRoleMapper mRoleMapper;

    public List<String> findRoleNamesByUserId(long userId){
        return mRoleMapper.findRoleNamesByUserId(userId);
    }
}
