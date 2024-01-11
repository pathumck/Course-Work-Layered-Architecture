package lk.ijse.st_clothing.bo.custom.impl;

import lk.ijse.st_clothing.bo.custom.EmployeeBO;
import lk.ijse.st_clothing.dao.DAOFactory;
import lk.ijse.st_clothing.dao.custom.EmployeeDAO;
import lk.ijse.st_clothing.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.st_clothing.dto.EmployeeDto;
import lk.ijse.st_clothing.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public String generateNextEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNextId();
    }
    @Override
    public ArrayList<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employee = employeeDAO.getAll();
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee emp : employee) {
            EmployeeDto dto = new EmployeeDto(emp.getId(),emp.getName(),emp.getAddress(),emp.getNic(),emp.getGender(),emp.getDob(),emp.getDate(),emp.getTp());
            employeeDtos.add(dto);
        }
        return employeeDtos;
    }
    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }
    @Override
    public ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAllIds();
    }
    @Override
    public boolean addEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new Employee(dto.getId(),dto.getName(),dto.getAddress(),dto.getNic(),dto.getGender(),dto.getDob(),dto.getDate(),dto.getTp()));
    }
    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getId(),dto.getName(),dto.getAddress(),dto.getNic(),dto.getGender(),dto.getDob(),dto.getDate(),dto.getTp()));
    }
}
