package com.epam.poc.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.epam.poc.lambda.dao.UserDAO;
import com.epam.poc.lambda.dao.UserDAOImpl;
import com.epam.poc.lambda.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Lambda function for getting all users.
 * <p>
 * Date: 2018-02-15
 *
 * @author Raman_Khrapavitski
 */
public class GetAllUsersHandler implements RequestHandler<Void, List<User>> {

    private static final Logger LOGGER = LogManager.getLogger(CreateUserHandler.class);

    private static final UserDAO USER_DAO = UserDAOImpl.getInstance();

    @Override
    public List<User> handleRequest(Void input, Context context) {
        LOGGER.debug("Handle request that gets all users");
        return USER_DAO.getAllUsers();
    }
}
