package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;

import java.sql.*;

public class ReturnsModel {
    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT returnId FROM Returns ORDER BY returnId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitReturnId(resultSet.getString(1));
        }
        return splitReturnId(null);
    }

    public static String splitReturnId(String currentReturnId) {
        if(currentReturnId != null) {
            String[] split = currentReturnId.split("r0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "r00" + id;
        } else {
            return "r001";
        }
    }

    public boolean saveReturn(String returnId, String customerId, String date, String time) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Returns VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, returnId);
        pstm.setString(2, date);
        pstm.setString(3, time);
        pstm.setString(4, customerId);
        return pstm.executeUpdate() > 0;
    }

}