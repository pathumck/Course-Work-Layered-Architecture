package lk.ijse.st_clothing.entity;

public class OrderDetail {
    private String itemCode;
    private String orderId;
    private Double unitPrice;
    private Integer qty;

    public OrderDetail() {
    }

    public OrderDetail(String itemCode, String orderId, Double unitPrice, Integer qty) {
        this.itemCode = itemCode;
        this.orderId = orderId;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", qty=" + qty +
                '}';
    }
}
