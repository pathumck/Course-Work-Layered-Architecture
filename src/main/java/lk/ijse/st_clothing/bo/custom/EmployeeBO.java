package lk.ijse.st_clothing.bo.custom;

import lk.ijse.st_clothing.bo.SuperBO;
import lk.ijse.st_clothing.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    String generateNextEmployeeId() throws SQLException, ClassNotFoundException;
    ArrayList<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException;
    boolean addEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
}
