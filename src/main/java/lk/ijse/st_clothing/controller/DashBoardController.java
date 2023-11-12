package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class DashBoardController {
    @FXML
    private JFXButton btnOrder;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private AnchorPane subAnchorPaneLeft;

    @FXML
    private AnchorPane subAnchorPaneRight;

    @FXML
    private Label lblDate;

    @FXML
    private AnchorPane subAnchorPaneTop;

    public void initialize() {
        setDate();
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/home_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

        // Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home");

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/orders_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

        // Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Order");
    }

    @FXML
    void btnReturnsOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/returns_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

        // Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Returns");
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/suppliers_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

        // Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Suppliers");
    }

    @FXML
    void btnItemsOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/items_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

        // Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Items");
    }

    @FXML
    void customersBtnOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customers_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

        // Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
    }


    public void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
        lblDate.setStyle("-fx-text-fill: white; -fx-font-family: 'Diyuthi';");

    }


}
