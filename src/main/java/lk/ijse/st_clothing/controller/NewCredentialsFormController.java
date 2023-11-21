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

public class NewCredentialsFormController {
    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXPasswordField txtPassword1;

    @FXML
    private JFXPasswordField txtPassword2;

    @FXML
    private JFXTextField txtUserName1;

    @FXML
    private JFXTextField txtUserName2;

    @FXML
    void btnSubmitOnAction(ActionEvent event) throws SQLException, IOException {
        String userName1 = txtUserName1.getText();
        String userName2 = txtUserName2.getText();
        String password1 = txtPassword1.getText();
        String password2 = txtPassword2.getText();

        if (userName1.isEmpty() || userName2.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fields Empty!").show();
            return;
        }
        if (!userName1.equals(userName2)) {
            new Alert(Alert.AlertType.ERROR, "Username fields not matching!").show();
            return;
        }
        if (!password1.equals(password2)) {
            new Alert(Alert.AlertType.ERROR, "Passwords fields not matching!").show();
            return;
        }
        if (userName1.equals(userName2)&&password1.equals(password2)) {
            CredentialsDto dto = new CredentialsDto(userName1, password1);
            CredentialsDto db = LoginModel.getCredentials();
            Boolean flag = LoginModel.updateCredentials(dto,db);

            if (flag) {
                // Locate the AnchorPane within dashboard_form.fxml
                AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

                // Load and set the content from another FXML file
                FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
                AnchorPane subContent = subLoader.load();

                // Add the subContent to the targetAnchorPane
                targetAnchorPane.getChildren().setAll(subContent);


                new Alert(Alert.AlertType.CONFIRMATION, "Username and password changed!").show();
            }
          //  new Alert(Alert.AlertType.ERROR, "Somthing Wrong!").show();
        }
    }
}
