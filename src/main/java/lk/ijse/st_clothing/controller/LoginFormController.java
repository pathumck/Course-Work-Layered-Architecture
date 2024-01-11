package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.st_clothing.bo.BOFactory;
import lk.ijse.st_clothing.bo.custom.LoginFormBO;
import lk.ijse.st_clothing.bo.custom.impl.LoginFormBOImpl;
import lk.ijse.st_clothing.dto.CredentialsDto;

import java.io.IOException;
import java.sql.SQLException;

import static lk.ijse.Launcher.rootNode;

public class LoginFormController {
    @FXML
    private JFXButton btnChangeCredentials;
    @FXML
    private JFXButton btnFogotPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private AnchorPane subAnchorPaneRight;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField txtUsername;
    LoginFormBO loginFormBO = (LoginFormBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);
    @FXML
    void btnChangeCredentialsOnAction(ActionEvent event) {
        // Locate the AnchorPane within dashboard_form.fxml
        AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

        // Load and set the content from another FXML file
        FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/change_credentials_form.fxml"));
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
    void btnFogotPasswordOnAction(ActionEvent event) {
        // Locate the AnchorPane within dashboard_form.fxml
        AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

        // Load and set the content from another FXML file
        FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/fogot_password_form.fxml"));
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
    void btnLoginOnAction(ActionEvent event) {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        CredentialsDto dto = null;
        try {
            dto = loginFormBO.getUserCredentials();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println(dto.getPassword());

        if (userName.equals(dto.getUserName()) && password.equals(dto.getPassword())) {
            System.out.println(dto.getPassword());
            try {
                rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(rootNode);
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("");
            stage.centerOnScreen();
            stage.show();

            // Locate the AnchorPane within dashboard_form.fxml
            AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

            // Load and set the content from another FXML file
            FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/home_form.fxml"));
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
