package BusinessLayer;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Order {
    private int orderID, clientID;
    private LocalDateTime orderDate;

    public Order(int orderID, int clientID, LocalDateTime orderDate) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (getClass() != object.getClass())
            return false;

        Order order = (Order) object;
        if(getClientID()!= order.getClientID() || getOrderID()!= order.getOrderID())
            return false;
        return (getOrderDate() != null || order.getOrderDate() == null) && (getOrderDate() == null || order.getOrderDate() != null);
    }

    @Override
    public int hashCode(){
        return getClientID() + getOrderDate().hashCode() +getOrderID();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", clientID=" + clientID +
                ", orderDate=" + orderDate +
                '}';
    }
}
