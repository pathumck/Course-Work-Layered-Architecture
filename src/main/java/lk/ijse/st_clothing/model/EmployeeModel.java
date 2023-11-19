package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public static ArrayList<EmployeeDto> getAllEmployee() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        ArrayList<EmployeeDto> dtos = new ArrayList<>();

        PreparedStatement pst = con.prepareStatement("SELECT * FROM Employee");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            EmployeeDto dto = new EmployeeDto();
            dto.setId(rs.getString(1));
            dto.setName(rs.getString(2));
            dto.setAddress(rs.getString(3));
            dto.setNic(rs.getString(4));
            dto.setGender(rs.getString(5));
            dto.setDob(rs.getString(6));
            dto.setDate(rs.getString(7));
            dto.setTp(rs.getString(8));

            dtos.add(dto);
        }
        return dtos;
    }

    public static Boolean deleteEmployee(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("DELETE FROM Employee WHERE employeeId = ?");
        pst.setString(1,id);

        return pst.executeUpdate()>0;
    }


    public static Boolean addEmployee(EmployeeDto dto) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("INSERT INTO Employee VALUES (?,?,?,?,?,?,?,?)");

        pst.setString(1,dto.getId());
        pst.setString(2,dto.getName());
        pst.setString(3,dto.getAddress());
        pst.setString(4,dto.getNic());
        pst.setString(5, dto.getGender());
        pst.setString(6, dto.getDob());
        pst.setString(7, dto.getDate());
        pst.setString(8, dto.getTp());

        return pst.executeUpdate()>0;
    }

    public static Boolean updateEmployee(EmployeeDto dto) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("UPDATE Employee SET name = ?, address = ?, nic = ?, gender = ?, dob = ?, tp = ? WHERE employeeId = ?");

        pst.setString(1,dto.getName());
        pst.setString(2,dto.getAddress());
        pst.setString(3,dto.getNic());
        pst.setString(4,dto.getGender());
        pst.setString(5,dto.getDob());
        pst.setString(6,dto.getTp());
        pst.setString(7,dto.getId());

        return pst.executeUpdate()>0;
    }

    public static ArrayList<String> getEmmployeeIds() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        ArrayList<String> employee = new ArrayList<>();
        PreparedStatement pst = con.prepareStatement("SELECT employeeId FROM Employee");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String id = rs.getString(1);
            employee.add(id);
        }
        return employee;
    }

    public static EmployeeDto getEmployeeById(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        EmployeeDto dto = null;
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Employee WHERE employeeId = ?");
        pst.setString(1,id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            dto = new EmployeeDto();
            String empId = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String nic = rs.getString(4);
            String gender = rs.getString(5);
            String dob = rs.getString(6);
            String date = rs.getString(7);
            String tp = rs.getString(8);
            dto.setId(empId);
            dto.setName(name);
            dto.setAddress(address);
            dto.setNic(nic);
            dto.setDob(dob);
            dto.setGender(gender);
            dto.setDate(date);
            dto.setTp(tp);
        }
        return dto;
    }


}
