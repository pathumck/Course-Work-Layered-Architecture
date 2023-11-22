package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.tm.ReturnCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReturnDetailsModel {
    public boolean saveReturnDetails(String returnId, List<ReturnCartTm> cartTmList) throws SQLException {
        for(ReturnCartTm tm : cartTmList) {
            if(!saveReturnDetails(returnId, tm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveReturnDetails(String returnId, ReturnCartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO ReturnDetails VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, returnId);
        pstm.setString(2, tm.getItemCode());
        pstm.setInt(3, tm.getQty());
        pstm.setDouble(4, tm.getUnitPrice());

        return pstm.executeUpdate() > 0;
    }

    public static Double getDeductionById(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT SUM(qty * unitPrice) AS totalReturnAmount FROM ReturnDetails WHERE returnId = ? GROUP BY returnId";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        ResultSet rs = pstm.executeQuery();
        Double sum = null;
        while (rs.next()) {
            sum = rs.getDouble(1);
        }
        return sum;
    }
}
