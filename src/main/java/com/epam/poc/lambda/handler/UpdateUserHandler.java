package com.epam.poc.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.epam.poc.lambda.dao.UserDAO;
import com.epam.poc.lambda.dao.UserDAOImpl;
import com.epam.poc.lambda.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda function for updating user.
 * <p>
 * Date: 2018-02-15
 *
 * @author Raman_Khrapavitski
 */
public class UpdateUserHandler {

    private static final Logger LOGGER = LogManager.getLogger(UpdateUserHandler.class);

    private static final UserDAO USER_DAO = UserDAOImpl.getInstance();

    /**
     * Handles a Lambda Function request.
     *
     * @param user    the user object.
     * @param context the aws context object.
     */
    public void handleRequest(User user, Context context) {
        LOGGER.debug("Handle request that updates user");
        LOGGER.debug("Aws lambda get the following user object - [{}]", user);
        USER_DAO.updateUser(user);
    }
}
