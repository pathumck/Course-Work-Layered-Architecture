package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.st_clothing.dto.tm.CredentialsDto;
import lk.ijse.st_clothing.model.LoginModel;

import java.io.IOException;
import java.sql.SQLException;

import static lk.ijse.Launcher.rootNode;

public class ChangeCredentialsForm {
    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

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
    void btnSubmitOnAction(ActionEvent event) throws SQLException, IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        CredentialsDto dto = LoginModel.getCredentials();
        System.out.println(dto.getPassword());

        if (userName.equals(dto.getUserName()) && password.equals(dto.getPassword())) {
            // Locate the AnchorPane within dashboard_form.fxml
            AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

            // Load and set the content from another FXML file
            FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/new_credentials_form.fxml"));
            AnchorPane subContent = subLoader.load();

            // Add the subContent to the targetAnchorPane
            targetAnchorPane.getChildren().setAll(subContent);
            return;
        }

        if (userName.isEmpty()||password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Fields Empty!").show();
            return;
        }
        else {
            new Alert(Alert.AlertType.ERROR, "Check Credentials!").show();
        }
    }

}
