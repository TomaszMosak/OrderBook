package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Trade;

import java.util.List;
import java.util.Optional;

/**
 * Repository for getting and creating trades in the system. Any trades retrieved or put into the system are guaranteed
 * to be valid after the function returns. In particular, all its fields will be filled.
 */
public interface TradeRepository {

    /**
     * Gets all {@link Trade}s in the system.
     */
    List<Trade> getTrades();

    /**
     * Gets a {@link Trade} by a given ID. If there is no {@link Trade} in the system with the given ID, the returned
     * {@link Optional} will not be present.
     */
    Optional<Trade> getTradeById(int id);

    /**
     * Creates a trade in the system, validates it, and assigns it a new ID (no matter what ID the passed trade has),
     * returning it back.
     *
     * @throws InvalidEntityException when the given trade is invalid.
     */
    Trade createTrade(Trade trade) throws InvalidEntityException;

}
