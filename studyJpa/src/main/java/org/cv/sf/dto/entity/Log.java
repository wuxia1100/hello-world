package org.cv.sf.dto.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Log extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6888792283054158153L;

    private Long id;
    private String message;
    private Integer userId;
    private String username;
    private Integer statusCode;
    private String method;
    private String path;
    private String permission;


}
