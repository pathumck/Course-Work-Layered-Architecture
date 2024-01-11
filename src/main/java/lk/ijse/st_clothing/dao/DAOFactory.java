package lk.ijse.st_clothing.dao;

import lk.ijse.st_clothing.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public static DAOFactory getDaoFactory() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,EMPLOYEE,EXPENCE,ITEM,ORDER,ORDER_DETAIL,QUERY,RETURN,RETURN_DETAIL,SUPPLIER,USER
    }
    public SuperDAO getDAO (DAOFactory.DAOTypes daoTypes) {
        switch (daoTypes) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case EXPENCE:
                return new ExpenceDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailsDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case RETURN:
                return new ReturnDAOImpl();
            case RETURN_DETAIL:
                return new ReturnDetailDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}
