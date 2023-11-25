package lk.ijse.st_clothing.dto.tm;

import javafx.scene.control.Button;

public class CartTm {
    private String itemCode;
    private String description;
    private Integer qty;
    private Double UnitPrice;
    private Double total;
    private Button btn;

    public CartTm() {
    }

    public CartTm(String itemCode, String description, Integer qty, Double unitPrice, Double total) {
        this.itemCode = itemCode;
        this.description = description;
        this.qty = qty;
        UnitPrice = unitPrice;
        this.total = total;
    }

    public CartTm(String itemCode, String description, Integer qty, Double unitPrice, Double total, Button btn) {
        this.itemCode = itemCode;
        this.description = description;
        this.qty = qty;
        UnitPrice = unitPrice;
        this.total = total;
        this.btn = btn;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        UnitPrice = unitPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "CartTm{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", UnitPrice=" + UnitPrice +
                ", total=" + total +
                ", btn=" + btn +
                '}';
    }
}
