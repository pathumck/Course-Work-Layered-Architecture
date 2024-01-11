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
import lk.ijse.st_clothing.bo.BOFactory;
import lk.ijse.st_clothing.bo.custom.ExpenceBO;
import lk.ijse.st_clothing.bo.custom.impl.ExpenceBOImpl;
import lk.ijse.st_clothing.dto.ExpenceDto;
import lk.ijse.st_clothing.dto.tm.ExpenceTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

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
    ExpenceBO expenceBO = (ExpenceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EXPENCE);
    public void initialize() {
        dpDate.setEditable(false);
        setTableExpences();
        vitualize();
        searchFilter();
        addExpenceAction();
        updateBtnAction();

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
            ArrayList<ExpenceDto> dtos = expenceBO.getAllExpences();
            ArrayList<ExpenceTm> tms = new ArrayList<>();
            for (ExpenceDto dto : dtos) {
                ExpenceTm tm = new ExpenceTm();
                tm.setId(dto.getId());
                tm.setDescription(dto.getDescription());
                tm.setType(dto.getType());
                tm.setDate(dto.getDate());
                tm.setAmount(dto.getAmount());
                Button btn = new Button("Delete");
                btn.setCursor(Cursor.HAND);
                btn.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
                setRemoveBtnAction(btn);
                tm.setBtn(btn);
                tms.add(tm);

            }

            toTable = FXCollections.observableArrayList(tms);
            tblExpence.setItems(toTable);
        } catch (SQLException | ClassNotFoundException e) {
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
                new Alert(Alert.AlertType.ERROR,"Please select a row from expences' table to delete an expence!").show();
                return;
            }
            String id = colExpenceId.getCellData(index).toString();
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove \""+id+"\" ?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {

                    try {
                        Boolean flag = expenceBO.deleteExpence(id);
                        if (flag) {
                            clearAllFields();
                            new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                    }
                }
        });
    }

    public void clearAllFields() {
        txtId.clear();
        txtAmount.clear();
        txtDescription.clear();
        txtType.clear();
        txtSearchByExpenceId.clear();
        dpDate.setValue(null);
        initialize();
    }


    public void addExpenceAction() {
        btnAdd.setOnAction((e) -> {
            try {
                List<String> temp = expenceBO.getAllExpenceIds();
                for (String s : temp) {
                    if(txtId.getText().equals(s)){
                        new Alert(Alert.AlertType.ERROR,"Expence already saved!").show();
                        return;
                    }
                }
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            Boolean isValidate = validateExpences();
            if(isValidate) {
                String id = txtId.getText();
                String description = txtDescription.getText();
                String type = txtType.getText();
                String amount = txtAmount.getText();
                String date = String.valueOf(dpDate.getValue());

                if (id.isEmpty() || description.isEmpty() || type.isEmpty() || amount.isEmpty() || dpDate.getValue() == null) {
                    new Alert(Alert.AlertType.ERROR, "Fields Empty!").show();
                    return;
                }

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to add expence \"" + txtId.getText() + "\" ?", yes, no).showAndWait();

                if (type1.orElse(no) == yes) {

                    Double amount1 = Double.parseDouble(amount);
                    ExpenceDto dto = new ExpenceDto(id, type, description, date, amount1);

                    try {
                        Boolean flag = expenceBO.addExpence(dto);
                        if (flag) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Expence Saved!").show();
                            clearAllFields();
                        }
                    } catch (SQLException | ClassNotFoundException exception) {
                        new Alert(Alert.AlertType.ERROR, "Error!").show();
                    }
                }
            }
        });
    }

    public void updateBtnAction() {
        btnUpdate.setOnAction((e) -> {

            String id = txtId.getText();
            String description = txtDescription.getText();
            String type = txtType.getText();
            String amount = txtAmount.getText();
            String date = String.valueOf(dpDate.getValue());

            try {
                List<String> temp = new ArrayList<>();
                temp = expenceBO.getAllExpenceIds();
                Boolean flag = false;
                for (String s : temp) {
                    if(txtId.getText().equals(s)) {
                        flag = true;
                    }
                }
                if (flag.equals(false)) {
                    new Alert(Alert.AlertType.ERROR,"Please select a row from expences' table to update a expence!").show();
                    return;
                }

            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            if (id.isEmpty() || description.isEmpty() || type.isEmpty() || amount.isEmpty() || dpDate.getValue() == null) {
                new Alert(Alert.AlertType.ERROR, "Fields Empty!").show();
                return;
            }

            Boolean isValidate = validateExpences();
            Double amount1 = Double.parseDouble(amount);
            if(isValidate) {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to update expence \"" + txtId.getText() + "\" ?", yes, no).showAndWait();

                if (type1.orElse(no) == yes) {
                    ExpenceDto dto = new ExpenceDto(id, type, description, date, amount1);
                    try {
                        boolean flag = expenceBO.updateExpence(dto);
                        if (flag) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Expence Updated").show();
                            clearAllFields();
                        }
                    } catch (SQLException | ClassNotFoundException exception) {
                        new Alert(Alert.AlertType.ERROR, "Check Expence ID!").show();
                    }
                }
            }
        });
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
    void btnClearAllFieldsOnAction(ActionEvent event) {
        clearAllFields();
    }
    @FXML
    void txtSearchByExpenceIdOnMouseClicked(MouseEvent event) {
        clearAllFields();
    }

    @FXML
    void txtIdOnMouseClicked(MouseEvent event) {
        clearAllFields();
    }

    private Boolean validateExpences() {
        String id = txtId.getText();
        boolean idMatch = Pattern.matches("^[\\w\\s.,!?'\"()-]+",id);
        if (!idMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid expence id!").show();
            return false;
        }

        String description = txtDescription.getText();
        boolean descriptionMatch = Pattern.matches("^[\\w\\s.,!?'\"()-]+",description);
        if (!descriptionMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid description!").show();
            return false;
        }

        String type = txtType.getText();
        boolean typeMatch = Pattern.matches("^[\\w\\s.,!?'\"()-]+",type);
        if (!typeMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid type!").show();
            return false;
        }

        String amount = txtAmount.getText();
        boolean amountMatch = Pattern.matches("^\\d+(\\.\\d+)?$",amount);
        if (!amountMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid amount!").show();
            return false;
        }
        return true;
    }
}

