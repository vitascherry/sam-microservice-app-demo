package com.example.accountservice.lambda;

import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.example.accountservice.domain.Account;

import java.util.HashMap;
import java.util.Map;

public class UpdateAccount extends RepositoryHandler<Account, Account> {

    @Override
    public Account handleRequest(Account account, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Updating account: " + account.getId());

        Map<String, AttributeValue> hashKey = new HashMap<>();
        hashKey.put("id", new AttributeValue().withS(account.getId()));

        HashMap<String, AttributeValueUpdate> updateValue = new HashMap<>();
        updateValue.put("balance",
                        new AttributeValueUpdate()
                            .withValue(new AttributeValue().withN(String.valueOf(account.getBalance())))
                            .withAction(AttributeAction.PUT));
        updateValue.put("status",
                        new AttributeValueUpdate()
                            .withValue(new AttributeValue().withS(account.getStatus().name()))
                            .withAction(AttributeAction.PUT));

        UpdateItemRequest request = new UpdateItemRequest()
            .withTableName("account")
            .withKey(hashKey)
            .withAttributeUpdates(updateValue);
        dynamoDB.updateItem(request);

        Account e = account;
        logger.log("Account: " + e.getId());
        return e;
    }
}
