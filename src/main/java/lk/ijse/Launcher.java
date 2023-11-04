package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        // Load the main Dashboard_form.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard_form.fxml"));
        AnchorPane rootNode = loader.load();

        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();

        // Locate the AnchorPane within Dashboard_form.fxml
        AnchorPane targetAnchorPane = (AnchorPane) rootNode.lookup("#subAnchorPaneRight");

        // Load and set the content from another FXML file
        FXMLLoader subLoader = new FXMLLoader(getClass().getResource("/view/Orders_form.fxml"));
        AnchorPane subContent = subLoader.load();

        // Add the subContent to the targetAnchorPane
        targetAnchorPane.getChildren().setAll(subContent);


    }
}
