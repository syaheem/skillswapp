package com.skillswapp.views.trades;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;
import com.skillswapp.core.DataStore;
import com.skillswapp.core.DataStore.TradeRequest;
import com.skillswapp.core.DataStore.Course;
import com.skillswapp.core.DataStore.TradeHistory;

public class IncomingRequestsView extends VBox {
    private SceneManager sceneManager;
    private VBox cardsContainer;

    public IncomingRequestsView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.getStyleClass().add("dashboard");
        this.setSpacing(20);
        this.setPadding(new Insets(30));

        Label title = new Label("Incoming Swap Requests");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #008080;");

        Label subtitle = new Label("Requests from other users who want to swap with you");
        subtitle.setStyle("-fx-text-fill: #777; -fx-font-size: 13px;");

        cardsContainer = new VBox(15);
        loadRequests();

        ScrollPane scrollPane = new ScrollPane(cardsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        this.getChildren().addAll(title, subtitle, scrollPane);
    }

    private void loadRequests() {
        cardsContainer.getChildren().clear();
        for (TradeRequest req : DataStore.getInstance().incomingRequests) {
            cardsContainer.getChildren().add(createRequestCard(req));
        }
        if (DataStore.getInstance().incomingRequests.isEmpty()) {
            cardsContainer.getChildren().add(new Label("No incoming requests at the moment."));
        }
    }

    private VBox createRequestCard(TradeRequest req) {
        VBox requestCard = new VBox(10);
        requestCard.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");
        
        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);
        Label avatar = new Label("👤");
        avatar.setStyle("-fx-font-size: 20px; -fx-background-color: #f0f7f4; -fx-background-radius: 50; -fx-padding: 5 8;");
        Label nameLabel = new Label(req.user);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Label timeLabel = new Label(req.timeAgo);
        timeLabel.setStyle("-fx-text-fill: #999; -fx-font-size: 11px;");
        topRow.getChildren().addAll(avatar, nameLabel, sp, timeLabel);

        Label reqInfo = new Label(req.user + " wants your " + req.requested + " and is offering their " + req.offered + " in return.");
        reqInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #555; -fx-font-weight: bold;");
        reqInfo.setWrapText(true);

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_LEFT);
        
        Button acceptBtn = new Button("✅ Accept");
        acceptBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 5; -fx-cursor: hand;");
        acceptBtn.setOnAction(e -> showAcceptPopup(req));

        Button rejectBtn = new Button("❌ Reject");
        rejectBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 5; -fx-cursor: hand;");
        rejectBtn.setOnAction(e -> showRejectPopup(req));

        Button counterBtn = new Button("🔀 Counter Offer");
        counterBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 5; -fx-cursor: hand;");
        counterBtn.setOnAction(e -> showCounterPopup(req));

        Button viewProfileBtn = new Button("👤 View Profile");
        viewProfileBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #008080; -fx-cursor: hand; -fx-padding: 8 15;");
        viewProfileBtn.setOnAction(e -> {
            DataStore.getInstance().currentViewingUser = req.user;
            sceneManager.switchView("OtherUserProfile");
        });
        
        actions.getChildren().addAll(acceptBtn, counterBtn, rejectBtn, viewProfileBtn);
        
        requestCard.getChildren().addAll(topRow, reqInfo, actions);
        return requestCard;
    }

    private void showAcceptPopup(TradeRequest req) {
        VBox popup = new VBox(15);
        popup.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 10; -fx-min-width: 320px;");
        popup.setAlignment(Pos.CENTER);
        
        Label title = new Label("Accept Swap with " + req.user);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label desc = new Label("Do you agree to swap your " + req.requested + " for " + req.user + "'s " + req.offered + "?");
        desc.setWrapText(true);
        
        Button confirmBtn = new Button("Confirm Swap");
        confirmBtn.getStyleClass().add("request-btn");
        confirmBtn.setMaxWidth(Double.MAX_VALUE);
        confirmBtn.setOnAction(e -> {
            ((javafx.stage.Stage)confirmBtn.getScene().getWindow()).close();
            
            // Remove from incoming
            DataStore.getInstance().incomingRequests.remove(req);
            // Add to history
            DataStore.getInstance().tradeHistory.add(0, new TradeHistory(
                req.user, req.requested, req.offered, DataStore.getInstance().getCurrentDateFormatted(), false
            ));
            DataStore.getInstance().saveToFile();
            
            loadRequests();
            ModalUtil.showSuccessAlert("Swap with " + req.user + " accepted! You traded '" + req.requested + "' for '" + req.offered + "'.");
        });
        
        popup.getChildren().addAll(title, desc, confirmBtn);
        ModalUtil.showModal(sceneManager.getStage(), popup);
    }

    private void showRejectPopup(TradeRequest req) {
        VBox popup = new VBox(15);
        popup.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 10; -fx-min-width: 300px;");
        popup.setAlignment(Pos.CENTER);
        
        Label title = new Label("Reject swap request from " + req.user + "?");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        title.setWrapText(true);

        TextArea reasonArea = new TextArea();
        reasonArea.setPromptText("Optional: reason for rejection...");
        reasonArea.setPrefRowCount(2);

        HBox btns = new HBox(10);
        btns.setAlignment(Pos.CENTER);

        Button cancelBtn = new Button("Go Back");
        cancelBtn.setStyle("-fx-background-color: #ccc; -fx-text-fill: #333; -fx-padding: 10 20; -fx-background-radius: 5;");
        cancelBtn.setOnAction(e -> ((javafx.stage.Stage)cancelBtn.getScene().getWindow()).close());
        
        Button confirmBtn = new Button("Yes, Reject");
        confirmBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;");
        confirmBtn.setOnAction(e -> {
            ((javafx.stage.Stage)confirmBtn.getScene().getWindow()).close();
            DataStore.getInstance().incomingRequests.remove(req);
            DataStore.getInstance().saveToFile();
            loadRequests();
            ModalUtil.showSuccessAlert("Swap request from " + req.user + " rejected.");
        });

        btns.getChildren().addAll(cancelBtn, confirmBtn);
        popup.getChildren().addAll(title, reasonArea, btns);
        ModalUtil.showModal(sceneManager.getStage(), popup);
    }

    private void showCounterPopup(TradeRequest req) {
        VBox popup = new VBox(15);
        popup.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 10; -fx-min-width: 320px;");
        popup.setAlignment(Pos.CENTER);
        
        Label title = new Label("Counter Offer to " + req.user);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label desc = new Label(req.user + " wants your " + req.requested + ". You don't want " + req.offered + ". Select a different course to ask for instead:");
        desc.setWrapText(true);
        
        ComboBox<String> newWantCombo = new ComboBox<>();
        // Mock courses the other user has
        newWantCombo.getItems().addAll("INFO 4402", "INFO 4303", "COMM 2100", "ENGR 2201");
        newWantCombo.setPromptText("Select a course to request...");
        newWantCombo.setMaxWidth(Double.MAX_VALUE);
        
        Button confirmBtn = new Button("Send Counter Offer");
        confirmBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;");
        confirmBtn.setMaxWidth(Double.MAX_VALUE);
        confirmBtn.setOnAction(e -> {
            if (newWantCombo.getValue() == null) {
                ModalUtil.showSuccessAlert("Please select a course to ask for.");
                return;
            }
            ((javafx.stage.Stage)confirmBtn.getScene().getWindow()).close();
            
            // Remove from incoming
            DataStore.getInstance().incomingRequests.remove(req);
            
            // Add a new outgoing request: We want newWantCombo, we offer req.requested
            DataStore.getInstance().outgoingRequests.add(0, new TradeRequest(
                req.user, req.requested, newWantCombo.getValue(), "Just now", "Pending"
            ));
            DataStore.getInstance().saveToFile();
            
            loadRequests();
            ModalUtil.showSuccessAlert("Counter offer sent to " + req.user + "! You requested '" + newWantCombo.getValue() + "'.");
        });
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #999; -fx-cursor: hand;");
        cancelBtn.setMaxWidth(Double.MAX_VALUE);
        cancelBtn.setOnAction(e -> ((javafx.stage.Stage)cancelBtn.getScene().getWindow()).close());
        
        popup.getChildren().addAll(title, desc, newWantCombo, confirmBtn, cancelBtn);
        ModalUtil.showModal(sceneManager.getStage(), popup);
    }
}
