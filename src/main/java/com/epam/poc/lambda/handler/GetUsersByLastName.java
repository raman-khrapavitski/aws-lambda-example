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
 * Lambda function for getting users by last name.
 * <p>
 * Date: 2018-02-15
 *
 * @author Raman_Khrapavitski
 */
public class GetUsersByLastName implements RequestHandler<String, List<User>> {

    private static final Logger LOGGER = LogManager.getLogger(GetUsersByLastName.class);

    private static final UserDAO USER_DAO = UserDAOImpl.getInstance();

    @Override
    public List<User> handleRequest(String lastName, Context context) {
        LOGGER.debug("Handle request that gets users by last name");
        LOGGER.debug("Aws lambda get the following last name - [{}]", lastName);
        return USER_DAO.getUsersByLastName(lastName);
    }
}
