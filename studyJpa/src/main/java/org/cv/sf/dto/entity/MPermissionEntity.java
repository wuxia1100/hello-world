package org.cv.sf.dto.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "m_permission")
public class MPermissionEntity implements Serializable {

    private static final long serialVersionUID = -8679299476983888180L;

    private Long id;
    private String name;
    private String cdPerm;
    private Integer flgMenu;
    private String icon;
    private String nmDisplay;
    private String urlA;
    private Long pid;
    private Integer flgDisplay;
    private Integer flgSystem;
    private String cdSort;
    private Integer flgDeleted;

}
