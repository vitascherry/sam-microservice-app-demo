package com.example.accountservice.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNSRecord;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.example.accountservice.domain.Account;
import com.example.accountservice.domain.EventMessage;
import com.example.accountservice.domain.Order;
import com.example.accountservice.domain.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ProcessOrder extends RepositoryHandler<SNSEvent, String> {

    private ObjectMapper jsonMapper = new ObjectMapper();

    private AmazonSNS sns;

    public ProcessOrder() {
        sns = AmazonSNSClientBuilder
            .standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .build();
    }

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
                if (o.getPrice() > a.getBalance()) {
                    logger.log(String.format("Order rejected: id = %s, amount = %d", o.getId(), o.getPrice()));
                    o.setStatus(OrderStatus.CANCELED);
                } else {
                    logger.log(String.format("Order allowed: id = %s, amount = %d", o.getId(), o.getPrice()));
                    a.setBalance(a.getBalance() - o.getPrice());
                    repository.save(a);
                    o.setStatus(OrderStatus.COMPLETE);
                    logger.log(String.format("Account update: id = %s, amount = %d", a.getId(), a.getBalance()));
                }
                String msg = jsonMapper.writeValueAsString(o);
                logger.log(String.format("SNS message: %s", msg));
                PublishRequest req = new PublishRequest(sns.createTopic("close-order").getTopicArn(),
                                                        jsonMapper.writeValueAsString(new EventMessage(msg)),
                                                        "Close order");
                req.setMessageStructure("json");
                PublishResult res = sns.publish(req);
                logger.log(String.format("SNS message sent: %s", res.getMessageId()));
            } catch (IOException e) {
                logger.log(e.getMessage());
            }
        }
        return "{\"StatusCode\": 200}";
    }
}
