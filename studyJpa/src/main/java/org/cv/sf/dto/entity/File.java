package org.cv.sf.dto.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "lin_file")
@Data
public class File implements Serializable {
    private static final long serialVersionUID = 1972887146675673025L;
    @Id
    private long id;
    private String path;
    private String type;
    private String name;
    private String extension;
    private int size;
    private String md5;

    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;

    public String getString(){
        return this.md5;
    }

    public void setString(){
        this.md5 = "123";
    }



}
