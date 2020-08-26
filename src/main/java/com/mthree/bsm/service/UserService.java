/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.service;

import com.mthree.bsm.entity.User;
import java.util.List;

/**
 *
 * @author tombarton
 */
public interface UserService {

    /**
     * Gets a list of all {@link User}s in the system.
     */
    List<User> getUsers();
    
}
