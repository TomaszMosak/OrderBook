package com.mthree.bsm.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * The user entity corresponds to a user trading with the application. It has an {@link #id} to act as the primary key,
 * a {@link #username} (the username), and a {@link #deleted} boolean to indicate whether the user is suppressed from
 * the system - deleting the user from the table entirely will break a foreign key constraint with orders, which we must
 * preserve.
 * <p>
 * A {@link User} is valid if none of its fields are null, and its {@link #username} is at most 20 characters.
 */
public class User {

    private int id;

    @NotNull(message = "The user's username must not be null.")
    @Size(max = 20, message = "The user's username must have at most 20 characters.")
    private String username;

    private String deleted;

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", deleted='" + deleted + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
               Objects.equals(username, user.username) &&
               Objects.equals(deleted, user.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, deleted);
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getDeleted() {
        return deleted;
    }

    public User setDeleted(String deleted) {
        this.deleted = deleted;
        return this;
    }

}
