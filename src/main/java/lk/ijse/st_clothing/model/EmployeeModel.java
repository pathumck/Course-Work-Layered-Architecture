package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public static String generateNextEmployeeId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT employeeId FROM Employee WHERE employeeId LIKE 'e00%' ORDER BY CAST(SUBSTRING(employeeId, 4) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitEmployeeId(resultSet.getString(1));
        }
        return splitEmployeeId(null);
    }

    private static String splitEmployeeId(String currentEmpId) {
        if (currentEmpId == null || currentEmpId.isEmpty() || !currentEmpId.matches("^e\\d+$")) {
            return "e001";
        } else {
            String numericPart = currentEmpId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "e00" + nextNumericPart;
        }
    }


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
}
