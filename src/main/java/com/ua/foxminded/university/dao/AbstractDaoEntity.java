package com.ua.foxminded.university.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;




public abstract class AbstractDaoEntity<T> {
    private Class<T> entity;

    @Autowired
    private SessionFactory sessionFactory;


    public void setEntity(Class<T> entityToSet) {
        entity = entityToSet;
    }


    public T getById(long id) {
        return getCurrentSession().get(entity, id);
    }

    public List<T> getAll() {
        return getCurrentSession()
                .createQuery("from " + entity.getName()).list();
    }

    public void create(T entity) {
        getCurrentSession().persist(entity);
    }

    public T update(T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(long id) {
        final T entity = getById(id);
        delete(entity);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


}