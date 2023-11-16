package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import lk.ijse.st_clothing.dto.SupplierDto;
import lk.ijse.st_clothing.dto.tm.SupplierTm;
import lk.ijse.st_clothing.model.SupplierModel;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    private JFXTextField txtTp;

    public void initialize() throws SQLException {
        setTableSuppliers();
        vitualize();
        loadAllItemCodes();
        setDate();
    }

    public void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
        lblDate.setStyle("-fx-text-fill: black; -fx-font-family: 'Diyuthi'; -fx-font-size: 12; -fx-font-weight: regular;");
    }

    public void setTableSuppliers() {
        try {
            ArrayList<SupplierDto> dtos = SupplierModel.getALlSuppliers();
            ArrayList<SupplierTm> tms = new ArrayList<>();
            for (SupplierDto dto : dtos) {
                SupplierTm tm = new SupplierTm();
                tm.setId(dto.getId());
                tm.setName(dto.getName());
                tm.setTp(dto.getTp());
                tm.setAddress(dto.getAddress());
                tm.setDate(dto.getDate());
                Button btn = new Button("Delete");
                setRemoveBtnAction(btn);
                tm.setBtn(btn);
                tms.add(tm);

            }

            ObservableList<SupplierTm> toTable = FXCollections.observableArrayList(tms);
            tblSupplier.setItems(toTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            Integer index = tblSupplier.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            String id = colId.getCellData(index).toString();
            try {
                Boolean flag = SupplierModel.deleteSupplier(id);
                if(flag){
                    clearAllFields();
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
                }
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
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

    @FXML
    void addSupplierBtnOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tp = txtTp.getText();
        String date = String.valueOf(LocalDate.now());

        if (id.isEmpty()||name.isEmpty()||address.isEmpty()||tp.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Text Fields Empty!").show();
            return;
        }

        SupplierDto dto = new SupplierDto(id,name,address,tp,date);


        try {
            Boolean flag = SupplierModel.addSupplier(dto);
            if (flag) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved!").show();
                clearAllFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void updateSupplierBtnOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tp = txtTp.getText();

        if (id.isEmpty()||name.isEmpty()||address.isEmpty()||tp.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Text Fields Empty!").show();
            return;
        }

        SupplierDto dto = new SupplierDto(id,name,address,tp);
        try {
            Boolean flag = SupplierModel.updateSupplier(dto);
            if(flag) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated").show();
                setTableSuppliers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Check Supplier ID!").show();
        }
    }

    public void mouseClickOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        Integer index = tblSupplier.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        txtName.setText(colName.getCellData(index).toString());
        txtId.setText(colId.getCellData(index).toString());
        txtAddress.setText(colAddress.getCellData(index).toString());
        txtTp.setText(colTp.getCellData(index).toString());
        lblDate.setText(colDate.getCellData(index).toString());
    }

    public void clearTextFields() {
        txtId.clear();
        txtName.clear();
        txtTp.clear();
        txtAddress.clear();
    }

    public void loadAllItemCodes() throws SQLException {
        ArrayList<String> supIds = SupplierModel.getSupplierIds();
        TextFields.bindAutoCompletion(txtSearchId,supIds);
    }

    @FXML
    void searchByIdOnAction(ActionEvent event) {
        try {
            SupplierDto dto = SupplierModel.getSupplierById(txtSearchId.getText());
            if(dto!=null) {
                SupplierTm tm = new SupplierTm();
                tm.setId(dto.getId());
                tm.setName(dto.getName());
                tm.setAddress(dto.getAddress());
                tm.setTp(dto.getTp());
                tm.setDate(dto.getDate());
                Button delete = new Button("Delete");
                setRemoveBtnAction(delete);
                tm.setBtn(delete);
                ArrayList<SupplierTm> id = new ArrayList<>();
                id.add(tm);
                ObservableList<SupplierTm> supplierTms = FXCollections.observableArrayList(id);
                tblSupplier.refresh();
                tblSupplier.setItems(supplierTms);}
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void clearAllFields() throws SQLException {
        txtSearchId.clear();
        txtId.clear();
        txtTp.clear();
        txtName.clear();
        txtAddress.clear();
        initialize();
    }
    @FXML
    void btnClearAllFieldsOnAction(ActionEvent event) throws SQLException {
        clearAllFields();
    }

}
