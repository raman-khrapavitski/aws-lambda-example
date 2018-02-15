package com.epam.poc.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.epam.poc.lambda.dao.UserDAO;
import com.epam.poc.lambda.dao.UserDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda function for deleting user.
 * <p>
 * Date: 2018-02-15
 *
 * @author Raman_Khrapavitski
 */
public class DeleteUserHandler {

    private static final Logger LOGGER = LogManager.getLogger(DeleteUserHandler.class);

    private static final UserDAO USER_DAO = UserDAOImpl.getInstance();

    /**
     * Handles a Lambda Function request.
     *
     * @param id      the user identifier.
     * @param context the aws context object.
     */
    public void handleRequest(String id, Context context) {
        LOGGER.debug("Handle request that deletes user");
        LOGGER.debug("Aws lambda get the following user id - [{}]", id);
        USER_DAO.deleteUser(id);
    }
}
