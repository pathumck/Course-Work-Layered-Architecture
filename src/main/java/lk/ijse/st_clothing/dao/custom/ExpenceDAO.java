package lk.ijse.st_clothing.dao.custom;

import lk.ijse.st_clothing.dao.CRUDDAO;
import lk.ijse.st_clothing.dto.ExpenceDto;
import lk.ijse.st_clothing.entity.Expence;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ExpenceDAO extends CRUDDAO<Expence> {
    Double getMonthlyExpence(int currentYear, int currentMonth) throws SQLException, ClassNotFoundException;
}
