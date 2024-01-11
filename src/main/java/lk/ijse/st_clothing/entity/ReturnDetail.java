package lk.ijse.st_clothing.entity;

public class ReturnDetail {
    private String returnId;
    private String itemCode;
    private Integer qty;
    private Double unitPrice;

    public ReturnDetail() {
    }

    public ReturnDetail(String returnId, String itemCode, Integer qty, Double unitPrice) {
        this.returnId = returnId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ReturnDetail{" +
                "returnId='" + returnId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty='" + qty + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
