package org.cv.sf.dto.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MRoleEntity implements Serializable {

    private static final long serialVersionUID = -8259498290575663756L;

    private Long id;
    private String name;
    private String description;
    private String nmDisplay;
    private Integer flgSystem;
    private Integer status;
    private Integer flgDeleted;
    private Long createBy;
    private Date createTime;
    private Long lastModifiedBy;
    private Date lastModifiedTime;

}
