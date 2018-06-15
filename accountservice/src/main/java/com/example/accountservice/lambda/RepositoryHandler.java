package com.example.accountservice.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.RequestHandler;

abstract class RepositoryHandler<Input, Output> implements RequestHandler<Input, Output> {

    DynamoDBMapper repository;

    static final DynamoDBScanExpression LIST_OF_FIVE = new DynamoDBScanExpression().withLimit(5);

    RepositoryHandler() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .build();
        repository = new DynamoDBMapper(client);
    }
}
