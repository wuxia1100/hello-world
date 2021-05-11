package org.cv.sf.dto.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MRolePermissionEntity implements Serializable {
    private static final long serialVersionUID = -9184486234631467365L;

    private Long id;
    private Long roleId;
    private Long permissionId;
    private Date createTime;
    private Date lastModifiedTime;
}
