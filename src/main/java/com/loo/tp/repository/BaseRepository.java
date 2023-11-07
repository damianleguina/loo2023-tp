package com.loo.tp.repository;

import com.loo.tp.entities.Entity;
import com.loo.tp.repository.interfaces.Repository;

public abstract class BaseRepository<T extends Entity> implements Repository<T> {
    protected final T[] data;
    private long autogeneratedId;

    protected BaseRepository(T[] data) {
        autogeneratedId = 1;
        this.data = data;
        for (int i = 0; i < data.length; i++) {
            T t = data[i];
            if (t == null) {
                continue;
            }
            t.setId(autogeneratedId);
            autogeneratedId++;
        }
        this.autogeneratedId = data.length;
    }

    public T[] get() {
        return data;
    }

    public T getById(long id) {
        for (T t : data) {
            if (t != null && t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public T add(T t) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                t.setId(autogeneratedId);
                data[i] = t;
                autogeneratedId++;
                return t;
            }
        }
        return null;
    }

    public T update(T t) {
        for (int i = 0; i < data.length; i++) {
            if (data[i].getId() == t.getId()) {
                data[i] = t;
            }
        }
        return null;
    }

    public boolean delete(long id) {
        for (int i = 0; i < data.length; i++) {
            if (data[i].getId() == id) {
                data[i] = null;
                return true;
            }
        }
        return false;
    }
}
