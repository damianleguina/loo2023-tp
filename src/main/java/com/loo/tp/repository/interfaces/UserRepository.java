package com.loo.tp.repository.interfaces;

import com.loo.tp.entities.User;

public interface UserRepository extends Repository<User> {
    User getByCredentials(String name, String password);
    User changeStatus(long userId, boolean status);
}
