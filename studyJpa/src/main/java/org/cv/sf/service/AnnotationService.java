package org.cv.sf.service;

import org.cv.sf.core.annotation.MyAnnotation;
import org.cv.sf.core.enums.HandelType;
import org.springframework.stereotype.Service;

@Service
public class AnnotationService {

    @MyAnnotation(HandelType.save)
    public void save(){
        System.out.println("这里做了一个保存对象动作");
        saveAspect();
    }

    @MyAnnotation(HandelType.delete)
    public void delete(){};

    public void saveAspect(){
        System.out.println("这里有一个aop方法待处理");
    }

    public void userSave(){
        save();
    }
}
