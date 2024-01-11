package lk.ijse.st_clothing.dao.custom.impl;

import lk.ijse.st_clothing.dao.SQLUtil;
import lk.ijse.st_clothing.dao.custom.SupplierDAO;
import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.SupplierDto;
import lk.ijse.st_clothing.entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT supplierId FROM Suppliers WHERE supplierId LIKE 's00%' ORDER BY CAST(SUBSTRING(supplierId, 4) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }

        return null;
    }
    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<Supplier> suppliers = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT * FROM Suppliers");

        while (rs.next()) {
            Supplier supplier = new Supplier(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
            suppliers.add(supplier);
        }

        return suppliers;
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM Suppliers WHERE supplierId = ?",id);
    }
    @Override
    public boolean add(Supplier supplier) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO Suppliers VALUES (?,?,?,?,?)",
                supplier.getId(),supplier.getName(),supplier.getAddress(),supplier.getTp(),supplier.getDate());
    }
    @Override
    public boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE Suppliers SET name = ?, address = ?, tp = ? WHERE supplierId = ?",
                supplier.getName(),supplier.getAddress(),supplier.getTp(),supplier.getId());
    }
    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {

        ArrayList<String> suppliers = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT supplierId FROM Suppliers");

        while (rs.next()) {
            String id = rs.getString(1);
            suppliers.add(id);
        }

        return suppliers;
    }
    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {

        Supplier supplier = null;

        ResultSet rs = SQLUtil.execute("SELECT * FROM Suppliers WHERE supplierId = ?",id);

        while (rs.next()) {
            supplier = new Supplier(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
        }

        return supplier;
    }




}
