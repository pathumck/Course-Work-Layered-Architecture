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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.st_clothing.bo.BOFactory;
import lk.ijse.st_clothing.bo.custom.SupplierBO;
import lk.ijse.st_clothing.bo.custom.impl.SupplierBOImpl;
import lk.ijse.st_clothing.dto.SupplierDto;
import lk.ijse.st_clothing.dto.tm.SupplierTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class SuppliersFormController {
    @FXML
    private JFXButton btnAddSupplier;
    @FXML
    private ImageView btnRefresh;
    @FXML
    private JFXButton btnUpdateSupplier;
    @FXML
    private TableColumn<SupplierTm, Button> colAction;
    @FXML
    private TableColumn<SupplierTm, String> colAddress;
    @FXML
    private TableColumn<SupplierTm, String> colDate;
    @FXML
    private TableColumn<SupplierTm, String> colId;
    @FXML
    private TableColumn<SupplierTm, String> colName;
    @FXML
    private TableColumn<SupplierTm, String> colTp;
    @FXML
    private Label lblDate;
    @FXML
    private TableView<SupplierTm> tblSupplier;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtSearchId;
    @FXML
    private JFXTextField txtTp;
    private ObservableList<SupplierTm> toTable;
    @FXML
    private Label lblSupId;
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    public void initialize() {
        setTableSuppliers();
        vitualize();
        searchFilter();
        setDate();
        searchFilter();
        generateNextSupplierId();
        addBtnAction();
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

    private void generateNextSupplierId() {
        try {
            String supplierId = splitSupplierId(supplierBO.generateNextSupplierId());
            lblSupId.setText(supplierId);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private String splitSupplierId(String supplierId) {
        if (supplierId == null || supplierId.isEmpty() || !supplierId.matches("^s\\d+$")) {
            return "s001";
        } else {
            String numericPart = supplierId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "s00" + nextNumericPart;
        }
    }

    public void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    public void setTableSuppliers() {
        try {
            ArrayList<SupplierDto> dtos = supplierBO.getAllSuppliers();
            ArrayList<SupplierTm> tms = new ArrayList<>();
            for (SupplierDto dto : dtos) {
                SupplierTm tm = new SupplierTm();
                tm.setId(dto.getId());
                tm.setName(dto.getName());
                tm.setTp(dto.getTp());
                tm.setAddress(dto.getAddress());
                tm.setDate(dto.getDate());
                Button btn = new Button("Delete");
                btn.setCursor(Cursor.HAND);
                btn.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
                setRemoveBtnAction(btn);
                tm.setBtn(btn);
                tms.add(tm);
            }
            toTable = FXCollections.observableArrayList(tms);
            tblSupplier.setItems(toTable);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void searchFilter() {
        FilteredList<SupplierTm> filterData= new FilteredList<>(toTable, e->true);
        txtSearchId.setOnKeyReleased(e->{
            txtSearchId.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super SupplierTm >) cust->{
                    if(newValue==null){
                        return true;
                    }
                    String toLowerCaseFilter = newValue.toLowerCase();
                    if(cust.getId().contains(newValue)){
                        return true;
                    }else  if(cust.getAddress().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getName().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getDate().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getTp().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }
                    return false;
                });
            });
            final SortedList<SupplierTm> customers = new SortedList<>(filterData);
            customers.comparatorProperty().bind(tblSupplier.comparatorProperty());
            tblSupplier.setItems(customers);
        });

    }

    public void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            Integer index = tblSupplier.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                new Alert(Alert.AlertType.ERROR,"Please select a supplier table's row to delete a supplier!").show();
                return;
            }
            String id = colId.getCellData(index).toString();

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete supplier \""+id+"\" ?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {

                try {
                    Boolean flag = supplierBO.deleteSupplier(id);
                    if (flag) {
                        clearAllFields();
                        new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                }
            }
        });
    }

    public void vitualize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTp.setCellValueFactory(new PropertyValueFactory<>("tp"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void addBtnAction() {
        btnAddSupplier.setOnAction((e) -> {
            try {
                List<String> temp = supplierBO.getAllSupplierIds();
                for (String s : temp) {
                    if(lblSupId.getText().equals(s)){
                        new Alert(Alert.AlertType.ERROR,"Supplier already saved!").show();
                        return;
                    }
                }
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            Boolean isValidate = validateSupplier();
            if(isValidate) {
                String id = lblSupId.getText();
                String name = txtName.getText();
                String address = txtAddress.getText();
                String tp = txtTp.getText();
                String date = String.valueOf(LocalDate.now());

                if (id.isEmpty() || name.isEmpty() || address.isEmpty() || tp.isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Text Fields Empty!").show();
                    return;
                }

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to add new supplier \"" + lblSupId.getText() + "\" ?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {

                    SupplierDto dto = new SupplierDto(id, name, address, tp, date);


                    try {
                        Boolean flag = supplierBO.addSupplier(dto);
                        if (flag) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved!").show();
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
        btnUpdateSupplier.setOnAction((e) -> {
        String id = lblSupId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tp = txtTp.getText();

        try {
            List<String> temp = supplierBO.getAllSupplierIds();
            Boolean flag = false;
            for (String s : temp) {
                if(lblSupId.getText().equals(s)) {
                    flag = true;
                }
            }
            if (flag.equals(false)) {
                new Alert(Alert.AlertType.ERROR,"Please select a row from suppliers' table to update a supplier!").show();
                return;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        if (id.isEmpty()||name.isEmpty()||address.isEmpty()||tp.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Text Fields Empty!").show();
            return;
        }

            Boolean isValidate = validateSupplier();
            if (isValidate) {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to update supplier \"" + lblSupId.getText() + "\" ?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {

                    SupplierDto dto = new SupplierDto(id, name, address, tp);
                    try {
                        Boolean flag = supplierBO.updateSupplier(dto);
                        if (flag) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated").show();
                            clearAllFields();
                        }
                    } catch (SQLException | ClassNotFoundException exception) {
                        new Alert(Alert.AlertType.ERROR, "Error!").show();
                    }
                }
            }
        });
    }

    public void mouseClickOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        Integer index = tblSupplier.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        txtName.setText(colName.getCellData(index).toString());
        lblSupId.setText(colId.getCellData(index).toString());
        txtAddress.setText(colAddress.getCellData(index).toString());
        txtTp.setText(colTp.getCellData(index).toString());
        lblDate.setText(colDate.getCellData(index).toString());
    }

    @FXML
    void searchByIdOnAction(ActionEvent event) {
        try {
            SupplierDto dto = supplierBO.searchSupplier(txtSearchId.getText());
            if(dto!=null) {
                SupplierTm tm = new SupplierTm();
                tm.setId(dto.getId());
                tm.setName(dto.getName());
                tm.setAddress(dto.getAddress());
                tm.setTp(dto.getTp());
                tm.setDate(dto.getDate());
                Button delete = new Button("Delete");
                delete.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
                setRemoveBtnAction(delete);
                tm.setBtn(delete);
                ArrayList<SupplierTm> id = new ArrayList<>();
                id.add(tm);
                ObservableList<SupplierTm> supplierTms = FXCollections.observableArrayList(id);
                tblSupplier.refresh();
                tblSupplier.setItems(supplierTms);}
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void clearAllFields() {
        txtSearchId.clear();
        txtTp.clear();
        txtName.clear();
        txtAddress.clear();
        initialize();
    }
    @FXML
    void btnClearAllFieldsOnAction(ActionEvent event) {
        clearAllFields();
    }
    @FXML
    void searchByIdOnMouseClicked(MouseEvent event) {
        clearAllFields();
    }

    private Boolean validateSupplier() {
        String name= txtName.getText();
        boolean nameMatch = Pattern.matches("[A-za-z\\s]{4,}",name);
        if (!nameMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid name!").show();
            return false;
        }
        String address= txtAddress.getText();
        boolean addressMatch = Pattern.matches("^[\\w\\s.,#-]+$",address);
        if (!addressMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid address!").show();
            return false;
        }
        String tp = txtTp.getText();
        boolean telMatch = Pattern.matches("[0-9]{10}",tp);
        if (!telMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid telphone!").show();
            return false;
        }
        return true;
    }
}
