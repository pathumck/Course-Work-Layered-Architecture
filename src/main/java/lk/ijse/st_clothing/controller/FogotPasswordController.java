package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

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

    private String pwd;

    public void initialize() {
        btnConfirm.setDisable(true);
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        // Locate the AnchorPane within dashboard_form.fxml
        pwd = null;
        btnConfirm.setDisable(true);

        AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

        // Load and set the content from another FXML file
        FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
        AnchorPane subContent = subLoader.load();

        // Add the subContent to the targetAnchorPane
        targetAnchorPane.getChildren().setAll(subContent);
    }

    @FXML
    void btnClickHereOnAction(ActionEvent event) {
        final String numbers = "0123456789";
        final int OTP_LENGTH = 6;

        final String username = "ckpathumlahiruck@gmail.com";
        final String password = "hjew sikl mkzi dnfe";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ckpathumlahiruck@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ckpathumck@gmail.com"));
            message.setSubject("ST Clothing Management");

            Random random = new Random();
            StringBuilder otp = new StringBuilder();

            for (int i = 0; i < OTP_LENGTH; i++) {
                int index = random.nextInt(numbers.length());
                otp.append(numbers.charAt(index));
            }

            pwd = otp.toString();

            message.setText("This is ST Clothing Management System.\n\nYour OTP is "+pwd);

            Transport.send(message);
            btnConfirm.setDisable(false);
            new Alert(Alert.AlertType.CONFIRMATION,"Otp Sent to your email successfully!").show();
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            new Alert(Alert.AlertType.ERROR,"No internet connection").show();
        }
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) throws IOException {
        String otp = txtOTP.getText();
        if(otp.isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Please Enter OTP!").show();
            return;
        }

        if(!otp.equals(pwd)) {
            new Alert(Alert.AlertType.ERROR,"Wrong Input!").show();
            return;
        }

        if(otp.equals(pwd)) {
            // Locate the AnchorPane within dashboard_form.fxml
            AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

            // Load and set the content from another FXML file
            FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/new_credentials_form.fxml"));
            AnchorPane subContent = subLoader.load();

            // Add the subContent to the targetAnchorPane
            targetAnchorPane.getChildren().setAll(subContent);
        }
    }
}
