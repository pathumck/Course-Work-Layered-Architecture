package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsModel {
    public boolean saveOrderDetails(String orderId, List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            if(!saveOrderDetails(orderId, tm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(String orderId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO OrderDetails VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, tm.getItemCode());
        pstm.setString(2, orderId);
        pstm.setDouble(3, tm.getUnitPrice());
        pstm.setInt(4,tm.getQty());

        return pstm.executeUpdate() > 0;
    }

}
