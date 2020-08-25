package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Party;
import com.mthree.bsm.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implements the {@link StockDao}, communicating with a relational database to do its work.
 */
@Repository
public class DatabaseStockDao implements StockDao {

    private final JdbcTemplate jdbc;
    private final StockRowMapper stockRowMapper = new StockRowMapper();
    private final DatabasePartyDao.PartyRowMapper partyRowMapper = new DatabasePartyDao.PartyRowMapper();

    @Autowired
    public DatabaseStockDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Gets a stock in the system with a given ID. If there is no stock in the system with the given ID, the returned
     * {@link Optional} will not be present.
     */
    @Override
    public Optional<Stock> getStockById(int stockId) {
        Stock stock;
        try {
            stock = jdbc.queryForObject("SELECT * " +
                                        "FROM stock " +
                                        "WHERE id = ?", stockRowMapper, stockId);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }

        assert stock != null;
        updateStockCentralParty(stock);

        return Optional.of(stock);
    }

    /**
     * Gets a list of all stocks in the system.
     */
    @Override
    public List<Stock> getStocks() {
        List<Stock> stocks = jdbc.query("SELECT * " +
                                        "FROM stock", stockRowMapper);

        stocks.forEach(this::updateStockCentralParty);

        return stocks;
    }

    /**
     * Deletes all stocks in the system, returning them in a list.
     */
    @Override
    public List<Stock> deleteStocks() {
        List<Stock> stocks = jdbc.query("SELECT * " +
                                        "FROM stock", stockRowMapper);

        stocks.forEach(this::updateStockCentralParty);

        jdbc.update("DELETE FROM Trade");
        jdbc.update("DELETE FROM OrderHistory");
        jdbc.update("DELETE FROM `order`");
        jdbc.update("DELETE FROM stock");

        return stocks;
    }

    /**
     * Adds a stock to the system, validating it, and assigning it a new ID (no matter what ID the passed stock has),
     * returning it back.
     *
     * @throws InvalidEntityException when the given stock is invalid.
     */
    @Override
    public Stock addStock(Stock stock) throws InvalidEntityException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Stock>> violations = validator.validate(stock);
        if (!violations.isEmpty()) {
            List<String> violationMessages = violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList());

            throw new InvalidEntityException(violationMessages);
        }

        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO stock (centralPartyId, companyName, stockSymbol, stockExchange, tickSize) " +
                    "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, stock.getCentralParty().getId());
            preparedStatement.setString(2, stock.getCompanyName());
            preparedStatement.setString(3, stock.getSymbol());
            preparedStatement.setString(4, stock.getExchange());
            preparedStatement.setBigDecimal(5, stock.getTickSize());

            return preparedStatement;
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(preparedStatementCreator, keyHolder);

        assert keyHolder.getKey() != null;
        stock.setId(keyHolder.getKey().intValue());

        return stock;
    }

    /**
     * Given a {@link Stock} whose {@link Stock#centralParty} field has an ID of a {@link Party} which exists in the
     * system, retrieves this {@link Party} from the system and sets the {@link Stock#centralParty} to this {@link
     * Party} object.
     * <p>
     * This method will panic if the given stock is null, or there is no party with the given ID in the system.
     */
    private void updateStockCentralParty(Stock stock) {
        assert stock != null;
        Party centralParty = jdbc.queryForObject("SELECT * " +
                                                 "FROM party " +
                                                 "WHERE id = ?", partyRowMapper, stock.getCentralParty().getId());
        assert centralParty != null;
        stock.setCentralParty(centralParty);
    }

    /**
     * Maps rows of the stock table to <em>incomplete</em> {@link Stock} objects through its {@link #mapRow(ResultSet,
     * int)} method.
     * <br>
     * Rows mapped by objects of this class are guaranteed to never be null.
     * <br>
     * {@link Stock} objects returned by this class have their {@link Stock#centralParty} field set to an
     * <em>incomplete</em> {@link Party}, with only the {@link Party#id} set. The {@link Stock#centralParty} should be
     * fully set by objects acting on the returned value.
     * <br>
     * <b>Example</b>
     * <br>
     * <code>
     * Stock stock = jdbc.queryForObject("SELECT * FROM stock WHERE id = ?", new StockRowMapper(), 10);
     * </code>
     */
    public static class StockRowMapper implements RowMapper<Stock> {

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
        public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
            Stock stock = new Stock();
            stock.setId(rs.getInt("id"));

            Party centralParty = new Party();
            centralParty.setId(rs.getInt("centralpartyid"));

            stock.setCentralParty(centralParty);
            stock.setCompanyName(rs.getString("companyname"));
            stock.setSymbol(rs.getString("stocksymbol"));
            stock.setExchange(rs.getString("stockexchange"));
            stock.setTickSize(rs.getBigDecimal("ticksize"));

            return stock;
        }

    }

}
