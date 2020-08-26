/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.service;

import com.mthree.bsm.entity.*;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.MissingEntityException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author tombarton
 */
public interface OrderService {

    /**
     * Gets a list of all {@link Order}s in the system.
     */
    List<Order> getOrders();

    /**
     * Gets a list of all {@link Order}s in the system by their side (buy or sell).
     *
     * @param isBuy if {@code true}, gets all buy {@link Order}s, otherwise gets all sell {@link Order}s
     */
    List<Order> getOrdersBySide(boolean isBuy);

    /**
     * Gets a list of all {@link Order}s with the provided status.
     *
     * @param status must not be null.
     */
    List<Order> getOrdersByStatus(OrderStatus status);

    /**
     * Gets a list of all orders made by the {@link Party} with the given ID.
     *
     * @param id the ID of the {@link Party} whose orders this method will get.
     *
     * @throws MissingEntityException if there is no {@link Party} with the given ID.
     */
    List<Order> getOrdersByPartyId(int id) throws MissingEntityException;

    /**
     * Gets a list of all orders made or edited by the {@link User} with the given ID.
     *
     * @param id the ID of the {@link User} whose orders this method will get.
     *
     * @throws MissingEntityException if there is no {@link User} with the given ID.
     */
    List<Order> getOrdersByUserId(int id) throws MissingEntityException;

    /**
     * Gets an {@link Order} in the system by its ID.
     *
     * @param id the ID of the {@link Order} to look for.
     *
     * @return an optional with a value present if there is a {@link Order} with the given ID, otherwise the value won't
     * be present.
     */
    Optional<Order> getOrderById(int id);

    /**
     * Gets a list of the history of an {@link Order} in the system.
     *
     * @param id the ID of the {@link Order} whose history we should get.
     *
     * @throws MissingEntityException if there is no {@link Order} with the given ID.
     */
    List<Order> getOrderHistoryById(int id) throws MissingEntityException;

    /**
     * Creates a new {@link Order} in the system with the given parameters.
     * <p>
     * Upon invocation of this method, the new {@link Order} will be constructed with the given parameters and
     * validated. It will then be added to the system.
     * <p>
     * Upon creation, the {@link Order}'s status will be {@link OrderStatus#PENDING} as the system checks for any
     * matching orders. If there are matches, the relevant {@link Order}s will be updated as required and the new {@link
     * Order} will be added to the system with status {@link OrderStatus#FULFILLED} if the match completed the order,
     * otherwise the status will be {@link OrderStatus#ACTIVE}, ready to be edited/canceled/matched against.
     * <p>
     * An audit log will be written when the order is added to the system, and thereafter each time the order changes as
     * it is being matched.
     *
     * @return the newly constructed {@link Order}.
     *
     * @throws InvalidEntityException if the constructed {@link Order} is invalid.
     * @throws MissingEntityException if there is no {@link Stock} with the given {@code stockId}, no {@link Party} with
     *                                the given {@code partyId}, or {@link User} with the given {@code userId}.
     * @throws IOException            if writing to the audit log failed for some reason.
     */
    Order createOrder(int stockId, int partyId, int userId, boolean isBuy, BigDecimal price, int size) throws
            InvalidEntityException,
            MissingEntityException,
            IOException;

    /**
     * Cancels an {@link Order}, setting its status to {@link OrderStatus#CANCELLED}, and then validates it. Such an
     * order will not be able to be edited or matched against in the future.
     * <p>
     * An audit log will be written to indicate this {@link Order}'s status has changed.
     *
     * @param id     the ID of the {@link Order} to cancel.
     * @param userId the ID of the {@link User} canceling the order.
     *
     * @throws MissingEntityException if there is no {@link Order} in the system with the given {@code id}, or no {@link
     *                                User} in the system with the given {@code userId}.
     * @throws InvalidEntityException if the {@link Order} is invalid.
     * @throws IOException            if writing to the audit log failed for some reason.
     */
    void cancelOrder(int id, int userId) throws MissingEntityException, InvalidEntityException, IOException;

    /**
     * Edits an {@link Order} in the system with new {@code price} and {@code size} parameters.
     * <p>
     * Upon invocation, the {@link Order} will be retrieved from the system, the relevant parameters set, and then
     * validated.
     * <p>
     * An audit log will be written to indicate this {@link Order} has been changed.
     *
     * @param orderId the ID of the {@link Order} to edit.
     * @param price   the new price of the {@link Order}.
     * @param size    the new size of the {@link Order}.
     * @param userId  the ID of the {@link User} editing the order.
     *
     * @return the edited {@link Order}.
     *
     * @throws MissingEntityException if there is no {@link Order} with the given {@code orderId}, or no {@link User}
     *                                with the given {@code userId}.
     * @throws InvalidEntityException if the newly edited {@link Order} is invalid.
     * @throws IOException            if writing to the audit log failed for some reason.
     */
    Order editOrder(int orderId, BigDecimal price, int size, int userId) throws
            MissingEntityException,
            InvalidEntityException,
            IOException;

    void matchOrders() throws IOException, MissingEntityException, InvalidEntityException;

    List<Order> getSideOrdersByStatus(boolean b, OrderStatus status);

}
