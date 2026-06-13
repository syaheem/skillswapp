package com.skillswapp.core;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.Node;
import javafx.geometry.Pos;

public class MainLayout extends BorderPane {
    private SceneManager sceneManager;
    private VBox sidebar;

    public MainLayout(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        setupSidebar();
        this.setLeft(sidebar);
        this.setStyle("-fx-background-color: #f4f9f7;");
    }

    private void setupSidebar() {
        sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(170);
        sidebar.setAlignment(Pos.TOP_CENTER);

        Label logo = new Label("🔄 SkillSwapp");
        logo.getStyleClass().add("logo");
        logo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #008080; -fx-padding: 0 0 20 0;");

        Button homeBtn = createNavBtn("🏠  Home", "HomeDashboard");
        Button skillsBtn = createNavBtn("📚  My Courses", "ManageSkills");

        Label tradeLabel = new Label("SWAPS");
        tradeLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #999; -fx-font-weight: bold; -fx-padding: 15 0 0 10;");

        Button incomingBtn = createNavBtn("📥  Incoming", "IncomingRequests");
        Button outgoingBtn = createNavBtn("📤  Outgoing", "OutgoingRequests");
        Button historyBtn = createNavBtn("📋  History", "TradeHistory");

        Label accountLabel = new Label("ACCOUNT");
        accountLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #999; -fx-font-weight: bold; -fx-padding: 15 0 0 10;");

        Button profileBtn = createNavBtn("👤  Profile", "ManageProfile");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = new Button("🚪  Logout");
        logoutBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.setAlignment(Pos.CENTER_LEFT);
        logoutBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #c0392b; -fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 10 15;");
        logoutBtn.setOnAction(e -> sceneManager.switchView("SignIn"));

        sidebar.getChildren().addAll(logo, homeBtn, skillsBtn,
            tradeLabel, incomingBtn, outgoingBtn, historyBtn,
            accountLabel, profileBtn, spacer, logoutBtn);
    }

    private Button createNavBtn(String text, String viewName) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 10 15;");
        btn.setOnAction(e -> sceneManager.switchView(viewName));
        return btn;
    }

    public void setContent(Node content) {
        this.setCenter(content);
    }
}
