package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.tm.CartTm;
import lk.ijse.st_clothing.dto.tm.ReturnCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemsModel {
    public static String generateNextItemCode() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT itemCode FROM Items WHERE itemCode LIKE 'i00%' ORDER BY CAST(SUBSTRING(itemCode, 4) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitItemCode(resultSet.getString(1));
        }
        return splitItemCode(null);
    }

    private static String splitItemCode(String currentItemCode) {
        if (currentItemCode == null || currentItemCode.isEmpty() || !currentItemCode.matches("^i\\d+$")) {
            return "i001";
        } else {
            String numericPart = currentItemCode.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "i00" + nextNumericPart;
        }
    }

    public static ArrayList<ItemDto> getAllItems() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        ArrayList<ItemDto> dtos = new ArrayList<>();

        PreparedStatement pst = con.prepareStatement("SELECT * FROM Items");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            ItemDto dto = new ItemDto();
            dto.setItemCode(rs.getString(1));
            dto.setQty(rs.getInt(2));
            dto.setSupplierId(rs.getString(3));
            dto.setDescription(rs.getString(4));
            dto.setUnitPrice(rs.getDouble(5));
            dto.setSize(rs.getString(6));
            dtos.add(dto);
        }
        return dtos;
    }

    public static Boolean deleteItem(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("DELETE FROM Items WHERE itemCode = ?");
        pst.setString(1,id);

        return pst.executeUpdate()>0;
    }

    public static Boolean addItems(ItemDto dto) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("INSERT INTO Items VALUES (?,?,?,?,?,?)");

        pst.setString(1,dto.getItemCode());
        pst.setInt(2,dto.getQty());
        pst.setString(3,dto.getSupplierId());
        pst.setString(4,dto.getDescription());
        pst.setDouble(5, dto.getUnitPrice());
        pst.setString(6, dto.getSize());
        return pst.executeUpdate()>0;
    }

    public static Boolean updateItem(ItemDto dto) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("UPDATE Items SET qty = ?, supplierId = ?, description = ?, unitPrice = ?, size = ? WHERE itemCode = ?");

        pst.setInt(1,dto.getQty());
        pst.setString(2,dto.getSupplierId());
        pst.setString(3,dto.getDescription());
        pst.setDouble(4,dto.getUnitPrice());
        pst.setString(5,dto.getSize());
        pst.setString(6,dto.getItemCode());

        return pst.executeUpdate()>0;
    }

    public static ArrayList<String> getItemCodes() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        ArrayList<String> items = new ArrayList<>();
        PreparedStatement pst = con.prepareStatement("SELECT itemCode FROM Items");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String id = rs.getString(1);
            items.add(id);
        }
        return items;
    }

    public static ItemDto getItemById(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        ItemDto dto = null;
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Items WHERE itemCode = ?");
        pst.setString(1,id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            dto = new ItemDto();
            String itemCode = rs.getString(1);
            Integer qty = rs.getInt(2);
            String supplierId = rs.getString(3);
            String description = rs.getString(4);
            Double unitPrice = rs.getDouble(5);
            String size = rs.getString(6);
            dto.setItemCode(itemCode);
            dto.setSupplierId(supplierId);
            dto.setDescription(description);
            dto.setUnitPrice(unitPrice);
            dto.setSize(size);
            dto.setQty(qty);
        }
        return dto;
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

    public static ArrayList<ItemDto> getItemBySupID(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        ArrayList<ItemDto> dtos = new ArrayList<>();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Items WHERE supplierId = ?");
        pst.setString(1,id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            ItemDto dto = new ItemDto();
            String itemCode = rs.getString(1);
            Integer qty = rs.getInt(2);
            String supplierId = rs.getString(3);
            String description = rs.getString(4);
            Double unitPrice = rs.getDouble(5);
            String size = rs.getString(6);
            dto.setItemCode(itemCode);
            dto.setSupplierId(supplierId);
            dto.setDescription(description);
            dto.setUnitPrice(unitPrice);
            dto.setSize(size);
            dto.setQty(qty);
            dtos.add(dto);
        }
        return dtos;
    }

    public boolean updateItems(List<ReturnCartTm> cartTmList) throws SQLException {
        for(ReturnCartTm tm : cartTmList) {
            //System.out.println("Item: " + tm);
            if(!updateQty(tm.getItemCode(), tm.getQty())) {
                return false;
            }
        }
        return true;
    }

    public boolean updateQty(String code, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Items SET qty = qty + ? WHERE itemCode = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, code);

        return pstm.executeUpdate() > 0; //false
    }

    public boolean updateItem(List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            System.out.println("Item: " + tm);
            if(!updatesQty(tm.getItemCode(), tm.getQty())) {
                return false;
            }
        }
        return true;
    }

    public boolean updatesQty(String code, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Items SET qty = qty - ? WHERE itemCode = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, code);

        return pstm.executeUpdate() > 0;
    }



}
