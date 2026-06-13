package com.skillswapp.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import com.skillswapp.core.ModalUtil;

public class SkillCard extends VBox {
    public String courseCode;
    public SkillCard(String iconEmoji, String title, String fullName, String description, int members, double rating) {
        this.courseCode = title;
        this.getStyleClass().add("skill-card");
        this.setSpacing(10);

        // Header
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        
        ImageView iconView = null;
        try {
            iconView = new ImageView(new Image(getClass().getResourceAsStream("/images/course.png")));
            iconView.setFitWidth(60);
            iconView.setFitHeight(60);
            iconView.setPreserveRatio(true);
            Rectangle clip = new Rectangle(60, 60);
            clip.setArcWidth(15);
            clip.setArcHeight(15);
            iconView.setClip(clip);
        } catch (Exception ex) {
            System.err.println("Could not load image.");
        }
        
        javafx.scene.Node iconNode = iconView != null ? iconView : new Label(iconEmoji);
        
        VBox titleBox = new VBox(2);
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #222;");
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(160);
        
        Label fullNameLabel = new Label(fullName);
        fullNameLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #008080; -fx-font-weight: bold;");
        fullNameLabel.setWrapText(true);
        fullNameLabel.setMaxWidth(160);
        
        titleBox.getChildren().addAll(titleLabel, fullNameLabel);
        
        header.getChildren().addAll(iconNode, titleBox);

        // Body
        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("card-desc");
        descLabel.setWrapText(true);
        descLabel.setMinHeight(60);
        descLabel.setAlignment(Pos.TOP_LEFT);

        // Footer: Rating and Members
        HBox statsBox = new HBox(10);
        statsBox.setAlignment(Pos.CENTER_LEFT);
        
        Label membersLabel = new Label("👥 " + members + " Members");
        membersLabel.getStyleClass().add("card-stats");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label ratingLabel = new Label("⭐ " + rating);
        ratingLabel.getStyleClass().add("card-rating");
        
        statsBox.getChildren().addAll(membersLabel, spacer, ratingLabel);

        // Action Button
        Button requestBtn = new Button("Request Swap");
        requestBtn.getStyleClass().add("request-btn");
        requestBtn.setMaxWidth(Double.MAX_VALUE);
        requestBtn.setOnAction(e -> {
            VBox popupContent = new VBox(15);
            popupContent.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 10; -fx-min-width: 320px;");
            popupContent.setAlignment(Pos.CENTER);
            Label popTitle = new Label("Request Swap for " + title);
            popTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            javafx.scene.control.ComboBox<String> courseCombo = new javafx.scene.control.ComboBox<>();
            for (com.skillswapp.core.DataStore.Course c : com.skillswapp.core.DataStore.getInstance().myCourses) {
                courseCombo.getItems().add(c.code);
            }
            courseCombo.setPromptText("Select your course to offer...");
            courseCombo.setMaxWidth(Double.MAX_VALUE);
            javafx.scene.control.TextArea msgArea = new javafx.scene.control.TextArea();
            msgArea.setPromptText("Write a message to the owner...");
            msgArea.setPrefRowCount(2);
            Button sendBtn = new Button("Send Swap Request");
            sendBtn.getStyleClass().add("request-btn");
            sendBtn.setMaxWidth(Double.MAX_VALUE);
            sendBtn.setOnAction(evt -> {
                if (courseCombo.getValue() == null) {
                    ModalUtil.showSuccessAlert("Please select a course to offer.");
                    return;
                }
                ((javafx.stage.Stage)sendBtn.getScene().getWindow()).close();
                // Add to outgoing requests dynamically
                com.skillswapp.core.DataStore.getInstance().outgoingRequests.add(0, 
                    new com.skillswapp.core.DataStore.TradeRequest("Owner of " + title, courseCombo.getValue(), title, "Just now", "Pending")
                );
                com.skillswapp.core.DataStore.getInstance().saveToFile();
                ModalUtil.showSuccessAlert("Swap request sent for " + title + "! You offered '" + courseCombo.getValue() + "'.");
            });
            popupContent.getChildren().addAll(popTitle, new Label("Your course to offer:"), courseCombo, new Label("Message:"), msgArea, sendBtn);
            ModalUtil.showModal((javafx.stage.Stage) this.getScene().getWindow(), popupContent);
        });

        this.getChildren().addAll(header, descLabel, statsBox, requestBtn);
    }
}
