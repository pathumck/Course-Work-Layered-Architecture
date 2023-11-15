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
}
