package lk.ijse.st_clothing.dto;

import lk.ijse.st_clothing.dto.tm.CartTm;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderDto {
    private String orderId;
    private String  date;
    private String time;
    private String customerId;
    private List<CartTm> cartTms = new ArrayList<>();

    public PlaceOrderDto() {
    }


    public PlaceOrderDto(String orderId, String date, String time, String customerId, List<CartTm> cartTms) {
        this.orderId = orderId;
        this.date = date;
        this.time = time;
        this.customerId = customerId;
        this.cartTms = cartTms;
    }

    public PlaceOrderDto(String orderId, String date, String time, String customerId) {
        this.orderId = orderId;
        this.date = date;
        this.time = time;
        this.customerId = customerId;
    }

    public List<CartTm> getCartTms() {
        return cartTms;
    }

    public void setCartTms(List<CartTm> cartTms) {
        this.cartTms = cartTms;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
