package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public static String generateNextSupplierId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT supplierId FROM Suppliers WHERE supplierId LIKE 's00%' ORDER BY CAST(SUBSTRING(supplierId, 4) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitSupplierId(resultSet.getString(1));
        }
        return splitSupplierId(null);
    }

    private static String splitSupplierId(String supplierId) {
        if (supplierId == null || supplierId.isEmpty() || !supplierId.matches("^s\\d+$")) {
            return "s001";
        } else {
            String numericPart = supplierId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "s00" + nextNumericPart;
        }
    }


    public static ArrayList<SupplierDto> getAllSuppliers() throws SQLException {
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
