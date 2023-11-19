package com.loo.tp.repository.interfaces;

import com.loo.tp.entities.User;

public interface UserRepository extends Repository<User> {
    /**
     * Searches an User by comparing credentials.
     * @param name The name of the user.
     * @param password The password of the User.
     * @return If User was found returns User, null otherwise.
     */
    User getByCredentials(String name, String password);

    /**
     * Changes the status of an User to the given status.
     * @param userId The id of the User.
     * @param status The status to set.
     * @return If User with given is found returns user, null otherwise
     */
    User changeStatus(long userId, boolean status);

    /**
     * Changes the status of several Users by their id.
     * @param userIds The ids of the Users.
     * @param status The status to set.
     * @return The number of Users modified.
     */
    long changeStatus(long[] userIds, boolean status);
}
