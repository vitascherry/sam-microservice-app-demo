package com.example.orderservice.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

abstract class EventPublisher<Event, Response> implements RequestHandler<Event, Response> {

    AmazonSNS client;

    EventPublisher() {
        client = AmazonSNSClientBuilder
            .standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .build();
    }
}
