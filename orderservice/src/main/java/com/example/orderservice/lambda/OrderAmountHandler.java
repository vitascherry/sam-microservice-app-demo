package com.example.orderservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNSRecord;
import com.example.orderservice.domain.Account;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class OrderAmountHandler extends RepositoryHandler<SNSEvent, String> {

    private ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public String handleRequest(SNSEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        for (SNSRecord record : event.getRecords()) {
            logger.log(String.format("SNS Event: %s, %s",
                                     record.getSNS().getMessageId(),
                                     record.getSNS().getMessage()));
            try {
                Order o = jsonMapper.readValue(record.getSNS().getMessage(), Order.class);
                Account a = repository.load(Account.class, o.getAccountId());
                if (o.getAmount() > a.getBalance()) {
                    o.setStatus(OrderStatus.CANCELED);
                    logger.log(String.format("Order rejected: id = %s, amount = %d", o.getId(), o.getAmount()));
                } else {
                    logger.log(String.format("Order allowed: id = %s, amount = %d", o.getId(), o.getAmount()));
                    a.setBalance(a.getBalance() - o.getAmount());
                    repository.save(a);
                    o.setStatus(OrderStatus.COMPLETE);
                    logger.log(String.format("Account balance update: id = %s, amount = %d",
                                             a.getId(),
                                             a.getBalance()));
                }
                repository.save(o);
            } catch (IOException e) {
                logger.log(e.getMessage());
            }
        }
        return "{\"StatusCode\": 200}";
    }
}
