package org.domi.model;

public class OrderSuccessEvent {
    private String orderId;
    private String product;
    private int price;
    private String customerID;
    private long orderDatabaseId;

    public OrderSuccessEvent() {
    }

    public OrderSuccessEvent(String orderId, String product, int price, String customerID, long orderDatabaseId) {
        this.orderId = orderId;
        this.product = product;
        this.price = price;
        this.customerID = customerID;
        this.orderDatabaseId = orderDatabaseId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public long getOrderDatabaseId() {
        return orderDatabaseId;
    }

    public void setOrderDatabaseId(long orderDatabaseId) {
        this.orderDatabaseId = orderDatabaseId;
    }

    @Override
    public String toString() {
        return "OrderSuccessEvent{" +
                "orderId='" + orderId + '\'' +
                ", product='" + product + '\'' +
                ", price=" + price +
                ", customerID='" + customerID + '\'' +
                ", orderDatabaseId=" + orderDatabaseId +
                '}';
    }
}

