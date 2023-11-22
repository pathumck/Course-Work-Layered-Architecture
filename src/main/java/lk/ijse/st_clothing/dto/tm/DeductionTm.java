package lk.ijse.st_clothing.dto.tm;

import javafx.scene.control.Button;

public class DeductionTm {
    private String id;
    private Double deduction;
    private Button btn;

    public DeductionTm() {
    }

    public DeductionTm(String id, Double deduction, Button btn) {
        this.id = id;
        this.deduction = deduction;
        this.btn = btn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "DeductionTm{" +
                "id='" + id + '\'' +
                ", deduction=" + deduction +
                ", btn=" + btn +
                '}';
    }
}
