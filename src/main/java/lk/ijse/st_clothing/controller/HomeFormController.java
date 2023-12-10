package lk.ijse.st_clothing.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lk.ijse.st_clothing.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomeFormController {
    @FXML
    private Label lblDateMonth1;

    @FXML
    private Label lblDateMonth2;

    @FXML
    private Label lblLessThanFive;

    @FXML
    private Label lblLastMonth1;

    @FXML
    private Label lblLastMonth2;

    @FXML
    private Label lblOutOfStock;

    @FXML
    private Label lblOrderCount;

    @FXML
    private Label lblReturnCount;

    @FXML
    private Label lblMonthlySales;

    @FXML
    private Label lblMonthlyExpences;

    @FXML
    private Label lblItem1;

    @FXML
    private Label lblItem10;

    @FXML
    private Label lblItem2;

    @FXML
    private Label lblItem3;

    @FXML
    private Label lblItem4;

    @FXML
    private Label lblItem5;

    @FXML
    private Label lblItem6;

    @FXML
    private Label lblItem7;

    @FXML
    private Label lblItem8;

    @FXML
    private Label lblItem9;

    @FXML
    private Label lblCus1;

    @FXML
    private Label lblCus10;

    @FXML
    private Label lblCus2;

    @FXML
    private Label lblCus3;

    @FXML
    private Label lblCus4;

    @FXML
    private Label lblCus5;

    @FXML
    private Label lblCus6;

    @FXML
    private Label lblCus7;

    @FXML
    private Label lblCus8;

    @FXML
    private Label lblCus9;
    public void initialize() throws SQLException {
        monthlyIncome();
        monthlyExpence();
        topTenSellingItemsOfMonth();
        topTenCustomers();
        lessThanFive();
        outOfStock();
        orderCount();
        returnCount();
        lastMonthSales();
        monthBeforelastMonthSales();

        if(OrdersFormController.scanning==true) {
            OrdersFormController.webcamPanel.stop();
            OrdersFormController.webcam.close();
            OrdersFormController.scanning = false;
        }

        if(ReturnsFormController.scanning1==true) {
            ReturnsFormController.webcamPanel1.stop();
            ReturnsFormController.webcam1.close();
            ReturnsFormController.scanning1 = false;
        }
    }

    public void monthlyIncome() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        // Get the current date
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        PreparedStatement pst = con.prepareStatement("SELECT SUM(od.unitPrice * od.qty) AS MonthlyIncome FROM Orders o JOIN OrderDetails od ON o.orderId = od.orderId WHERE YEAR(o.date) = ? AND MONTH(o.date) = ?");

        pst.setInt(1,currentYear);
        pst.setInt(2,currentMonth);

        ResultSet rs = pst.executeQuery();

        Double monthlyIncome = null;
        while(rs.next()) {
            monthlyIncome = rs.getDouble(1);
        }

        lblMonthlySales.setText("Lkr: "+String.valueOf(monthlyIncome));
        //System.out.println(monthlyIncome);
    }

    public void monthlyExpence() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        PreparedStatement pst = con.prepareStatement("SELECT SUM(amount) AS MonthlyExpenses FROM Expences WHERE YEAR(date) = ? AND MONTH(date) = ?");

        pst.setInt(1,currentYear);
        pst.setInt(2,currentMonth);

        ResultSet rs = pst.executeQuery();
        Double monthlyExpence = null;
        while (rs.next()) {
            monthlyExpence = rs.getDouble(1);
        }

        lblMonthlyExpences.setText("Lkr: "+String.valueOf(monthlyExpence));
    }

    public void topTenSellingItemsOfMonth() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        PreparedStatement pst = con.prepareStatement("SELECT itemCode, SUM(qty) AS TotalQuantity FROM OrderDetails od JOIN Orders o ON od.orderId = o.orderId WHERE YEAR(o.date) = ? AND MONTH(o.date) = ? GROUP BY itemCode ORDER BY TotalQuantity DESC LIMIT 10");

        pst.setInt(1,currentYear);
        pst.setInt(2,currentMonth);

        ResultSet rs = pst.executeQuery();
        String[] itemsCodes = new String[10];
        int i =0;
        while (rs.next()) {
            itemsCodes[i] = rs.getString(1);
            i++;
        }

        lblItem1.setText(itemsCodes[0]);
        lblItem2.setText(itemsCodes[1]);
        lblItem3.setText(itemsCodes[2]);
        lblItem4.setText(itemsCodes[3]);
        lblItem5.setText(itemsCodes[4]);
        lblItem6.setText(itemsCodes[5]);
        lblItem7.setText(itemsCodes[6]);
        lblItem8.setText(itemsCodes[7]);
        lblItem9.setText(itemsCodes[8]);
        lblItem10.setText(itemsCodes[9]);
        //System.out.println(Arrays.toString(itemsCodes));
    }

    public void topTenCustomers() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT customerId, SUM(unitPrice * qty) AS TotalAmountSpent FROM Orders o JOIN OrderDetails od ON o.orderId = od.orderId WHERE customerId IS NOT NULL AND customerId <> '' GROUP BY customerId ORDER BY TotalAmountSpent DESC LIMIT 10");
        ResultSet rs = pst.executeQuery();
        String [] cusIds = new String[10];
        int i = 0;
        while (rs.next()) {
            cusIds[i] = rs.getString(1);
            i++;
        }
        lblCus1.setText(cusIds[0]);
        lblCus2.setText(cusIds[1]);
        lblCus3.setText(cusIds[2]);
        lblCus4.setText(cusIds[3]);
        lblCus5.setText(cusIds[4]);
        lblCus6.setText(cusIds[5]);
        lblCus7.setText(cusIds[6]);
        lblCus8.setText(cusIds[7]);
        lblCus9.setText(cusIds[8]);
        lblCus10.setText(cusIds[9]);
    }

    public void lessThanFive() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) AS ItemCount FROM Items WHERE qty < 5");

        ResultSet rs = pst.executeQuery();
        Integer count = 0;
        while (rs.next()) {
            count = Integer.valueOf(rs.getString(1));
        }
        lblLessThanFive.setText(String.valueOf(count));
    }

    public void outOfStock() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) AS ItemCount FROM Items WHERE qty = 0");

        ResultSet rs = pst.executeQuery();
        Integer count = 0;
        while (rs.next()) {
            count = Integer.valueOf(rs.getString(1));
        }
        lblOutOfStock.setText(String.valueOf(count));
    }

    public void orderCount() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) AS OrderCount FROM Orders WHERE DATE(date) = CURDATE()");
        ResultSet rs = pst.executeQuery();

        Integer count = 0;
        while (rs.next()) {
            count = Integer.valueOf(rs.getString(1));
        }
        lblOrderCount.setText(String.valueOf(count));
    }

    public void returnCount() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) AS ReturnCount FROM Returns WHERE DATE(date) = CURDATE()");
        ResultSet rs = pst.executeQuery();

        Integer count = 0;
        while (rs.next()) {
            count = Integer.valueOf(rs.getString(1));
        }
        lblReturnCount.setText(String.valueOf(count));
    }

    public void lastMonthSales() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT SUM(unitPrice * qty) AS LastMonthSales FROM Orders o JOIN OrderDetails od ON o.orderId = od.orderId WHERE YEAR(o.date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) AND MONTH(o.date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)");
        ResultSet rs = pst.executeQuery();

        String count = "0.0";
        while (rs.next()) {
           String count1 = rs.getString(1);
           if(!(count1 ==null)) {
               count=count1;
           }
        }
        lblLastMonth1.setText("Lkr: "+count);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get details of the last month based on the current date
        LocalDate lastMonth = currentDate.minusMonths(1);
        int lastYear = lastMonth.getYear();
        int lastMonthValue = lastMonth.getMonthValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedLastMonth = lastYear + "-" + String.format("%02d", lastMonthValue);

        lblDateMonth1.setText(formattedLastMonth);
    }

    public void monthBeforelastMonthSales() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT SUM(unitPrice * qty) AS LastMonthSales FROM Orders o JOIN OrderDetails od ON o.orderId = od.orderId WHERE YEAR(o.date) = YEAR(CURRENT_DATE - INTERVAL 2 MONTH) AND MONTH(o.date) = MONTH(CURRENT_DATE - INTERVAL 2 MONTH)");
        ResultSet rs = pst.executeQuery();

        String count = "0.0";
        while (rs.next()) {
            String count1 = rs.getString(1);
            if(!(count1 ==null)) {
                count=count1;
            }
        }
        lblLastMonth2.setText("Lkr: "+count);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get details of the last month based on the current date
        LocalDate lastMonth = currentDate.minusMonths(2);
        int lastYear = lastMonth.getYear();
        int lastMonthValue = lastMonth.getMonthValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedLastMonth = lastYear + "-" + String.format("%02d", lastMonthValue);

        lblDateMonth2.setText(formattedLastMonth);
    }
}
