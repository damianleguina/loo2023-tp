package repositories.interfaces;

import entities.Entity;

public interface Repository<T extends Entity> {
    T getById(long id);
    T add(T entity);
    T update(T entity);
    boolean delete(long id);
}
