package lk.ijse.st_clothing.entity;

public class Item {
    private String itemCode;
    private Integer qty;
    private String supplierId;
    private String description;
    private Double unitPrice;
    private String size;

    public Item() {
    }

    public Item(String itemCode, Integer qty, String supplierId, String description, Double unitPrice, String size) {
        this.itemCode = itemCode;
        this.qty = qty;
        this.supplierId = supplierId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.size = size;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", qty='" + qty + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
