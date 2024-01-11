package lk.ijse.st_clothing.bo.custom;

import lk.ijse.st_clothing.bo.SuperBO;
import lk.ijse.st_clothing.dto.ExpenceDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ExpenceBO extends SuperBO {
    ArrayList<ExpenceDto> getAllExpences() throws SQLException, ClassNotFoundException;
    boolean deleteExpence(String id) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllExpenceIds() throws SQLException, ClassNotFoundException;
    boolean addExpence(ExpenceDto dto) throws SQLException, ClassNotFoundException;
    boolean updateExpence(ExpenceDto dto) throws SQLException, ClassNotFoundException;
}
