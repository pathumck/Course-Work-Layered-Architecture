package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.st_clothing.dto.CustomerDto;
import lk.ijse.st_clothing.dto.tm.CustomerTm;
import lk.ijse.st_clothing.model.CustomerModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomersFormController {

    @FXML
    private TableView<CustomerTm> tblCustomers;

    @FXML
    private TableColumn<CustomerTm, Button> colAction;

    @FXML
    private TableColumn<CustomerTm, String> colAddress;

    @FXML
    private TableColumn<CustomerTm, String> colId;

    @FXML
    private TableColumn<CustomerTm, String> colName;

    @FXML
    private TableColumn<CustomerTm, String> colRegDate;

    @FXML
    private TableColumn<CustomerTm, String> colTp;

    @FXML
    private Label lblRegDate;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtTp;

    @FXML
    private JFXTextField txtSearchByID;

    @FXML
    private JFXTextField txtSearchByName;


    public void initialize() {
        setTableCustomers();
        vitualize();
        setDate();
    }

    public void setTableCustomers() {
        try {
            ArrayList<CustomerDto> dtos = CustomerModel.getAllCustomers();
            ArrayList<CustomerTm> tms = new ArrayList<>();
            for (CustomerDto dto : dtos) {
                CustomerTm tm = new CustomerTm();
                tm.setId(dto.getId());
                tm.setName(dto.getName());
                tm.setAddress(dto.getAddress());
                tm.setTp(dto.getTp());
                tm.setDate(dto.getDate());
                tm.setBtn(new Button("Delete"));

                tms.add(tm);
            }
            ObservableList<CustomerTm> objects = FXCollections.observableArrayList(tms);
            tblCustomers.setItems(objects);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void vitualize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTp.setCellValueFactory(new PropertyValueFactory<>("tp"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void setDate() {
        lblRegDate.setText(String.valueOf(LocalDate.now()));
        lblRegDate.setStyle("-fx-text-fill: black; -fx-font-family: 'Diyuthi'; -fx-font-size: 12; -fx-font-weight: regular;");
    }

    @FXML
    void addCustomerBtnOnAction(ActionEvent event) throws SQLException {
        /*CustomerDto dto = new CustomerDto();
        dto.setName(txtName.getText());
        dto.setId(txtId.getText());
        dto.setAddress(txtAddress.getText());
        dto.setTp(txtTp.getText());
        dto.setDate(lblRegDate.getText());*/
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tp = txtTp.getText();
        String date = lblRegDate.getText();

        if (id.isEmpty()||name.isEmpty()||address.isEmpty()||tp.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Text Fields Empty!").show();
            return;
        }

        CustomerDto dto = new CustomerDto(id,name,address,tp,date);


            try {
                Boolean flag = CustomerModel.addCustomer(dto);
                if (flag) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved!").show();
                    setTableCustomers();
                    clearTextFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }

    public void clearTextFields() {
        txtAddress.clear();
        txtId.clear();
        txtName.clear();
        txtTp.clear();
    }

    @FXML
    void txtSearchByIdOnAction(ActionEvent event) {
        System.out.println("gfdfggf");
    }

    @FXML
    void IdSearchOnAction(ActionEvent event) {
        System.out.println("dhdhf");
    }

}


