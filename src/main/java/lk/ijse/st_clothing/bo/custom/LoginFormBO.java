package lk.ijse.st_clothing.bo.custom;

import lk.ijse.st_clothing.bo.SuperBO;
import lk.ijse.st_clothing.dto.CredentialsDto;

import java.sql.SQLException;

public interface LoginFormBO extends SuperBO {
    CredentialsDto getUserCredentials() throws SQLException, ClassNotFoundException;
}
