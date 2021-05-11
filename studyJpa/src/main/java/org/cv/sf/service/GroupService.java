package org.cv.sf.service;

import org.cv.sf.dto.entity.Group;
import org.cv.sf.repository.mybatis_plus.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupMapper groupMapper;

    public Group findById(long id){
        return groupMapper.selectById(id);
    }
}
