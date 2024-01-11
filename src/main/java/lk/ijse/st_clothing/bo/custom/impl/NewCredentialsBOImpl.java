package lk.ijse.st_clothing.bo.custom.impl;

import lk.ijse.st_clothing.bo.custom.NewCredentialsBO;
import lk.ijse.st_clothing.dao.DAOFactory;
import lk.ijse.st_clothing.dao.custom.UserDAO;
import lk.ijse.st_clothing.dao.custom.impl.UserDAOImpl;
import lk.ijse.st_clothing.dto.CredentialsDto;
import lk.ijse.st_clothing.entity.User;

import java.sql.SQLException;

public class NewCredentialsBOImpl implements NewCredentialsBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public CredentialsDto getUserCredentials() throws SQLException, ClassNotFoundException {
        User user = userDAO.getCredentials();
        return new CredentialsDto(user.getUserName(),user.getPassword());
    }
    @Override
    public boolean updateUserCredentials(CredentialsDto newCred, CredentialsDto curCred) throws SQLException, ClassNotFoundException {
        return userDAO.updateCredentials(new User(newCred.getUserName(),newCred.getPassword()),new User(curCred.getUserName(),curCred.getPassword()));
    }
}
