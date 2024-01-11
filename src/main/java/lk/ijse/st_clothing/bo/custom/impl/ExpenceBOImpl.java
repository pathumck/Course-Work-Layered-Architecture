package lk.ijse.st_clothing.bo.custom.impl;

import lk.ijse.st_clothing.bo.custom.ExpenceBO;
import lk.ijse.st_clothing.dao.DAOFactory;
import lk.ijse.st_clothing.dao.custom.ExpenceDAO;
import lk.ijse.st_clothing.dao.custom.impl.ExpenceDAOImpl;
import lk.ijse.st_clothing.dto.ExpenceDto;
import lk.ijse.st_clothing.entity.Expence;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenceBOImpl implements ExpenceBO {
    ExpenceDAO expenceDAO = (ExpenceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EXPENCE);
    @Override
    public ArrayList<ExpenceDto> getAllExpences() throws SQLException, ClassNotFoundException {
        ArrayList<Expence> expences = expenceDAO.getAll();
        ArrayList<ExpenceDto> expenceDtos = new ArrayList<>();
        for (Expence expence : expences) {
            ExpenceDto dto = new ExpenceDto(expence.getId(),expence.getType(),expence.getDescription(),expence.getDate(),expence.getAmount());
            expenceDtos.add(dto);
        }
        return expenceDtos;
    }
    @Override
    public boolean deleteExpence(String id) throws SQLException, ClassNotFoundException {
        return expenceDAO.delete(id);
    }
    @Override
    public ArrayList<String> getAllExpenceIds() throws SQLException, ClassNotFoundException {
        return expenceDAO.getAllIds();
    }
    @Override
    public boolean addExpence(ExpenceDto dto) throws SQLException, ClassNotFoundException {
        return expenceDAO.add(new Expence(dto.getId(),dto.getType(),dto.getDescription(),dto.getDate(),dto.getAmount()));
    }
    @Override
    public boolean updateExpence(ExpenceDto dto) throws SQLException, ClassNotFoundException {
        return expenceDAO.update(new Expence(dto.getId(),dto.getType(),dto.getDescription(),dto.getDate(),dto.getAmount()));
    }
}
