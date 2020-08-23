package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Party;
import com.mthree.bsm.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing {@link User}s in the system.
 */
public interface UserDao {

    /**
     * Gets a user in the system with a given ID. If there is no user in the system with the given ID, the returned
     * {@link Optional} will not be present.
     */
    Optional<User> getUserById(int userId);

    /**
     * Gets a list of all users in the system.
     */
    List<User> getUsers();

    /**
     * Deletes all trades in the system, returning them in a list.
     */
    List<User> deleteUsers();

    /**
     * Adds a user to the system, validating it, and assigning it a new ID (no matter what ID the passed user has),
     * returning it back.
     *
     * @throws InvalidEntityException when the given user is invalid.
     */
    User addUser(User user) throws InvalidEntityException;

}
