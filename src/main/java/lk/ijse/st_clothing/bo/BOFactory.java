package lk.ijse.st_clothing.bo;

import lk.ijse.st_clothing.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }
    public static BOFactory getBoFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        CHANGE_CRED,CUSTOMER,EMPLOYEE,EXPENCE,HOME,ITEM,LOGIN,NEWCRED,ORDER,RETURN,SUPPLIER
    }
    public SuperBO getBO (BOFactory.BOTypes boTypes) {
        switch (boTypes) {
            case CHANGE_CRED:
                return new ChangeCredentialsBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case EXPENCE:
                return new ExpenceBOImpl();
            case HOME:
                return new HomeBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case LOGIN:
                return new LoginFormBOImpl();
            case NEWCRED:
                return new NewCredentialsBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case RETURN:
                return new ReturnBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            default:
                return null;
        }
    }
}
