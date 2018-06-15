package com.example.orderservice.lambda;

import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateOrderStatus extends RepositoryHandler<SNSEvent, String> {

    private ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public String handleRequest(SNSEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        for (SNSEvent.SNSRecord record : event.getRecords()) {
            logger.log(String.format("SNS Event: %s, %s",
                                     record.getSNS().getMessageId(),
                                     record.getSNS().getMessage()));
            try {
                Order o = jsonMapper.readValue(record.getSNS().getMessage(), Order.class);
                if (o.getStatus().ordinal() == OrderStatus.COMPLETE.ordinal()) {
                    logger.log(String.format("Order complete: id = %s, amount = %d", o.getId(), o.getPrice()));
                } else {
                    logger.log(String.format("Order cancelled: id = %s, amount = %d", o.getId(), o.getPrice()));
                }

                Map<String, AttributeValue> hashKey = new HashMap<>();
                hashKey.put("id", new AttributeValue().withS(o.getId()));

                HashMap<String, AttributeValueUpdate> updateValue = new HashMap<>();
                updateValue.put("status",
                                   new AttributeValueUpdate()
                                       .withValue(new AttributeValue().withS(o.getStatus().name()))
                                       .withAction(AttributeAction.PUT));

                UpdateItemRequest request = new UpdateItemRequest()
                    .withTableName("order")
                    .withKey(hashKey)
                    .withAttributeUpdates(updateValue);
                dynamoDB.updateItem(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "{\"StatusCode\": 200}";
    }
}
