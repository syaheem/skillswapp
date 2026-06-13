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
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-cursor: hand;");

        Label iconLabel = new Label(emoji);
        iconLabel.getStyleClass().add("community-icon");

        VBox textBox = new VBox(5);
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("community-title");
        
        Label subLabel = new Label(subtitle);
        subLabel.getStyleClass().add("community-subtitle");
        
        textBox.getChildren().addAll(titleLabel, subLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button joinBtn = new Button("Join");
        joinBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 5 12; -fx-background-radius: 5; -fx-cursor: hand;");
        joinBtn.setOnAction(e -> {
            joinBtn.setText("Joined ✓");
            joinBtn.setDisable(true);
            joinBtn.setStyle("-fx-background-color: #ccc; -fx-text-fill: #555; -fx-font-size: 11px; -fx-padding: 5 12; -fx-background-radius: 5;");
        });

        this.getChildren().addAll(iconLabel, textBox, spacer, joinBtn);
    }
}
