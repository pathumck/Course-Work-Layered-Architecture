package lk.ijse.st_clothing.bo.custom;

import lk.ijse.st_clothing.bo.SuperBO;
import lk.ijse.st_clothing.dto.CredentialsDto;

import java.sql.SQLException;

public interface NewCredentialsBO extends SuperBO {
    CredentialsDto getUserCredentials() throws SQLException, ClassNotFoundException;
    boolean updateUserCredentials(CredentialsDto newCred, CredentialsDto curCred) throws SQLException, ClassNotFoundException;

}
