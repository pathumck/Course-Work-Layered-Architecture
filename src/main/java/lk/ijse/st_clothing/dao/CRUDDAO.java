package lk.ijse.st_clothing.dao;

import lk.ijse.st_clothing.dto.ItemDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUDDAO <T> extends SuperDAO {
    String generateNextId() throws SQLException, ClassNotFoundException;
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    boolean add(T dto) throws SQLException, ClassNotFoundException;
    boolean update(T dto) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
    T search(String id) throws SQLException, ClassNotFoundException;
}
