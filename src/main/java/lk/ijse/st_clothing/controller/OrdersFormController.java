package lk.ijse.st_clothing.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.st_clothing.dto.CustomerDto;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.PlaceOrderDto;
import lk.ijse.st_clothing.dto.tm.CartTm;
import lk.ijse.st_clothing.dto.tm.DeductionTm;
import lk.ijse.st_clothing.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class OrdersFormController {
    @FXML
    private JFXButton btnResetCarts;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private JFXButton btnAddNewCus;

    @FXML
    private JFXButton btnAddReturn;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private TableColumn<CartTm, Button> colAction;

    @FXML
    private TableColumn<?, ?> colAction2;

    @FXML
    private TableColumn<CartTm, String> colCode;

    @FXML
    private TableColumn<?, ?> colDeduction;

    @FXML
    private TableColumn<CartTm, String> colDescription;

    @FXML
    private TableColumn<CartTm, Integer> colQty;

    @FXML
    private TableColumn<?, ?> colReturnId;

    @FXML
    private TableColumn<CartTm, Double> colTotal;

    @FXML
    private TableColumn<CartTm, Double> colUnitPrice;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDeduction;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderTotal;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<CartTm> tblCart;

    @FXML
    private TableView<DeductionTm> tblReturn;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private TextField txtPayment;

    @FXML
    private JFXTextField txtReturnId;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    private ObservableList<DeductionTm> obList2 = FXCollections.observableArrayList();

    private List<CartTm> list2 = new ArrayList<>();
    private double total = 0;
    private double deduction = 0;
    private double netTotal = 0;

    @FXML
    private AnchorPane setPane;

    private CustomerModel customerModel = new CustomerModel();
    private ItemsModel itemModel = new ItemsModel();
    private OrdersModel orderModel = new OrdersModel();
    private PlaceOrderModel placeOrderModel = new PlaceOrderModel();

    public static volatile boolean scanning = false;

    public static Webcam webcam;
    public static WebcamPanel webcamPanel;

    private static long lastPlayTime = 0;
    private static final long SOUND_DELAY_MS = 1000;

    private JasperPrint jasperPrint;

    public void initialize() throws SQLException {
        generateNextOrderId();
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
        loadAllCustomerIds();
        loadAllItemCodes();
        vitualize();
        vilualizeDeductions();
        loadAllReturnIds();
        btnResetCartsAction();

        total = 0.0;
        deduction = 0.0;
        netTotal = 0.0;
        lblBalance.setText("0.0");
        lblOrderTotal.setText(String.valueOf(total));
        lblDeduction.setText(String.valueOf(deduction));
        lblNetTotal.setText(String.valueOf(netTotal));

        if(ReturnsFormController.scanning1==true) {
            ReturnsFormController.webcamPanel1.stop();
            ReturnsFormController.webcam1.close();
            ReturnsFormController.scanning1 = false;
        }

        if (!scanning) {
            scanning = true;
            new Thread(this::triggerScanning).start();
        }

        txtItemCode.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                lblDescription.setText("");
                lblQtyOnHand.setText("");
                lblUnitPrice.setText("");
                txtQty.clear();
                getItemByItemCode();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void triggerScanning() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(320, 240));
        webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setMirrored(false);

        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater(() -> swingNode.setContent(webcamPanel));

        Platform.runLater(() -> setPane.getChildren().add(swingNode));

        while (scanning) {
            try {
                BufferedImage image = webcam.getImage();
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);
                if (result != null && result.getText() != null) {
                    String messageText = result.getText();
                    Platform.runLater(() -> txtItemCode.setText(messageText));
                    playSound();
                    //    getItemByItemCode();
                }
                //  Thread.sleep(100); // Adjust sleep time if needed
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        webcamPanel.stop();
        webcam.close();
    }

    private void playSound() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPlayTime >= SOUND_DELAY_MS) {
            try {
                File soundFile = new File("/home/pathum/IdeaProjects/SemesterOneFinalProject/src/main/resources/sound/beep-06.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                lastPlayTime = currentTime;
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void generateNextOrderId() {
        try {
            String orderId = OrdersModel.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadAllCustomerIds() throws SQLException {
        ArrayList<String> cusIds = CustomerModel.getCustomerIds();
        TextFields.bindAutoCompletion(txtCustomerId, cusIds);
    }

    public void loadAllItemCodes() throws SQLException {
        ArrayList<String> itemCodes = ItemsModel.getItemCodes();
        TextFields.bindAutoCompletion(txtItemCode, itemCodes);
    }

    public void loadAllReturnIds() throws SQLException {
        ArrayList<String> returnIds = ReturnsModel.getAllReturnIds();
        TextFields.bindAutoCompletion(txtReturnId, returnIds);
    }

    @FXML
    void btnAddNewCusOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/customers_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add New Customer");
        stage.show();
    }

    public void getCustomerNameById() throws SQLException {
        lblCustomerName.setText("");
        String cusId = txtCustomerId.getText();
        CustomerDto customerDto = CustomerModel.getCustomerById(cusId);
        lblCustomerName.setText(customerDto.getName());
    }

    @FXML
    void txtSelectCustomerKeyReleased(KeyEvent event) throws SQLException {
        getCustomerNameById();
    }

    public void getItemByItemCode() throws SQLException {
        lblDescription.setText("");
        lblUnitPrice.setText("");
        lblQtyOnHand.setText("");
        String itemCode = txtItemCode.getText();
        ItemDto dto = ItemsModel.getItemById(itemCode);
        lblDescription.setText(dto.getDescription());
        lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
        lblQtyOnHand.setText(String.valueOf(dto.getQty()));
    }

    @FXML
    void txtItemCodeOnKeyReleased(KeyEvent event) throws SQLException {
        getItemByItemCode();
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) throws SQLException {

        String itemCode1 = txtItemCode.getText();
        String qty1 = txtQty.getText();
        if (itemCode1.isEmpty() || qty1.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Check Empty Fields").show();
            return;
        }

        ArrayList<String> temp = ItemsModel.getItemCodes();
        boolean flag = false;
        for (String s : temp) {
            if(s.equals(txtItemCode.getText())) {
                flag = true;
            }
        }
        if(flag==false) {
            new Alert(Alert.AlertType.ERROR,"Please check itemCode!").show();
            return;
        }
        Boolean isValidate = validateQty();
        if (isValidate) {
            String itemCode = txtItemCode.getText();
            String description = lblDescription.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(lblUnitPrice.getText());
            double tot = unitPrice * qty;

            int qtyOnHand = Integer.parseInt(lblQtyOnHand.getText());

            if (qtyOnHand < qty) {
                new Alert(Alert.AlertType.ERROR, "Over the Item's quantity limit!").show();
                return;
            }

            Button btn = new Button("Remove");
            btn.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
            setRemoveBtnAction(btn);
            btn.setCursor(Cursor.HAND);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblCart.getItems().size(); i++) {
                    if (colCode.getCellData(i).equals(itemCode)) {
                        int col_qty = (int) colQty.getCellData(i);
                        qty += col_qty;
                        if (qty > qtyOnHand) {
                            new Alert(Alert.AlertType.ERROR, "Over the item's quantity limit!").show();
                            return;
                        }
                        tot = unitPrice * qty;

                        obList.get(i).setQty(qty);
                        obList.get(i).setTotal(tot);
                        calculateTotal();
                        netTotal();
                        checkBalance();
                        txtQty.setText("");
                        tblCart.refresh();
                        return;
                    }
                }
            }
            var cartTm = new CartTm(itemCode, description, qty, unitPrice, tot, btn);

            obList.add(cartTm);

            tblCart.setItems(obList);
            FXCollections.reverse(obList);
            calculateTotal();
            netTotal();
            checkBalance();
            txtQty.clear();
        }
    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Integer index = tblCart.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                new Alert(Alert.AlertType.ERROR,"Please select a cart's row to remove!").show();
                return;
            }
            String description = colDescription.getCellData(index).toString();
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove \""+description+"\" ?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblCart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                calculateTotal();
                netTotal();
                checkBalance();
                tblCart.refresh();

            }
        });
    }

    private void calculateTotal() {
        total = 0;
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblOrderTotal.setText(String.valueOf(total));
    }

    private void calculateDeduction() {
        deduction = 0;
        for (int i = 0; i < tblReturn.getItems().size(); i++) {
            deduction += (double) colDeduction.getCellData(i);
        }
        lblDeduction.setText(String.valueOf(deduction));
    }

    public void netTotal() {
        netTotal = total - deduction;
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    public void checkBalance() {
        if (txtPayment.getText().isEmpty()) {
            lblBalance.setText("");
            return;
        }
        Double payment = Double.valueOf(txtPayment.getText());
        Double netTotal = Double.valueOf(lblNetTotal.getText());
        lblBalance.setText(String.valueOf(payment - netTotal));
    }

    @FXML
    void txtPaymentOnKeyReleased(KeyEvent event) {
        lblBalance.setText("");
        checkBalance();
    }

    public void vitualize() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @FXML
    void btnAddReturnOnAction(ActionEvent event) throws SQLException {
        ArrayList<String> temp = ReturnsModel.getAllReturnIds();
        boolean flag = false;
        for (String s : temp) {
           if(s.equals(txtReturnId.getText())) {
               flag = true;
           }
        }
        if(flag==false) {
            new Alert(Alert.AlertType.ERROR,"Please check return id!").show();
            return;
        }

        String id = txtReturnId.getText();
        if (id.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select ReturnId!").show();
            return;
        }
        Double deduction = ReturnDetailsModel.getDeductionById(id);

        DeductionTm tm = new DeductionTm();
        tm.setId(id);
        tm.setDeduction(deduction);
        Button btn = new Button("Remove");
        btn.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff;");
        setDeductionTblRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);
        tm.setBtn(btn);
        obList2.add(tm);
        tblReturn.setItems(obList2);
        FXCollections.reverse(obList2);
        calculateDeduction();
        netTotal();
        checkBalance();
        txtReturnId.setText("");
    }

    public void vilualizeDeductions() {
        colReturnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDeduction.setCellValueFactory(new PropertyValueFactory<>("deduction"));
        colAction2.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setDeductionTblRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Integer index = tblReturn.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                new Alert(Alert.AlertType.ERROR,"Please select a return cart's row to remove!").show();
                return;
            }
            String id = colReturnId.getCellData(index).toString();
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove \""+id+"\" ?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblReturn.getSelectionModel().getSelectedIndex();
                obList2.remove(focusedIndex);
                calculateDeduction();
                netTotal();
                checkBalance();
                txtReturnId.setText("");
                tblReturn.refresh();
            }
        });
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        String id = lblOrderId.getText();
        String checkId = OrdersModel.isOrderSaved(id);
        if(checkId!=null) {
            new Alert(Alert.AlertType.ERROR,"This cart already placed!").show();
            return;
        }

        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Cart is Empty!").show();
            return;
        }

        if (txtPayment.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Input Customer's Payment!").show();
            return;
        }

        Boolean isValidate = validatePayment();
        if (isValidate) {

            if (netTotal < 0) {
                new Alert(Alert.AlertType.ERROR, "Ask customer to buy some items!").show();
                return;
            }

            Double blnce = Double.valueOf(lblBalance.getText());
            if (blnce < 0) {
                new Alert(Alert.AlertType.ERROR, "Payment is not Enough!").show();
                return;
            }


            String orderId = lblOrderId.getText();
            String date = String.valueOf(LocalDate.parse(lblOrderDate.getText()));
            String customerId = txtCustomerId.getText();
            String time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));


            List<CartTm> cartTmList = new ArrayList<>();
            System.out.println(tblCart.getItems().size());
            for (int i = 0; i < tblCart.getItems().size(); i++) {
                CartTm cartTm = obList.get(i);
                CartTm tm2 = new CartTm();
                tm2.setItemCode(cartTm.getItemCode());
                tm2.setDescription(cartTm.getDescription());
                tm2.setQty(cartTm.getQty());
                tm2.setUnitPrice(cartTm.getUnitPrice());
                tm2.setTotal(cartTm.getTotal());
                list2.add(tm2);
                cartTmList.add(cartTm);
            }

            System.out.println("Place order form controller: " + cartTmList);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to place the order?");
            alert.setContentText("Click OK to confirm.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                var placeOrderDto = new PlaceOrderDto(orderId, date, time, customerId, cartTmList);
                try {
                    boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
                    if (isSuccess) {
                        orderJasper();
                        new Alert(Alert.AlertType.CONFIRMATION, "Order Success!").show();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    void btnPrintOnAction(ActionEvent event) throws JRException, FileNotFoundException, SQLException {

        String id = lblOrderId.getText();
        String checkId = OrdersModel.isOrderSaved(id);
        if(checkId!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to print the order?");
            alert.setContentText("Click OK to confirm.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                print();
                return;
            }else {
                return;
            }
        }
        new Alert(Alert.AlertType.ERROR,"Please place order first!").show();
    }

    public void orderJasper() {
        try {


            /* User home directory location */
            String userHomeDirectory = System.getProperty("user.home");
            String name = lblOrderId.getText();
            /* Output file location */
            String outputFile = "/home/pathum/Desktop/orderBills/" +name+".pdf";

            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            String date = String.valueOf(LocalDate.now());
            String time= formattedTime;
            String id = lblOrderId.getText();
            String orderTotal = lblOrderTotal.getText();
            String deduction = lblDeduction.getText();
            String netTotal = lblNetTotal.getText();
            Double payment = Double.valueOf(txtPayment.getText());
            String balance = lblBalance.getText();

            /* Convert List to JRBeanCollectionDataSource */
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(list2);

            /* Map to hold Jasper report Parameters */
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ItemDataSource", itemsJRBean);
            parameters.put("dat",date);
            parameters.put("tim",time);
            parameters.put("i",id);
            parameters.put("oT",orderTotal);
            parameters.put("ded",deduction);
            parameters.put("nT",netTotal);
            parameters.put("pay",payment);
            parameters.put("bal",balance);

            /* Using compiled version(.jasper) of Jasper report to generate PDF */
            jasperPrint = JasperFillManager.fillReport("/home/pathum/IdeaProjects/SemesterOneFinalProject/src/main/resources/report/paycart.jasper", parameters, new JREmptyDataSource());

            /* outputStream to create PDF */
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(new File(outputFile));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            /* Write content to PDF file */
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

            // Open the generated PDF in JasperViewer


            System.out.println("File Generated");
        } catch (JRException ex) {
            ex.printStackTrace();
        }

    }

    public void print() {
        JasperViewer.viewReport(jasperPrint, false);

    }


    public void btnResetCartsAction() {
        btnResetCarts.setOnAction((e) -> {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to reset carts?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            obList.clear();
            obList2.clear();
            list2.clear();
            txtCustomerId.setText("");
            lblCustomerName.setText("");
            txtItemCode.setText("");
            lblDescription.setText("");
            lblUnitPrice.setText("");
            lblQtyOnHand.setText("");
            txtQty.setText("");
            tblCart.refresh();
            tblReturn.refresh();
            txtReturnId.setText("");
            lblOrderTotal.setText("0.0");
            lblDeduction.setText("0.0");
            lblNetTotal.setText("0.0");
            txtPayment.setText("");
            lblBalance.setText("0.0");
            try {
                initialize();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
        });
    }
    private Boolean validateQty() {
        String qty = txtQty.getText();
        boolean qtyMatch = Pattern.matches("^[1-9]\\d*$",qty);
        if (!qtyMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid qty!").show();
            return false;
        }
        return true;
    }

    private Boolean validatePayment() {
        String amount = txtPayment.getText();
        boolean amountMatch = Pattern.matches("^\\d+(\\.\\d+)?$",amount);
        if (!amountMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid payment!").show();
            return false;
        }
        return true;
    }
}