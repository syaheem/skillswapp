package com.skillswapp.views.search;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;
import com.skillswapp.core.DataStore;
import com.skillswapp.components.SkillCard;

public class SearchResultsView extends VBox {
    private SceneManager sceneManager;
    private List<SkillCard> allCards = new ArrayList<>();

    public SearchResultsView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.setStyle("-fx-background-color: #f4f9f7;");
        this.setSpacing(20);
        this.setPadding(new Insets(30, 40, 30, 40));

        String query = DataStore.getInstance().currentSearchQuery;

        // Top Search Bar
        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        TextField searchField = new TextField(query);
        searchField.setPromptText("Search courses, members...");
        searchField.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 25; -fx-border-color: #ddd; -fx-border-radius: 25; -fx-background-color: white;");
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchField.setOnAction(e -> {
            DataStore.getInstance().currentSearchQuery = searchField.getText();
            sceneManager.switchView("SearchResults");
        });
        
        Button filtersBtn = new Button("🔍 Advanced Filters");
        filtersBtn.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 20; -fx-padding: 10 20; -fx-cursor: hand; -fx-font-weight: bold; -fx-text-fill: #555;");
        filtersBtn.setOnAction(e -> showFiltersPopup(sceneManager.getStage()));
        
        Region spacer = new Region();
        spacer.setMinWidth(40);
        
        Label profileIcon = new Label("👤");
        profileIcon.setStyle("-fx-font-size: 20px; -fx-cursor: hand; -fx-background-color: white; -fx-background-radius: 50; -fx-padding: 5 10;");
        profileIcon.setOnMouseClicked(e -> sceneManager.switchView("ManageProfile"));
        
        Label bellIcon = new Label("🔔");
        bellIcon.setStyle("-fx-font-size: 20px; -fx-cursor: hand; -fx-text-fill: #555; -fx-padding: 5;");
        
        topBar.getChildren().addAll(searchField, filtersBtn, spacer, profileIcon, bellIcon);

        // Filter Tags
        HBox tagsBox = new HBox(10);
        Button tagAll = new Button("All Results");
        tagAll.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 20; -fx-cursor: hand;");
        
        Button tagSkills = new Button("Courses Only");
        tagSkills.setStyle("-fx-background-color: white; -fx-text-fill: #555; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 20; -fx-border-color: #ddd; -fx-border-radius: 20; -fx-cursor: hand;");
        
        Button tagMembers = new Button("Members");
        tagMembers.setStyle("-fx-background-color: white; -fx-text-fill: #555; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 20; -fx-border-color: #ddd; -fx-border-radius: 20; -fx-cursor: hand;");
        
        tagsBox.getChildren().addAll(tagAll, tagSkills, tagMembers);

        this.getChildren().addAll(topBar, tagsBox);

        // Check for Member Match
        String lowerQ = query.toLowerCase();
        boolean isSyaheem = lowerQ.contains("syaheem");
        boolean isFatimah = lowerQ.contains("fatimah");
        
        if (isSyaheem || isFatimah) {
            String memberName = isSyaheem ? "Syaheem" : "Fatimah";
            VBox bestMatchBox = new VBox(15);
            Label bestMatchLabel = new Label("Best Member Match");
            bestMatchLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-padding: 10 0 0 0;");
            
            HBox memberCard = new HBox(20);
            memberCard.setStyle("-fx-background-color: white; -fx-padding: 20 30; -fx-background-radius: 12; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 4);");
            memberCard.setAlignment(Pos.CENTER_LEFT);
            memberCard.setOnMouseClicked(e -> {
                DataStore.getInstance().currentViewingUser = memberName;
                sceneManager.switchView("OtherUserProfile");
            });
            
            StackPane avatarPane = new StackPane();
            Circle avatarBg = new Circle(35, javafx.scene.paint.Color.web(isSyaheem ? "#e0f2f1" : "#ffebee"));
            Label avatarText = new Label(memberName.substring(0,1));
            avatarText.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + (isSyaheem ? "#008080" : "#c62828") + ";");
            avatarPane.getChildren().addAll(avatarBg, avatarText);
            
            VBox memInfo = new VBox(5);
            Label mName = new Label(memberName);
            mName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #222;");
            Label mBio = new Label(isSyaheem ? "Computer Science major. Expert in Mobile Dev." : "Engineering student. Great at Math and Physics.");
            mBio.setStyle("-fx-text-fill: #666; -fx-font-size: 13px;");
            memInfo.getChildren().addAll(mName, mBio);
            
            Region memSpacer = new Region();
            HBox.setHgrow(memSpacer, Priority.ALWAYS);
            
            Label mRating = new Label("⭐ " + (isSyaheem ? "4.8" : "4.7") + " Rating");
            mRating.setStyle("-fx-background-color: #fff8e1; -fx-text-fill: #f57f17; -fx-padding: 6 12; -fx-background-radius: 15; -fx-font-weight: bold; -fx-font-size: 12px;");
            
            Button viewProfileBtn = new Button("View Profile →");
            viewProfileBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 8;");
            
            memberCard.getChildren().addAll(avatarPane, memInfo, memSpacer, mRating, viewProfileBtn);
            bestMatchBox.getChildren().addAll(bestMatchLabel, memberCard);
            this.getChildren().add(bestMatchBox);
        }

        List<SkillCard> filteredCards = new ArrayList<>();
        if (query.trim().isEmpty() || isSyaheem || isFatimah) {
            int limit = isSyaheem ? 3 : (isFatimah ? 2 : DataStore.getInstance().allAvailableCourses.size());
            int count = 0;
            for(DataStore.GlobalCourse gc : DataStore.getInstance().allAvailableCourses) {
                if (count >= limit) break;
                filteredCards.add(new SkillCard(gc.icon, gc.code, gc.title, gc.desc, gc.requests, gc.rating));
                count++;
            }
        } else {
            boolean matchKict = lowerQ.contains("kict") || lowerQ.contains("bics") || lowerQ.contains("bcs") || lowerQ.contains("bit");
            boolean matchKoe = lowerQ.contains("koe") || lowerQ.contains("engineering");
            boolean matchKenms = lowerQ.contains("kenms") || lowerQ.contains("bba") || lowerQ.contains("bacc");
            boolean matchKaed = lowerQ.contains("kaed") || lowerQ.contains("architecture");
            boolean matchKirkhs = lowerQ.contains("kirkhs") || lowerQ.contains("human science");
            boolean matchAikol = lowerQ.contains("aikol") || lowerQ.contains("law");

            for(DataStore.GlobalCourse gc : DataStore.getInstance().allAvailableCourses) {
                String cLower = gc.code.toLowerCase();
                boolean matchesKeyword = false;
                
                if (matchKict && (cLower.startsWith("info") || cLower.startsWith("csci") || cLower.startsWith("bics") || cLower.startsWith("biit"))) matchesKeyword = true;
                if (matchKoe && (cLower.startsWith("engr") || cLower.startsWith("mech") || cLower.startsWith("elec") || cLower.startsWith("mfg") || cLower.startsWith("aero") || cLower.startsWith("matl") || cLower.startsWith("cive"))) matchesKeyword = true;
                if (matchKenms && (cLower.startsWith("econ") || cLower.startsWith("acc") || cLower.startsWith("mgt") || cLower.startsWith("fin") || cLower.startsWith("mkt") || cLower.startsWith("isf"))) matchesKeyword = true;
                if (matchKaed && (cLower.startsWith("arch") || cLower.startsWith("aad") || cLower.startsWith("larc") || cLower.startsWith("urp"))) matchesKeyword = true;
                if (matchKirkhs && (cLower.startsWith("rk") || cLower.startsWith("psyc") || cLower.startsWith("soc") || cLower.startsWith("comm") || cLower.startsWith("engl"))) matchesKeyword = true;
                if (matchAikol && (cLower.startsWith("law"))) matchesKeyword = true;

                if (matchesKeyword || gc.title.toLowerCase().contains(lowerQ) || gc.code.toLowerCase().contains(lowerQ) || gc.desc.toLowerCase().contains(lowerQ)) {
                    filteredCards.add(new SkillCard(gc.icon, gc.code, gc.title, gc.desc, gc.requests, gc.rating));
                }
            }
        }

        // Results Header (Courses)
        HBox resultsHeader = new HBox();
        resultsHeader.setAlignment(Pos.CENTER_LEFT);
        resultsHeader.setPadding(new Insets(20, 0, 0, 0));
        
        Label resultsLabel = new Label("Courses found (" + filteredCards.size() + ")");
        if (!query.isEmpty()) {
            resultsLabel.setText("Courses matching '" + query + "' (" + filteredCards.size() + ")");
        }
        resultsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        
        Label sortLabel = new Label("Sort By: ");
        sortLabel.setStyle("-fx-text-fill: #555;");
        ComboBox<String> sortCombo = new ComboBox<>();
        sortCombo.getItems().addAll("Relevance", "Highest Rated", "Newest");
        sortCombo.getSelectionModel().selectFirst();
        sortCombo.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        resultsHeader.getChildren().addAll(resultsLabel, spacer2, sortLabel, sortCombo);
        this.getChildren().add(resultsHeader);

        // Grid for Cards inside a ScrollPane
        TilePane cardsGrid = new TilePane(20, 20);
        cardsGrid.setPrefColumns(4);
        
        for (SkillCard card : filteredCards) {
            card.setOnMouseClicked(e -> {
                DataStore.getInstance().currentViewingUser = isSyaheem ? "Syaheem" : (isFatimah ? "Fatimah" : "Ahmad");
                sceneManager.switchView("OtherUserProfile");
            });
            card.setStyle("-fx-cursor: hand;");
            cardsGrid.getChildren().add(card);
        }
        
        ScrollPane scrollPane = new ScrollPane(cardsGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        this.getChildren().add(scrollPane);
    }

    private void showFiltersPopup(javafx.stage.Stage stage) {
        VBox popup = new VBox(15);
        popup.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 12; -fx-min-width: 320px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 20, 0, 0, 5);");
        popup.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("Advanced Filters");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 0 0 10 0;");
        
        ComboBox<String> kulliyyahFilter = new ComboBox<>();
        kulliyyahFilter.getItems().addAll("All Kulliyyahs", "KICT", "KOE", "KENMS", "KAED");
        kulliyyahFilter.getSelectionModel().selectFirst();
        kulliyyahFilter.setMaxWidth(Double.MAX_VALUE);
        kulliyyahFilter.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 5;");

        CheckBox cb1 = new CheckBox("Only Verified Users");
        CheckBox cb2 = new CheckBox("Available for Swap Now");
        CheckBox cb3 = new CheckBox("Minimum 4-Star Rating");
        
        HBox btnBox = new HBox(10);
        btnBox.setAlignment(Pos.CENTER_RIGHT);
        btnBox.setPadding(new Insets(20, 0, 0, 0));
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #777; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 10 20;");
        cancelBtn.setOnAction(e -> ((javafx.stage.Stage)cancelBtn.getScene().getWindow()).close());
        
        Button applyBtn = new Button("Apply Filters");
        applyBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 10 20; -fx-background-radius: 8;");
        applyBtn.setOnAction(e -> {
            ((javafx.stage.Stage)applyBtn.getScene().getWindow()).close();
            ModalUtil.showSuccessAlert("Advanced filters applied to your search!");
        });
        
        btnBox.getChildren().addAll(cancelBtn, applyBtn);
        
        popup.getChildren().addAll(title, new Label("Kulliyyah:"), kulliyyahFilter, cb1, cb2, cb3, btnBox);
        ModalUtil.showModal(stage, popup);
    }
}
