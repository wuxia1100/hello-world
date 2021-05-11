package org.cv.sf.dto.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "lin_user")
@Data
public class MUserEntity implements Serializable {
    private static final long serialVersionUID = 2319707475774538276L;

    @Id
    private long id;
    private String username;
    private String nickname;
    private String avatar;
    private String email;

    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Date deleteTime;



}
