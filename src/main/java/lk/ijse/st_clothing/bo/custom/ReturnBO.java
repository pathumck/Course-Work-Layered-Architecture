package lk.ijse.st_clothing.bo.custom;

import lk.ijse.st_clothing.bo.SuperBO;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.PlaceOrderDto;
import lk.ijse.st_clothing.dto.PlaceReturnDto;
import lk.ijse.st_clothing.dto.tm.ReturnCartTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ReturnBO extends SuperBO {
    ItemDto searchReturnId(String id) throws SQLException, ClassNotFoundException;
    String generateNextReturnId() throws SQLException, ClassNotFoundException;
    String isReturnSaved(String id) throws SQLException, ClassNotFoundException;
    boolean saveReturn(PlaceReturnDto dto) throws SQLException, ClassNotFoundException;
    ArrayList<String> getOrderIds() throws SQLException, ClassNotFoundException;
    PlaceOrderDto searchOrder(String id) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException;
    Boolean placeReturn(PlaceReturnDto placeReturnDto) throws SQLException;
    boolean saveReturnDetails(String returnId, List<ReturnCartTm> cartTmList) throws SQLException;
    boolean updateItems(List<ReturnCartTm> cartTmList) throws SQLException;
}
