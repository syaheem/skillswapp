package com.skillswapp.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class CommunityCard extends HBox {
    public CommunityCard(String emoji, String title, String subtitle) {
        this.getStyleClass().add("community-card");
        this.setSpacing(12);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-cursor: hand;");

        Label iconLabel = new Label(emoji);
        iconLabel.getStyleClass().add("community-icon");
        iconLabel.setMinWidth(Region.USE_PREF_SIZE);

        VBox textBox = new VBox(4);
        HBox.setHgrow(textBox, Priority.ALWAYS);
        
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("community-title");
        titleLabel.setWrapText(true);
        // Remove max width restriction so full degree name is visible
        titleLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #222;");
        
        Label subLabel = new Label(subtitle);
        subLabel.getStyleClass().add("community-subtitle");
        subLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");
        
        textBox.getChildren().addAll(titleLabel, subLabel);

        Button joinBtn = new Button("Join");
        joinBtn.setMinWidth(Region.USE_PREF_SIZE);
        joinBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 5 12; -fx-background-radius: 5; -fx-cursor: hand;");
        joinBtn.setOnAction(e -> {
            joinBtn.setText("Joined ✓");
            joinBtn.setDisable(true);
            joinBtn.setStyle("-fx-background-color: #ccc; -fx-text-fill: #555; -fx-font-size: 11px; -fx-padding: 5 12; -fx-background-radius: 5;");
        });

        this.getChildren().addAll(iconLabel, textBox, joinBtn);
    }
}
