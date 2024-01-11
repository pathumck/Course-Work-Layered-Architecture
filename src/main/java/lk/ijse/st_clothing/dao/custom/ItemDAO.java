package lk.ijse.st_clothing.dao.custom;

import lk.ijse.st_clothing.dao.CRUDDAO;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CRUDDAO<Item> {
    boolean updateQtyWhenReturn(String code, int qty) throws SQLException, ClassNotFoundException;
    boolean updatesQtyWhenOrder(String code, int qty) throws SQLException, ClassNotFoundException;
    Integer lessThanFiveUnits() throws SQLException, ClassNotFoundException;
    Integer outOfStock() throws SQLException, ClassNotFoundException;
}
