package repositories;

import entities.User;
import repositories.interfaces.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private final User[] data;

    public UserRepositoryImpl() {
        data = new User[4];
        data[0] = new User(1, "Blinky", "qwerty", true);
        data[1] = new User(1, "Pinky", "dvorak", false);
        data[2] = new User(1, "Inky", "colemak", false);
        data[3] = new User(1, "Clyde", "qwerty", false);
    }

    @Override
    public User getById(long id) {
        for (User user : data) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User add(User entity) {
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(long id) {
        for (User user : data) {
            if (user.getId() == id) {
                user.setActive(false);
                return true;
            }
        }
        return false;
    }

    @Override
    public User getByCredentials(String name, String password) {
        for (User user : data) {
            if (user.getName() == name && user.getPassword() == password) {
                return user;
            }
        }
        return null;
    }
}
