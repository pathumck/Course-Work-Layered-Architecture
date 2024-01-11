package lk.ijse.st_clothing.dao.custom.impl;

import lk.ijse.st_clothing.dao.SQLUtil;
import lk.ijse.st_clothing.dao.custom.ItemDAO;
import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT itemCode FROM Items WHERE itemCode LIKE 'i00%' ORDER BY CAST(SUBSTRING(itemCode, 4) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }

        return null;
    }
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<Item> items = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT * FROM Items");

        while (rs.next()) {
            Item item = new Item(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getString(6));
            items.add(item);
        }

        return items;
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM Items WHERE itemCode = ?",id);
    }
    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO Items VALUES (?,?,?,?,?,?)",
                item.getItemCode(),item.getQty(),item.getSupplierId(),item.getDescription(),
                item.getUnitPrice(),item.getSize());
    }
    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE Items SET qty = ?, supplierId = ?, description = ?, unitPrice = ?, size = ? WHERE itemCode = ?",
                item.getQty(),item.getSupplierId(),item.getDescription(),item.getUnitPrice(),
                item.getSize(),item.getItemCode());
    }
    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {

        ArrayList<String> items = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT itemCode FROM Items");
        while (rs.next()) {
            String id = rs.getString(1);
            items.add(id);
        }
        return items;
    }
    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {

        Item item = null;

        ResultSet rs = SQLUtil.execute("SELECT * FROM Items WHERE itemCode = ?",id);

        while (rs.next()) {
            item = new Item(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getString(6));

        }

        return item;
    }
    @Override
    public boolean updateQtyWhenReturn(String code, int qty) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE Items SET qty = qty + ? WHERE itemCode = ?",qty,code);
    }
    @Override
    public boolean updatesQtyWhenOrder(String code, int qty) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE Items SET qty = qty - ? WHERE itemCode = ?",qty,code);
    }
    @Override
    public Integer lessThanFiveUnits() throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT COUNT(*) AS ItemCount FROM Items WHERE qty < 5");

        Integer count = 0;

        while (rs.next()) {
            count = Integer.valueOf(rs.getString(1));
        }

        return count;
    }
    @Override
    public Integer outOfStock() throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT COUNT(*) AS ItemCount FROM Items WHERE qty = 0");

        Integer count = 0;

        while (rs.next()) {
            count = Integer.valueOf(rs.getString(1));
        }

        return count;
    }
}
