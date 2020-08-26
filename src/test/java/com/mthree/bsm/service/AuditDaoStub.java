/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.service;

import com.mthree.bsm.repository.AuditDao;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 *
 * @author tombarton
 */
@Repository
public class AuditDaoStub implements AuditDao {

    @Override
    public void writeMessage(String message) throws IOException {
        //do nothing
    }
    
}
