package org.cv.sf.repository.hibernate;

import org.cv.sf.dto.entity.Articles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class ArticlesDao {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void save(Articles articles){
        entityManager.persist(articles);
    }}
