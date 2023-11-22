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

        String sql = "SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("o0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "o00"+id;
            }else {
                if (length < 3){
                    return "o0"+id;
                }else {
                    return "o"+id;
                }
            }
        }
        return "o001";
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
}
