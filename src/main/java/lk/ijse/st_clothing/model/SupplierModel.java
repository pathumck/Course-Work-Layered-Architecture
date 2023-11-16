package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public static ArrayList<SupplierDto> getALlSuppliers() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        ArrayList<SupplierDto> dtos = new ArrayList<>();

        PreparedStatement pst = con.prepareStatement("SELECT * FROM Suppliers");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            SupplierDto dto = new SupplierDto();
            dto.setId(rs.getString(1));
            dto.setName(rs.getString(2));
            dto.setTp(rs.getString(3));
            dto.setAddress(rs.getString(4));
            dto.setDate(rs.getString(5));
            dtos.add(dto);
        }
        return dtos;
    }

    public static Boolean deleteSupplier(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("DELETE FROM Suppliers WHERE supplierId = ?");
        pst.setString(1,id);

        return pst.executeUpdate()>0;
    }

    public static Boolean addSupplier(SupplierDto dto) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("INSERT INTO Suppliers VALUES (?,?,?,?,?)");

        pst.setString(1,dto.getId());
        pst.setString(2,dto.getName());
        pst.setString(3,dto.getAddress());
        pst.setString(4,dto.getTp());
        pst.setString(5, dto.getDate());

        return pst.executeUpdate()>0;
    }

    public static Boolean updateSupplier(SupplierDto dto) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("UPDATE Suppliers SET name = ?, address = ?, tp = ? WHERE supplierId = ?");

        pst.setString(1,dto.getName());
        pst.setString(2,dto.getAddress());
        pst.setString(3,dto.getTp());
        pst.setString(4,dto.getId());

        return pst.executeUpdate()>0;
    }

    public static ArrayList<String> getSupplierIds() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        ArrayList<String> suppliers = new ArrayList<>();
        PreparedStatement pst = con.prepareStatement("SELECT supplierId FROM Suppliers");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String id = rs.getString(1);
            suppliers.add(id);
        }
        return suppliers;
    }

    public static SupplierDto getSupplierById(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        SupplierDto dto = null;
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Suppliers WHERE supplierId = ?");
        pst.setString(1,id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            dto = new SupplierDto();
            String supId = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String tp = rs.getString(4);
            String date = rs.getString(5);
            dto.setName(name);
            dto.setId(supId);
            dto.setAddress(address);
            dto.setDate(date);
            dto.setTp(tp);
        }
        return dto;
    }


}
