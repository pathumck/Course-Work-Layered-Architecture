package lk.ijse.st_clothing.dao.custom.impl;

import lk.ijse.st_clothing.dao.SQLUtil;
import lk.ijse.st_clothing.dao.custom.ReturnDAO;
import lk.ijse.st_clothing.entity.Return;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnDAOImpl implements ReturnDAO {
    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {

        ArrayList<String> list = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT returnId FROM Returns");

        while (rs.next()) {
            String id = rs.getString(1);
            list.add(id);
        }

        return list;
    }

    @Override
    public Return search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT returnId FROM Returns WHERE returnId LIKE 'r00%' ORDER BY CAST(SUBSTRING(returnId, 4) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }

        return null;
    }

    @Override
    public ArrayList<Return> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Return returnn) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO Returns VALUES(?, ?, ?, ?)",returnn.getReturnId(),returnn.getDate(),returnn.getTime(),returnn.getCustomerId());
    }

    @Override
    public boolean update(Return dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String isReturnSaved(String id) throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT * FROM Returns WHERE returnId = ?",id);
        String checkId = null;
        while (rs.next()) {
            checkId = rs.getString(1);
        }
        return checkId;
    }
    @Override
    public Integer getReturnCount() throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT COUNT(*) AS ReturnCount FROM Returns WHERE DATE(date) = CURDATE()");

        Integer count = 0;

        while (rs.next()) {
            count = Integer.valueOf(rs.getString(1));
        }

        return count;
    }



}
