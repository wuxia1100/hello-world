package org.cv.sf.service;

import org.cv.sf.core.exception.check.HandelException;
import org.cv.sf.dto.entity.File;
import org.cv.sf.repository.mybatis.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    public File findById(int id) throws HandelException {
        File file = fileMapper.findById(id);
        if(file == null){
            throw new HandelException();
        }
        return file;
    }

}
