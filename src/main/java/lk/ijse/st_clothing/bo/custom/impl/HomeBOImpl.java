package lk.ijse.st_clothing.bo.custom.impl;

import lk.ijse.st_clothing.bo.custom.HomeBO;
import lk.ijse.st_clothing.dao.DAOFactory;
import lk.ijse.st_clothing.dao.custom.*;
import lk.ijse.st_clothing.dao.custom.impl.*;

import java.sql.SQLException;
import java.time.LocalDate;

public class HomeBOImpl implements HomeBO {
    private int currentMonth = LocalDate.now().getMonthValue();
    private int currentYear = LocalDate.now().getYear();
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    ExpenceDAO expenceDAO = (ExpenceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EXPENCE);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ReturnDAO returnDAO = (ReturnDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RETURN);
    @Override
    public Double getMonthlyIncome() throws SQLException, ClassNotFoundException {
        return queryDAO.getMonthlyIncome(currentYear,currentMonth);
    }
    @Override
    public Double getMonthlyExpence() throws SQLException, ClassNotFoundException {
        return expenceDAO.getMonthlyExpence(currentYear,currentMonth);
    }
    @Override
    public String [] getTopTenSellingItems() throws SQLException, ClassNotFoundException {
        return queryDAO.topSellingItemsOfMonth(currentYear,currentMonth);
    }
    @Override
    public String [] getTopTenCustomers() throws SQLException, ClassNotFoundException {
        return queryDAO.topTenCustomers();
    }
    @Override
    public Integer itemCountUnitslessThanFive() throws SQLException, ClassNotFoundException {
        return itemDAO.lessThanFiveUnits();
    }
    @Override
    public Integer itemCountOutOfStock() throws SQLException, ClassNotFoundException {
        return itemDAO.outOfStock();
    }
    @Override
    public Integer getTodayOrderCount() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderCount();
    }
    @Override
    public Integer getTodayReturnCount() throws SQLException, ClassNotFoundException {
        return returnDAO.getReturnCount();
    }
    @Override
    public String getLastMonthSalesAmount() throws SQLException, ClassNotFoundException {
        return queryDAO.getLastMonthSales();
    }
    @Override
    public String getMonthBeforeLatMonthSalesAmount() throws SQLException, ClassNotFoundException {
        return queryDAO.monthBeforeLastMonthSales();
    }
}
