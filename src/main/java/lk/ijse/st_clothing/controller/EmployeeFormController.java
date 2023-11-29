package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.st_clothing.dto.EmployeeDto;
import lk.ijse.st_clothing.dto.tm.EmployeeTm;
import lk.ijse.st_clothing.model.EmployeeModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

public class EmployeeFormController {
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClearAllFields;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private TableColumn<EmployeeTm, Button> colAction;

    @FXML
    private TableColumn<EmployeeTm, String> colAddress;

    @FXML
    private TableColumn<EmployeeTm, String> colDOB;

    @FXML
    private TableColumn<EmployeeTm, String> colDate;

    @FXML
    private TableColumn<EmployeeTm, String> colGender;

    @FXML
    private TableColumn<EmployeeTm, String> colId;

    @FXML
    private TableColumn<EmployeeTm, String> colNIC;

    @FXML
    private TableColumn<EmployeeTm, String> colName;

    @FXML
    private TableColumn<EmployeeTm, String> colTp;

    @FXML
    private DatePicker dpDOB;

    @FXML
    private Label lblRegDate;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearchId;
    @FXML
    private JFXTextField txtTp;
    @FXML
    private JFXTextField txtId;

    private ObservableList<EmployeeTm> toTable;

    public void initialize() throws SQLException {
        String[] genders = {"Male", "Female", "Other"};
        cmbGender.setItems(FXCollections.observableArrayList(genders));
        dpDOB.setEditable(false);
        setDate();
        setTableEmployee();
        vitualize();
        searchFilter();

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
    }

    private void searchFilter() {
        FilteredList<EmployeeTm> filterData= new FilteredList<>(toTable, e->true);
        txtSearchId.setOnKeyReleased(e->{


            txtSearchId.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super EmployeeTm >) cust->{
                    if(newValue==null){
                        return true;
                    }
                    String toLowerCaseFilter = newValue.toLowerCase();
                    if(cust.getId().contains(newValue)){
                        return true;
                    }else  if(cust.getAddress().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getNic().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getDob().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getName().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getTp().toLowerCase().contains(toLowerCaseFilter)) {
                        return true;
                    }else  if(cust.getDate().toLowerCase().contains(toLowerCaseFilter)) {
                        return true;
                    }else  if(cust.getGender().toLowerCase().contains(toLowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            final SortedList<EmployeeTm> customers = new SortedList<>(filterData);
            customers.comparatorProperty().bind(tblEmployee.comparatorProperty());
            tblEmployee.setItems(customers);
        });

    }


    public void setDate() {
        lblRegDate.setText(String.valueOf(LocalDate.now()));
        lblRegDate.setStyle("-fx-text-fill: black; -fx-font-family: 'Diyuthi'; -fx-font-size: 12; -fx-font-weight: regular;");
    }

    public void setTableEmployee() {
        try {
            ArrayList<EmployeeDto> dtos = EmployeeModel.getAllEmployee();
            ArrayList<EmployeeTm> tms = new ArrayList<>();
            for (EmployeeDto dto : dtos) {
                EmployeeTm tm = new EmployeeTm();
                tm.setId(dto.getId());
                tm.setName(dto.getName());
                tm.setAddress(dto.getAddress());
                tm.setNic(dto.getNic());
                tm.setGender(dto.getGender());
                tm.setDob(dto.getDob());
                tm.setDate(dto.getDate());
                tm.setTp(dto.getTp());
                Button btn = new Button("Delete");
                btn.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
                setRemoveBtnAction(btn);
                tm.setBtn(btn);
                tms.add(tm);
            }
            toTable = FXCollections.observableArrayList(tms);
            tblEmployee.setItems(toTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void vitualize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTp.setCellValueFactory(new PropertyValueFactory<>("tp"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            Integer index = tblEmployee.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            String id = colId.getCellData(index).toString();
            try {
                Boolean flag = EmployeeModel.deleteEmployee(id);
                if(flag){
                    clearAllFields();
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
                }
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }
        });
    }

    @FXML
    void addBtnOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String gender = String.valueOf(cmbGender.getValue());
        String dob = String.valueOf(dpDOB.getValue());
        String date = String.valueOf(LocalDate.now());
        String tp = txtTp.getText();

        if (id.isEmpty()||name.isEmpty()||address.isEmpty()||tp.isEmpty()||nic.isEmpty()|| cmbGender.getValue()==null||dpDOB.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"Fields Empty!").show();
            return;
        }

        EmployeeDto dto = new EmployeeDto(id,name,address,nic,gender,dob,date,tp);

        try {
            Boolean flag = EmployeeModel.addEmployee(dto);
            if (flag) {
                clearAllFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String gender = String.valueOf(cmbGender.getValue());
        String dob = String.valueOf(dpDOB.getValue());
        String tp = txtTp.getText();

        if (id.isEmpty()||name.isEmpty()||address.isEmpty()||tp.isEmpty()||nic.isEmpty()|| cmbGender.getValue()==null||dpDOB.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"Fields Empty!").show();
            return;
        }

        EmployeeDto dto = new EmployeeDto(id,name,address,nic,gender,dob,tp);
        try {
            Boolean flag = EmployeeModel.updateEmployee(dto);
            if(flag) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated").show();
                setTableEmployee();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Check Employee ID!").show();
        }
    }

    @FXML
    void mouseClickOnAction(MouseEvent event) {
        Integer index = tblEmployee.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        txtId.setText(colId.getCellData(index).toString());
        txtName.setText(colName.getCellData(index).toString());
        txtAddress.setText(colAddress.getCellData(index).toString());
        txtNIC.setText(colNIC.getCellData(index).toString());
        txtTp.setText(colTp.getCellData(index).toString());
        lblRegDate.setText(colDate.getCellData(index).toString());
        cmbGender.setValue(colGender.getCellData(index).toString());
        dpDOB.setValue(LocalDate.parse(colDOB.getCellData(index).toString()));

    }




    public void clearAllFields() throws SQLException {
        txtId.clear();;
        txtName.clear();
        txtAddress.clear();
        txtNIC.clear();
        txtTp.clear();
        txtSearchId.clear();
        cmbGender.getItems().clear();
        dpDOB.setValue(null);
        initialize();
    }




    @FXML
    void btnClearAllFieldsOnAction(ActionEvent event) throws SQLException {
        clearAllFields();
    }
    @FXML
    void txtSearchEmployeeOnMouseClicked(MouseEvent event) throws SQLException {
        clearAllFields();
    }
}
