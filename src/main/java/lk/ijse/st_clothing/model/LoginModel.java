package lk.ijse.st_clothing.model;

import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.tm.CredentialsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    public static CredentialsDto getCredentials() throws SQLException {
        DbConnection db = DbConnection.getInstance();
        Connection con = db.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM User");
        ResultSet rs = pst.executeQuery();
        CredentialsDto dto = new CredentialsDto();
        while (rs.next()) {
            String userName = rs.getString(1);
            String password = rs.getString(2);
            dto.setUserName(userName);
            dto.setPassword(password);
        }
        return dto;
    }
}
