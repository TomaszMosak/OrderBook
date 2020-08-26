package com.mthree.bsm.repository;

import com.mthree.bsm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implements the {@link UserDao}, communicating with a relational database to do its work.
 */
@Repository
public class DatabaseUserDao implements UserDao {

    private final JdbcTemplate jdbc;
    private final UserRowMapper userRowMapper = new UserRowMapper();

    @Autowired
    public DatabaseUserDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Gets a user in the system with a given ID. If there is no user in the system with the given ID, the returned
     * {@link Optional} will not be present.
     */
    @Override
    public Optional<User> getUserById(int userId) {
        try {
            User user = jdbc.queryForObject("SELECT * " +
                                            "FROM User " +
                                            "WHERE id = ?", userRowMapper, userId);

            return Optional.ofNullable(user);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Gets a list of all users in the system.
     */
    @Override
    public List<User> getUsers() {
        return jdbc.query("SELECT * " +
                          "FROM User", userRowMapper);
    }

    /**
     * Deletes all trades in the system, returning them in a list.
     */
    @Override
    @Transactional
    public List<User> deleteUsers() {
        List<User> users = jdbc.query("SELECT * " +
                                      "FROM User", userRowMapper);

        jdbc.update("DELETE FROM Trade");
        jdbc.update("DELETE FROM OrderHistory");
        jdbc.update("DELETE FROM User");

        return users;
    }

    /**
     * Adds a user to the system, validating it, and assigning it a new ID (no matter what ID the passed user has),
     * returning it back.
     *
     * @throws InvalidEntityException when the given user is invalid.
     */
    @Override
    public User addUser(User user) throws InvalidEntityException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            List<String> violationMessages = violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList());

            throw new InvalidEntityException(violationMessages);
        }

        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (userName, deleted) " +
                                                                              "VALUES (?, ?)",
                                                                              Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setBoolean(2, user.getDeleted());

            return preparedStatement;
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(preparedStatementCreator, keyHolder);

        user.setId(keyHolder.getKey().intValue());

        return user;
    }

    /**
     * Maps rows of the user table to {@link User} objects through its {@link #mapRow(ResultSet, int)} method.
     * <br>
     * Rows mapped by objects of this class are guaranteed to never be null.
     * <br>
     * <b>Example</b>
     * <br>
     * <code>
     * List<User> users = jdbc.query("SELECT * FROM user", new UserRowMapper());
     * </code>
     */
    public static class UserRowMapper implements RowMapper<User> {

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
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setDeleted(rs.getBoolean("deleted"));

            return user;
        }

    }

}
