package org.cv.sf.service;

import org.cv.sf.repository.jdbc.LogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LogService {

    @Autowired
    private LogDao logDao;

    public Map findById(long id){
        return  logDao.findById(Integer.valueOf(id+""));
    }

    public void save(Map<String,Object> params){
        logDao.insertByNameTemplate(params);
    }
}
