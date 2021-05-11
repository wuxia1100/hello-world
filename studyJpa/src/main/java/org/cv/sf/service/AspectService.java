package org.cv.sf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AspectService {
    @Autowired
    private AnnotationService annotationService;

    public void userAspect(){
        annotationService.save();
        annotationService.saveAspect();
    }

    public void userSave(){
        annotationService.userSave();
    }

    public void save(){
        annotationService.save();
    }
}
