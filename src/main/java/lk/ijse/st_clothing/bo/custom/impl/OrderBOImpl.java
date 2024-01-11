package lk.ijse.st_clothing.bo.custom.impl;

import lk.ijse.st_clothing.bo.custom.OrderBO;
import lk.ijse.st_clothing.dao.DAOFactory;
import lk.ijse.st_clothing.dao.custom.*;
import lk.ijse.st_clothing.dao.custom.impl.*;
import lk.ijse.st_clothing.db.DbConnection;
import lk.ijse.st_clothing.dto.CustomerDto;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.PlaceOrderDto;
import lk.ijse.st_clothing.dto.tm.CartTm;
import lk.ijse.st_clothing.entity.Customer;
import lk.ijse.st_clothing.entity.Item;
import lk.ijse.st_clothing.entity.Order;
import lk.ijse.st_clothing.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ReturnDetailDAO returnDetailDAO = (ReturnDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RETURN_DETAIL);
    ReturnDAO returnDAO = (ReturnDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RETURN);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
    @Override
    public String generateNextOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNextId();
    }
    public String isOrderSaved(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.isOrderSaved(id);
    }
    @Override
    public boolean saveOrder(PlaceOrderDto dto) throws SQLException, ClassNotFoundException {
        return orderDAO.add(new Order(dto.getOrderId(),dto.getDate(),dto.getTime(),dto.getCustomerId()));
    }
    @Override
    public ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllIds();
    }
    @Override
    public ItemDto searchItem(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id);
        return new ItemDto(item.getItemCode(),item.getSupplierId(),item.getDescription(),item.getUnitPrice(),item.getQty(),item.getSize());
    }
    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllIds();
    }
    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {

        Customer customer = customerDAO.search(id);

        CustomerDto customerDto = new CustomerDto(customer.getId(), customer.getName(), customer.getAddress(), customer.getTp(), customer.getDate() );

        return customerDto;
    }
    @Override
    public Double getDeductionById(String id) throws SQLException, ClassNotFoundException {
        return returnDetailDAO.getDeductionById(id);
    }
    @Override
    public ArrayList<String> getAllReturnIds() throws SQLException, ClassNotFoundException {
        return returnDAO.getAllIds();
    }
    @Override
    public boolean placeOrder(PlaceOrderDto placeOrderDto) throws SQLException {
        String orderId = placeOrderDto.getOrderId();
        String customerId = placeOrderDto.getCustomerId();
        String date = placeOrderDto.getDate();
        String time = placeOrderDto.getTime();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = saveOrder(new PlaceOrderDto(orderId, date, time,customerId));
            if (isOrderSaved) {
                boolean isUpdated = updateItemWhenOrder(placeOrderDto.getCartTms());
                if (isUpdated) {
                    boolean isOrderDetailSaved = saveOrderDetails(placeOrderDto.getOrderId(), placeOrderDto.getCartTms());
                    if (isOrderDetailSaved) {
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
    public boolean updateItemWhenOrder(List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            try {
                if(!itemDAO.updatesQtyWhenOrder(tm.getItemCode(), tm.getQty())) {
                    return false;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    @Override
    public boolean saveOrderDetails(String orderId, List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            try {
                if(!orderDetailDAO.add(new OrderDetail(tm.getItemCode(),orderId,tm.getUnitPrice(),tm.getQty()))) {
                    return false;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
