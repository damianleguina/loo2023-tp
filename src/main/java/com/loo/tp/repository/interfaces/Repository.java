package com.loo.tp.repository.interfaces;

import com.loo.tp.entities.Entity;

public interface Repository<T extends Entity> {
    T getById(long id);
    T add(T entity);
    T update(T entity);
    boolean delete(long id);
}
