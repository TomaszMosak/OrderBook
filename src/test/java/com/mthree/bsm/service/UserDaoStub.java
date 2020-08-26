package com.mthree.bsm.service;

import com.mthree.bsm.entity.Order;
import com.mthree.bsm.entity.User;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.UserDao;
import org.springframework.stereotype.Repository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDaoStub implements UserDao {

    private List<User> users = new ArrayList<>();

    /**
     * Gets a user in the system with a given ID. If there is no user in the system with the given ID, the returned
     * {@link Optional} will not be present.
     *
     * @param userId
     */
    @Override
    public Optional<User> getUserById(int userId) {
        return users.stream()
                    .filter(user -> user.getId() == userId)
                    .findAny();
    }

    /**
     * Gets a list of all users in the system.
     */
    @Override
    public List<User> getUsers() {
        return users;
    }

    /**
     * Deletes all trades in the system, returning them in a list.
     */
    @Override
    public List<User> deleteUsers() {
        List<User> oldUsers = new ArrayList<>(users);
        users = new ArrayList<>();

        return oldUsers;
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
        int newUserId = users.stream()
                             .map(User::getId)
                             .max(Comparator.naturalOrder())
                             .map(id -> id + 1)
                             .orElse(1);
        user.setId(newUserId);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new InvalidEntityException(violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList()));
        }

        users.add(user);

        return user;
    }

}
