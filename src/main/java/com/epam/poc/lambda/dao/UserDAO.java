package com.epam.poc.lambda.dao;

import com.epam.poc.lambda.model.User;

import java.util.List;
import java.util.Optional;

/**
 * The DAO layer contract for interacting with storage.
 * <p>
 * Date: 2018-02-15
 *
 * @author Raman_Khrapavitski
 */
public interface UserDAO {

    /**
     * Stores user into storage.
     *
     * @param user the user to store
     */
    void addUser(User user);

    /**
     * Looks for user by identifier in storage.
     *
     * @param id the identifier to look for
     * @return the found user or {@code null}
     */
    Optional<User> getUser(String id);

    /**
     * Looks for users by last name in storage.
     *
     * @param lastName the last name of user
     * @return the found list of users or empty list
     */
    List<User> getUsersByLastName(String lastName);

    /**
     * Looks for users by first name in storage.
     *
     * @param firstName the first name of user
     * @return the found list of users or empty list
     */
    List<User> getUsersByFirstName(String firstName);

    /**
     * Updates user in storage.
     *
     * @param user the user object for update
     */
    void updateUser(User user);

    /**
     * Deletes user by identifier from storage.
     *
     * @param id the user identifier
     */
    void deleteUser(String id);

    /**
     * Looks for all users in storage.
     *
     * @return the found list of users or empty list
     */
    List<User> getAllUsers();
}
