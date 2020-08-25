/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.three.bsm.service;

import com.mthree.bsm.entity.Party;
import com.mthree.bsm.repository.PartyDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tombarton
 */
public class DataPartyService implements PartyService {
    
    @Autowired
    PartyDao partyDao;

    @Override
    public List<Party> getParties() {
        return partyDao.getParties();
    }
}
