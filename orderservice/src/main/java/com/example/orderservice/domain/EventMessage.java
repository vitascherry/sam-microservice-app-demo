package com.example.orderservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventMessage {

    @JsonProperty("default")
    private String message;

    public EventMessage() {
    }

    public EventMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventMessage that = (EventMessage) o;

        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
               "message='" + message + '\'' +
               '}';
    }
}
