package org.cv.sf.repository.mybatis;

import org.cv.sf.dto.entity.File;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMapper {


    File findById(@Param("id")int id);
}
