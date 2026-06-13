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
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);

        Label avatarLabel = new Label(emoji);
        avatarLabel.getStyleClass().add("swapper-avatar");
        avatarLabel.setMinWidth(Region.USE_PREF_SIZE);

        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("swapper-name");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        nameLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #222;");
        HBox.setHgrow(nameLabel, Priority.ALWAYS);

        Label ratingLabel = new Label("⭐ " + rating);
        ratingLabel.getStyleClass().add("swapper-rating");
        ratingLabel.setMinWidth(Region.USE_PREF_SIZE);

        Button viewBtn = new Button("View");
        viewBtn.setMinWidth(Region.USE_PREF_SIZE);
        viewBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 5 12; -fx-background-radius: 5; -fx-cursor: hand;");
        viewBtn.setOnAction(e -> onViewProfile.run());

        this.getChildren().addAll(avatarLabel, nameLabel, ratingLabel, viewBtn);
    }
}
