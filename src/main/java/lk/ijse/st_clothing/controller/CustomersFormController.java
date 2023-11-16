package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
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
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomersFormController {
    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnClearAllFields;

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



    public void initialize() throws SQLException {
        setTableCustomers();
        vitualize();
        setDate();
        loadAllItemCodes();

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
                Button deleteButton = new Button("Delete");
                setRemoveBtnAction(deleteButton);
                tm.setBtn(deleteButton);
                tms.add(tm);
            }
            ObservableList<CustomerTm> objects = FXCollections.observableArrayList(tms);
            tblCustomers.setItems(objects);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            Integer index = tblCustomers.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            String id = colId.getCellData(index).toString();
            try {
                Boolean flag = CustomerModel.deleteCustomer(id);
                if(flag){
                    clearAllFields();
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
                }
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }
        });
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
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tp = txtTp.getText();
        String date = String.valueOf(LocalDate.now());

        if (id.isEmpty()||name.isEmpty()||address.isEmpty()||tp.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Text Fields Empty!").show();
            return;
        }

        CustomerDto dto = new CustomerDto(id,name,address,tp,date);


        try {
            Boolean flag = CustomerModel.addCustomer(dto);
            if (flag) {
                clearAllFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tp = txtTp.getText();

        if (id.isEmpty()||name.isEmpty()||address.isEmpty()||tp.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Text Fields Empty!").show();
            return;
        }

        CustomerDto dto = new CustomerDto(id,name,address,tp);
        try {
            Boolean flag = CustomerModel.updateCustomer(dto);
            if(flag) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Updated").show();
                setTableCustomers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Check Item Code!").show();
        }
    }

    public void clearTextFields() {
        txtAddress.clear();
        txtId.clear();
        txtName.clear();
        txtTp.clear();
    }

    public void loadAllItemCodes() throws SQLException {
        ArrayList<String> cusIds = CustomerModel.getCustomerIds();
        TextFields.bindAutoCompletion(txtSearchByID,cusIds);
    }

    @FXML
    void txtSearchByIdOnAction(ActionEvent event) {
        try {
            CustomerDto dto = CustomerModel.getCustomerById(txtSearchByID.getText());
            if(dto!=null) {
            CustomerTm tm = new CustomerTm();
            tm.setId(dto.getId());
            tm.setName(dto.getName());
            tm.setAddress(dto.getAddress());
            tm.setTp(dto.getTp());
            tm.setDate(dto.getDate());
            Button delete = new Button("Delete");
            setRemoveBtnAction(delete);
            tm.setBtn(delete);
            ArrayList<CustomerTm> id = new ArrayList<>();
            id.add(tm);
            ObservableList<CustomerTm> customerTms = FXCollections.observableArrayList(id);
            tblCustomers.refresh();
            tblCustomers.setItems(customerTms);}
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void mouseClickOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        Integer index = tblCustomers.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        txtName.setText(colName.getCellData(index).toString());
        txtId.setText(colId.getCellData(index).toString());
        txtAddress.setText(colAddress.getCellData(index).toString());
        txtTp.setText(colTp.getCellData(index).toString());
        lblRegDate.setText(colRegDate.getCellData(index).toString());
    }

    @FXML
    void btnClearAllFieldsOnAction(ActionEvent event) throws SQLException {
        clearAllFields();
    }

    public void clearAllFields() throws SQLException {
        txtSearchByID.clear();
        txtId.clear();
        txtTp.clear();
        txtName.clear();
        txtAddress.clear();
        initialize();
    }
}


