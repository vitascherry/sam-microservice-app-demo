package com.example.accountservice.domain;

import java.io.Serializable;

public class Order implements Serializable {

    private static final long serialVersionUID = 8331074361667921244L;

    private String id;

    private String accountId;

    private int price;

    private OrderStatus status;

    public Order() {
    }

    public Order(String id, String accountId, int price, OrderStatus status) {
        this.id = id;
        this.accountId = accountId;
        this.price = price;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) {
            return false;
        }
        return accountId != null ? accountId.equals(order.accountId) : order.accountId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
               "id='" + id + '\'' +
               ", accountId='" + accountId + '\'' +
               ", price=" + price +
               ", status=" + status +
               '}';
    }
}
