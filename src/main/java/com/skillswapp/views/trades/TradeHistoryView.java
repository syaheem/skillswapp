package com.skillswapp.views.trades;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;
import com.skillswapp.core.DataStore;
import com.skillswapp.core.DataStore.TradeHistory;

public class TradeHistoryView extends VBox {
    private SceneManager sceneManager;
    private VBox cards;

    public TradeHistoryView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.setStyle("-fx-background-color: #f4f9f7;");
        this.setSpacing(25);
        this.setPadding(new Insets(30, 40, 30, 40));

        Label title = new Label("🔄 Swap History");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #222;");
        Label sub = new Label("A timeline of your completed swaps");
        sub.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-padding: -10 0 0 0;");

        cards = new VBox(20);
        loadHistory();

        ScrollPane sp = new ScrollPane(cards);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(sp, Priority.ALWAYS);
        this.getChildren().addAll(title, sub, sp);
    }

    private void loadHistory() {
        cards.getChildren().clear();
        for (TradeHistory th : DataStore.getInstance().tradeHistory) {
            cards.getChildren().add(makeCard(th));
        }
        if (DataStore.getInstance().tradeHistory.isEmpty()) {
            cards.getChildren().add(new Label("No swap history available yet."));
        }
    }

    private VBox makeCard(TradeHistory th) {
        VBox c = new VBox(15);
        c.setStyle("-fx-background-color: white; -fx-padding: 25; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 15, 0, 0, 5);");
        
        HBox top = new HBox(15); 
        top.setAlignment(Pos.CENTER_LEFT);
        
        Label icon = new Label("✅");
        icon.setStyle("-fx-font-size: 22px; -fx-background-color: #e8f5e9; -fx-padding: 8 12; -fx-background-radius: 50;");
        
        VBox infoBox = new VBox(5);
        Label info = new Label("Swapped '" + th.gave + "' with " + th.user + " for '" + th.got + "'");
        info.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
        info.setWrapText(true);
        
        Label dateL = new Label("Completed on: " + th.date);
        dateL.setStyle("-fx-text-fill: #888; -fx-font-size: 13px;");
        
        infoBox.getChildren().addAll(info, dateL);
        top.getChildren().addAll(icon, infoBox);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        HBox acts = new HBox(15); 
        acts.setAlignment(Pos.CENTER_RIGHT);
        
        if (!th.rated) {
            Button rateBtn = new Button("⭐ Rate " + th.user);
            String btnStyle = "-fx-background-color: #f5a623; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 13px;";
            rateBtn.setStyle(btnStyle);
            rateBtn.setOnMouseEntered(e -> rateBtn.setStyle(btnStyle.replace("#f5a623", "#e09015")));
            rateBtn.setOnMouseExited(e -> rateBtn.setStyle(btnStyle));
            rateBtn.setOnAction(e -> showRate(th));
            acts.getChildren().add(rateBtn);
        } else {
            Label ratedL = new Label("⭐ Rated");
            ratedL.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #777; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 8; -fx-font-size: 13px;");
            acts.getChildren().add(ratedL);
        }
        
        Button sharedBtn = new Button("📂 Shared Content");
        String sharedStyle = "-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 13px;";
        sharedBtn.setStyle(sharedStyle);
        sharedBtn.setOnMouseEntered(e -> sharedBtn.setStyle(sharedStyle.replace("#008080", "#006666")));
        sharedBtn.setOnMouseExited(e -> sharedBtn.setStyle(sharedStyle));
        sharedBtn.setOnAction(e -> {
            DataStore.getInstance().currentViewingSwap = th;
            sceneManager.switchView("SharedResources");
        });

        Button vp = new Button("View Profile →");
        vp.setStyle("-fx-background-color: transparent; -fx-text-fill: #008080; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 13px;");
        vp.setOnAction(e -> {
            DataStore.getInstance().currentViewingUser = th.user;
            sceneManager.switchView("OtherUserProfile");
        });
        
        acts.getChildren().addAll(sharedBtn, vp);
        c.getChildren().addAll(top, acts);
        return c;
    }

    private void showRate(TradeHistory th) {
        VBox p = new VBox(20);
        p.setStyle("-fx-background-color: white; -fx-padding: 35; -fx-background-radius: 12; -fx-min-width: 350px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 25, 0, 0, 10);");
        p.setAlignment(Pos.CENTER);
        
        Label t = new Label("⭐ Rate " + th.user);
        t.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #222;");
        
        HBox stars = new HBox(8); 
        stars.setAlignment(Pos.CENTER);
        Label[] s = new Label[5];
        for (int i = 0; i < 5; i++) {
            final int idx = i;
            s[i] = new Label("☆");
            s[i].setStyle("-fx-font-size: 45px; -fx-text-fill: #f5a623; -fx-cursor: hand;");
            s[i].setOnMouseClicked(e -> { 
                for (int j=0; j<=idx; j++) s[j].setText("★"); 
                for (int j=idx+1; j<5; j++) s[j].setText("☆"); 
            });
            stars.getChildren().add(s[i]);
        }
        
        TextArea comment = new TextArea();
        comment.setPromptText("Write a public review for " + th.user + "...");
        comment.setPrefRowCount(3);
        comment.setStyle("-fx-padding: 5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #ccc; -fx-font-size: 14px;");
        
        HBox btns = new HBox(15);
        btns.setAlignment(Pos.CENTER);
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> ((javafx.stage.Stage)cancelBtn.getScene().getWindow()).close());
        
        Button submit = new Button("Submit Review");
        String submitStyle = "-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;";
        submit.setStyle(submitStyle);
        submit.setOnMouseEntered(e -> submit.setStyle(submitStyle.replace("#008080", "#006666")));
        submit.setOnMouseExited(e -> submit.setStyle(submitStyle));
        submit.setOnAction(e -> { 
            ((javafx.stage.Stage)submit.getScene().getWindow()).close(); 
            th.rated = true;
            DataStore.getInstance().saveToFile();
            loadHistory();
            ModalUtil.showSuccessAlert("Review for " + th.user + " successfully submitted!"); 
        });
        
        btns.getChildren().addAll(cancelBtn, submit);
        p.getChildren().addAll(t, stars, comment, btns);
        ModalUtil.showModal(sceneManager.getStage(), p);
    }
}
