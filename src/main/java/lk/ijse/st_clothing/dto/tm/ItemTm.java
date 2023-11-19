package lk.ijse.st_clothing.dto.tm;

import javafx.scene.control.Button;

public class ItemTm {
   private String itemCode;
   private String supplierId;
   private String description;
   private Double unitPrice;
   private Integer qty;
   private String size;
   private Button btn;

   public ItemTm() {
   }

    public ItemTm(String itemCode, String supplierId, String description, Double unitPrice, Integer qty, String size, Button btn) {
        this.itemCode = itemCode;
        this.supplierId = supplierId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.size = size;
        this.btn = btn;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ItemTm{" +
                "itemCode='" + itemCode + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty='" + qty + '\'' +
                ", size='" + size + '\'' +
                ", btn=" + btn +
                '}';
    }
}
