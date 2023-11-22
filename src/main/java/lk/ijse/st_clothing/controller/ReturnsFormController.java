package lk.ijse.st_clothing.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.PlaceOrderDto;
import lk.ijse.st_clothing.dto.PlaceReturnDto;
import lk.ijse.st_clothing.dto.tm.ReturnCartTm;
import lk.ijse.st_clothing.model.ItemsModel;
import lk.ijse.st_clothing.model.OrdersModel;
import lk.ijse.st_clothing.model.PlaceReturnModel;
import lk.ijse.st_clothing.model.ReturnsModel;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReturnsFormController {
    @FXML
    private JFXButton btnAdd;
    @FXML
    private Label lblTotal;

    @FXML
    private JFXButton btnPlaceReturn;

    @FXML
    private TableColumn<ReturnCartTm, Button> colAction;

    @FXML
    private TableColumn<ReturnCartTm, String> colItemCode;

    @FXML
    private TableColumn<ReturnCartTm, Integer> colQty;

    @FXML
    private TableColumn<ReturnCartTm, Double> colTotal;

    @FXML
    private TableColumn<ReturnCartTm, Double> colUnitPrice;

    @FXML
    private TableView<ReturnCartTm> tblReturnCart;

    @FXML
    private Label lblCusId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblReturnId;

    @FXML
    private JFXTextField txtSelectItemCode;

    @FXML
    private JFXTextField txtSelectOrderId;
    private ObservableList<ReturnCartTm> obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        generateNextReturnId();
        lblDate.setText(String.valueOf(LocalDate.now()));
        loadAllOrderIds();
        loadAllItemCodes();
        vitualize();

    }

    private void generateNextReturnId() {
        try {
            String returnId = ReturnsModel.generateNextOrderId();
            lblReturnId.setText(returnId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void loadAllOrderIds() throws SQLException {
        ArrayList<String> orderIds = OrdersModel.getOrderIds();
        TextFields.bindAutoCompletion(txtSelectOrderId,orderIds);
    }

    public void loadAllItemCodes() throws SQLException {
        ArrayList<String> itemCodes = ItemsModel.getItemCodes();
        TextFields.bindAutoCompletion(txtSelectItemCode,itemCodes);
    }

    public void setLabelsOfSelectedItem() throws SQLException {
       ItemDto dto = ItemsModel.getItemById(txtSelectItemCode.getText());
       lblDescription.setText(dto.getDescription());
       lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
    }

    public void setLabelsOfSelectedOrderId() throws SQLException {
        PlaceOrderDto dto = OrdersModel.getOrderByorderId(txtSelectOrderId.getText());
        lblOrderDate.setText(dto.getDate());
        lblCusId.setText(dto.getCustomerId());
        lblTime.setText(dto.getTime());
    }
    @FXML
    void txtSelectItemCodeOnAction(ActionEvent event) throws SQLException {
        setLabelsOfSelectedItem();
    }
    @FXML
    void txtSelectOrderIdOnAction(ActionEvent event) throws SQLException {
        setLabelsOfSelectedOrderId();
    }
    @FXML
    void txtSelectItemCodeOnKeyReleased(KeyEvent event) throws SQLException {
        txtQty.clear();
        setLabelsOfSelectedItem();
    }
    @FXML
    void txtSelectOrderIdOnKeyReleased(KeyEvent event) throws SQLException {
        setLabelsOfSelectedOrderId();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String selectId = txtSelectOrderId.getText();
        String selectCode = txtSelectItemCode.getText();
        String qty1 = txtQty.getText();
        if(selectId.isEmpty()||selectCode.isEmpty()||qty1.isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Check Empty Fields").show();
            return;
        }

        String code = txtSelectItemCode.getText();
        String description = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double tot = unitPrice * qty;
        Button btn = new Button("Remove");
        btn.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);


        if (!obList.isEmpty()) {
            for (int i = 0; i < tblReturnCart.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(code)) {
                    int col_qty = (int) colQty.getCellData(i);
                    qty += col_qty;
                    tot = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(tot);

                    calculateTotal();
                    tblReturnCart.refresh();
                    return;
                }
            }
        }
        var ReturnCartTm = new ReturnCartTm(code,unitPrice , qty, tot, btn);

        obList.add(ReturnCartTm);

        tblReturnCart.setItems(obList);
        calculateTotal();
        txtQty.clear();
    }
    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblReturnCart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblReturnCart.refresh();
                calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblReturnCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblTotal.setText(String.valueOf(total));
    }

    private void vitualize() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @FXML
    void btnPlaceReturnOnAction(ActionEvent event) {
        String returnId = lblReturnId.getText();
        String date = lblDate.getText();
        String customerId = lblCusId.getText();
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String currentTimeString = currentTime.format(formatter);
        String selectId = txtSelectOrderId.getText();
        String selectCode = txtSelectItemCode.getText();
        String qty = txtQty.getText();
        if(tblReturnCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Cart is empty!").show();
            return;
        }

        List<ReturnCartTm> cartTmList = new ArrayList<>();
        for (int i = 0; i < tblReturnCart.getItems().size(); i++) {
            ReturnCartTm cartTm = obList.get(i);

            cartTmList.add(cartTm);
        }
       // System.out.println(returnId);
        System.out.println("Place order form controller: " + cartTmList);
        var placeReturnDto = new PlaceReturnDto(returnId,customerId,date,currentTimeString,cartTmList);
        System.out.println(returnId);
        try {
            Boolean isSuccess = PlaceReturnModel.placeReturn(placeReturnDto);
            if (isSuccess) {
                obList.clear();
                clearAllFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Return Success!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearAllFields() throws SQLException {
        lblOrderDate.setText("");
        lblTime.setText("");
        lblCusId.setText("");
        txtSelectItemCode.clear();
        txtSelectOrderId.clear();
        lblDescription.setText("");
        lblUnitPrice.setText("");
        lblTotal.setText("");
        initialize();
    }


}
