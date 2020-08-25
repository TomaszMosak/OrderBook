/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.three.bsm.service;

import com.mthree.bsm.entity.User;
import com.mthree.bsm.repository.UserDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tombarton
 */
public class DataUserService implements UserService {
    
    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUsers() {
       return userDao.getUsers();
    }
    
}
