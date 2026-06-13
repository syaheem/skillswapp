package com.skillswapp;

import javafx.application.Application;
import javafx.stage.Stage;
import com.skillswapp.core.SceneManager;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class SkillSwappApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SkillSwapp");
        
        SceneManager sceneManager = new SceneManager(primaryStage);
        
        // Register placeholder views for the 11 interfaces
        sceneManager.registerView("SignIn", () -> new com.skillswapp.views.auth.SignInView(sceneManager));
        sceneManager.registerView("Register", () -> new com.skillswapp.views.auth.RegisterView(sceneManager));
        sceneManager.registerView("HomeDashboard", () -> new com.skillswapp.views.dashboard.HomeDashboardView(sceneManager));
        sceneManager.registerView("ManageSkills", () -> new com.skillswapp.views.skills.ManageSkillsView(sceneManager));
        sceneManager.registerView("ManageProfile", () -> new com.skillswapp.views.profile.ManageProfileView(sceneManager));
        sceneManager.registerView("SearchResults", () -> new com.skillswapp.views.search.SearchResultsView(sceneManager));
        sceneManager.registerView("OtherUserProfile", () -> new com.skillswapp.views.profile.OtherUserProfileView(sceneManager));
        sceneManager.registerView("IncomingRequests", () -> new com.skillswapp.views.trades.IncomingRequestsView(sceneManager));
        sceneManager.registerView("OutgoingRequests", () -> new com.skillswapp.views.trades.OutgoingRequestsView(sceneManager));
        sceneManager.registerView("TradeHistory", () -> new com.skillswapp.views.trades.TradeHistoryView(sceneManager));
        sceneManager.registerView("SharedResources", () -> new com.skillswapp.views.trades.SharedResourcesView(sceneManager));
        
        // Start at Sign In
        sceneManager.switchView("SignIn");
        primaryStage.show();
    }

    private VBox createPlaceholder(String text) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        box.getChildren().add(label);
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
