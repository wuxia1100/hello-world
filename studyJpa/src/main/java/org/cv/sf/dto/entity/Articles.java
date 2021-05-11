package org.cv.sf.dto.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "articles")
public class Articles implements Serializable {

    private static final long serialVersionUID = 5466018400044094131L;
    @Id
    @Column(name = "article_id")
    private int articleId;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category;
}
