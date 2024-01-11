package lk.ijse.st_clothing.bo.custom;

import lk.ijse.st_clothing.bo.SuperBO;
import lk.ijse.st_clothing.dto.ItemDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    String generateNextItemId() throws SQLException, ClassNotFoundException;
    ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException;
    boolean addItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    ItemDto searchItem(String id) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException;
}
