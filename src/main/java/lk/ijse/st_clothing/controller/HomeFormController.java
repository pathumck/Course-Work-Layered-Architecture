package lk.ijse.st_clothing.controller;

public class HomeFormController {
    public void initialize() {
        if(OrdersFormController.scanning==true) {
            OrdersFormController.webcamPanel.stop();
            OrdersFormController.webcam.close();
            OrdersFormController.scanning = false;
        }

        if(ReturnsFormController.scanning1==true) {
            ReturnsFormController.webcamPanel1.stop();
            ReturnsFormController.webcam1.close();
            ReturnsFormController.scanning1 = false;
        }
    }
}
