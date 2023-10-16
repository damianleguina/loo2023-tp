package repositories.interfaces;

import entities.User;

public interface UserRepository extends Repository<User> {
    User getByCredentials(String name, String password);
}
