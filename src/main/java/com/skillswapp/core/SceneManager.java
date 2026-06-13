package com.skillswapp.core;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SceneManager {
    private Stage stage;
    private Scene mainScene;
    private MainLayout mainLayout;
    private Map<String, Supplier<Pane>> viewFactories = new HashMap<>();

    public SceneManager(Stage stage) {
        this.stage = stage;
        this.mainLayout = new MainLayout(this);
        this.mainScene = new Scene(mainLayout, 1200, 800);
        // We will load the CSS later
        try {
            this.mainScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("CSS not loaded yet");
        }
        stage.setScene(mainScene);
    }

    public Stage getStage() {
        return stage;
    }

    public void registerView(String name, Supplier<Pane> viewFactory) {
        viewFactories.put(name, viewFactory);
    }

    public void switchView(String name) {
        if (viewFactories.containsKey(name)) {
            Pane view = viewFactories.get(name).get();
            if (name.equals("SignIn") || name.equals("Register")) {
                // Auth views don't have the sidebar
                mainScene.setRoot(view);
            } else {
                // Authenticated views use the MainLayout
                mainLayout.setContent(view);
                mainScene.setRoot(mainLayout);
            }
        }
    }
}
