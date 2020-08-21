package com.mthree.bsm.repository;

import com.mthree.bsm.entity.User;

import java.util.List;

/**
 * Repository for accessing {@link User}s in the system.
 */
public interface UserRepository {

    /**
     * Gets a list of all users in the system.
     */
    List<User> getUsers();

}