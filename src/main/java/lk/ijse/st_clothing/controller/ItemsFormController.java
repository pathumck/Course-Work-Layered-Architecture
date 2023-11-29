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
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.tm.ItemTm;
import lk.ijse.st_clothing.model.ItemsModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

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
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtUnitPrice;
    private ObservableList<ItemTm> toTable;
    public void initialize() throws SQLException {
        setTableItems();
        vitualize();
        searchFilter();
        String[] sizes = {"S","M","L","XL","XXL"};
        cmbSize.setItems(FXCollections.observableArrayList(sizes));

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

            toTable = FXCollections.observableArrayList(tms);
            tblItems.setItems(toTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void searchFilter() {
        FilteredList<ItemTm> filterData= new FilteredList<>(toTable, e->true);
        txtSearchItemByItemCode.setOnKeyReleased(e->{


            txtSearchItemByItemCode.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super ItemTm >) cust->{
                    if(newValue==null){
                        return true;
                    }
                    String toLowerCaseFilter = newValue.toLowerCase();
                    if(cust.getItemCode().contains(newValue)){
                        return true;
                    }else  if(cust.getSupplierId().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getDescription().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getUnitPrice().toString().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getQty().toString().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(cust.getSize().toString().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }

                    return false;
                });
            });

            final SortedList<ItemTm> customers = new SortedList<>(filterData);
            customers.comparatorProperty().bind(tblItems.comparatorProperty());
            tblItems.setItems(customers);
        });
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
        cmbSize.setValue(null);
        initialize();
    }

    @FXML
    void btnClearAllFieldsOnAction(ActionEvent event) throws SQLException {
        clearAllFields();
    }

}
