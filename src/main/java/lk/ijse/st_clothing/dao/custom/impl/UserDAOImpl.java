package lk.ijse.st_clothing.dao.custom.impl;

import lk.ijse.st_clothing.dao.SQLUtil;
import lk.ijse.st_clothing.dao.custom.UserDAO;
import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.CredentialsDto;
import lk.ijse.st_clothing.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public User getCredentials() throws SQLException, ClassNotFoundException {

        ResultSet rs = SQLUtil.execute("SELECT * FROM User");

        User user = null;

        while (rs.next()) {
            user = new User(rs.getString(1),rs.getString(2));
        }

        return user;
    }
    @Override
    public boolean updateCredentials(User newCred, User currentCred) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE User SET userName = ?, password = ? WHERE userName = ?",
                newCred.getUserName(),newCred.getPassword(),currentCred.getUserName());
    }

}
