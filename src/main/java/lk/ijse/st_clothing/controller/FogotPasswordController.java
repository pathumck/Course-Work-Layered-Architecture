package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static lk.ijse.Launcher.rootNode;

public class FogotPasswordController {
    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClickHere;

    @FXML
    private JFXButton btnConfirm;

    @FXML
    private JFXPasswordField txtOTP;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        // Locate the AnchorPane within dashboard_form.fxml
        AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

        // Load and set the content from another FXML file
        FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
        AnchorPane subContent = subLoader.load();

        // Add the subContent to the targetAnchorPane
        targetAnchorPane.getChildren().setAll(subContent);
    }

    @FXML
    void btnClickHereOnAction(ActionEvent event) {

    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) {

    }
}
