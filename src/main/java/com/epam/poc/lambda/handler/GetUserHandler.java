package com.epam.poc.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.epam.poc.lambda.dao.UserDAO;
import com.epam.poc.lambda.dao.UserDAOImpl;
import com.epam.poc.lambda.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Lambda function for getting user by identifier.
 * <p>
 * Date: 2018-02-15
 *
 * @author Raman_Khrapavitski
 */
public class GetUserHandler implements RequestHandler<String, Optional<User>> {

    private static final Logger LOGGER = LogManager.getLogger(GetUserHandler.class);

    private static final UserDAO USER_DAO = UserDAOImpl.getInstance();

    @Override
    public Optional<User> handleRequest(String id, Context context) {
        LOGGER.debug("Handle request that gets user");
        LOGGER.debug("Aws lambda get the following user id - [{}]", id);
        return USER_DAO.getUser(id);
    }
}
