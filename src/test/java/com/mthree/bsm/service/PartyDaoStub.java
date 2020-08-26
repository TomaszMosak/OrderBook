package com.mthree.bsm.service;

import com.mthree.bsm.entity.Party;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.PartyDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PartyDaoStub implements PartyDao {

    /**
     * Gets an party in the system with a given ID. If there is no party in the system with the given ID, the returned
     * {@link Optional} will not be present.
     *
     * @param partyId
     */
    @Override
    public Optional<Party> getPartyById(int partyId) {
        return Optional.empty();
    }

    /**
     * Gets a list of all parties in the system.
     */
    @Override
    public List<Party> getParties() {
        return null;
    }

    /**
     * Deletes all parties in the system, returning them in a list.
     */
    @Override
    public List<Party> deleteParties() {
        return null;
    }

    /**
     * Adds a party to the system, validating it, and assigning it a new ID (no matter what ID the passed user has),
     * returning it back.
     *
     * @param party
     *
     * @throws InvalidEntityException when the given party is invalid.
     */
    @Override
    public Party addParty(Party party) throws InvalidEntityException {
        return null;
    }

}
