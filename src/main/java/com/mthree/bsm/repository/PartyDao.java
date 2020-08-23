package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Party;
import com.mthree.bsm.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing {@link Party}s in the system.
 */
public interface PartyDao {

    /**
     * Gets an party in the system with a given ID. If there is no party in the system with the given ID, the returned
     * {@link Optional} will not be present.
     */
    Optional<Party> getPartyById(int partyId);

    /**
     * Gets a list of all parties in the system.
     */
    List<Party> getParties();

    /**
     * Deletes all parties in the system, returning them in a list.
     */
    List<Party> deleteParties();

    /**
     * Adds a party to the system, validating it, and assigning it a new ID (no matter what ID the passed user has),
     * returning it back.
     *
     * @throws InvalidEntityException when the given party is invalid.
     */
    Party addParty(Party party) throws InvalidEntityException;

}
