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
import lk.ijse.st_clothing.bo.custom.NewCredentialsBO;
import lk.ijse.st_clothing.bo.custom.impl.NewCredentialsBOImpl;
import lk.ijse.st_clothing.dto.CredentialsDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

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
    NewCredentialsBO newCredentialsBO = (NewCredentialsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.NEWCRED);
    @FXML
    void btnSubmitOnAction(ActionEvent event) {
        String userName1 = txtUserName1.getText();
        String userName2 = txtUserName2.getText();
        String password1 = txtPassword1.getText();
        String password2 = txtPassword2.getText();

        if (userName1.isEmpty() || userName2.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fields Empty!").show();
            return;
        }

        if(!validateCredentials()) {
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
            CredentialsDto newCred = new CredentialsDto(userName1, password1);
            CredentialsDto currentCred = null;
            try {
                currentCred = newCredentialsBO.getUserCredentials();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            Boolean flag = null;
            try {
                flag = newCredentialsBO.updateUserCredentials(newCred,currentCred);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            if (flag) {
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
                new Alert(Alert.AlertType.CONFIRMATION, "Username and password changed!").show();
            }
        }
    }
    private Boolean validateCredentials() {
        String name1= txtUserName1.getText();
        String name2= txtUserName2.getText();
        String pwd1 = txtPassword1.getText();
        String pwd2 = txtPassword2.getText();
        boolean name1Match = Pattern.matches("^.{8,}$",name1);
        boolean name2Match = Pattern.matches("^.{8,}$",name2);
        boolean pwd1Match = Pattern.matches("^.{8,}$",pwd1);
        boolean pwd2Match = Pattern.matches("^.{8,}$",pwd2);
        if (!name1Match||!name2Match||!pwd1Match||!pwd2Match) {
            new Alert(Alert.AlertType.ERROR,"User Name Or Password should contain atleast 8 characters!").show();
            return false;
        }
        return true;
    }
}
