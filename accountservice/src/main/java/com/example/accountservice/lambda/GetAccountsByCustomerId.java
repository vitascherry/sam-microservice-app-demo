package com.example.accountservice.lambda;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.example.accountservice.domain.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAccountsByCustomerId extends RepositoryHandler<Account, List<Account>> {

    @Override
    public List<Account> handleRequest(Account account, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Customer: " + account.getCustomerId());

        Map<String, AttributeValue> m = new HashMap<>();
        m.put(":customerId", new AttributeValue().withS(account.getCustomerId()));

        DynamoDBQueryExpression<Account> qe = new DynamoDBQueryExpression<Account>()
            .withIndexName("customerId-index")
            .withKeyConditionExpression("customerId = :customerId")
            .withConsistentRead(false)
            .withExpressionAttributeValues(m);

        List<Account> accounts = repository.query(Account.class, qe);
        logger.log("Fetched accounts: " + accounts.size());
        return accounts;
    }
}
