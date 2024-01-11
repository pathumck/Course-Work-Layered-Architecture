package lk.ijse.st_clothing.bo.custom;

import lk.ijse.st_clothing.bo.SuperBO;
import lk.ijse.st_clothing.dto.CredentialsDto;

import java.sql.SQLException;

public interface ChangeCredentialsBO extends SuperBO {
    CredentialsDto getCredentials() throws SQLException, ClassNotFoundException;
}
