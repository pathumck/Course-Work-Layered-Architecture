package lk.ijse.st_clothing.bo.custom.impl;

import lk.ijse.st_clothing.bo.custom.ChangeCredentialsBO;
import lk.ijse.st_clothing.dao.DAOFactory;
import lk.ijse.st_clothing.dao.custom.UserDAO;
import lk.ijse.st_clothing.dao.custom.impl.UserDAOImpl;
import lk.ijse.st_clothing.dto.CredentialsDto;
import lk.ijse.st_clothing.entity.User;

import java.sql.SQLException;

public class ChangeCredentialsBOImpl implements ChangeCredentialsBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public CredentialsDto getCredentials() throws SQLException, ClassNotFoundException {
        User user = userDAO.getCredentials();
        return new CredentialsDto(user.getUserName(),user.getPassword());
    }
}
