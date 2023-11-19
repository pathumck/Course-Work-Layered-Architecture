package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.ExpenceDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenceModel {
    public static ArrayList<ExpenceDto> getAllExpences() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        ArrayList<ExpenceDto> dtos = new ArrayList<>();

        PreparedStatement pst = con.prepareStatement("SELECT * FROM Expences");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            ExpenceDto dto = new ExpenceDto();
            dto.setId(rs.getString(1));
            dto.setType(rs.getString(2));
            dto.setDescription(rs.getString(3));
            dto.setDate(rs.getString(4));
            dto.setAmount(rs.getDouble(5));
            dtos.add(dto);
        }
        return dtos;
    }

    public static Boolean deleteExpence(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("DELETE FROM Expences WHERE expenceId = ?");
        pst.setString(1,id);

        return pst.executeUpdate()>0;
    }

    public static Boolean addExpence(ExpenceDto dto) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("INSERT INTO Expences VALUES (?,?,?,?,?)");

        pst.setString(1,dto.getId());
        pst.setString(2,dto.getType());
        pst.setString(3,dto.getDescription());
        pst.setString(4,dto.getDate());
        pst.setDouble(5, dto.getAmount());

        return pst.executeUpdate()>0;
    }

    public static Boolean updateExpences(ExpenceDto dto) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement pst = con.prepareStatement("UPDATE Expences SET type = ?, description = ?, date = ?, amount = ? WHERE expenceId = ?");

        pst.setString(1,dto.getType());
        pst.setString(2,dto.getDescription());
        pst.setString(3,dto.getDate());
        pst.setDouble(4,dto.getAmount());
        pst.setString(5,dto.getId());

        return pst.executeUpdate()>0;
    }
    public static ArrayList<String> getExpenceIds() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        ArrayList<String> expences = new ArrayList<>();
        PreparedStatement pst = con.prepareStatement("SELECT expenceId FROM Expences");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String id = rs.getString(1);
            expences.add(id);
        }
        return expences;
    }

    public static ExpenceDto getExpenceById(String id) throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        ExpenceDto dto = null;
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Expences WHERE expenceId = ?");
        pst.setString(1,id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            dto = new ExpenceDto();
            String expenceId = rs.getString(1);
            String type = rs.getString(2);
            String description = rs.getString(3);
            String date = rs.getString(4);
            Double amount = rs.getDouble(5);
            dto.setId(expenceId);
            dto.setType(type);
            dto.setDescription(description);
            dto.setDate(date);
            dto.setAmount(amount);
        }
        return dto;
    }
}
