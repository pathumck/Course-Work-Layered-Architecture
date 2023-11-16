package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.CustomerDto;

import java.sql.*;
import java.util.ArrayList;

public class CustomerModel {
    public static ArrayList<CustomerDto> getAllCustomers () throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT * FROM Customers");
        ResultSet rs = pst.executeQuery();

        ArrayList<CustomerDto> dtos = new ArrayList<>();

        while (rs.next()) {
            CustomerDto dto = new CustomerDto();
            String id = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String tp = rs.getString(4);
            String date = rs.getString(5);

            dto.setId(id);
            dto.setName(name);
            dto.setAddress(address);
            dto.setTp(tp);
            dto.setDate(date);

            dtos.add(dto);
        }
        return dtos;
    }

    public static Boolean addCustomer(CustomerDto dto) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("INSERT INTO Customers VALUES (?,?,?,?,?)");

        pst.setString(1,dto.getId());
        pst.setString(2,dto.getName());
        pst.setString(3,dto.getAddress());
        pst.setString(4,dto.getTp());
        pst.setString(5, dto.getDate());

        return pst.executeUpdate()>0;
    }

    public static Boolean updateCustomer(CustomerDto dto) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("UPDATE Customers SET name = ?, address = ?, tp = ? WHERE customerId = ?");

        pst.setString(1,dto.getName());
        pst.setString(2,dto.getAddress());
        pst.setString(3,dto.getTp());
        pst.setString(4,dto.getId());

        return pst.executeUpdate()>0;
    }

    public  static Boolean deleteCustomer(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("DELETE FROM Customers WHERE customerId = ?");
        pst.setString(1,id);

        return pst.executeUpdate()>0;
    }

    public static ArrayList<String> getCustomerIds() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        ArrayList<String> items = new ArrayList<>();
        PreparedStatement pst = con.prepareStatement("SELECT customerId FROM Customers");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String id = rs.getString(1);
            items.add(id);
        }
        return items;
    }

    public static CustomerDto getCustomerById(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        CustomerDto dto = null;
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Customers WHERE customerId = ?");
        pst.setString(1,id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            dto = new CustomerDto();
            String cusId = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String tp = rs.getString(4);
            String date = rs.getString(5);
            dto.setName(name);
            dto.setId(id);
            dto.setAddress(address);
            dto.setDate(date);
            dto.setTp(tp);
        }
        return dto;
    }
}
