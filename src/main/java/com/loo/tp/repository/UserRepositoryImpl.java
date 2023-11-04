package com.loo.tp.repository;

import com.loo.tp.entities.User;
import com.loo.tp.repository.interfaces.UserRepository;

public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
        super(new User[] {
                new User(1, "Blinky", "qwerty", true),
                new User(1, "Pinky", "dvorak", false),
                new User(1, "Inky", "colemak", false),
                new User(1, "Clyde", "qwerty", false),
        });
    }

    public User add(User entity) {
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    public boolean delete(long id) {
        for (User user : data) {
            if (user.getId() == id) {
                user.setActive(false);
                return true;
            }
        }
        return false;
    }

    public User getByCredentials(String name, String password) {
        for (User user : data) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
