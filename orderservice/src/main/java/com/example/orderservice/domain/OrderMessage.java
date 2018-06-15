package com.example.orderservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderMessage {

    @JsonProperty("default")
    private String message;

    public OrderMessage() {
    }

    public OrderMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
               "message='" + message + '\'' +
               '}';
    }
}
