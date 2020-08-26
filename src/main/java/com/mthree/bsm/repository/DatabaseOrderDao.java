package com.mthree.bsm.repository;

import com.mthree.bsm.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implements the {@link OrderDao}, communicating with a relational database to do its work.
 */
@Repository
public class DatabaseOrderDao implements OrderDao {

    private final JdbcTemplate jdbc;
    private final OrderRowMapper orderRowMapper = new OrderRowMapper();
    private final DatabasePartyDao.PartyRowMapper partyRowMapper = new DatabasePartyDao.PartyRowMapper();
    private final DatabaseUserDao.UserRowMapper userRowMapper = new DatabaseUserDao.UserRowMapper();
    private final DatabaseStockDao.StockRowMapper stockRowMapper = new DatabaseStockDao.StockRowMapper();

    @Autowired
    public DatabaseOrderDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Gets all {@link Order}s in the system as they currently stand.
     */
    @Override
    public List<Order> getOrders() {
        final String GET_ORDERS = " SELECT o.id AS orderId, " +
                                  "        o.stockId, " +
                                  "        o.partyId, " +
                                  "        o.orderStatus, " +
                                  "        o.side, " +
                                  "        h.id AS historyId, " +
                                  "        h.userId, " +
                                  "        h.price, " +
                                  "        h.currentSize, " +
                                  "        h.timestamp " +
                                  " FROM `order` o " +
                                  " INNER JOIN OrderHistory h " +
                                  "     ON o.id = h.orderId " +
                                  " ORDER BY historyId ";

        List<Order> orders = determineCurrentOrders(jdbc.query(GET_ORDERS, orderRowMapper));
        orders.forEach(this::updateOrderPartyStockUser);

        return orders;
    }

    /**
     * Gets all {@link Order}s of a certain side. Takes a boolean {@code isBuy} which indicates whether to get buy
     * orders or sell orders (so {@code true} means get all buy orders, and {@code false} means get all sell orders).
     */
    @Override
    public List<Order> getOrdersBySide(boolean isBuy) {
        final String GET_ORDER_BY_SIDE = "SELECT o.id AS orderId, " +
                                         "       o.stockId, " +
                                         "       o.partyId, " +
                                         "       o.orderStatus, " +
                                         "       o.side, " +
                                         "       h.id AS historyId, " +
                                         "       h.userId, " +
                                         "       h.price, " +
                                         "       h.currentSize, " +
                                         "       h.timestamp " +
                                         "FROM `order` o " +
                                         "INNER JOIN OrderHistory h " +
                                         "     ON o.id = h.orderId " +
                                         "WHERE o.side = ? " +
                                         "ORDER BY historyId ";

        List<Order> orders = determineCurrentOrders(jdbc.query(GET_ORDER_BY_SIDE, orderRowMapper, isBuy));
        orders.forEach(this::updateOrderPartyStockUser);

        return orders;
    }

    /**
     * Gets all {@link Order}s with a particular status.
     *
     * @param status
     */
    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        final String GET_ORDER_BY_STATUS = "SELECT o.id AS orderId, " +
                                           "       o.stockId, " +
                                           "       o.partyId, " +
                                           "       o.orderStatus, " +
                                           "       o.side, " +
                                           "       h.id AS historyId, " +
                                           "       h.userId, " +
                                           "       h.price, " +
                                           "       h.currentSize, " +
                                           "       h.timestamp " +
                                           "FROM `order` o " +
                                           "INNER JOIN OrderHistory h " +
                                           "     ON o.id = h.orderId " +
                                           "WHERE o.orderStatus = ? " +
                                           "ORDER BY historyId ";

        List<Order> orders = determineCurrentOrders(jdbc.query(GET_ORDER_BY_STATUS, orderRowMapper, status.ordinal()));
        orders.forEach(this::updateOrderPartyStockUser);

        return orders;
    }

    /**
     * Gets all {@link Order}s made by a particular user.
     *
     * @param userId
     * @throws MissingEntityException if a user in the system with the given ID cannot be found.
     */
    @Override
    public List<Order> getOrdersByUserId(int userId) throws MissingEntityException {
        final String GET_USER_BY_ID = "SELECT * " +
                                      "FROM User " +
                                      "WHERE id = ?";
        List<User> users = jdbc.query(GET_USER_BY_ID, userRowMapper, userId);
        if (users.isEmpty()) {
            throw new MissingEntityException("No user with the given ID found.");
        }

        final String GET_ORDER_BY_USER_ID = "SELECT o.id AS orderId, " +
                                            "       o.stockId, " +
                                            "       o.partyId, " +
                                            "       o.orderStatus, " +
                                            "       o.side, " +
                                            "       h.id AS historyId, " +
                                            "       h.userId, " +
                                            "       h.price, " +
                                            "       h.currentSize, " +
                                            "       h.timestamp " +
                                            "FROM `order` o " +
                                            "INNER JOIN OrderHistory h " +
                                            "     ON o.id = h.orderId " +
                                            "WHERE h.userId = ? " +
                                            "ORDER BY historyId ";

        List<Order> orders = determineCurrentOrders(jdbc.query(GET_ORDER_BY_USER_ID, orderRowMapper, userId));
        orders.forEach(this::updateOrderPartyStockUser);

        return orders;
    }

    /**
     * Gets all {@link Order}s made by a particular party.
     *
     * @throws MissingEntityException if a party in the system with the given ID cannot be found.
     */
    @Override
    public List<Order> getOrdersByPartyId(int partyId) throws MissingEntityException {
        final String GET_PARTY_BY_ID = "SELECT * " +
                                       "FROM party " +
                                       "WHERE id = ?";
        List<Party> parties = jdbc.query(GET_PARTY_BY_ID, partyRowMapper, partyId);
        if (parties.isEmpty()) {
            throw new MissingEntityException("No party with the given ID found.");
        }

        final String GET_ORDER_BY_PARTY_ID = "SELECT o.id AS orderId, " +
                                             "       o.stockId, " +
                                             "       o.partyId, " +
                                             "       o.orderStatus, " +
                                             "       o.side, " +
                                             "       h.id AS historyId, " +
                                             "       h.userId, " +
                                             "       h.price, " +
                                             "       h.currentSize, " +
                                             "       h.timestamp " +
                                             "FROM `order` o " +
                                             "INNER JOIN OrderHistory h " +
                                             "     ON o.id = h.orderId " +
                                             "WHERE o.partyId = ? " +
                                             "ORDER BY historyId ";

        List<Order> orders = determineCurrentOrders(jdbc.query(GET_ORDER_BY_PARTY_ID, orderRowMapper, partyId));
        orders.forEach(this::updateOrderPartyStockUser);

        return orders;

    }

    /**
     * Gets an order in the system with a given ID. If there is no order in the system with the given ID, the returned
     * {@link Optional} will not be present.
     */
    @Override
    public Optional<Order> getOrderById(int id) {
        final String GET_ORDER_BY_ID = "SELECT o.id AS orderId, " +
                                       "       o.stockId, " +
                                       "       o.partyId, " +
                                       "       o.orderStatus, " +
                                       "       o.side, " +
                                       "       h.id AS historyId, " +
                                       "       h.userId, " +
                                       "       h.price, " +
                                       "       h.currentSize, " +
                                       "       h.timestamp " +
                                       "FROM `order` o " +
                                       "INNER JOIN OrderHistory h " +
                                       "     ON o.id = h.orderId " +
                                       "WHERE o.id = ? " +
                                       "ORDER BY historyId ";

        List<Order> orderHistory = jdbc.query(GET_ORDER_BY_ID, orderRowMapper, id);
        if (orderHistory.isEmpty()) {
            return Optional.empty();
        }

        orderHistory.sort(Comparator.comparing(Order::getHistoryId));
        for (int i = 0; i < orderHistory.size(); i++) {
            orderHistory.get(i).setVersion(i + 1);
        }

        Order order = orderHistory.stream()
                                  .max(Comparator.comparing(Order::getVersion))
                                  .get();
        updateOrderPartyStockUser(order);

        return Optional.of(order);
    }

    /**
     * Gets the full history of the order with the given ID.
     *
     * @throws MissingEntityException if there is no order in the system with the given ID.
     */
    @Override
    public List<Order> getOrderHistoryById(int id) throws MissingEntityException {
        final String GET_HISTORY_BY_ID = "SELECT o.id AS orderId, " +
                                         "       o.stockId, " +
                                         "       o.partyId, " +
                                         "       o.orderStatus, " +
                                         "       o.side, " +
                                         "       h.id AS historyId, " +
                                         "       h.userId, " +
                                         "       h.price, " +
                                         "       h.currentSize, " +
                                         "       h.timestamp " +
                                         "FROM `order` o " +
                                         "INNER JOIN OrderHistory h " +
                                         "     ON o.id = h.orderId " +
                                         "WHERE o.id = ? " +
                                         "ORDER BY historyId ";

        List<Order> orderHistory = jdbc.query(GET_HISTORY_BY_ID, orderRowMapper, id);
        if (orderHistory.isEmpty()) {
            throw new MissingEntityException("No order in the system with the given ID");
        }
        orderHistory.forEach(this::updateOrderPartyStockUser);

        orderHistory.sort(Comparator.comparing(Order::getHistoryId));
        for (int i = 0; i < orderHistory.size(); i++) {
            orderHistory.get(i).setVersion(i + 1);
        }

        return orderHistory;
    }

    /**
     * Creates an order in the system, validates it, and assigns it a new ID (no matter what ID the passed order has),
     * returning it back.
     *
     * @throws InvalidEntityException when the given order is invalid.
     */
    @Override
    @Transactional
    public Order createOrder(Order order) throws InvalidEntityException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (!violations.isEmpty()) {
            List<String> violationMessages = violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList());

            throw new InvalidEntityException(violationMessages);
        }

        final String CREATE_ORDER = "INSERT INTO `order` (partyId, stockId, orderStatus, side) " +
                                    "VALUES (?, ?, ?, ?)";


        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER,
                                                                              Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, order.getParty().getId());
            preparedStatement.setInt(2, order.getStock().getId());
            preparedStatement.setInt(3, order.getStatus().ordinal());
            preparedStatement.setBoolean(4, order.isBuy());

            return preparedStatement;
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(preparedStatementCreator, keyHolder);

        assert keyHolder.getKey() != null;
        order.setId(keyHolder.getKey().intValue());

        final String CREATE_VERSION = "INSERT INTO OrderHistory (orderId, Price, currentSize, userId, timestamp) " +
                                      "VALUES (?, ?, ?, ?, ?)";
        jdbc.update(CREATE_VERSION,
                    order.getId(),
                    order.getPrice(),
                    order.getSize(),
                    order.getUser().getId(),
                    order.getVersionTime());

        Order retrievedOrder = getOrderById(order.getId()).get();
        order.setHistoryId(retrievedOrder.getHistoryId());
        order.setVersion(retrievedOrder.getVersion());

        return retrievedOrder;
    }

    /**
     * Edits an order already in the system, replacing it with a new order which will be validated. Checks the system
     * based on the given order's ID to find the order to replace it with.
     *
     * @throws MissingEntityException if there is no order in the system with the given order's ID.
     * @throws InvalidEntityException if the given order is invalid.
     */
    @Override
    @Transactional
    public void editOrder(Order order) throws MissingEntityException, InvalidEntityException {
        if (getOrderById(order.getId()).isEmpty()) {
            throw new MissingEntityException("Order with given order's ID does not already exist in the system.");
        }

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (!violations.isEmpty()) {
            List<String> violationMessages = violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList());

            throw new InvalidEntityException(violationMessages);
        }

        final String CREATE_VERSION = "INSERT INTO OrderHistory (orderId, Price, currentSize, userId, timestamp) " +
                                      "VALUES (?, ?, ?, ?, ?)";
        jdbc.update(CREATE_VERSION,
                    order.getId(),
                    order.getPrice(),
                    order.getSize(),
                    order.getUser().getId(),
                    order.getVersionTime());

        Order retrievedOrder = getOrderById(order.getId()).get();
        order.setHistoryId(retrievedOrder.getHistoryId());
        order.setVersion(retrievedOrder.getVersion());
    }

    /**
     * Deletes all orders in the system, returning them in a list.
     */
    @Override
    @Transactional
    public List<Order> deleteOrders() {
        List<Order> orders = getOrders();

        jdbc.update("DELETE FROM OrderHistory");
        jdbc.update("DELETE FROM `order`");

        return orders;
    }

    /**
     * Given an {@link Order} whose {@link Order#party}, {@link Order#stock}, and {@link Order#user} fields has have IDs
     * of respective entities which exists in the system, retrieve these entities from the system and sets the
     * respective fields in the {@link Order}.
     * <p>
     * This method will panic if the given order is null, or there is no entity with one of the given IDs in the
     * system.
     */
    private void updateOrderPartyStockUser(@NonNull Order order) {
        Party party = jdbc.queryForObject("SELECT * " +
                                          "FROM party " +
                                          "WHERE id = ?", partyRowMapper, order.getParty().getId());
        assert party != null;
        order.setParty(party);

        Stock stock = jdbc.queryForObject("SELECT * " +
                                          "FROM Stock " +
                                          "WHERE id = ?", stockRowMapper, order.getStock().getId());
        assert stock != null;
        order.setStock(stock);

        Party stockCentralParty = jdbc.queryForObject("SELECT * " +
                                                      "FROM Party " +
                                                      "WHERE id = ?",
                                                      partyRowMapper,
                                                      order.getStock().getCentralParty().getId());
        assert stockCentralParty != null;
        order.getStock().setCentralParty(stockCentralParty);

        User user = jdbc.queryForObject("SELECT * " +
                                        "FROM User " +
                                        "WHERE id = ?", userRowMapper, order.getUser().getId());
        assert user != null;
        order.setUser(user);
    }

    /**
     * Given a list of versions of multiple orders, groups these order versions according to the order they represent,
     * and retrieves the latest order for each, returning this list of latest orders.
     */
    private List<Order> determineCurrentOrders(List<Order> orderHistories) {
        Map<Integer, List<Order>> orderHistoryMap = orderHistories.stream()
                                                                  .collect(Collectors.groupingBy(Order::getId));

        orderHistoryMap.values().forEach(orderHistory -> {
            orderHistory.sort(Comparator.comparing(Order::getHistoryId));
            for (int i = 0; i < orderHistory.size(); i++) {
                orderHistory.get(i).setVersion(i + 1);
            }
        });

        return orderHistoryMap.values().stream()
                              .map(orderHistory -> orderHistory.stream()
                                                               .max(Comparator.comparing(Order::getHistoryId))
                                                               .get())
                              .collect(Collectors.toList());
    }

    /**
     * For the following table:
     * <pre>{@code
     * SELECT o.id AS orderId,
     *        o.stockId,
     *        o.partyId,
     *        o.orderStatus,
     *        o.side,
     *        h.id AS historyId,
     *        h.userId,
     *        h.price,
     *        h.currentSize,
     *        h.timestamp
     * FROM order o
     * INNER JOIN order_history h
     *     ON o.id = h.orderId
     * WHERE o.id = ?
     * ORDER BY h.id
     * }</pre>
     * Maps rows of the table to <em>incomplete</em> {@link Order} objects of a single order across its entire history
     * through its {@link #mapRow(ResultSet, int)} method.
     * <br>
     * Rows mapped by objects of this class are guaranteed to never be null.
     * <br>
     * {@link Order} objects returned by this class have their {@link Order#party}, {@link Order#stock}, and {@link
     * Order#user} fields set to
     * <em>incomplete</em> {@link Party}, {@link Stock}, and {@link User} objects, with only the {@link Party#id},
     * {@link Stock#id}, and {@link User#id} set respectively. The {@link Order#party}, {@link Order#stock}, and {@link
     * Order#user} should be fully set by objects acting on the returned value.
     */
    public static class OrderRowMapper implements RowMapper<Order> {

        /**
         * Implementations must implement this method to map each row of data in the ResultSet. This method should not
         * call {@code next()} on the ResultSet; it is only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if an SQLException is encountered getting column values (that is, there's no need to
         *                      catch SQLException)
         */
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();

            order.setId(rs.getInt("orderId"));
            order.setHistoryId(rs.getInt("historyId"));

            User user = new User();
            user.setId(rs.getInt("userId"));
            order.setUser(user);

            Party party = new Party();
            party.setId(rs.getInt("partyId"));
            order.setParty(party);

            Stock stock = new Stock();
            stock.setId(rs.getInt("stockId"));
            order.setStock(stock);

            order.setPrice(rs.getBigDecimal("price"));
            order.setSize(rs.getInt("currentSize"));
            order.setBuy(rs.getBoolean("side"));
            order.setStatus(OrderStatus.values()[rs.getInt("orderStatus")]);
            order.setVersionTime(rs.getTimestamp("timestamp").toLocalDateTime());

            return order;
        }

    }

}
