package lk.ijse.st_clothing.bo.custom.impl;

import lk.ijse.st_clothing.bo.custom.ReturnBO;
import lk.ijse.st_clothing.dao.DAOFactory;
import lk.ijse.st_clothing.dao.custom.ItemDAO;
import lk.ijse.st_clothing.dao.custom.OrderDAO;
import lk.ijse.st_clothing.dao.custom.ReturnDAO;
import lk.ijse.st_clothing.dao.custom.ReturnDetailDAO;
import lk.ijse.st_clothing.dao.custom.impl.ItemDAOImpl;
import lk.ijse.st_clothing.dao.custom.impl.OrderDAOImpl;
import lk.ijse.st_clothing.dao.custom.impl.ReturnDAOImpl;
import lk.ijse.st_clothing.dao.custom.impl.ReturnDetailDAOImpl;
import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.PlaceOrderDto;
import lk.ijse.st_clothing.dto.PlaceReturnDto;
import lk.ijse.st_clothing.dto.tm.ReturnCartTm;
import lk.ijse.st_clothing.entity.Item;
import lk.ijse.st_clothing.entity.Order;
import lk.ijse.st_clothing.entity.Return;
import lk.ijse.st_clothing.entity.ReturnDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnBOImpl implements ReturnBO {
    ReturnDAO returnDAO = (ReturnDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RETURN);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ReturnDetailDAO returnDetailDAO = (ReturnDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RETURN_DETAIL);
    @Override
    public ItemDto searchReturnId(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id);
        return new ItemDto(item.getItemCode(),item.getSupplierId(),item.getDescription(),item.getUnitPrice(),item.getQty(),item.getSize());
    }
    @Override
    public String generateNextReturnId() throws SQLException, ClassNotFoundException {
        return returnDAO.generateNextId();
    }
    @Override
    public String isReturnSaved(String id) throws SQLException, ClassNotFoundException {
        return returnDAO.isReturnSaved(id);
    }
    @Override
    public boolean saveReturn(PlaceReturnDto dto) throws SQLException, ClassNotFoundException {
        return returnDAO.add(new Return(dto.getReturnId(),dto.getDate(),dto.getCurrentTimeString(),dto.getCustomerId()));
    }
    @Override
    public ArrayList<String> getOrderIds() throws SQLException, ClassNotFoundException {
        return orderDAO.getAllIds();
    }
    @Override
    public PlaceOrderDto searchOrder(String id) throws SQLException, ClassNotFoundException {
        Order order = orderDAO.search(id);
        return new PlaceOrderDto(order.getOrderId(),order.getDate(),order.getTime(),order.getCustomerId());
    }
    @Override
    public ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllIds();
    }
    @Override
    public Boolean placeReturn(PlaceReturnDto placeReturnDto) throws SQLException {

        String returnId = placeReturnDto.getReturnId();
        String customerId = placeReturnDto.getCustomerId();
        String date = placeReturnDto.getDate();
        String time = placeReturnDto.getCurrentTimeString();
        System.out.println(returnId);
        Connection connection = null;
        try {

            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isReturnSaved = saveReturn(new PlaceReturnDto(returnId, customerId, date,time));
            if (isReturnSaved) {
                boolean isUpdated = updateItems(placeReturnDto.getList());
                if (isUpdated) {
                    boolean isReturnDetailsSaved = saveReturnDetails(placeReturnDto.getReturnId(), placeReturnDto.getList());
                    if (isReturnDetailsSaved) {
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
    @Override
    public boolean saveReturnDetails(String returnId, List<ReturnCartTm> cartTmList) throws SQLException {
        for(ReturnCartTm tm : cartTmList) {
            try {
                if(!returnDetailDAO.add(new ReturnDetail(returnId, tm.getItemCode(),tm.getQty(), tm.getUnitPrice()))) {
                    return false;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    @Override
    public boolean updateItems(List<ReturnCartTm> cartTmList) throws SQLException {
        for(ReturnCartTm tm : cartTmList) {
            try {
                if(!itemDAO.updateQtyWhenReturn(tm.getItemCode(), tm.getQty())) {
                    return false;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
