package com.example.orderservice.lambda;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.example.orderservice.domain.OrderMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessOrder extends EventPublisher<DynamodbEvent, String> {

    private ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public String handleRequest(DynamodbEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        for (DynamodbStreamRecord record : event.getRecords()) {
            if ("INSERT".equals(record.getEventName())) {
                try {
                    Map<String, AttributeValue> newImage = record.getDynamodb().getNewImage();
                    List<Map<String, AttributeValue>> listOfMaps = new ArrayList<>();
                    listOfMaps.add(newImage);
                    List<Item> itemList = ItemUtils.toItemList(listOfMaps);
                    for (Item item : itemList) {
                        String msg = item.toJSON();
                        logger.log(String.format("SNS message: %s", msg));
                        String topicArn = client.createTopic("new-order").getTopicArn();
                        PublishRequest req = new PublishRequest(topicArn,
                                                                jsonMapper.writeValueAsString(new OrderMessage(msg)),
                                                                "New order processed");
                        req.setMessageStructure("json");
                        PublishResult res = client.publish(req);
                        logger.log(String.format("SNS message sent: %s", res.getMessageId()));
                    }
                } catch (JsonProcessingException e) {
                    logger.log(e.getMessage());
                }
            }
        }
        return "{\"StatusCode\": 200}";
    }
}
