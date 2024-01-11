package lk.ijse.st_clothing.dao.custom;

import lk.ijse.st_clothing.dao.CRUDDAO;
import lk.ijse.st_clothing.dto.PlaceReturnDto;
import lk.ijse.st_clothing.entity.Return;
import lk.ijse.st_clothing.entity.ReturnDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnDAO extends CRUDDAO<Return> {
    String isReturnSaved(String id) throws SQLException, ClassNotFoundException;
    Integer getReturnCount() throws SQLException, ClassNotFoundException;
}
