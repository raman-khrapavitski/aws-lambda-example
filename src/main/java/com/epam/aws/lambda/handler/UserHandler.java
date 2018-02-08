package com.epam.aws.lambda.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.epam.aws.lambda.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UserHandler implements RequestHandler<User, List<User>> {

    private static final Logger LOGGER = LogManager.getLogger(UserHandler.class);
    private static final String USER_TABLE_NAME = "User";

    private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    private DynamoDB dynamoDB = new DynamoDB(client);

    @Override
    public List<User> handleRequest(User user, Context context) {
        LOGGER.debug("Aws lambda get the following user object - [{}]", user);
        createUser(user);
        LOGGER.debug("Saved Successfully");
        return findAllUsers();
    }

    private void createUser(User user) {
        try {
            Table table = dynamoDB.getTable(USER_TABLE_NAME);
            Item item = new Item().withPrimaryKey("Id", UUID.randomUUID().toString())
                    .withString("FirstName", user.getFirstName())
                    .withString("LastName", user.getLastName());
            table.putItem(item);
        } catch (Exception ex) {
            LOGGER.error("Some problems with saving user", ex.getMessage());
        }
    }

    private List<User> findAllUsers() {
        List<User> users = Collections.emptyList();
        try {
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            users = mapper.scan(User.class, new DynamoDBScanExpression());
        } catch (Exception ex) {
            LOGGER.error("Some problems with getting all users", ex.getMessage());
        }
        return users;
    }
}
