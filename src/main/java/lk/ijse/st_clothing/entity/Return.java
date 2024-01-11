package lk.ijse.st_clothing.entity;

public class Return {
    private String returnId;
    private String date;
    private String time;
    private String customerId;

    public Return() {
    }

    public Return(String returnId, String date, String time, String customerId) {
        this.returnId = returnId;
        this.date = date;
        this.time = time;
        this.customerId = customerId;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Return{" +
                "returnId='" + returnId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
