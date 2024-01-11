package lk.ijse.st_clothing.dao.custom.impl;

import lk.ijse.st_clothing.dao.SQLUtil;
import lk.ijse.st_clothing.dao.custom.CustomerDAO;
import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.CustomerDto;
import lk.ijse.st_clothing.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Customer> getAll () throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT * FROM Customers");

        ArrayList<Customer> customers = new ArrayList<>();

        while (rs.next()) {
            Customer customer = new Customer(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
            customers.add(customer);
        }
        return customers;
    }
    @Override
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO Customers VALUES (?,?,?,?,?)",
                customer.getId(),customer.getName(),customer.getAddress(),customer.getTp(),customer.getDate());
    }
    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE Customers SET name = ?, address = ?, tp = ? WHERE customerId = ?",
                customer.getName(),customer.getAddress(),customer.getTp(),customer.getId());
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM Customers WHERE customerId = ?",id);
    }
    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {

        ArrayList<String> items = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT customerId FROM Customers");

        while (rs.next()) {
            String id = rs.getString(1);
            items.add(id);
        }

        return items;
    }
    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {

        Customer customer = null;

        ResultSet rs = SQLUtil.execute("SELECT * FROM Customers WHERE customerId = ?",id);

        while (rs.next()) {
            customer = new Customer(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5));
        }

        return customer;
    }
}
