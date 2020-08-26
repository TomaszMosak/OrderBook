/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.controller;

import com.mthree.bsm.entity.Order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.mthree.bsm.entity.OrderStatus;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.MissingEntityException;
import com.mthree.bsm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author tombarton
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/buy")
    public List<Order> displayBuyOrders() {
        return orderService.getOrdersBySide(true);
    }

    @GetMapping("/order/sell")
    public List<Order> displaySellOrders() {
        return orderService.getOrdersBySide(false);
    }

    /**
     * Returns a list of orders with the given status. If the status does not match one of the seven given below,
     * returns a 400 Bad Request response.
     *
     * @param status can be one of "unknown", "edit_lock", "match_lock", "active", "fulfilled", "cancelled", "pending".
     *               This status is not case sensitive.
     */
    @GetMapping("/order/status/{status}")
    public ResponseEntity<List<Order>> displayOrdersByStatus(@PathVariable String status) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());

            return ResponseEntity.ok(orderService.getOrdersByStatus(orderStatus));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns an order with the given ID, unless there is no order with the given ID, in which case returns 404.
     */
    @GetMapping("/order/{id}")
    public ResponseEntity<Order> displayOrderById(@PathVariable int id) {
        Optional<Order> order = orderService.getOrderById(id);

        return order.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * Returns the history of an order with the given ID.
     *
     * @throws MissingEntityException if there is no order with the given ID.
     */
    @GetMapping("/order/history/{id}")
    public List<Order> displayOrderHistoryById(@PathVariable int id) throws MissingEntityException {
        return orderService.getOrderHistoryById(id);
    }

    /**
     * Returns all orders made or edited by the given user, unless there is no user with the given ID, in which case
     * returns 404.
     */
    @GetMapping("/order/user/{id}")
    public ResponseEntity<List<Order>> displayOrdersByUserId(@PathVariable int id) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(id);

            return ResponseEntity.ok(orders);
        } catch (MissingEntityException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/order/buy/{status}")
    public ResponseEntity<List<Order>> displayBuyOrdersByStatus(@PathVariable String status) {
        try {
            OrderStatus oStatus = OrderStatus.valueOf(status.toUpperCase());
            List<Order> orders = orderService.getSideOrdersByStatus(true, oStatus);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order/sell/{status}")
    public ResponseEntity<List<Order>> displaySellOrdersByStatus(@PathVariable String status) {
        try {
            OrderStatus oStatus = OrderStatus.valueOf(status.toUpperCase());
            List<Order> orders = orderService.getSideOrdersByStatus(false, oStatus);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Creates a new order in the system with the given parameters. If the parameters passed are OK, returns the created
     * order. Otherwise, returns a 422 Unprocessable Entity.
     *
     * @param stockId must exist in the system.
     * @param partyId must exist in the system.
     * @param userId  must exist in the system.
     * @param price   cannot be null, must have at most 6 digits before the decimal point and 2 digits after.
     * @param size    must be between 0 and 10 000 000.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/order")
    public Order createOrder(@RequestBody OrderRequestEntity orderRequestEntity) throws
            IOException,
            InvalidEntityException,
            MissingEntityException {
        return orderService.createOrder(orderRequestEntity.stockId,
                                        orderRequestEntity.partyId, orderRequestEntity.userId,
                                        orderRequestEntity.isBuy, orderRequestEntity.price, orderRequestEntity.size);
    }

    /**
     * Cancels an order in the system - such orders cannot be matched against or edited in the future.
     * <p>
     * Any request of this type should pass a JSON object with one field named "userId" to identify the user cancelling
     * the order.
     *
     * @param orderId the order to cancel.
     * @param userId  the user cancelling the order.
     */
    @PostMapping("/order/cancel/{orderId}")
    public void cancelOrder(@PathVariable int orderId, @RequestBody int userId) throws
            IOException,
            MissingEntityException,
            InvalidEntityException {
        orderService.cancelOrder(orderId, userId);
    }

    /**
     * Edits an order in the system.
     *
     * @param id     the ID of the order to edit.
     * @param userId the ID of the user editing the order.
     * @param price  the new price of the order.
     * @param size   the new size of the order.
     *
     * @throws InvalidEntityException when the new data for the order (price and size) is invalid.
     * @throws MissingEntityException when an order or user with the given ID(s) cannot be found.
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/order/edit/{id}")
    public void editOrder(@PathVariable int id,
                          @RequestBody int userId,
                          @RequestBody BigDecimal price,
                          @RequestBody int size) throws IOException, InvalidEntityException, MissingEntityException {
        orderService.editOrder(id, price, size, userId);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleIOException() {
    }

    @ExceptionHandler(MissingEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleMissingEntityException() {
    }

    @ExceptionHandler(InvalidEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<String> handleInvalidEntityException(InvalidEntityException e) {
        return e.getValidationErrors();
    }

    public static class OrderRequestEntity {

        int stockId;
        int partyId;
        int userId;
        boolean isBuy;
        BigDecimal price;
        int size;

        public int getStockId() {
            return stockId;
        }

        public OrderRequestEntity setStockId(int stockId) {
            this.stockId = stockId;
            return this;
        }

        public int getPartyId() {
            return partyId;
        }

        public OrderRequestEntity setPartyId(int partyId) {
            this.partyId = partyId;
            return this;
        }

        public int getUserId() {
            return userId;
        }

        public OrderRequestEntity setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public boolean isBuy() {
            return isBuy;
        }

        public OrderRequestEntity setBuy(boolean buy) {
            isBuy = buy;
            return this;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public OrderRequestEntity setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public int getSize() {
            return size;
        }

        public OrderRequestEntity setSize(int size) {
            this.size = size;
            return this;
        }

    }

}
