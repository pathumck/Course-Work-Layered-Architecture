package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoard {
    @FXML
    private JFXButton btnOrder;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private AnchorPane subAnchorPaneLeft;

    @FXML
    private AnchorPane subAnchorPaneRight;

    @FXML
    public void btnOrderOnAction(ActionEvent event) throws IOException {
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/Login_form.fxml"));
            this.subAnchorPaneRight.getChildren().clear();
            this.subAnchorPaneRight.getChildren().add(rootNode);
    }



}
