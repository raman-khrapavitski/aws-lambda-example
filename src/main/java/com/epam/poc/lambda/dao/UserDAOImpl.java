package com.epam.poc.lambda.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.epam.poc.lambda.exception.AwsRuntimeException;
import com.epam.poc.lambda.model.User;
import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The implementation of DAO layer contract for interacting with storage.
 * <p>
 * Date: 2018-02-15
 *
 * @author Raman_Khrapavitski
 */
public final class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    private static final UserDAOImpl INSTANCE = new UserDAOImpl();
    private final DynamoDBMapper dynamoDBMapper;

    private UserDAOImpl() {
        dynamoDBMapper = new DynamoDBMapper(
                AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build());
    }

    /**
     * Gets instance of UserDAOImpl.
     *
     * @return the instance of UserDAOImpl
     */
    public static UserDAOImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void addUser(User user) {
        try {
            LOGGER.debug("Attempt to save user - [{}]", user);
            dynamoDBMapper.save(user);
        } catch (AmazonDynamoDBException ex) {
            LOGGER.error("Some problems with saving user - [{}]. Message error: [{}]", user, ex.getMessage());
            throw new AwsRuntimeException(ex);
        }
    }

    @Override
    public Optional<User> getUser(String id) {
        try {
            LOGGER.debug("Attempt to get user by id - [{}]", id);
            return Optional.ofNullable(dynamoDBMapper.load(User.class, id));
        } catch (AmazonDynamoDBException ex) {
            LOGGER.error("Some problems with getting user by id - [{}]. Message error: [{}]",
                    id, ex.getMessage());
            throw new AwsRuntimeException(ex);
        }
    }

    @Override
    public List<User> getUsersByLastName(String lastName) {
        Map<String, AttributeValue> queryParams = ImmutableMap.<String, AttributeValue>builder()
                .put(":lastName", new AttributeValue().withS(lastName)).build();
        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withIndexName(User.LAST_NAME_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("LastName = :lastName")
                .withExpressionAttributeValues(queryParams);
        try {
            LOGGER.debug("Attempt to get user by last name - [{}]", lastName);
            return dynamoDBMapper.query(User.class, queryExpression);
        } catch (AmazonDynamoDBException ex) {
            LOGGER.error("Some problems with getting user by last name - [{}]. Message error: [{}]",
                    lastName, ex.getMessage());
            throw new AwsRuntimeException(ex);
        }
    }

    @Override
    public List<User> getUsersByFirstName(String firstName) {
        Map<String, AttributeValue> queryParams = ImmutableMap.<String, AttributeValue>builder()
                .put(":firstName", new AttributeValue().withS(firstName)).build();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("FirstName = :firstName")
                .withExpressionAttributeValues(queryParams);
        try {
            LOGGER.debug("Attempt to get user by first name - [{}]", firstName);
            return dynamoDBMapper.scan(User.class, scanExpression);
        } catch (AmazonDynamoDBException ex) {
            LOGGER.error("Some problems with getting user by first name - [{}]. Message error: [{}]",
                    firstName, ex.getMessage());
            throw new AwsRuntimeException(ex);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            LOGGER.debug("Attempt to update user - [{}]", user);
            Optional<User> userForUpdate = getUser(user.getId());
            if (userForUpdate.isPresent()) {
                dynamoDBMapper.save(user);
            }
        } catch (AmazonDynamoDBException ex) {
            LOGGER.error("Some problems with updating user - [{}]. Message error: [{}]",
                    user, ex.getMessage());
            throw new AwsRuntimeException(ex);
        }
    }

    @Override
    public void deleteUser(String id) {
        Optional<User> user = getUser(id);
        try {
            LOGGER.debug("Attempt to delete user with id - [{}]", id);
            user.ifPresent(dynamoDBMapper::delete);
        } catch (AmazonDynamoDBException ex) {
            LOGGER.error("Some problems with deleting user with id - [{}]. Message error: [{}]",
                    id, ex.getMessage());
            throw new AwsRuntimeException(ex);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            LOGGER.debug("Attempt to get all users");
            return dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
        } catch (AmazonDynamoDBException ex) {
            LOGGER.error("Some problems with getting all users. Message error: [{}]", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
