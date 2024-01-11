package lk.ijse.st_clothing.bo.custom.impl;

import lk.ijse.st_clothing.bo.custom.ItemBO;
import lk.ijse.st_clothing.dao.DAOFactory;
import lk.ijse.st_clothing.dao.custom.ItemDAO;
import lk.ijse.st_clothing.dao.custom.SupplierDAO;
import lk.ijse.st_clothing.dao.custom.impl.ItemDAOImpl;
import lk.ijse.st_clothing.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public String generateNextItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNextId();
    }
    @Override
    public ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            ItemDto dto = new ItemDto(item.getItemCode(),item.getSupplierId(),item.getDescription(),item.getUnitPrice(),item.getQty(),item.getSize());
            itemDtos.add(dto);
        }
        return itemDtos;
    }
    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }
    @Override
    public ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllIds();
    }
    @Override
    public boolean addItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(dto.getItemCode(),dto.getQty(),dto.getSupplierId(),dto.getDescription(),dto.getUnitPrice(),dto.getSize()));
    }
    @Override
    public  boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return  itemDAO.update(new Item(dto.getItemCode(),dto.getQty(),dto.getSupplierId(),dto.getDescription(),dto.getUnitPrice(),dto.getSize()));
    }
    @Override
    public ItemDto searchItem(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id);
        return new ItemDto(item.getItemCode(),item.getSupplierId(),item.getDescription(),item.getUnitPrice(),item.getQty(),item.getSize());
    }
    @Override
    public ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        return supplierDAO.getAllIds();
    }
}
