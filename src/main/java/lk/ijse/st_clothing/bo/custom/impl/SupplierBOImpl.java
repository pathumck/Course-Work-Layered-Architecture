package lk.ijse.st_clothing.bo.custom.impl;

import lk.ijse.st_clothing.bo.custom.SupplierBO;
import lk.ijse.st_clothing.dao.DAOFactory;
import lk.ijse.st_clothing.dao.custom.SupplierDAO;
import lk.ijse.st_clothing.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.st_clothing.dto.SupplierDto;
import lk.ijse.st_clothing.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public String generateNextSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNextId();
    }
    @Override
    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers = supplierDAO.getAll();
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            SupplierDto dto = new SupplierDto(supplier.getId(),supplier.getName(),supplier.getTp(),supplier.getAddress(),supplier.getDate());
            supplierDtos.add(dto);
        }
        return supplierDtos;
    }
    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }
    @Override
    public ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        return supplierDAO.getAllIds();
    }
    @Override
    public boolean addSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.add(new Supplier(dto.getId(),dto.getName(),dto.getAddress(),dto.getTp(),dto.getDate()));
    }
    @Override
    public boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(dto.getId(),dto.getName(),dto.getAddress(),dto.getTp(),dto.getDate()));
    }
    public SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.search(id);
        return new SupplierDto(supplier.getId(),supplier.getName(),supplier.getTp(),supplier.getAddress(),supplier.getDate());
    }
}
