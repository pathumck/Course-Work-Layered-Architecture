package lk.ijse.st_clothing.dto;

public class ItemDto {
    private String itemCode;
    private String supplierId;
    private String description;
    private Double unitPrice;
    private Integer qty;
    private String size;

    public ItemDto() {
    }

    public ItemDto(String itemCode, String supplierId, String description, Double unitPrice, Integer qty, String size) {
        this.itemCode = itemCode;
        this.supplierId = supplierId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.size = size;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "itemCode='" + itemCode + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", qty='" + qty + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
