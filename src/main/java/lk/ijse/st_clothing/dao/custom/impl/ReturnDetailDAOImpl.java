package lk.ijse.st_clothing.dao.custom.impl;

import lk.ijse.st_clothing.dao.SQLUtil;
import lk.ijse.st_clothing.dao.custom.ReturnDetailDAO;
import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.tm.ReturnCartTm;
import lk.ijse.st_clothing.entity.ReturnDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnDetailDAOImpl implements ReturnDetailDAO {
    @Override
    public Double getDeductionById(String id) throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT SUM(qty * unitPrice) AS totalReturnAmount FROM ReturnDetails WHERE returnId = ? GROUP BY returnId",id);

        Double sum = null;

        while (rs.next()) {
            sum = rs.getDouble(1);
        }

        return sum;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ReturnDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(ReturnDetail returnDetail) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO ReturnDetails VALUES(?, ?, ?, ?)",
                returnDetail.getReturnId(),returnDetail.getItemCode(),returnDetail.getQty(),returnDetail.getUnitPrice());
    }

    @Override
    public boolean update(ReturnDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ReturnDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
