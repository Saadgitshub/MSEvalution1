package com.project.evaljava.dao;

import java.util.List;

public interface IDao<T> {
    T add(T t);
    T update(T t);
    void delete(T t);
    T findById(int id);
    List<T> findAll();
}

