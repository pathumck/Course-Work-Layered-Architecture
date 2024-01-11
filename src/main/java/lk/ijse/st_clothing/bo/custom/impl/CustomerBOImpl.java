package lk.ijse.st_clothing.bo.custom.impl;

import lk.ijse.st_clothing.bo.custom.CustomerBO;
import lk.ijse.st_clothing.dao.DAOFactory;
import lk.ijse.st_clothing.dao.custom.CustomerDAO;
import lk.ijse.st_clothing.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.st_clothing.dto.CustomerDto;
import lk.ijse.st_clothing.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDto dto = new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(),customer.getTp(),customer.getDate());
            customerDtos.add(dto);
        }
        return customerDtos;
    }
    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }
    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllIds();
    }
    @Override
    public boolean addCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getTp(),dto.getDate()));
    }
    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getTp(),dto.getDate()));
    }
}
