package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.tm.ItemTm;
import lk.ijse.st_clothing.model.ItemsModel;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemsFormController {
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbSize;

    @FXML
    private TableColumn<ItemTm, Button> colAction;

    @FXML
    private TableColumn<ItemTm, String> colDescription;

    @FXML
    private TableColumn<ItemTm, String> colItemCode;

    @FXML
    private TableColumn<ItemTm, Integer> colQty;

    @FXML
    private TableColumn<ItemTm, String> colSize;

    @FXML
    private TableColumn<ItemTm, String> colSupplierId;

    @FXML
    private TableColumn<ItemTm, Double> colUnitPrice;

    @FXML
    private TableView<ItemTm> tblItems;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearchItemByItemCode;

    @FXML
    private JFXTextField txtSearchItemBySupplierId;

    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtUnitPrice;

    public void initialize() throws SQLException {
        setTableItems();
        vitualize();
        String[] sizes = {"S","M","L","XL","XXL"};
        cmbSize.setItems(FXCollections.observableArrayList(sizes));
        loadAllItemCodes();
        loadAllsupplierIds();
    }

    public void setTableItems() {
        try {
            ArrayList<ItemDto> dtos = ItemsModel.getAllItems();
            ArrayList<ItemTm> tms = new ArrayList<>();
            for (ItemDto dto : dtos) {
                ItemTm tm = new ItemTm();
                tm.setItemCode(dto.getItemCode());
                tm.setSupplierId(dto.getSupplierId());
                tm.setDescription(dto.getDescription());
                tm.setSize(dto.getSize());
                tm.setUnitPrice(dto.getUnitPrice());
                tm.setQty(dto.getQty());
                Button btn = new Button("Delete");
                btn.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
                setRemoveBtnAction(btn);
                tm.setBtn(btn);
                tms.add(tm);

            }

            ObservableList<ItemTm> toTable = FXCollections.observableArrayList(tms);
            tblItems.setItems(toTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void vitualize() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            Integer index = tblItems.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            String id = colItemCode.getCellData(index).toString();
            try {
                Boolean flag = ItemsModel.deleteItem(id);
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
        String id = txtItemCode.getText();
        String qty = txtQty.getText();
        String description = txtDescription.getText();
        String unitPrice = txtUnitPrice.getText();
        String supplierId = txtSupplierId.getText();
        String size = cmbSize.getValue();

        if (id.isEmpty()||qty.isEmpty()||description.isEmpty()||unitPrice.isEmpty()||supplierId.isEmpty()||cmbSize.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"Text Fields Empty!").show();
            return;
        }

        Double unitPrice1 = Double.parseDouble(unitPrice);
        Integer qty1 = Integer.parseInt(qty);

        ItemDto dto = new ItemDto(id,supplierId,description,unitPrice1,qty1,size);

        try {
            Boolean flag = ItemsModel.addItems(dto);
            if (flag) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Saved!").show();
                clearAllFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {
        String itemCode = txtItemCode.getText();
        String supId = txtSupplierId.getText();
        String description = txtDescription.getText();
        String unitPrice = txtUnitPrice.getText();
        String size = cmbSize.getValue();
        String qty = txtQty.getText();

        if (itemCode.isEmpty()||qty.isEmpty()||description.isEmpty()||unitPrice.isEmpty()||supId.isEmpty()||cmbSize.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"Text Fields Empty!").show();
            return;
        }

        Double unitPrice1 = Double.parseDouble(unitPrice);
        Integer qty1 = Integer.parseInt(qty);

        ItemDto dto = new ItemDto(itemCode,supId,description,unitPrice1,qty1,size);
        try {
            Boolean flag = ItemsModel.updateItem(dto);
            if(flag) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Updated").show();
                setTableItems();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Check Item ID!").show();
        }
    }

    @FXML
    void mouseClickOnAction(MouseEvent event) {
        Integer index = tblItems.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        txtItemCode.setText(colItemCode.getCellData(index).toString());
        txtSupplierId.setText(colSupplierId.getCellData(index).toString());
        txtDescription.setText(colDescription.getCellData(index).toString());
        txtUnitPrice.setText(colUnitPrice.getCellData(index).toString());
        txtQty.setText(colQty.getCellData(index).toString());
        cmbSize.setValue(colSize.getCellData(index).toString());
    }

    public void clearAllFields() throws SQLException {
        txtItemCode.clear();
        txtSupplierId.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQty.clear();
        txtSearchItemByItemCode.clear();
        txtSearchItemBySupplierId.clear();
        cmbSize.setValue(null);
        initialize();
    }

    public void loadAllItemCodes() throws SQLException {
        ArrayList<String> itemCodes = ItemsModel.getItemCodes();
        TextFields.bindAutoCompletion(txtSearchItemByItemCode,itemCodes);
    }

    public void loadAllsupplierIds() throws SQLException {
        ArrayList<String> supIds = ItemsModel.getSupplierIds();
        TextFields.bindAutoCompletion(txtSearchItemBySupplierId,supIds);
        TextFields.bindAutoCompletion(txtSupplierId,supIds);

    }


    @FXML
    void searchBySupplierIdOnAction(ActionEvent event) {
        try {
            String supId = txtSearchItemBySupplierId.getText() ;
            ArrayList<ItemDto> dtos = ItemsModel.getItemBySupID(supId);
            ArrayList<ItemTm> tms = new ArrayList<>();
            for (ItemDto dto : dtos) {
                ItemTm tm = new ItemTm();
                tm.setItemCode(dto.getItemCode());
                tm.setSupplierId(dto.getSupplierId());
                tm.setDescription(dto.getDescription());
                tm.setSize(dto.getSize());
                tm.setUnitPrice(dto.getUnitPrice());
                tm.setQty(dto.getQty());
                Button btn = new Button("Delete");
                btn.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
                setRemoveBtnAction(btn);
                tm.setBtn(btn);
                tms.add(tm);
            }
            ObservableList<ItemTm> toTable = FXCollections.observableArrayList(tms);
            tblItems.setItems(toTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchByItemCodeOnAction(ActionEvent event) {
        try {
            ItemDto dto = ItemsModel.getItemById(txtSearchItemByItemCode.getText());
            if(dto!=null) {
                ItemTm tm = new ItemTm();
                tm.setItemCode(dto.getItemCode());
                tm.setSupplierId(dto.getSupplierId());
                tm.setDescription(dto.getDescription());
                tm.setQty(dto.getQty());
                tm.setUnitPrice(dto.getUnitPrice());
                tm.setSize(dto.getSize());
                Button delete = new Button("Delete");
                delete.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
                setRemoveBtnAction(delete);
                tm.setBtn(delete);
                ArrayList<ItemTm> id = new ArrayList<>();
                id.add(tm);
                ObservableList<ItemTm> itemsTms = FXCollections.observableArrayList(id);
                tblItems.refresh();
                tblItems.setItems(itemsTms);}
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchBySupIdOnClicked(MouseEvent event) throws SQLException {
        clearAllFields();
    }


    @FXML
    void txtSearchByItemCodeOnClicked(MouseEvent event) throws SQLException {
        clearAllFields();
    }

    @FXML
    void btnClearAllFieldsOnAction(ActionEvent event) throws SQLException {
        clearAllFields();
    }

}
