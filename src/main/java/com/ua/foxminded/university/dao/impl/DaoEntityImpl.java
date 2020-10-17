package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.AbstractDaoEntity;
import com.ua.foxminded.university.dao.DaoEntity;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DaoEntityImpl<T>
        extends AbstractDaoEntity<T> implements DaoEntity<T> {

}
