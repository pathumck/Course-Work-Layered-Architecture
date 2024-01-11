package lk.ijse.st_clothing.bo.custom;

import lk.ijse.st_clothing.bo.SuperBO;
import lk.ijse.st_clothing.dto.CustomerDto;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.PlaceOrderDto;
import lk.ijse.st_clothing.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderBO extends SuperBO {
    String generateNextOrderId() throws SQLException, ClassNotFoundException;
    String isOrderSaved(String id) throws SQLException, ClassNotFoundException;
    boolean saveOrder(PlaceOrderDto dto) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException;
    ItemDto searchItem(String id) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
    CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;
    Double getDeductionById(String id) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllReturnIds() throws SQLException, ClassNotFoundException;
    boolean placeOrder(PlaceOrderDto placeOrderDto) throws SQLException;
    boolean updateItemWhenOrder(List<CartTm> cartTmList) throws SQLException;
    boolean saveOrderDetails(String orderId, List<CartTm> cartTmList) throws SQLException;
}
