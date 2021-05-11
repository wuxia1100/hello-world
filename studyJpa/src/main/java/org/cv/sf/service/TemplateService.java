package org.cv.sf.service;

import org.cv.sf.core.exception.run.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor=Exception.class)
public class TemplateService {

    @Transactional(rollbackFor= BusinessException.class)
    public void getString(){

    }
}
