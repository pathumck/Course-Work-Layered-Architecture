package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static lk.ijse.Launcher.rootNode;


public class DashBoardController {
    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnReturns;

    @FXML
    private JFXButton btnItems;

    @FXML
    private JFXButton btnCustomers;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnExpences;

    @FXML
    private JFXButton btnSuppliers;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private AnchorPane subAnchorPaneLeft;

    @FXML
    private AnchorPane subAnchorPaneRight;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTitle;

    @FXML
    private AnchorPane subAnchorPaneTop;

    @FXML
    private Label lblTime;

    public void initialize() {
        setTitle("Home");
        setDate();
        setTime();
        startUpButtonsColours();
    }

    public void startUpButtonsColours() {
        // Set corner radius and change color when pressed
        btnHome.setStyle("-fx-background-color: white; -fx-background-radius: 0;");

        btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color:  white; -fx-background-radius: 0;"));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: white; -fx-background-radius: 0;"));
        btnOrder.setOnMouseEntered(e -> btnOrder.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnOrder.setOnMouseExited(e -> btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnReturns.setOnMouseEntered(e -> btnReturns.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnReturns.setOnMouseExited(e -> btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnItems.setOnMouseEntered(e -> btnItems.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnItems.setOnMouseExited(e -> btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnExpences.setOnMouseEntered(e -> btnExpences.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnExpences.setOnMouseExited(e -> btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseEntered(e -> btnEmployee.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseExited(e -> btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        // Set corner radius and change color when pressed
        btnHome.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnHome.setOnMousePressed(e -> btnHome.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
// Reset corner radius and revert color when released
        btnHome.setOnMouseReleased(e -> btnHome.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));

// Set the initial styles
        btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");

// Set the button's style on mouse enter and revert on mouse exit
        btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnOrder.setOnMouseEntered(e -> btnOrder.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnOrder.setOnMouseExited(e -> btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnReturns.setOnMouseEntered(e -> btnReturns.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnReturns.setOnMouseExited(e -> btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnItems.setOnMouseEntered(e -> btnItems.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnItems.setOnMouseExited(e -> btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnExpences.setOnMouseEntered(e -> btnExpences.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnExpences.setOnMouseExited(e -> btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseEntered(e -> btnEmployee.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseExited(e -> btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/home_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

// Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setTitle("Home");

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {

        if(OrdersFormController.scanning==true) {
            OrdersFormController.webcamPanel.stop();
            OrdersFormController.webcam.close();
            OrdersFormController.scanning = false;
        }

        // Set corner radius and change color when pressed
        btnOrder.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnOrder.setOnMousePressed(e -> btnOrder.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
// Reset corner radius and revert color when released
        btnOrder.setOnMouseReleased(e -> btnOrder.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));

// Set the initial styles
        btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");

// Set the button's style on mouse enter and revert on mouse exit
        btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnOrder.setOnMouseEntered(e -> btnOrder.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnOrder.setOnMouseExited(e -> btnOrder.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnReturns.setOnMouseEntered(e -> btnReturns.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnReturns.setOnMouseExited(e -> btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnItems.setOnMouseEntered(e -> btnItems.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnItems.setOnMouseExited(e -> btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnExpences.setOnMouseEntered(e -> btnExpences.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnExpences.setOnMouseExited(e -> btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseEntered(e -> btnEmployee.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseExited(e -> btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/orders_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

// Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setTitle("Order");

    }

    @FXML
    void btnReturnsOnAction(ActionEvent event) throws IOException {
        if(ReturnsFormController.scanning1==true) {
            ReturnsFormController.webcamPanel1.stop();
            ReturnsFormController.webcam1.close();
            ReturnsFormController.scanning1 = false;
        }

        // Set corner radius and change color when pressed
        btnReturns.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnReturns.setOnMousePressed(e -> btnReturns.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
// Reset corner radius and revert color when released
        btnReturns.setOnMouseReleased(e -> btnReturns.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));

// Set the initial styles
        btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");

// Set the button's style on mouse enter and revert on mouse exit
        btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnOrder.setOnMouseEntered(e -> btnOrder.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnOrder.setOnMouseExited(e -> btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnReturns.setOnMouseEntered(e -> btnReturns.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnReturns.setOnMouseExited(e -> btnReturns.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnItems.setOnMouseEntered(e -> btnItems.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnItems.setOnMouseExited(e -> btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnExpences.setOnMouseEntered(e -> btnExpences.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnExpences.setOnMouseExited(e -> btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseEntered(e -> btnEmployee.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseExited(e -> btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/returns_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

// Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setTitle("Returns");

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        // Set corner radius and change color when pressed
        btnSuppliers.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnSuppliers.setOnMousePressed(e -> btnSuppliers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
// Reset corner radius and revert color when released
        btnSuppliers.setOnMouseReleased(e -> btnSuppliers.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));

// Set the initial styles
        btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");

// Set the button's style on mouse enter and revert on mouse exit
        btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnOrder.setOnMouseEntered(e -> btnOrder.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnOrder.setOnMouseExited(e -> btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnReturns.setOnMouseEntered(e -> btnReturns.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnReturns.setOnMouseExited(e -> btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnItems.setOnMouseEntered(e -> btnItems.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnItems.setOnMouseExited(e -> btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnExpences.setOnMouseEntered(e -> btnExpences.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnExpences.setOnMouseExited(e -> btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseEntered(e -> btnEmployee.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseExited(e -> btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/suppliers_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

// Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setTitle("Suppliers");

    }

    @FXML
    void btnItemsOnAction(ActionEvent event) throws IOException {
        // Set corner radius and change color when pressed
        btnItems.setStyle("-fx-background-color:  #e4e6eb; -fx-background-radius: 0;");
        btnItems.setOnMousePressed(e -> btnItems.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
// Reset corner radius and revert color when released
        btnItems.setOnMouseReleased(e -> btnItems.setStyle("-fx-background-color:  #e4e6eb; -fx-background-radius: 0;"));

// Set the initial styles
        btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");

// Set the button's style on mouse enter and revert on mouse exit
        btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnOrder.setOnMouseEntered(e -> btnOrder.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnOrder.setOnMouseExited(e -> btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnReturns.setOnMouseEntered(e -> btnReturns.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnReturns.setOnMouseExited(e -> btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnItems.setOnMouseEntered(e -> btnItems.setStyle("-fx-background-color:  #e4e6eb; -fx-background-radius: 0;"));
        btnItems.setOnMouseExited(e -> btnItems.setStyle("-fx-background-color:  #e4e6eb; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnExpences.setOnMouseEntered(e -> btnExpences.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnExpences.setOnMouseExited(e -> btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseEntered(e -> btnEmployee.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseExited(e -> btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/items_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

// Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setTitle("Items");

    }

    @FXML
    void customersBtnOnAction(ActionEvent event) throws IOException {
        if(OrdersFormController.scanning==true) {
            OrdersFormController.webcamPanel.stop();
            OrdersFormController.webcam.close();
            OrdersFormController.scanning = false;
        }

        if(ReturnsFormController.scanning1==true) {
            ReturnsFormController.webcamPanel1.stop();
            ReturnsFormController.webcam1.close();
            ReturnsFormController.scanning1 = false;
        }

        // Set corner radius and change color when pressed
        btnCustomers.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnCustomers.setOnMousePressed(e -> btnCustomers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
// Reset corner radius and revert color when released
        btnCustomers.setOnMouseReleased(e -> btnCustomers.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));

// Set the initial styles
        btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");

// Set the button's style on mouse enter and revert on mouse exit
        btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnOrder.setOnMouseEntered(e -> btnOrder.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnOrder.setOnMouseExited(e -> btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnReturns.setOnMouseEntered(e -> btnReturns.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnReturns.setOnMouseExited(e -> btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnItems.setOnMouseEntered(e -> btnItems.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnItems.setOnMouseExited(e -> btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnExpences.setOnMouseEntered(e -> btnExpences.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnExpences.setOnMouseExited(e -> btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseEntered(e -> btnEmployee.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseExited(e -> btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customers_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

// Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setTitle("Customers");

    }


    public void setDate() {
        lblDate.setText(String.valueOf("Date : "+LocalDate.now()));
        lblDate.setStyle("-fx-text-fill: #636e72; -fx-font-family: 'Dyuthi'; -fx-font-size: 12; -fx-font-weight: bold;");
    }
    @FXML
    void expencesBtnOnAction(ActionEvent event) throws IOException {
        // Set corner radius and change color when pressed
        btnExpences.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnExpences.setOnMousePressed(e -> btnExpences.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
// Reset corner radius and revert color when released
        btnExpences.setOnMouseReleased(e -> btnExpences.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));

// Set the initial styles
        btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");

// Set the button's style on mouse enter and revert on mouse exit
        btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnOrder.setOnMouseEntered(e -> btnOrder.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnOrder.setOnMouseExited(e -> btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnReturns.setOnMouseEntered(e -> btnReturns.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnReturns.setOnMouseExited(e -> btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnItems.setOnMouseEntered(e -> btnItems.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnItems.setOnMouseExited(e -> btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnExpences.setOnMouseEntered(e -> btnExpences.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnExpences.setOnMouseExited(e -> btnExpences.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseEntered(e -> btnEmployee.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseExited(e -> btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/expences_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

// Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setTitle("Expences");

    }

    @FXML
    void employeeBtnOnAction(ActionEvent event) throws IOException {
        // Set corner radius and change color when pressed
        btnEmployee.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnEmployee.setOnMousePressed(e -> btnEmployee.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
// Reset corner radius and revert color when released
        btnEmployee.setOnMouseReleased(e -> btnEmployee.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));

// Set the initial styles
        btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");

// Set the button's style on mouse enter and revert on mouse exit
        btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnOrder.setOnMouseEntered(e -> btnOrder.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnOrder.setOnMouseExited(e -> btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnReturns.setOnMouseEntered(e -> btnReturns.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnReturns.setOnMouseExited(e -> btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnItems.setOnMouseEntered(e -> btnItems.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnItems.setOnMouseExited(e -> btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnExpences.setOnMouseEntered(e -> btnExpences.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnExpences.setOnMouseExited(e -> btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseEntered(e -> btnEmployee.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseExited(e -> btnEmployee.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
        this.subAnchorPaneRight.getChildren().clear();
        this.subAnchorPaneRight.getChildren().add(rootNode);

// Get the stage from the button's Scene and change the title
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setTitle("Employee");

    }

    public void setTitle(String title) {
        double fontSize = 16.0;
        lblTitle.setText(title);
        lblTitle.setStyle("-fx-text-fill: Black; -fx-font-family: 'Dyuthi'; -fx-font-size: " + fontSize + "pt;");
    }

    public void setTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateTime())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTime() {
        // Get the current time
        LocalTime currentTime = LocalTime.now();
        lblTime.setStyle("-fx-text-fill: #636e72; -fx-font-family: 'Dyuthi'; -fx-font-size: 12; -fx-font-weight: bold;");
        // Format the time using a DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        // Update the Label with the formatted time

        lblTime.setText("Time : "+formattedTime);

    }
    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {

        if(OrdersFormController.scanning==true) {
            OrdersFormController.webcamPanel.stop();
            OrdersFormController.webcam.close();
            OrdersFormController.scanning = false;
        }

        if(ReturnsFormController.scanning1==true) {
            ReturnsFormController.webcamPanel1.stop();
            ReturnsFormController.webcam1.close();
            ReturnsFormController.scanning1 = false;
        }

        // Set corner radius and change color when pressed
        btnLogout.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnLogout.setOnMousePressed(e -> btnLogout.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
// Reset corner radius and revert color when released
        btnLogout.setOnMouseReleased(e -> btnLogout.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));

// Set the initial styles
        btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        btnEmployee.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");

// Set the button's style on mouse enter and revert on mouse exit
        btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnOrder.setOnMouseEntered(e -> btnOrder.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnOrder.setOnMouseExited(e -> btnOrder.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnReturns.setOnMouseEntered(e -> btnReturns.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnReturns.setOnMouseExited(e -> btnReturns.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnItems.setOnMouseEntered(e -> btnItems.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnItems.setOnMouseExited(e -> btnItems.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnExpences.setOnMouseEntered(e -> btnExpences.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 0;"));
        btnExpences.setOnMouseExited(e -> btnExpences.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseEntered(e -> btnEmployee.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnEmployee.setOnMouseExited(e -> btnEmployee.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;"));







        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to logout!", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            Stage currentStage = (Stage) btnLogout.getScene().getWindow();
            rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_forum.fxml"));
            Scene scene = new Scene(rootNode);
            currentStage.setScene(scene);
            currentStage.centerOnScreen();
            currentStage.setTitle("");
            currentStage.show();
            // Locate the AnchorPane within dashboard_form.fxml
            AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

            // Load and set the content from another FXML file
            FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
            AnchorPane subContent = subLoader.load();

            // Add the subContent to the targetAnchorPane
            targetAnchorPane.getChildren().setAll(subContent);
            return;
        }
        btnLogout.setFocusTraversable(false);

        btnLogout.setStyle("-fx-background-color: #B0B3B8; -fx-background-radius: 0;");
        startUpButtonsColours();
        // Locate the AnchorPane within dashboard_form.fxml
        AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

        // Load and set the content from another FXML file
        FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/home_form.fxml"));
        AnchorPane subContent = subLoader.load();

        // Add the subContent to the targetAnchorPane
        targetAnchorPane.getChildren().setAll(subContent);

    }
}
