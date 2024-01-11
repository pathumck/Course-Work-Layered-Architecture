package lk.ijse.st_clothing.dao.custom.impl;

import lk.ijse.st_clothing.dao.SQLUtil;
import lk.ijse.st_clothing.dao.custom.EmployeeDAO;
import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.EmployeeDto;
import lk.ijse.st_clothing.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT employeeId FROM Employee WHERE employeeId LIKE 'e00%' ORDER BY CAST(SUBSTRING(employeeId, 4) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }

        return null;
    }
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<Employee> employee = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT * FROM Employee");

        while (rs.next()) {
            Employee emp = new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),rs.getString(8));
            employee.add(emp);
        }

        return employee;
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM Employee WHERE employeeId = ?",id);
    }
    @Override
    public boolean add(Employee employee) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO Employee VALUES (?,?,?,?,?,?,?,?)",
                employee.getId(),employee.getName(),employee.getAddress(),employee.getNic(),employee.getGender(),employee.getDob(),employee.getDate(),employee.getTp());
    }
    @Override
    public boolean update(Employee employee) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE Employee SET name = ?, address = ?, nic = ?, gender = ?, dob = ?, tp = ? WHERE employeeId = ?",
                employee.getName(),employee.getAddress(),employee.getNic(),employee.getGender(),employee.getDob(),employee.getTp(),employee.getId());
    }
    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {

        ArrayList<String> employee = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT employeeId FROM Employee");

        while (rs.next()) {
            String id = rs.getString(1);
            employee.add(id);
        }

        return employee;
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {

        return null;
    }

}
