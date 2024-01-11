package lk.ijse.st_clothing.dao.custom.impl;

import lk.ijse.st_clothing.dao.SQLUtil;
import lk.ijse.st_clothing.dao.custom.ExpenceDAO;
import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.ExpenceDto;
import lk.ijse.st_clothing.entity.Expence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenceDAOImpl implements ExpenceDAO {
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Expence> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<Expence> expences = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT * FROM Expences");

        while (rs.next()) {
            Expence expence = new Expence(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5));
            expences.add(expence);
        }

        return expences;
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM Expences WHERE expenceId = ?",id);
    }
    @Override
    public boolean add(Expence expence) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO Expences VALUES (?,?,?,?,?)",
                expence.getId(),expence.getType(),expence.getDescription(),expence.getDate(),expence.getAmount());
    }
    @Override
    public boolean update(Expence expence) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE Expences SET type = ?, description = ?, date = ?, amount = ? WHERE expenceId = ?",
                expence.getType(),expence.getDescription(),expence.getDate(),expence.getAmount(),expence.getId());
    }
    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {

        ArrayList<String> expences = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT expenceId FROM Expences");
        while (rs.next()) {
            String id = rs.getString(1);
            expences.add(id);
        }
        return expences;
    }

    @Override
    public Expence search(String id) throws SQLException, ClassNotFoundException {

        return null;
    }
    @Override
    public Double getMonthlyExpence(int currentYear, int currentMonth) throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT SUM(amount) AS MonthlyExpenses FROM Expences WHERE YEAR(date) = ? AND MONTH(date) = ?",
                currentYear,currentMonth);

        Double monthlyExpence = null;

        while (rs.next()) {
            monthlyExpence = rs.getDouble(1);
        }

        return monthlyExpence;
    }
}
