package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderModel {
    private OrdersModel orderModel = new OrdersModel();
    private ItemsModel itemModel = new ItemsModel();
    private OrderDetailsModel orderDetailModel = new OrderDetailsModel();
    public boolean placeOrder(PlaceOrderDto placeOrderDto) throws SQLException {
        System.out.println(placeOrderDto);

        String orderId = placeOrderDto.getOrderId();
        String customerId = placeOrderDto.getCustomerId();
        String date = placeOrderDto.getDate();
        String time = placeOrderDto.getTime();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderModel.saveOrder(orderId, date, time,customerId);
            if (isOrderSaved) {
                boolean isUpdated = itemModel.updateItem(placeOrderDto.getCartTms());
                if (isUpdated) {
                    boolean isOrderDetailSaved = orderDetailModel.saveOrderDetails(placeOrderDto.getOrderId(), placeOrderDto.getCartTms());
                    if (isOrderDetailSaved) {
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
