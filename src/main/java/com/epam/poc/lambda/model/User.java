package com.epam.poc.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

/**
 * It represent an user int the system.
 * <p>
 * Date: 2018-02-15
 *
 * @author Raman_Khrapavitski
 */
@Data
@DynamoDBTable(tableName = "User")
public class User {
    /**
     * DynamoDB index name of last name column.
     */
    public static final String LAST_NAME_INDEX = "Last-Name-Index";

    @DynamoDBHashKey(attributeName = "Id")
    private String id;
    @DynamoDBAttribute(attributeName = "FirstName")
    private String firstName;
    @DynamoDBIndexHashKey(globalSecondaryIndexName = LAST_NAME_INDEX)
    @DynamoDBAttribute(attributeName = "LastName")
    private String lastName;
}
