package lk.ijse.st_clothing.bo.custom;

import lk.ijse.st_clothing.bo.SuperBO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HomeBO extends SuperBO {
    Double getMonthlyIncome() throws SQLException, ClassNotFoundException;
    Double getMonthlyExpence() throws SQLException, ClassNotFoundException;
    String[] getTopTenSellingItems() throws SQLException, ClassNotFoundException;
    String [] getTopTenCustomers() throws SQLException, ClassNotFoundException;
    Integer itemCountUnitslessThanFive() throws SQLException, ClassNotFoundException;
    Integer itemCountOutOfStock() throws SQLException, ClassNotFoundException;
    Integer getTodayOrderCount() throws SQLException, ClassNotFoundException;
    Integer getTodayReturnCount() throws SQLException, ClassNotFoundException;
    String getLastMonthSalesAmount() throws SQLException, ClassNotFoundException;
    String getMonthBeforeLatMonthSalesAmount() throws SQLException, ClassNotFoundException;
}
