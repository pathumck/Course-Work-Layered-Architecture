package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static lk.ijse.st_clothing.controller.OrdersFormController.webcam;
import static lk.ijse.st_clothing.controller.OrdersFormController.webcamPanel;
import static lk.ijse.st_clothing.controller.ReturnsFormController.webcam1;
import static lk.ijse.st_clothing.controller.ReturnsFormController.webcamPanel1;


public class Launcher extends Application {
    public static AnchorPane rootNode;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_forum.fxml"));
        Scene scene = new Scene(rootNode,1374,710);
        stage.setScene(scene);
        stage.setTitle("ST Clothing");
        stage.centerOnScreen();
        stage.show();

        // Locate the AnchorPane within dashboard_form.fxml
        AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

        // Load and set the content from another FXML file
        FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
        AnchorPane subContent = subLoader.load();

        // Add the subContent to the targetAnchorPane
        targetAnchorPane.getChildren().setAll(subContent);
    }

    @Override
    public void stop() {
        // This method is called when the application is closing

        // Close the webcam if it's open
        if (webcam != null && webcam.isOpen()) {
            webcamPanel.stop();
            webcam.close();
            System.exit(0);
        }

        if (webcam1 != null && webcam1.isOpen()) {
            webcamPanel1.stop();
            webcam1.close();
            System.exit(0);
        }
        System.exit(0);
    }
}