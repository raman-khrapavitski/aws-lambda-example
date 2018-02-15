package com.epam.poc.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.epam.poc.lambda.dao.UserDAO;
import com.epam.poc.lambda.dao.UserDAOImpl;
import com.epam.poc.lambda.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

/**
 * Lambda function for creating user.
 * <p>
 * Date: 2018-02-15
 *
 * @author Raman_Khrapavitski
 */
public class CreateUserHandler implements RequestHandler<User, String> {

    private static final Logger LOGGER = LogManager.getLogger(CreateUserHandler.class);

    private static final UserDAO USER_DAO = UserDAOImpl.getInstance();

    @Override
    public String handleRequest(User user, Context context) {
        LOGGER.debug("Handle request that creates user");
        LOGGER.debug("Aws lambda get the following user object - [{}]", user);
        String userUuid = UUID.randomUUID().toString();
        user.setId(userUuid);
        USER_DAO.addUser(user);
        LOGGER.debug("User with id - [{}] was successfully stored.", userUuid);
        return userUuid;
    }
}
