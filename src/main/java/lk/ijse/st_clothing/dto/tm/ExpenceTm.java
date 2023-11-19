package lk.ijse.st_clothing.dto.tm;

import javafx.scene.control.Button;

public class ExpenceTm {
    private String id;
    private String type;
    private String description;
    private String date;
    private Double amount;
    private Button btn;

    public ExpenceTm() {
    }

    public ExpenceTm(String id, String type, String description, String date, Double amount, Button btn) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.btn = btn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ExpenceTm{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", btn=" + btn +
                '}';
    }
}
