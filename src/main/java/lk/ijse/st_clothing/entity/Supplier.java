package lk.ijse.st_clothing.entity;

public class Supplier {
    private String id;
    private String name;
    private String address;
    private String tp;
    private String date;

    public Supplier() {
    }

    public Supplier(String id, String name, String address, String tp, String date) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tp = tp;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tp='" + tp + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
