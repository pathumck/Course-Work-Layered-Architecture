package lk.ijse.st_clothing.dto;

public class SupplierDto {
    private String id;
    private String name;
    private String tp;
    private String address;
    private String date;

    public SupplierDto() {
    }

    public SupplierDto(String id, String name, String tp, String address) {
        this.id = id;
        this.name = name;
        this.tp = tp;
        this.address = address;
    }

    public SupplierDto(String id, String name, String tp, String address, String date) {
        this.id = id;
        this.name = name;
        this.tp = tp;
        this.address = address;
        this.date = date;
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

    @Override
    public String toString() {
        return "SupplierDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tp='" + tp + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
