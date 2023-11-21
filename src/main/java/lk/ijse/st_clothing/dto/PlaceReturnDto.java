package lk.ijse.st_clothing.dto;

import lk.ijse.st_clothing.dto.tm.ReturnCartTm;

import java.util.ArrayList;
import java.util.List;

public class PlaceReturnDto {

    private String returnId;
    private String customerId;
    private String date;
    private String currentTimeString;
    private List<ReturnCartTm> list = new ArrayList<>();

    public PlaceReturnDto() {
    }

    public PlaceReturnDto(String returnId, String customerId, String date, String currentTimeString, List<ReturnCartTm> list) {
        this.returnId = returnId;
        this.customerId = customerId;
        this.date = date;
        this.currentTimeString = currentTimeString;
        this.list = list;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrentTimeString() {
        return currentTimeString;
    }

    public void setCurrentTimeString(String currentTimeString) {
        this.currentTimeString = currentTimeString;
    }

    public List<ReturnCartTm> getList() {
        return list;
    }

    public void setList(List<ReturnCartTm> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PlaceReturnDto{" +
                "returnId='" + returnId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", date='" + date + '\'' +
                ", currentTimeString='" + currentTimeString + '\'' +
                ", list=" + list +
                '}';
    }
}
