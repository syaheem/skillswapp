package com.skillswapp.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import com.skillswapp.core.ModalUtil;

public class SkillCard extends VBox {
    public String courseCode;
    public String ownerName;

    public SkillCard(String iconEmoji, String title, String fullName, String description, int members, double rating) {
        this(iconEmoji, title, fullName, description, members, rating, "Unknown User");
    }

    public SkillCard(String iconEmoji, String title, String fullName, String description, int members, double rating, String ownerName) {
        this.courseCode = title;
        this.ownerName = (ownerName != null && !ownerName.isEmpty()) ? ownerName : "Unknown User";

        // Card container — clean white row with subtle shadow
        this.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 14;" +
            "-fx-padding: 14 18 14 14;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 8, 0, 0, 3);" +
            "-fx-cursor: hand;"
        );
        this.setSpacing(0);
        this.setMaxWidth(Double.MAX_VALUE);

        // ── Main horizontal row ──────────────────────────────────
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        VBox.setVgrow(row, Priority.ALWAYS);

        // Left: Emoji icon badge
        StackPane iconBadge = new StackPane();
        iconBadge.setMinSize(50, 50);
        iconBadge.setMaxSize(50, 50);
        iconBadge.setStyle(
            "-fx-background-color: #e8f5f3;" +
            "-fx-background-radius: 12;"
        );
        Label iconLabel = new Label(iconEmoji);
        iconLabel.setStyle("-fx-font-size: 22px;");
        iconBadge.getChildren().add(iconLabel);

        // Center: course info
        VBox centerBox = new VBox(3);
        HBox.setHgrow(centerBox, Priority.ALWAYS);

        HBox titleRow = new HBox(8);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label codeLabel = new Label(title);
        codeLabel.setStyle(
            "-fx-font-weight: bold;" +
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #1a1a2e;"
        );

        // Rating pill inline with title
        Label ratingPill = new Label("⭐ " + rating);
        ratingPill.setStyle(
            "-fx-background-color: #fff8e1;" +
            "-fx-text-fill: #e65100;" +
            "-fx-font-size: 11px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 2 8;" +
            "-fx-background-radius: 10;"
        );

        titleRow.getChildren().addAll(codeLabel, ratingPill);

        Label nameLabel = new Label(fullName);
        nameLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #008080;" +
            "-fx-font-weight: bold;"
        );
        nameLabel.setWrapText(true);

        // Owner row
        HBox ownerRow = new HBox(6);
        ownerRow.setAlignment(Pos.CENTER_LEFT);
        Label avatarDot = new Label("👤");
        avatarDot.setStyle("-fx-font-size: 11px;");
        Label ownerLbl = new Label(this.ownerName);
        ownerLbl.setStyle(
            "-fx-font-size: 11px;" +
            "-fx-text-fill: #666;" +
            "-fx-font-weight: bold;"
        );
        Label membersBadge = new Label("• " + members + " requests");
        membersBadge.setStyle(
            "-fx-font-size: 11px;" +
            "-fx-text-fill: #999;"
        );
        ownerRow.getChildren().addAll(avatarDot, ownerLbl, membersBadge);

        centerBox.getChildren().addAll(titleRow, nameLabel, ownerRow);

        // Right: Request button
        Button requestBtn = new Button("Swap");
        requestBtn.setStyle(
            "-fx-background-color: #008080;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-padding: 8 18;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
        requestBtn.setMinWidth(Region.USE_PREF_SIZE);

        // Hover effect on card
        this.setOnMouseEntered(e -> this.setStyle(
            "-fx-background-color: #f0faf8;" +
            "-fx-background-radius: 14;" +
            "-fx-padding: 14 18 14 14;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,128,128,0.15), 12, 0, 0, 4);" +
            "-fx-cursor: hand;"
        ));
        this.setOnMouseExited(e -> this.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 14;" +
            "-fx-padding: 14 18 14 14;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 8, 0, 0, 3);" +
            "-fx-cursor: hand;"
        ));

        requestBtn.setOnMouseEntered(e -> requestBtn.setStyle(
            "-fx-background-color: #006666;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-padding: 8 18;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        ));
        requestBtn.setOnMouseExited(e -> requestBtn.setStyle(
            "-fx-background-color: #008080;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-padding: 8 18;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        ));

        requestBtn.setOnAction(e -> {
            VBox popupContent = new VBox(15);
            popupContent.setStyle(
                "-fx-background-color: white;" +
                "-fx-padding: 30;" +
                "-fx-background-radius: 12;" +
                "-fx-min-width: 340px;"
            );
            popupContent.setAlignment(Pos.CENTER_LEFT);

            Label popTitle = new Label("🔄 Request Swap");
            popTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1a1a2e;");

            Label courseInfo = new Label(title + " — " + fullName);
            courseInfo.setStyle("-fx-font-size: 13px; -fx-text-fill: #008080; -fx-font-weight: bold;");

            Label offerLabel = new Label("Your course to offer:");
            offerLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

            javafx.scene.control.ComboBox<String> courseCombo = new javafx.scene.control.ComboBox<>();
            for (com.skillswapp.core.DataStore.Course c : com.skillswapp.core.DataStore.getInstance().myCourses) {
                courseCombo.getItems().add(c.code + " — " + c.fullName);
            }
            courseCombo.setPromptText("Select your course...");
            courseCombo.setMaxWidth(Double.MAX_VALUE);
            courseCombo.setStyle("-fx-font-size: 13px;");

            Label msgLabel = new Label("Message (optional):");
            msgLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

            javafx.scene.control.TextArea msgArea = new javafx.scene.control.TextArea();
            msgArea.setPromptText("Write a message to " + this.ownerName + "...");
            msgArea.setPrefRowCount(3);
            msgArea.setStyle("-fx-font-size: 13px;");

            Button sendBtn = new Button("✉ Send Swap Request");
            sendBtn.setStyle(
                "-fx-background-color: #008080;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 12;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;" +
                "-fx-font-size: 14px;"
            );
            sendBtn.setMaxWidth(Double.MAX_VALUE);
            sendBtn.setOnAction(evt -> {
                if (courseCombo.getValue() == null) {
                    ModalUtil.showSuccessAlert("Please select a course to offer.");
                    return;
                }
                ((javafx.stage.Stage) sendBtn.getScene().getWindow()).close();
                String offeredCode = courseCombo.getValue().split(" — ")[0];
                com.skillswapp.core.DataStore.getInstance().outgoingRequests.add(0,
                    new com.skillswapp.core.DataStore.TradeRequest(
                        this.ownerName, offeredCode, title, "Just now", "Pending"
                    )
                );
                com.skillswapp.core.DataStore.getInstance().saveToFile();
                ModalUtil.showSuccessAlert("Swap request sent to " + this.ownerName + " for " + title + "!");
            });

            popupContent.getChildren().addAll(popTitle, courseInfo, offerLabel, courseCombo, msgLabel, msgArea, sendBtn);
            ModalUtil.showModal((javafx.stage.Stage) this.getScene().getWindow(), popupContent);
        });

        row.getChildren().addAll(iconBadge, centerBox, requestBtn);
        this.getChildren().add(row);
    }
}
