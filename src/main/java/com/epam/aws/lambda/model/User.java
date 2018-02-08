package com.epam.aws.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "User")
public class User {
    @DynamoDBHashKey(attributeName = "Id")
    private String id;
    @DynamoDBAttribute(attributeName = "FirstName")
    private String firstName;
    @DynamoDBAttribute(attributeName = "LastName")
    private String lastName;
}
