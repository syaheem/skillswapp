package com.skillswapp.views.trades;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;
import com.skillswapp.core.DataStore;
import com.skillswapp.core.DataStore.TradeRequest;

public class OutgoingRequestsView extends VBox {
    private SceneManager sceneManager;
    private VBox cards;

    public OutgoingRequestsView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.getStyleClass().add("dashboard");
        this.setSpacing(20);
        this.setPadding(new Insets(30));

        Label title = new Label("Outgoing Swap Requests");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #008080;");
        Label subtitle = new Label("Swap requests you have sent to other users");
        subtitle.setStyle("-fx-text-fill: #777; -fx-font-size: 13px;");

        cards = new VBox(15);
        loadRequests();

        ScrollPane sp = new ScrollPane(cards);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(sp, Priority.ALWAYS);
        this.getChildren().addAll(title, subtitle, sp);
    }

    private void loadRequests() {
        cards.getChildren().clear();
        for (TradeRequest req : DataStore.getInstance().outgoingRequests) {
            cards.getChildren().add(makeCard(req));
        }
        if (DataStore.getInstance().outgoingRequests.isEmpty()) {
            cards.getChildren().add(new Label("No outgoing requests at the moment."));
        }
    }

    private VBox makeCard(TradeRequest req) {
        VBox c = new VBox(10);
        c.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");
        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER_LEFT);
        Label n = new Label("To: " + req.user);
        n.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
        Region r = new Region(); HBox.setHgrow(r, Priority.ALWAYS);
        String col = req.status.startsWith("Pending") ? "#f5a623" : "#4CAF50";
        Label s = new Label(req.status);
        s.setStyle("-fx-text-fill: " + col + "; -fx-font-weight: bold;");
        top.getChildren().addAll(n, r, s);
        Label info = new Label("You requested " + req.requested + " and offered your " + req.offered + " in return. (Sent " + req.timeAgo + ")");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;"); info.setWrapText(true);
        HBox acts = new HBox(10);
        if (req.status.equals("Pending")) {
            Button cancel = new Button("❌ Cancel");
            cancel.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 5; -fx-cursor: hand;");
            cancel.setOnAction(e -> showCancel(req));
            acts.getChildren().add(cancel);
        }
        Button vp = new Button("👤 View Profile");
        vp.setStyle("-fx-background-color: transparent; -fx-text-fill: #008080; -fx-cursor: hand;");
        vp.setOnAction(e -> {
            DataStore.getInstance().currentViewingUser = req.user;
            sceneManager.switchView("OtherUserProfile");
        });
        acts.getChildren().add(vp);
        c.getChildren().addAll(top, info, acts);
        return c;
    }

    private void showCancel(TradeRequest req) {
        VBox p = new VBox(15);
        p.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 10;");
        p.setAlignment(Pos.CENTER);
        Label t = new Label("Cancel request to " + req.user + "?");
        t.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        HBox b = new HBox(10); b.setAlignment(Pos.CENTER);
        Button back = new Button("Go Back");
        back.setStyle("-fx-background-color: #ccc; -fx-padding: 10 20; -fx-background-radius: 5;");
        back.setOnAction(e -> ((javafx.stage.Stage)back.getScene().getWindow()).close());
        Button yes = new Button("Yes, Cancel");
        yes.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;");
        yes.setOnAction(e -> { 
            ((javafx.stage.Stage)yes.getScene().getWindow()).close(); 
            DataStore.getInstance().outgoingRequests.remove(req);
            DataStore.getInstance().saveToFile();
            loadRequests();
            ModalUtil.showSuccessAlert("Request to " + req.user + " cancelled."); 
        });
        b.getChildren().addAll(back, yes);
        p.getChildren().addAll(t, b);
        ModalUtil.showModal(sceneManager.getStage(), p);
    }
}
