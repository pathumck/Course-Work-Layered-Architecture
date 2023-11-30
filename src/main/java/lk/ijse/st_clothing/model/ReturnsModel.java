package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;

import java.sql.*;
import java.util.ArrayList;

public class ReturnsModel {
    public static String generateNextReturnId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT returnId FROM Returns WHERE returnId LIKE 'r00%' ORDER BY CAST(SUBSTRING(returnId, 4) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitReturnId(resultSet.getString(1));
        }
        return splitReturnId(null);
    }

    private static String splitReturnId(String currentReturnId) {
        if (currentReturnId == null || currentReturnId.isEmpty() || !currentReturnId.matches("^r\\d+$")) {
            return "r001";
        } else {
            String numericPart = currentReturnId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "r00" + nextNumericPart;
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

    public static ArrayList<String> getAllReturnIds() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT returnId FROM Returns";
        ArrayList<String> list = new ArrayList<>();
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String id = rs.getString(1);
            list.add(id);
        }
        return list;
    }

    public static String isReturnSaved(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Returns WHERE returnId = ?");
        pst.setString(1,id);
        ResultSet rs = pst.executeQuery();
        String checkId = null;
        while (rs.next()) {
            checkId = rs.getString(1);
        }
        return checkId;
    }
}
