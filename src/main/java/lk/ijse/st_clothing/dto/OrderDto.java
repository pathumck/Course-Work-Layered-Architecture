package lk.ijse.st_clothing.dto;

public class OrderDto {
    private String orderId;
    private String  date;
    private String time;
    private String customerId;

    public OrderDto() {
    }

    public OrderDto(String orderId, String date, String time, String customerId) {
        this.orderId = orderId;
        this.date = date;
        this.time = time;
        this.customerId = customerId;
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
