package com.mthree.bsm.service;

import com.mthree.bsm.entity.User;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.UserDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoStub implements UserDao {

    /**
     * Gets a user in the system with a given ID. If there is no user in the system with the given ID, the returned
     * {@link Optional} will not be present.
     *
     * @param userId
     */
    @Override
    public Optional<User> getUserById(int userId) {
        return Optional.empty();
    }

    /**
     * Gets a list of all users in the system.
     */
    @Override
    public List<User> getUsers() {
        return null;
    }

    /**
     * Deletes all trades in the system, returning them in a list.
     */
    @Override
    public List<User> deleteUsers() {
        return null;
    }

    /**
     * Adds a user to the system, validating it, and assigning it a new ID (no matter what ID the passed user has),
     * returning it back.
     *
     * @param user
     *
     * @throws InvalidEntityException when the given user is invalid.
     */
    @Override
    public User addUser(User user) throws InvalidEntityException {
        return null;
    }

}
