package org.cv.sf.dto.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "lin-user")
@Data
public class User {
    @Id
    private long id;
    private String userName;
    private String password;
    private String nickName;
    private String avatar;
    private String email;
    private Date createTime;
    private Date updateTime;
    private Date deletedTime;



}
