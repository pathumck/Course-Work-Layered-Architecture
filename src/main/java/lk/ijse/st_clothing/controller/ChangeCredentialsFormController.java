package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.st_clothing.bo.BOFactory;
import lk.ijse.st_clothing.bo.custom.ChangeCredentialsBO;
import lk.ijse.st_clothing.bo.custom.impl.ChangeCredentialsBOImpl;
import lk.ijse.st_clothing.dto.CredentialsDto;

import java.io.IOException;
import java.sql.SQLException;

import static lk.ijse.Launcher.rootNode;

public class ChangeCredentialsFormController {
    @FXML
    private JFXButton btnBack;
    @FXML
    private JFXButton btnSubmit;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField txtUserName;
    ChangeCredentialsBO changeCredentialsBO = (ChangeCredentialsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CHANGE_CRED);
    @FXML
    void btnBackOnAction(ActionEvent event) {
        // Locate the AnchorPane within dashboard_form.fxml
        AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

        // Load and set the content from another FXML file
        FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
        AnchorPane subContent = null;
        try {
            subContent = subLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the subContent to the targetAnchorPane
        targetAnchorPane.getChildren().setAll(subContent);
    }
    @FXML
    void btnSubmitOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        CredentialsDto dto = null;
        try {
            dto = changeCredentialsBO.getCredentials();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println(dto.getPassword());

        if (userName.equals(dto.getUserName()) && password.equals(dto.getPassword())) {
            // Locate the AnchorPane within dashboard_form.fxml
            AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

            // Load and set the content from another FXML file
            FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/new_credentials_form.fxml"));
            AnchorPane subContent = null;
            try {
                subContent = subLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Add the subContent to the targetAnchorPane
            targetAnchorPane.getChildren().setAll(subContent);
            return;
        }

        if (userName.isEmpty()||password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Fields Empty!").show();
        }
        else {
            new Alert(Alert.AlertType.ERROR, "Check Credentials!").show();
        }
    }
}
