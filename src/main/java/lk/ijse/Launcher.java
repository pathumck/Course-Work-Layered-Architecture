package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Launcher extends Application {
    public static AnchorPane rootNode;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_forum.fxml"));
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.setTitle("ST Clothing");
        stage.centerOnScreen();
        stage.show();

        // Locate the AnchorPane within dashboard_form.fxml
        AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

        // Load and set the content from another FXML file
        FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
        AnchorPane subContent = subLoader.load();

        // Add the subContent to the targetAnchorPane
        targetAnchorPane.getChildren().setAll(subContent);
    }


}