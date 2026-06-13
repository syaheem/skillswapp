package com.skillswapp.core;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class ModalUtil {
    public static void showModal(Stage owner, Pane content) {
        Stage modalStage = new Stage();
        modalStage.initOwner(owner);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);

        StackPane root = new StackPane(content);
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-padding: 40;");
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        try {
            scene.getStylesheets().add(ModalUtil.class.getResource("/styles.css").toExternalForm());
        } catch (Exception e) {}
        
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    public static void showSuccessAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
