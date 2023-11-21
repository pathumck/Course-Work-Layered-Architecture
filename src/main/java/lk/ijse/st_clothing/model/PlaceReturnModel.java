package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.PlaceReturnDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceReturnModel {
    private static ItemsModel itemsModel = new ItemsModel();
    private static ReturnsModel returnsModel =new ReturnsModel();
    private static ReturnDetailsModel returnDetailsModel =new ReturnDetailsModel();

    public static Boolean placeReturn(PlaceReturnDto placeReturnDto) throws SQLException {
        //System.out.println(placeReturnDto);

        String returnId = placeReturnDto.getReturnId();
        String customerId = placeReturnDto.getCustomerId();
        String date = placeReturnDto.getDate();
        String time = placeReturnDto.getCurrentTimeString();
        System.out.println(returnId);
        Connection connection = null;
        try {

            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isReturnSaved = returnsModel.saveReturn(returnId, customerId, date,time);
            if (isReturnSaved) {
                System.out.println("xxxxxxxxxxxx");
                boolean isUpdated = itemsModel.updateItem(placeReturnDto.getList());
                if (isUpdated) {
                    boolean isReturnDetailsSaved = returnDetailsModel.saveReturnDetails(placeReturnDto.getReturnId(), placeReturnDto.getList());
                    if (isReturnDetailsSaved) {
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
