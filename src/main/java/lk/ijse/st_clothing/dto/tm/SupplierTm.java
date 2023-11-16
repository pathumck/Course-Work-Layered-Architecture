package lk.ijse.st_clothing.dto.tm;

import javafx.scene.control.Button;

public class SupplierTm {
    private String id;
    private String name;
    private String tp;
    private String address;
    private String date;
    private Button btn;

    public SupplierTm() {
    }

    public SupplierTm(String id, String name, String tp, String address, String date, Button btn) {
        this.id = id;
        this.name = name;
        this.tp = tp;
        this.address = address;
        this.date = date;
        this.btn = btn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "SupplierTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tp='" + tp + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", btn=" + btn +
                '}';
    }
}
