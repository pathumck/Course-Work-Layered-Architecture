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
import lk.ijse.st_clothing.dto.ExpenceDto;
import lk.ijse.st_clothing.dto.tm.ExpenceTm;
import lk.ijse.st_clothing.model.ExpenceModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

public class ExpencesFormController {
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<ExpenceTm, Button> colAction;

    @FXML
    private TableColumn<ExpenceTm, Double> colAmount;

    @FXML
    private TableColumn<ExpenceTm, String> colDate;

    @FXML
    private TableColumn<ExpenceTm, String> colDescription;

    @FXML
    private TableColumn<ExpenceTm, String> colExpenceId;

    @FXML
    private TableColumn<ExpenceTm, String> colType;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TableView<ExpenceTm> tblExpence;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtSearchByExpenceId;

    @FXML
    private JFXTextField txtType;

    private ObservableList<ExpenceTm> toTable;
    public void initialize() throws SQLException {
        dpDate.setEditable(false);
        setTableExpences();
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
    public void setTableExpences() {
        try {
            ArrayList<ExpenceDto> dtos = ExpenceModel.getAllExpences();
            ArrayList<ExpenceTm> tms = new ArrayList<>();
            for (ExpenceDto dto : dtos) {
                ExpenceTm tm = new ExpenceTm();
                tm.setId(dto.getId());
                tm.setDescription(dto.getDescription());
                tm.setType(dto.getType());
                tm.setDate(dto.getDate());
                tm.setAmount(dto.getAmount());
                Button btn = new Button("Delete");
                btn.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
                setRemoveBtnAction(btn);
                tm.setBtn(btn);
                tms.add(tm);

            }

            toTable = FXCollections.observableArrayList(tms);
            tblExpence.setItems(toTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchFilter() {
        FilteredList<ExpenceTm> filterData= new FilteredList<>(toTable, e->true);
        txtSearchByExpenceId.setOnKeyReleased(e->{


            txtSearchByExpenceId.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super ExpenceTm >) cust->{
                    if(newValue==null){
                        return true;
                    }
                    String toLowerCaseFilter = newValue.toLowerCase();
                    if(cust.getId().contains(newValue)){
                        return true;
                    }else  if(cust.getType().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getDescription().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getDate().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getAmount().toString().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }

                    return false;
                });
            });

            final SortedList<ExpenceTm> customers = new SortedList<>(filterData);
            customers.comparatorProperty().bind(tblExpence.comparatorProperty());
            tblExpence.setItems(customers);
            //ok let's check it
        });

    }


    public void vitualize() {
        colExpenceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            Integer index = tblExpence.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            String id = colExpenceId.getCellData(index).toString();
            try {
                Boolean flag = ExpenceModel.deleteExpence(id);
                if(flag){
                    clearAllFields();
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
                }
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }
        });
    }

    public void clearAllFields() throws SQLException {
        txtId.clear();
        txtAmount.clear();
        txtDescription.clear();
        txtType.clear();
        txtSearchByExpenceId.clear();
        dpDate.setValue(null);
        initialize();
    }

    @FXML
    void addExpenceBtnOnAction(ActionEvent event) {
        String id = txtId.getText();
        String description = txtDescription.getText();
        String type = txtType.getText();
        String amount = txtAmount.getText();
        String date = String.valueOf(dpDate.getValue());

        if (id.isEmpty()||description.isEmpty()||type.isEmpty()||amount.isEmpty()||dpDate.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"Fields Empty!").show();
            return;
        }
        Double amount1 = Double.parseDouble(amount);
        ExpenceDto dto = new ExpenceDto(id,type,description,date,amount1);

        try {
            Boolean flag = ExpenceModel.addExpence(dto);
            if (flag) {
                new Alert(Alert.AlertType.CONFIRMATION, "Expence Saved!").show();
                clearAllFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {
        String id = txtId.getText();
        String description = txtDescription.getText();
        String type = txtType.getText();
        String amount = txtAmount.getText();
        String date = String.valueOf(dpDate.getValue());

        if (id.isEmpty()||description.isEmpty()||type.isEmpty()||amount.isEmpty()||dpDate.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"Fields Empty!").show();
            return;
        }
        Double amount1 = Double.parseDouble(amount);

        ExpenceDto dto = new ExpenceDto(id,type,description,date,amount1);
        try {
            Boolean flag = ExpenceModel.updateExpences(dto);
            if(flag) {
                new Alert(Alert.AlertType.CONFIRMATION, "Expence Updated").show();
                setTableExpences();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Check Expence ID!").show();
        }
    }

    @FXML
    void mouseClickOnAction(MouseEvent event) {
        Integer index = tblExpence.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        txtId.setText(colExpenceId.getCellData(index).toString());
        txtType.setText(colType.getCellData(index).toString());
        txtDescription.setText(colDescription.getCellData(index).toString());
        txtAmount.setText(colAmount.getCellData(index).toString());
        dpDate.setValue(LocalDate.parse(colDate.getCellData(index).toString()));
    }

    @FXML
    void btnClearAllFieldsOnAction(ActionEvent event) throws SQLException {
        clearAllFields();
    }
    @FXML
    void txtSearchByExpenceIdOnMouseClicked(MouseEvent event) throws SQLException {
        clearAllFields();
    }
}
