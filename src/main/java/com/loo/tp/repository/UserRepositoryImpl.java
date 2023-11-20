package com.loo.tp.repository;

import com.loo.tp.entities.User;
import com.loo.tp.repository.interfaces.UserRepository;
import com.loo.tp.utils.ArrayUtils;

public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
        super(populate());
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
            if (user != null && user.getId() == userId) {
                user.setActive(status);
                return user;
            }
        }
        return null;
    }

    @Override
    public long changeStatus(long[] userIds, boolean status) {
        int result = 0;
        for (User user : data) {
            if (user != null && ArrayUtils.contains(userIds, user.getId())) {
                user.setActive(status);
                result++;
            }
        }
        return result;
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
