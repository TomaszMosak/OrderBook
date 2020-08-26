/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.service;

import com.mthree.bsm.entity.User;
import com.mthree.bsm.repository.UserDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author tombarton
 */
@Service
public class DataUserService implements UserService {
    
    UserDao userDao;

    @Autowired
    public DataUserService(@Qualifier("databaseUserDao") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsers() {
       return userDao.getUsers();
    }
    
}
