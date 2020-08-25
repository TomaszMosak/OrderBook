package com.mthree.bsm.repository;

import com.mthree.bsm.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implements the {@link TradeDao}, communicating with a relational database to do its work.
 */
@Repository
public class DatabaseTradeDao implements TradeDao {

    private final JdbcTemplate jdbc;
    private final TradeRowMapper tradeRowMapper = new TradeRowMapper();
    private final DatabaseOrderDao.OrderRowMapper orderRowMapper = new DatabaseOrderDao.OrderRowMapper();
    private final DatabasePartyDao.PartyRowMapper partyRowMapper = new DatabasePartyDao.PartyRowMapper();
    private final DatabaseStockDao.StockRowMapper stockRowMapper = new DatabaseStockDao.StockRowMapper();
    private final DatabaseUserDao.UserRowMapper userRowMapper = new DatabaseUserDao.UserRowMapper();

    @Autowired
    public DatabaseTradeDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Gets all {@link Trade}s in the system.
     */
    @Override
    public List<Trade> getTrades() {
        List<Trade> trades = jdbc.query("SELECT * " +
                                        "FROM trade", tradeRowMapper);
        if (!trades.isEmpty()) {
            trades.forEach(this::updateTradeOrders);
        }

        return trades;
    }

    /**
     * Gets a {@link Trade} by a given ID. If there is no {@link Trade} in the system with the given ID, the returned
     * {@link Optional} will not be present.
     *
     * @param id
     */
    @Override
    public Optional<Trade> getTradeById(int id) {
        Trade trade;
        try {
            trade = jdbc.queryForObject("SELECT * " +
                                        "FROM Trade " +
                                        "WHERE id = ?", tradeRowMapper, id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }

        assert trade != null;
        updateTradeOrders(trade);

        return Optional.of(trade);
    }

    /**
     * Creates a trade in the system, validates it, and assigns it a new ID (no matter what ID the passed trade has),
     * returning it back.
     *
     * @param trade
     * @throws InvalidEntityException when the given trade is invalid.
     */
    @Override
    public Trade addTrade(Trade trade) throws InvalidEntityException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
        if (!violations.isEmpty()) {
            List<String> violationMessages = violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList());

            throw new InvalidEntityException(violationMessages);
        }

        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Trade (buyId, sellId, executionTime) " +
                    "VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, trade.getBuyOrder().getHistoryId());
            preparedStatement.setInt(2, trade.getSellOrder().getHistoryId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(trade.getExecutionTime()));

            return preparedStatement;
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(preparedStatementCreator, keyHolder);

        assert keyHolder.getKey() != null;
        trade.setId(keyHolder.getKey().intValue());

        return trade;
    }

    /**
     * Deletes all trades in the system, returning them in a list.
     */
    @Override
    public List<Trade> deleteTrades() {
        //List<Trade> trades = getTrades();

        jdbc.update("DELETE FROM Trade");

        return new ArrayList<>();
    }

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

        Party centralParty = jdbc.queryForObject("SELECT * " +
                                                 "FROM Party " +
                                                 "WHERE id = ?", partyRowMapper, stock.getCentralParty().getId());
        order.getStock().setCentralParty(centralParty);

        User user = jdbc.queryForObject("SELECT * " +
                                        "FROM User " +
                                        "WHERE id = ?", userRowMapper, order.getUser().getId());
        assert user != null;
        order.setUser(user);
    }


    private void updateTradeOrders(Trade trade) {
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
                                       "WHERE h.id = ? " +
                                       "ORDER BY h.id ";

        Order buyOrder = jdbc.queryForObject(GET_ORDER_BY_ID, orderRowMapper, trade.getBuyOrder().getHistoryId());
        updateOrderPartyStockUser(buyOrder);
        trade.setBuyOrder(buyOrder);

        Order sellOrder = jdbc.queryForObject(GET_ORDER_BY_ID, orderRowMapper, trade.getSellOrder().getHistoryId());
        updateOrderPartyStockUser(sellOrder);
        trade.setSellOrder(sellOrder);
    }

    /**
     * Maps rows of the trade table to <em>incomplete</em> {@link Trade} objects through its {@link #mapRow(ResultSet,
     * int)} method.
     * <br>
     * Rows mapped by objects of this class are guaranteed to never be null.
     * <br>
     * {@link Trade} objects returned by this class have their two {@link Order} fields set to an
     * <em>incomplete</em> {@link Order}, with only the {@link Order#id} set. The {@link Order}s should be
     * fully set by objects acting on the returned value.
     * <br>
     * <b>Example</b>
     * <br>
     * <code>
     * Trade trade = jdbc.queryForObject("SELECT * FROM trade WHERE id = ?", new TradeRowMapper(), 10);
     * </code>
     */
    public static class TradeRowMapper implements RowMapper<Trade> {

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
        public Trade mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trade trade = new Trade();
            trade.setId(rs.getInt("id"));

            Order buyOrder = new Order();
            buyOrder.setHistoryId(rs.getInt("buyId"));
            trade.setBuyOrder(buyOrder);

            Order sellOrder = new Order();
            sellOrder.setHistoryId(rs.getInt("sellId"));
            trade.setSellOrder(sellOrder);

            trade.setExecutionTime(rs.getTimestamp("executionTime").toLocalDateTime());

            return trade;
        }

    }

}
