package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Order;
import com.mthree.bsm.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * Repository for creating, reading, and updating {@link Order}s in the system. Any orders retrieved or put into the
 * system are guaranteed to be valid after the function returns. In particular, all its fields will be filled.
 */
public interface OrderDao {

    /**
     * Gets all {@link Order}s in the system.
     */
    List<Order> getOrders();

    /**
     * Gets all {@link Order}s of a certain side. Takes a boolean {@code isBuy} which indicates whether to get buy
     * orders or sell orders (so {@code true} means get all buy orders, and {@code false} means get all sell orders).
     */
    List<Order> getOrdersBySide(boolean isBuy);

    /**
     * Gets all {@link Order}s with a particular status.
     */
    List<Order> getOrdersByStatus(OrderStatus status);

    /**
     * Gets all {@link Order}s made by a particular user.
     *
     * @throws MissingEntityException if a user in the system with the given ID cannot be found.
     */
    List<Order> getOrdersByUserId(int userId) throws MissingEntityException;

    /**
     * Gets all {@link Order}s made by a particular party.
     *
     * @throws MissingEntityException if a party in the system with the given ID cannot be found.
     */
    List<Order> getOrdersByPartyId(int partyId) throws MissingEntityException;

    /**
     * Gets an order in the system with a given ID. If there is no order in the system with the given ID, the returned
     * {@link Optional} will not be present.
     */
    Optional<Order> getOrderById(int id);

    /**
     * Gets the full history of the order with the given ID. If there is no order in the system with the given ID, the
     * returned {@link Optional} will not be present.
     */
    List<Order> getOrderHistoryById(int id) throws MissingEntityException;

    /**
     * Creates an order in the system, validates it, and assigns it a new ID (no matter what ID the passed order has),
     * returning it back.
     *
     * @throws InvalidEntityException when the given order is invalid.
     */
    Order createOrder(Order order) throws InvalidEntityException;

    /**
     * Edits an order already in the system, replacing it with a new order which will be validated. Checks the system
     * based on the given order's ID to find the order to replace it with.
     *
     * @throws MissingEntityException if there is no order in the system with the given order's ID.
     * @throws InvalidEntityException if the given order is invalid.
     */
    void editOrder(Order order) throws MissingEntityException, InvalidEntityException;


    /**
     * Deletes all orders in the system, returning them in a list.
     */
    List<Order> deleteOrders();

}
