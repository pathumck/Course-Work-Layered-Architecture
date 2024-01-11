package lk.ijse.st_clothing.dao.custom;

import lk.ijse.st_clothing.dao.SuperDAO;
import lk.ijse.st_clothing.dto.CredentialsDto;
import lk.ijse.st_clothing.entity.User;

import java.sql.SQLException;

public interface UserDAO extends SuperDAO {
    User getCredentials() throws SQLException, ClassNotFoundException;
    boolean updateCredentials(User newCred, User currentCred) throws SQLException, ClassNotFoundException;
}
