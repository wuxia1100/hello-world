package org.cv.sf.dto.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MUserRoleEntity implements Serializable {
    private static final long serialVersionUID = -5039495089861111986L;

    private Long id;
    private Long userId;
    private Long roleId;
    private Long createBy;
    private Date createTime;
    private Long lastModifiedBy;
    private Date lastModifiedTime;

}
