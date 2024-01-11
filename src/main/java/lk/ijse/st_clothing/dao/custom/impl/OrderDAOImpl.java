package lk.ijse.st_clothing.dao.custom.impl;

import lk.ijse.st_clothing.dao.SQLUtil;
import lk.ijse.st_clothing.dao.custom.OrderDAO;
import lk.ijse.st_clothing.entity.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT orderId FROM Orders WHERE orderId LIKE 'o00%' ORDER BY CAST(SUBSTRING(orderId, 4) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }

        return null;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {

        return null;
    }

    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO Orders VALUES(?, ?, ?, ?)",
                order.getOrderId(),order.getDate(),order.getTime(),order.getCustomerId());
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String isOrderSaved(String id) throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT * FROM Orders WHERE orderId = ?",id);
        String checkId = null;
        while (rs.next()) {
            checkId = rs.getString(1);
        }
        return checkId;
    }
    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {

        Order order = null;

        ResultSet rs = SQLUtil.execute("SELECT * FROM Orders WHERE orderId = ?",id);

        while (rs.next()) {
            order = new Order(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
        }

        return order;
    }
    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {

        ArrayList<String> orderIds = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT orderId FROM Orders");
        while (rs.next()) {
            String id = rs.getString(1);
            orderIds.add(id);
        }
        return orderIds;
    }
    @Override
    public Integer getOrderCount() throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT COUNT(*) AS OrderCount FROM Orders WHERE DATE(date) = CURDATE()");

        Integer count = 0;

        while (rs.next()) {
            count = Integer.valueOf(rs.getString(1));
        }

        return count;
    }

}
