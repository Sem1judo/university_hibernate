package com.ua.foxminded.university.dao;


import java.util.List;

public interface DaoEntity<T> {

    T getById(final long id);

    List<T> getAll();

    void create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final long entityId);
}
