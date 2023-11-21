package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static OrderDto getOrderByorderId(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        OrderDto dto = null;
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Orders WHERE orderId = ?");
        pst.setString(1,id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            dto = new OrderDto();
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


}
