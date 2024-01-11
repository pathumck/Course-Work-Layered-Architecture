package lk.ijse.st_clothing.dao.custom;

import lk.ijse.st_clothing.dao.CRUDDAO;
import lk.ijse.st_clothing.dto.PlaceOrderDto;
import lk.ijse.st_clothing.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CRUDDAO<Order> {
    String isOrderSaved(String id) throws SQLException, ClassNotFoundException;
    Integer getOrderCount() throws SQLException, ClassNotFoundException;
}
