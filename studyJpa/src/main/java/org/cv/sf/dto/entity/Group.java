package org.cv.sf.dto.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("lin_group")
public class Group extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -5568324661195156062L;

    private Long id;
    private String name;
    private String info;
    private String level;

}
