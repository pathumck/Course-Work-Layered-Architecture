package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.st_clothing.dto.CustomerDto;
import lk.ijse.st_clothing.dto.tm.CustomerTm;
import lk.ijse.st_clothing.model.CustomerModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CustomersFormController {
    @FXML
    private JFXButton btnAddCus;

    @FXML
    private JFXButton btnUpdateCus;

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

    private ObservableList<CustomerTm> objects;


    public void initialize() throws SQLException {
        setTableCustomers();
        vitualize();
        setDate();
        searchFilter();
        btnAddAction();
        btnUpdateAction();

    }

    private void searchFilter() {
        FilteredList<CustomerTm> filterData= new FilteredList<>(objects, e->true);
        txtSearchByID.setOnKeyReleased(e->{


            txtSearchByID.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super CustomerTm >) cust->{
                    if(newValue==null){
                        return true;
                    }
                    String toLowerCaseFilter = newValue.toLowerCase();
                    if(cust.getId().contains(newValue)){
                        return true;
                    }else  if(cust.getAddress().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getDate().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getTp().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getName().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }

                    return false;
                });
            });

            final SortedList<CustomerTm> customers = new SortedList<>(filterData);
            customers.comparatorProperty().bind(tblCustomers.comparatorProperty());
            tblCustomers.setItems(customers);
        });

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
                deleteButton.setCursor(Cursor.HAND);
                deleteButton.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
                setRemoveBtnAction(deleteButton);
                tm.setBtn(deleteButton);
                tms.add(tm);
            }
             objects = FXCollections.observableArrayList(tms);
            tblCustomers.setItems(objects);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            Integer index = tblCustomers.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                new Alert(Alert.AlertType.ERROR, "Please select a customers' table row to delete a customer!").show();
                return;
            }
            String id = colId.getCellData(index).toString();

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete customer \"" + id + "\" ?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {

                try {
                    Boolean flag = CustomerModel.deleteCustomer(id);
                    if (flag) {
                        clearAllFields();
                        new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                    }
                } catch (SQLException ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                }
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
       // lblRegDate.setStyle("-fx-text-fill: black; -fx-font-family: 'Diyuthi'; -fx-font-size: 12; -fx-font-weight: regular;");
    }

    public void btnAddAction() {
        btnAddCus.setOnAction((e) -> {
            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String tp = txtTp.getText();
            String date = String.valueOf(LocalDate.now());


            if (id.isEmpty()||name.isEmpty()||address.isEmpty()||tp.isEmpty()){
                new Alert(Alert.AlertType.ERROR,"Text Fields Empty!").show();
                return;
            }

            try {
                List<String> temp = CustomerModel.getCustomerIds();
                for (String s : temp) {
                    if(txtId.getText().equals(s)){
                        new Alert(Alert.AlertType.ERROR,"Customer already saved!").show();
                        return;
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            Boolean isValidate = validateCustomer();
            if(isValidate) {
                CustomerDto dto = new CustomerDto(id,name,address,tp,date);

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to add new customer \""+txtId.getText()+"\" ?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {

                    try {
            Boolean flag = CustomerModel.addCustomer(dto);
            if (flag) {
                clearAllFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved!").show();
            }
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR,"Error!").show();
        }

        }
    }
        });
    }

    public void btnUpdateAction() {
        btnUpdateCus.setOnAction((e) -> {

        try {
            List<String> temp = new ArrayList<>();
            temp = CustomerModel.getCustomerIds();
            Boolean flag = false;
            for (String s : temp) {
                if(txtId.getText().equals(s)) {
                    flag = true;
                }
            }
            if (flag.equals(false)) {
                new Alert(Alert.AlertType.ERROR,"Please select a row from customers' table to update acustomer!").show();
                return;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String tp = txtTp.getText();

            if (id.isEmpty() || name.isEmpty() || address.isEmpty() || tp.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Text Fields Empty!").show();
                return;
            }

            Boolean isValidate = validateCustomer();
            if (isValidate) {

                CustomerDto dto = new CustomerDto(id, name, address, tp);

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to update customer \""+txtId.getText()+"\" ?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {

                    try {
                        Boolean flag = CustomerModel.updateCustomer(dto);
                        if (flag) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated").show();
                            clearAllFields();
                        }
                    } catch (SQLException exception) {
                        new Alert(Alert.AlertType.ERROR, "Error!").show();
                    }
                }
            }
        });
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

    @FXML
    void txtSearchByIdOnMouseClicked(MouseEvent event) throws SQLException {
        clearAllFields();
    }

    private Boolean validateCustomer() {
        String id = txtId.getText();
        boolean idMatch = Pattern.matches("^(?:19|20)?\\d{2}[0-9]{10}|[0-9]{9}[x|X|v|V]$",id);
        if (!idMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid customer id!").show();
            return false;
        }


        String tp = txtTp.getText();
        boolean telMatch = Pattern.matches("[0-9]{10}",tp);
        if (!telMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid telphone!").show();
            return false;
        }

        String address = txtAddress.getText();
        boolean addressMatch= Pattern.matches("[A-za-z]{3,}",address);
        if (!addressMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid address!").show();
            return false;
        }
        String name= txtName.getText();
        boolean nameMatch = Pattern.matches("[A-za-z\\s]{4,}",name);
        if (!nameMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid name!").show();
            return false;
        }
        return true;
    }

    @FXML
    void idOnMouseClicked(MouseEvent event) throws SQLException {
        clearAllFields();
    }

}


