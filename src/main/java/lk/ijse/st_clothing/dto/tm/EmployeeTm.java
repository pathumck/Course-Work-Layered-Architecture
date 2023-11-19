package lk.ijse.st_clothing.dto.tm;

import javafx.scene.control.Button;

public class EmployeeTm {
    private String id;
    private String name;
    private String address;
    private String nic;
    private String gender;
    private String dob;
    private String date;
    private String tp;
    private Button btn;

    public EmployeeTm() {
    }

    public EmployeeTm(String id, String name, String address, String nic, String gender, String dob, String date, String tp, Button btn) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.gender = gender;
        this.dob = dob;
        this.date = date;
        this.tp = tp;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "EmployeeTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", nic='" + nic + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", date='" + date + '\'' +
                ", tp='" + tp + '\'' +
                ", btn=" + btn +
                '}';
    }
}
