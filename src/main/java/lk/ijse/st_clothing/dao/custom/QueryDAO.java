package lk.ijse.st_clothing.dao.custom;

import lk.ijse.st_clothing.dao.SuperDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    public Double getMonthlyIncome(int currentYear, int currentMonth) throws SQLException, ClassNotFoundException;
    String[] topSellingItemsOfMonth(int currentYear, int currentMonth) throws SQLException, ClassNotFoundException;
    String[] topTenCustomers() throws SQLException, ClassNotFoundException;
    String getLastMonthSales() throws SQLException, ClassNotFoundException;
    String monthBeforeLastMonthSales() throws SQLException, ClassNotFoundException;
}
