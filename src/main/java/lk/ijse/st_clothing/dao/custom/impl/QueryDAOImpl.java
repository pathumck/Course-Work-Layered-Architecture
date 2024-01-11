package lk.ijse.st_clothing.dao.custom.impl;

import lk.ijse.st_clothing.dao.SQLUtil;
import lk.ijse.st_clothing.dao.custom.QueryDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public Double getMonthlyIncome(int currentYear, int currentMonth) throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT SUM(od.unitPrice * od.qty) AS MonthlyIncome FROM Orders o JOIN OrderDetails od ON o.orderId = od.orderId WHERE YEAR(o.date) = ? AND MONTH(o.date) = ?",
                currentYear,currentMonth);


        Double monthlyIncome = null;

        while(rs.next()) {
            monthlyIncome = rs.getDouble(1);
        }

        return monthlyIncome;
    }
    @Override
    public String [] topSellingItemsOfMonth(int currentYear, int currentMonth) throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT itemCode, SUM(qty) AS TotalQuantity FROM OrderDetails od JOIN Orders o ON od.orderId = o.orderId WHERE YEAR(o.date) = ? AND MONTH(o.date) = ? GROUP BY itemCode ORDER BY TotalQuantity DESC LIMIT 10",
                currentYear,currentMonth);

        String [] items = new String[10];
        int i = 0;
        while (rs.next()) {
            items[i] = rs.getString(1);
            i++;
        }

        return items;
    }
    @Override
    public String[] topTenCustomers() throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT customerId, SUM(unitPrice * qty) AS TotalAmountSpent FROM Orders o JOIN OrderDetails od ON o.orderId = od.orderId WHERE customerId IS NOT NULL AND customerId <> '' GROUP BY customerId ORDER BY TotalAmountSpent DESC LIMIT 10");

        String [] cusIds = new String[10];

        int i = 0;

        while (rs.next()) {
            cusIds[i] = rs.getString(1);
            i++;
        }

        return cusIds;
    }
    @Override
    public String getLastMonthSales() throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT SUM(unitPrice * qty) AS LastMonthSales FROM Orders o JOIN OrderDetails od ON o.orderId = od.orderId WHERE YEAR(o.date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) AND MONTH(o.date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)");

        String count = "0.0";

        while (rs.next()) {
            String count1 = rs.getString(1);
            if(!(count1 ==null)) {
                count=count1;
            }
        }

        return count;
    }
    @Override
    public String monthBeforeLastMonthSales() throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT SUM(unitPrice * qty) AS LastMonthSales FROM Orders o JOIN OrderDetails od ON o.orderId = od.orderId WHERE YEAR(o.date) = YEAR(CURRENT_DATE - INTERVAL 2 MONTH) AND MONTH(o.date) = MONTH(CURRENT_DATE - INTERVAL 2 MONTH)");

        String count = "0.0";

        while (rs.next()) {
            String count1 = rs.getString(1);
            if(!(count1 ==null)) {
                count=count1;
            }
        }

        return count;
    }
}
