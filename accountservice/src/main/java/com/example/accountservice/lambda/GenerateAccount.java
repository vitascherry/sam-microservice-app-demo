package com.example.accountservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.example.accountservice.domain.Account;
import com.example.accountservice.domain.AccountStatus;
import com.example.accountservice.domain.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GenerateAccount extends RepositoryHandler<SNSEvent, String> {

    private ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public String handleRequest(SNSEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        for (SNSEvent.SNSRecord record : event.getRecords()) {
            logger.log(String.format("SNS Event: %s, %s",
                                     record.getSNS().getMessageId(),
                                     record.getSNS().getMessage()));
            try {
                Customer c = jsonMapper.readValue(record.getSNS().getMessage(), Customer.class);
                Account a = new Account();
                a.setCustomerId(c.getId());
                a.setStatus(AccountStatus.ACTIVE);
                a.setBalance(0);
                repository.save(a);
                context.getLogger().log("Account generated: " + a.getId());
            } catch (IOException e) {
                logger.log(e.getMessage());
            }
        }
        return "{\"StatusCode\": 200}";
    }
}
