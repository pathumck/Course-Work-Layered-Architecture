package lk.ijse.st_clothing.dto.tm;

import javafx.scene.control.Button;

public class ReturnCartTm {
    private String itemCode;
    private Double unitPrice;
    private Integer qty;
    private Double total;
    private Button btn;

    public ReturnCartTm() {
    }

    public ReturnCartTm(String itemCode, Double unitPrice, Integer qty, Double total, Button btn) {
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.total = total;
        this.btn = btn;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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
        return "ReturnCartTm{" +
                "itemCode='" + itemCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", total=" + total +
                ", btn=" + btn +
                '}';
    }
}
