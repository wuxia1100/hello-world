package org.cv.sf.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cv.sf.dto.entity.MPermissionEntity;
import org.cv.sf.repository.mybatis.MPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private MPermissionMapper permissionMapper;


    public List<String> findPermissionNamesByUserId(long userId){
        return permissionMapper.findPermissionNamesByUserId(userId);
    }
    public PageInfo<MPermissionEntity> queryPage(int page, int size){
        PageHelper.startPage(page,size);
        List<MPermissionEntity> varList = permissionMapper.findPagePermission();
        return new PageInfo<MPermissionEntity>(varList);
    }
/*
    public List<MPermissionEntity> queryPage(int page,int size){
        return permissionMapper.findPagePermission();
    }*/

}
