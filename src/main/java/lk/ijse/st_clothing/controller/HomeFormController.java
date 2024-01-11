package lk.ijse.st_clothing.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lk.ijse.st_clothing.bo.BOFactory;
import lk.ijse.st_clothing.bo.custom.HomeBO;
import lk.ijse.st_clothing.bo.custom.impl.HomeBOImpl;
import lk.ijse.st_clothing.db.DbConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    HomeBO homeBO = (HomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HOME);
    public void initialize() {
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

    public void monthlyIncome() {
        try {
            lblMonthlySales.setText("Lkr: "+ homeBO.getMonthlyIncome());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void monthlyExpence() {
        try {
            lblMonthlyExpences.setText("Lkr: "+ homeBO.getMonthlyExpence());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void topTenSellingItemsOfMonth() {
        try {
            String [] items = homeBO.getTopTenSellingItems();
            Label[] itemLabels = {lblItem1, lblItem2, lblItem3, lblItem4, lblItem5, lblItem6, lblItem7, lblItem8, lblItem9, lblItem10};
            for (int i = 0; i < items.length; i++) {
                itemLabels[i].setText(items[i]);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void topTenCustomers() {
        try {
            String [] cusIds = homeBO.getTopTenCustomers();
            Label[] cusIdLbls = {lblCus1, lblCus2, lblCus3, lblCus4, lblCus5, lblCus6, lblCus7, lblCus8, lblCus9, lblCus10};
            for (int i = 0; i < cusIds.length; i++) {
                cusIdLbls[i].setText(cusIds[i]);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void lessThanFive() {
        try {
            lblLessThanFive.setText(String.valueOf(homeBO.itemCountUnitslessThanFive()));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void outOfStock() {
        try {
            lblOutOfStock.setText(String.valueOf(homeBO.itemCountOutOfStock()));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void orderCount() {
        try {
            lblOrderCount.setText(String.valueOf(homeBO.getTodayOrderCount()));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnCount() {
        try {
            lblReturnCount.setText(String.valueOf(homeBO.getTodayReturnCount()));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void lastMonthSales() {
        try {
            lblLastMonth1.setText("Lkr: "+ homeBO.getLastMonthSalesAmount());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // Get details of the last month based on the current date
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        int lastYear = lastMonth.getYear();
        int lastMonthValue = lastMonth.getMonthValue();

        String formattedLastMonth = lastYear + "-" + String.format("%02d", lastMonthValue);

        lblDateMonth1.setText(formattedLastMonth);
    }

    public void monthBeforelastMonthSales() {
        try {
            lblLastMonth2.setText("Lkr: "+homeBO.getMonthBeforeLatMonthSalesAmount());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // Get details of the last month based on the current date
        LocalDate lastMonth = LocalDate.now().minusMonths(2);
        int lastYear = lastMonth.getYear();
        int lastMonthValue = lastMonth.getMonthValue();

        String formattedLastMonth = lastYear + "-" + String.format("%02d", lastMonthValue);

        lblDateMonth2.setText(formattedLastMonth);
    }
}
