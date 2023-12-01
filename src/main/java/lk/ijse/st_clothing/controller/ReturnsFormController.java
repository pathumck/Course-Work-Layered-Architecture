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
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.PlaceOrderDto;
import lk.ijse.st_clothing.dto.PlaceReturnDto;
import lk.ijse.st_clothing.dto.tm.ReturnCartTm;
import lk.ijse.st_clothing.model.ItemsModel;
import lk.ijse.st_clothing.model.OrdersModel;
import lk.ijse.st_clothing.model.PlaceReturnModel;
import lk.ijse.st_clothing.model.ReturnsModel;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

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

    @FXML
    private AnchorPane setPane;

    public static volatile boolean scanning1 = false;

    public static Webcam webcam1;
    public static WebcamPanel webcamPanel1;

    private static long lastPlayTime = 0;
    private static final long SOUND_DELAY_MS = 1000;

    private List<ReturnCartTm> list = new ArrayList<>();

    public void initialize() throws SQLException {
        generateNextReturnId();
        lblDate.setText(String.valueOf(LocalDate.now()));
        loadAllOrderIds();
        loadAllItemCodes();
        vitualize();
        lblTotal.setText("0.0");

        if(OrdersFormController.scanning==true) {
            OrdersFormController.webcamPanel.stop();
            OrdersFormController.webcam.close();
            OrdersFormController.scanning = false;
        }

        if (!scanning1) {
            scanning1 = true;
            new Thread(this::triggerScanning).start();
        }

        txtSelectItemCode.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setLabelsOfSelectedItem();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


    }

    public void triggerScanning() {
        webcam1 = Webcam.getDefault();
        webcam1.setViewSize(new Dimension(320, 240));
        webcamPanel1 = new WebcamPanel(webcam1);
        webcamPanel1.setMirrored(false);

        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater(() -> swingNode.setContent(webcamPanel1));

        Platform.runLater(() -> setPane.getChildren().add(swingNode));

        while (scanning1) {
            try {
                BufferedImage image = webcam1.getImage();
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);
                if (result != null && result.getText() != null) {
                    String messageText = result.getText();
                    Platform.runLater(() -> txtSelectItemCode.setText(messageText));
                    playSound();
                    //    getItemByItemCode();
                }
                //  Thread.sleep(100); // Adjust sleep time if needed
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        webcamPanel1.stop();
        webcam1.close();
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


    private void generateNextReturnId() {
        try {
            String returnId = ReturnsModel.generateNextReturnId();
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
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String selectId = txtSelectOrderId.getText();
        String selectCode = txtSelectItemCode.getText();
        String qty1 = txtQty.getText();
        if(selectId.isEmpty()||selectCode.isEmpty()||qty1.isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Check Empty Fields").show();
            return;
        }

        ArrayList<String> temp = OrdersModel.getOrderIds();
        boolean flag = false;
        for (String s : temp) {
            if(s.equals(txtSelectOrderId.getText())) {
                flag = true;
            }
        }
        if(flag==false) {
            new Alert(Alert.AlertType.ERROR,"Please check order id!!").show();
            return;
        }

        ArrayList<String> temp1 = ItemsModel.getItemCodes();
        boolean flag1 = false;
        for (String s : temp1) {
            if(s.equals(txtSelectItemCode.getText())) {
                flag1 = true;
            }
        }
        if(flag1==false) {
            new Alert(Alert.AlertType.ERROR,"Please check item code!!").show();
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
        FXCollections.reverse(obList);
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
    void btnPlaceReturnOnAction(ActionEvent event) throws SQLException {
        String id = lblReturnId.getText();
        String checkId = ReturnsModel.isReturnSaved(id);
        if(checkId!=null) {
            new Alert(Alert.AlertType.ERROR,"This cart already placed!").show();
            return;
        }

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
            ReturnCartTm tm = new ReturnCartTm();
            tm.setItemCode(cartTm.getItemCode());
            tm.setQty(cartTm.getQty());
            tm.setUnitPrice(cartTm.getUnitPrice());
            tm.setTotal(cartTm.getTotal());
            list.add(tm);
            cartTmList.add(cartTm);
        }
       // System.out.println(returnId);
        System.out.println("Place order form controller: " + cartTmList);
        var placeReturnDto = new PlaceReturnDto(returnId,customerId,date,currentTimeString,cartTmList);
        System.out.println(returnId);
        try {
            Boolean isSuccess = PlaceReturnModel.placeReturn(placeReturnDto);
            if (isSuccess) {
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

    @FXML
    void btnPrintOnAction(ActionEvent event) throws SQLException {
        String id = lblReturnId.getText();
        String checkId = ReturnsModel.isReturnSaved(id);
        if(checkId!=null) {
            returnJasper();
            return;
        }
        new Alert(Alert.AlertType.ERROR,"Please place return first!").show();
    }

    public void returnJasper() {
        try {
            /* User home directory location */
            String userHomeDirectory = System.getProperty("user.home");
            String name = lblReturnId.getText();
            /* Output file location */
            String outputFile = userHomeDirectory + File.separatorChar +name+".pdf";

            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            String date = String.valueOf(LocalDate.now());
            String time= formattedTime;
            String id = lblReturnId.getText();
            String deduction = lblTotal.getText();

            /* Convert List to JRBeanCollectionDataSource */
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(list);

            /* Map to hold Jasper report Parameters */
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ItemData", itemsJRBean);
            parameters.put("da",date);
            parameters.put("ti",time);
            parameters.put("rid",id);
            parameters.put("deduc",deduction);

            /* Using compiled version(.jasper) of Jasper report to generate PDF */
            JasperPrint jasperPrint = JasperFillManager.fillReport("/home/pathum/IdeaProjects/SemesterOneFinalProject/src/main/resources/report/retuns.jasper", parameters, new JREmptyDataSource());

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
            JasperViewer.viewReport(jasperPrint, false);


            System.out.println("File Generated");
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }


}
