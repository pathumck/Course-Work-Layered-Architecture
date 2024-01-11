package lk.ijse.st_clothing.dao.custom;

import lk.ijse.st_clothing.dao.CRUDDAO;
import lk.ijse.st_clothing.dto.tm.ReturnCartTm;
import lk.ijse.st_clothing.entity.ReturnDetail;

import java.sql.SQLException;

public interface ReturnDetailDAO extends CRUDDAO<ReturnDetail> {
    Double getDeductionById(String id) throws SQLException, ClassNotFoundException;
}
