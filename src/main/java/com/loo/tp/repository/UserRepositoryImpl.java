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

    @Override
    public User[] get() {
        int aux = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                aux++;
            }
        }
        User[] result = new User[aux];
        aux = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                result[aux] = data[i];
                aux++;
            }
        }
        return result;
    }

    public User getByCredentials(String name, String password) {
        for (User user : data) {
            if (user != null && user.getName().equals(name) && user.getPassword().equals(password)) {
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
        var users = new User[20];
        users[0] = new User(0, "Blinky", "qwerty", true);
        users[1] = new User(0, "Pinky", "dvorak", false);
        users[2] = new User(0, "Inky", "colemak", false);
        users[3] = new User(0, "Clyde", "qwerty", false);
        return users;
    }
}
