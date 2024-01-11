package lk.ijse.st_clothing.bo.custom;

import lk.ijse.st_clothing.bo.SuperBO;
import lk.ijse.st_clothing.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    String generateNextSupplierId() throws SQLException, ClassNotFoundException;
    ArrayList<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException;
    boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException;
    boolean addSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException;
    boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException;
    SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException;
}
