package com.skillswapp.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class SwapperRow extends HBox {
    public SwapperRow(String emoji, String name, double rating, Runnable onViewProfile) {
        this.getStyleClass().add("swapper-row");
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER_LEFT);

        Label avatarLabel = new Label(emoji);
        avatarLabel.getStyleClass().add("swapper-avatar");

        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("swapper-name");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label ratingLabel = new Label("⭐ " + rating);
        ratingLabel.getStyleClass().add("swapper-rating");

        Button viewBtn = new Button("View");
        viewBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 5 12; -fx-background-radius: 5; -fx-cursor: hand;");
        viewBtn.setOnAction(e -> onViewProfile.run());

        this.getChildren().addAll(avatarLabel, nameLabel, spacer, ratingLabel, viewBtn);
    }
}
