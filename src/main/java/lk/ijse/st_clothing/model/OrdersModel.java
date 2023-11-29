package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.PlaceOrderDto;

import java.sql.*;
import java.util.ArrayList;

public class OrdersModel {
    public static ArrayList<String> getOrderIds() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        ArrayList<String> orderIds = new ArrayList<>();
        PreparedStatement pst = con.prepareStatement("SELECT orderId FROM Orders");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String id = rs.getString(1);
            orderIds.add(id);
        }
        return orderIds;
    }

    public static PlaceOrderDto getOrderByorderId(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        PlaceOrderDto dto = null;
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Orders WHERE orderId = ?");
        pst.setString(1,id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            dto = new PlaceOrderDto();
            String orderId = rs.getString(1);
            String date = rs.getString(2);
            String time = rs.getString(3);
            String customerId = rs.getString(4);
            dto.setOrderId(orderId);
            dto.setDate(date);
            dto.setTime(time);
            dto.setCustomerId(customerId);

        }
        return dto;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT orderId FROM Orders WHERE orderId LIKE 'o00%' ORDER BY CAST(SUBSTRING(orderId, 4) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if (currentOrderId == null || currentOrderId.isEmpty() || !currentOrderId.matches("^o\\d+$")) {
            return "o001";
        } else {
            String numericPart = currentOrderId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "o00" + nextNumericPart;
        }
    }

    public boolean saveOrder(String orderId, String date, String time, String cusId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Orders VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setDate(2, Date.valueOf(date));
        pstm.setString(3,time);
        pstm.setString(4,cusId);

        return pstm.executeUpdate() > 0;
    }

    public static String isOrderSaved(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Orders WHERE orderId = ?");
        pst.setString(1,id);
        ResultSet rs = pst.executeQuery();
        String checkId = null;
        while (rs.next()) {
            checkId = rs.getString(1);
        }
        return checkId;
    }
}
