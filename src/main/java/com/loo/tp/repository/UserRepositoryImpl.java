package com.loo.tp.repository;

import com.loo.tp.entities.User;
import com.loo.tp.repository.interfaces.UserRepository;

public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
        super(populate());
    }

    public User add(User entity) {
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    public User getByCredentials(String name, String password) {
        for (User user : data) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User changeStatus(long userId, boolean status) {
        for (User user : data) {
            if (user.getId() == userId) {
                user.setActive(status);
                return user;
            }
        }
        return null;
    }

    private static User[] populate() {
        return new User[] {
                new User(1, "Blinky", "qwerty", true),
                new User(1, "Pinky", "dvorak", false),
                new User(1, "Inky", "colemak", false),
                new User(1, "Clyde", "qwerty", false),
        };
    }
}
