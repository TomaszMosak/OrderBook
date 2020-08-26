package com.mthree.bsm.service;

import com.mthree.bsm.entity.Party;
import com.mthree.bsm.entity.User;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.PartyDao;
import org.springframework.stereotype.Repository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PartyDaoStub implements PartyDao {

    private List<Party> parties = new ArrayList<>();

    /**
     * Gets an party in the system with a given ID. If there is no party in the system with the given ID, the returned
     * {@link Optional} will not be present.
     *
     * @param partyId
     */
    @Override
    public Optional<Party> getPartyById(int partyId) {
        return parties.stream()
                      .filter(user -> user.getId() == partyId)
                      .findAny();
    }

    /**
     * Gets a list of all parties in the system.
     */
    @Override
    public List<Party> getParties() {
        return parties;
    }

    /**
     * Deletes all parties in the system, returning them in a list.
     */
    @Override
    public List<Party> deleteParties() {
        List<Party> oldParties = new ArrayList<>(parties);
        parties = new ArrayList<>();

        return oldParties;
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
        int newPartyId = parties.stream()
                                .map(Party::getId)
                                .max(Comparator.naturalOrder())
                                .map(id -> id + 1)
                                .orElse(1);
        party.setId(newPartyId);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Party>> violations = validator.validate(party);
        if (!violations.isEmpty()) {
            throw new InvalidEntityException(violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList()));
        }

        parties.add(party);

        return party;
    }

}
