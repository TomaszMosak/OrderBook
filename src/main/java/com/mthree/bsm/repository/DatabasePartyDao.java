package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Party;
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

@Repository
public class DatabasePartyDao implements PartyDao {

    private final JdbcTemplate jdbc;
    private final PartyRowMapper partyRowMapper = new PartyRowMapper();

    @Autowired
    public DatabasePartyDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Gets an party in the system with a given ID. If there is no party in the system with the given ID, the returned
     * {@link Optional} will not be present.
     */
    @Override
    public Optional<Party> getPartyById(int partyId) {
        try {
            Party party = jdbc.queryForObject("SELECT * " +
                                              "FROM Party " +
                                              "WHERE id = ?", partyRowMapper, partyId);

            return Optional.ofNullable(party);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Gets a list of all parties in the system.
     */
    @Override
    public List<Party> getParties() {
        return jdbc.query("SELECT * " +
                          "FROM Party", partyRowMapper);
    }

    /**
     * Deletes all parties in the system, returning them in a list.
     */
    @Override
    public List<Party> deleteParties() {
        List<Party> parties = jdbc.query("SELECT * " +
                                         "FROM Party", partyRowMapper);

        jdbc.update("DELETE FROM Trade");
        jdbc.update("DELETE FROM OrderHistory");
        jdbc.update("DELETE FROM `Order`");
        jdbc.update("DELETE FROM Stock");
        jdbc.update("DELETE FROM Party");

        return parties;
    }

    /**
     * Adds a party to the system, validating it, and assigning it a new ID (no matter what ID the passed user has),
     * returning it back.
     *
     * @throws InvalidEntityException when the given party is invalid.
     */
    @Override
    public Party addParty(Party party) throws InvalidEntityException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Party>> violations = validator.validate(party);
        if (!violations.isEmpty()) {
            List<String> violationMessages = violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList());

            throw new InvalidEntityException(violationMessages);
        }

        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Party (name, symbol) " +
                                                                              "VALUES (?, ?)",
                                                                              Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, party.getName());
            preparedStatement.setString(2, party.getSymbol());

            return preparedStatement;
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(preparedStatementCreator, keyHolder);

        party.setId(keyHolder.getKey().intValue());

        return party;
    }

    /**
     * Maps rows of the party table to {@link Party} objects through its {@link #mapRow(ResultSet, int)} method.
     * <br>
     * Rows mapped by objects of this class are guaranteed to never be null.
     * <br>
     * <b>Example</b>
     * <br>
     * <code>
     * List<Party> parties = jdbc.query("SELECT * FROM party", new PartyRowMapper());
     * </code>
     */
    public static class PartyRowMapper implements RowMapper<Party> {

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
        public Party mapRow(ResultSet rs, int rowNum) throws SQLException {
            Party party = new Party();
            party.setId(rs.getInt("id"));
            party.setName(rs.getString("name"));
            party.setSymbol(rs.getString("symbol"));

            return party;
        }

    }

}
