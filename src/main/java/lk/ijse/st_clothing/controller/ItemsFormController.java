package lk.ijse.st_clothing.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.st_clothing.dto.ItemDto;
import lk.ijse.st_clothing.dto.tm.ItemTm;
import lk.ijse.st_clothing.model.ItemsModel;
import lk.ijse.st_clothing.model.SupplierModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.controlsfx.control.textfield.TextFields;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ItemsFormController {
    @FXML
    private Label lblItemCode;

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
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearchItemByItemCode;


    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtUnitPrice;
    private ObservableList<ItemTm> toTable;

    @FXML
    private ImageView imgViewer;

    private Integer index = null;
    public void initialize() throws SQLException {
        loadAllSupplierIds();
        generateNextItemCode();
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

    private void generateNextItemCode() {
        try {
            String itemCode = ItemsModel.generateNextItemCode();
            lblItemCode.setText(itemCode);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadAllSupplierIds() throws SQLException {
        ArrayList<String> supIds = SupplierModel.getSupplierIds();
        TextFields.bindAutoCompletion(txtSupplierId, supIds);
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
        String id = lblItemCode.getText();
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
        String itemCode = lblItemCode.getText();
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
        index = tblItems.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        lblItemCode.setText(colItemCode.getCellData(index).toString());
        txtSupplierId.setText(colSupplierId.getCellData(index).toString());
        txtDescription.setText(colDescription.getCellData(index).toString());
        txtUnitPrice.setText(colUnitPrice.getCellData(index).toString());
        txtQty.setText(colQty.getCellData(index).toString());
        cmbSize.setValue(colSize.getCellData(index).toString());
        try {
            String str = colItemCode.getCellData(index).toString();
            String path = System.getProperty("user.dir") + "/";
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            generateQRcode(str, path + str + ".png", charset, hashMap, 200, 200);

            File file = new File(path + str + ".png");
            Image image = new Image(file.toURI().toString());
            imgViewer.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    @FXML
    void printQrOnAction(ActionEvent event) {
        try {
            if(index==null) {
                new Alert(Alert.AlertType.ERROR,"Please select a item table's row to genarate item's QR!").show();
                return;
            }
            // Get the image from the ImageView
            Image img = imgViewer.getImage();
            BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);

            // Create a new PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a content stream to draw onto the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Add text at the top of the page with the QR code data
            String qrData = colItemCode.getCellData(index).toString(); // Get the QR code text
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12); // Set font and font size
            contentStream.newLineAtOffset(100, 700); // Set position (adjust as needed)
            contentStream.showText("Item Code: " + qrData);
            contentStream.endText();

            // Draw the image on the PDF page
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bImage);
            contentStream.drawImage(pdImage, 100, 500); // Adjust the coordinates as needed

            // Close the content stream
            contentStream.close();

            // Save the PDF document
            File outputFile = new File("/home/pathum/Desktop/"+qrData+".pdf");
            document.save(outputFile);
            new Alert(Alert.AlertType.CONFIRMATION,"QR code saved successfully to your device's storage!").show();
            // Close the document
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearAllFields() throws SQLException {
//        lblItemCode.setText("");
        txtSupplierId.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQty.clear();
        txtSearchItemByItemCode.clear();
        cmbSize.setValue(null);
        imgViewer.setImage(null);
        index=null;
        initialize();
    }

    @FXML
    void btnClearAllFieldsOnAction(ActionEvent event) throws SQLException {
        clearAllFields();
    }

}
