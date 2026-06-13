package com.skillswapp.views.profile;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;
import com.skillswapp.components.SkillCard;

public class OtherUserProfileView extends VBox {
    private SceneManager sceneManager;

    public OtherUserProfileView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.setStyle("-fx-background-color: #f4f9f7;");
        this.setSpacing(30);
        this.setPadding(new Insets(30, 40, 30, 40));

        // Header Section with Back Button
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        Button backBtn = new Button("← Back");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #555; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        backBtn.setOnAction(e -> sceneManager.switchView("HomeDashboard"));
        
        Label pageTitle = new Label("User Profile");
        pageTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #999;");
        
        topBar.getChildren().addAll(backBtn, pageTitle);

        // Profile Card
        HBox profileCard = new HBox(30);
        profileCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 30; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 20, 0, 0, 5);");
        profileCard.setAlignment(Pos.CENTER_LEFT);

        // Avatar (Using a distinct styled circle so it doesn't look like the logged-in user)
        String viewUser = com.skillswapp.core.DataStore.getInstance().currentViewingUser;
        
        StackPane avatarPane = new StackPane();
        Circle avatarBg = new Circle(50, javafx.scene.paint.Color.web("#e0f2f1"));
        Label avatarText = new Label(viewUser.substring(0, 1).toUpperCase());
        avatarText.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #008080;");
        avatarPane.getChildren().addAll(avatarBg, avatarText);

        VBox userInfo = new VBox(8);
        userInfo.setAlignment(Pos.CENTER_LEFT);
        
        Label nameLabel = new Label(viewUser);
        nameLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");
        
        HBox badges = new HBox(10);
        Label ratingLabel = new Label("⭐ 4.8 Rating");
        ratingLabel.setStyle("-fx-background-color: #fff8e1; -fx-text-fill: #f57f17; -fx-padding: 4 10; -fx-background-radius: 15; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        Label swapsLabel = new Label("🔄 15 Completed Swaps");
        swapsLabel.setStyle("-fx-background-color: #e8f5e9; -fx-text-fill: #2e7d32; -fx-padding: 4 10; -fx-background-radius: 15; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        Label verifiedLabel = new Label("✅ Verified User");
        verifiedLabel.setStyle("-fx-background-color: #e3f2fd; -fx-text-fill: #1565c0; -fx-padding: 4 10; -fx-background-radius: 15; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        badges.getChildren().addAll(ratingLabel, swapsLabel, verifiedLabel);
        
        Label bioLabel = new Label("Computer Science major. Expert in Mobile and Backend development.");
        bioLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-padding: 5 0 0 0;");
        bioLabel.setWrapText(true);
        
        userInfo.getChildren().addAll(nameLabel, badges, bioLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button requestTradeBtn = new Button("Request Swap");
        String btnStyle = "-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 8; -fx-font-size: 14px; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,128,128,0.3), 10, 0, 0, 4);";
        requestTradeBtn.setStyle(btnStyle);
        requestTradeBtn.setOnMouseEntered(e -> requestTradeBtn.setStyle(btnStyle.replace("#008080", "#006666")));
        requestTradeBtn.setOnMouseExited(e -> requestTradeBtn.setStyle(btnStyle));
        requestTradeBtn.setOnAction(e -> showTradeRequestPopup());

        profileCard.getChildren().addAll(avatarPane, userInfo, spacer, requestTradeBtn);

        // Courses Section
        VBox coursesSection = new VBox(15);
        
        Label skillsLabel = new Label("📚 Courses offered by " + viewUser);
        skillsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TilePane skillsGrid = new TilePane(20, 20);
        skillsGrid.setPrefColumns(4);
        skillsGrid.getChildren().addAll(
            new SkillCard("💻", "INFO 4402", "Mobile App Dev", "Mobile Application Development.", 20, 4.9),
            new SkillCard("🌐", "INFO 3305", "Web Development", "Web Development using HTML, CSS.", 15, 4.7),
            new SkillCard("🔒", "INFO 4303", "Info Security", "Information Security and Cryptography.", 8, 5.0)
        );
        
        coursesSection.getChildren().addAll(skillsLabel, skillsGrid);

        this.getChildren().addAll(topBar, profileCard, coursesSection);
    }

    private void showTradeRequestPopup() {
        String viewUser = com.skillswapp.core.DataStore.getInstance().currentViewingUser;

        VBox popup = new VBox(20);
        popup.setStyle("-fx-background-color: white; -fx-padding: 35; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 25, 0, 0, 10); -fx-min-width: 400px;");
        popup.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("🤝 Request Swap with " + viewUser);
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #222; -fx-padding: 0 0 5 0;");
        
        Label wantLabel = new Label("What course do you want from " + viewUser + "?");
        wantLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #555; -fx-font-size: 13px;");
        ComboBox<String> wantCombo = new ComboBox<>();
        wantCombo.getItems().addAll("INFO 4402", "INFO 3305", "INFO 4303");
        wantCombo.setPromptText("Select a course to request...");
        wantCombo.setMaxWidth(Double.MAX_VALUE);
        wantCombo.setStyle("-fx-padding: 5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #ccc;");

        Label offerLabel = new Label("What course will you offer in return?");
        offerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #555; -fx-font-size: 13px;");
        ComboBox<String> mySkillCombo = new ComboBox<>();
        for (com.skillswapp.core.DataStore.Course c : com.skillswapp.core.DataStore.getInstance().myCourses) {
            mySkillCombo.getItems().add(c.code);
        }
        mySkillCombo.setPromptText("Select a course to offer...");
        mySkillCombo.setMaxWidth(Double.MAX_VALUE);
        mySkillCombo.setStyle("-fx-padding: 5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #ccc;");
        
        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Write an optional message to " + viewUser + "...");
        messageArea.setPrefRowCount(3);
        messageArea.setStyle("-fx-padding: 5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #ccc;");

        HBox btns = new HBox(15);
        btns.setAlignment(Pos.CENTER_RIGHT);
        btns.setPadding(new Insets(10, 0, 0, 0));
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> ((javafx.stage.Stage)cancelBtn.getScene().getWindow()).close());
        
        Button sendBtn = new Button("Send Request");
        String sendStyle = "-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;";
        sendBtn.setStyle(sendStyle);
        sendBtn.setOnMouseEntered(e -> sendBtn.setStyle(sendStyle.replace("#008080", "#006666")));
        sendBtn.setOnMouseExited(e -> sendBtn.setStyle(sendStyle));
        sendBtn.setOnAction(e -> {
            if (wantCombo.getValue() == null || mySkillCombo.getValue() == null) {
                com.skillswapp.core.ModalUtil.showSuccessAlert("Please select both courses.");
                return;
            }
            ((javafx.stage.Stage)sendBtn.getScene().getWindow()).close();
            com.skillswapp.core.DataStore.getInstance().outgoingRequests.add(0, 
                new com.skillswapp.core.DataStore.TradeRequest(viewUser, mySkillCombo.getValue(), wantCombo.getValue(), "Just now", "Pending")
            );
            com.skillswapp.core.DataStore.getInstance().saveToFile();
            com.skillswapp.core.ModalUtil.showSuccessAlert("Swap request sent to " + viewUser + "!");
        });
        
        btns.getChildren().addAll(cancelBtn, sendBtn);
        popup.getChildren().addAll(title, wantLabel, wantCombo, offerLabel, mySkillCombo, messageArea, btns);
        ModalUtil.showModal(sceneManager.getStage(), popup);
    }
}
