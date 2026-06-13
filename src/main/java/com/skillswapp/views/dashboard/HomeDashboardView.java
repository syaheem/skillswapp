package com.skillswapp.views.dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;
import com.skillswapp.components.SkillCard;
import com.skillswapp.components.CommunityCard;
import com.skillswapp.components.SwapperRow;

public class HomeDashboardView extends VBox {
    private SceneManager sceneManager;
    private TilePane cardsGrid;
    private List<SkillCard> allCards = new ArrayList<>();

    public HomeDashboardView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // General Styling
        this.setStyle("-fx-background-color: #f4f9f7;");
        this.setSpacing(25);
        this.setPadding(new Insets(30, 40, 30, 40));
        
        // 1. Top Bar
        HBox topBar = createTopBar();
        
        // 3. Main Content Split
        HBox contentBox = new HBox(40);
        
        // Left Side: Skill Cards
        VBox leftSide = new VBox(20);
        HBox.setHgrow(leftSide, Priority.ALWAYS);
        
        HBox skillsHeader = new HBox();
        skillsHeader.setAlignment(Pos.CENTER_LEFT);
        Label skillsTitle = new Label("🔥 Top Courses for Swap");
        skillsTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #222;");
        
        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        
        Hyperlink showAllLink = new Hyperlink("Show all →");
        showAllLink.setStyle("-fx-text-fill: #008080; -fx-font-weight: bold; -fx-font-size: 14px; -fx-underline: false;");
        showAllLink.setOnAction(e -> sceneManager.switchView("SearchResults"));
        skillsHeader.getChildren().addAll(skillsTitle, headerSpacer, showAllLink);
        
        cardsGrid = new TilePane(20, 20);
        cardsGrid.setPrefColumns(3);
        
        // Initialize all cards from DataStore
        for(com.skillswapp.core.DataStore.GlobalCourse gc : com.skillswapp.core.DataStore.getInstance().allAvailableCourses) {
            allCards.add(new SkillCard(gc.icon, gc.code, gc.title, gc.desc, gc.requests, gc.rating, gc.ownerName));
        }
        
        // Add only the first 9 to the grid for the dashboard so it's not overwhelmed
        cardsGrid.getChildren().addAll(allCards.subList(0, 9));
        leftSide.getChildren().addAll(skillsHeader, cardsGrid);
        
        // Right Side: Communities and Swappers
        VBox rightSide = new VBox(35);
        rightSide.setMinWidth(360);
        rightSide.setMaxWidth(360);
        
        VBox communitiesBox = new VBox(12);
        Label commTitle = new Label("🏛 Suggested Communities");
        commTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #222;");
        communitiesBox.getChildren().addAll(
            commTitle,
            new CommunityCard("💻", "Bachelor of Computer Science (BCS)", "128 members • KICT"),
            new CommunityCard("📊", "Bachelor of Business Administration (BBA)", "85 members • KENMS"),
            new CommunityCard("🤖", "Bachelor of Information Technology (BIT)", "203 members • KICT"),
            new CommunityCard("🎨", "Bachelor of Architecture (B.ARCH)", "67 members • KAED"),
            new CommunityCard("⚙", "Bachelor of Engineering (B.ENG)", "156 members • KOE")
        );
        
        VBox swappersBox = new VBox(12);
        Label swapperTitle = new Label("⭐ Top Skill Swappers");
        swapperTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #222;");
        swappersBox.getChildren().addAll(
            swapperTitle,
            new SwapperRow("👨", "Syaheem (BCS)", 4.8, () -> { com.skillswapp.core.DataStore.getInstance().currentViewingUser = "Syaheem (BCS)"; sceneManager.switchView("OtherUserProfile"); }),
            new SwapperRow("👩", "Fatimah (BBA)", 4.7, () -> { com.skillswapp.core.DataStore.getInstance().currentViewingUser = "Fatimah (BBA)"; sceneManager.switchView("OtherUserProfile"); }),
            new SwapperRow("👨", "Ahmad Rafiq (MECH)", 4.5, () -> { com.skillswapp.core.DataStore.getInstance().currentViewingUser = "Ahmad Rafiq (MECH)"; sceneManager.switchView("OtherUserProfile"); }),
            new SwapperRow("👩", "Nurul Ain (BIT)", 4.9, () -> { com.skillswapp.core.DataStore.getInstance().currentViewingUser = "Nurul Ain (BIT)"; sceneManager.switchView("OtherUserProfile"); }),
            new SwapperRow("👨", "Haziq (BARCH)", 4.3, () -> { com.skillswapp.core.DataStore.getInstance().currentViewingUser = "Haziq (BARCH)"; sceneManager.switchView("OtherUserProfile"); })
        );
        
        rightSide.getChildren().addAll(communitiesBox, swappersBox);
        
        contentBox.getChildren().addAll(leftSide, rightSide);
        
        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        // 2. Category Tags - Must be created after cardsGrid is initialized
        HBox tagsBox = createTagsBox();

        this.getChildren().addAll(topBar, tagsBox, scrollPane);
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        // Search Bar
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Search courses (e.g. INFO 3305), communities...");
        searchField.setStyle("-fx-padding: 10 20; -fx-background-radius: 20; -fx-border-color: #ddd; -fx-border-radius: 20; -fx-background-color: white; -fx-font-size: 14px;");
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchField.setOnAction(e -> {
            com.skillswapp.core.DataStore.getInstance().currentSearchQuery = searchField.getText();
            sceneManager.switchView("SearchResults");
        });
        
        // Filter Button
        Button filtersBtn = new Button("⚙ Filters");
        filtersBtn.setStyle("-fx-padding: 10 20; -fx-background-radius: 20; -fx-border-color: #ddd; -fx-border-radius: 20; -fx-background-color: white; -fx-font-size: 14px; -fx-cursor: hand; -fx-font-weight: bold; -fx-text-fill: #555;");
        filtersBtn.setOnMouseEntered(e -> filtersBtn.setStyle(filtersBtn.getStyle() + "-fx-background-color: #f0f0f0;"));
        filtersBtn.setOnMouseExited(e -> filtersBtn.setStyle(filtersBtn.getStyle().replace("-fx-background-color: #f0f0f0;", "-fx-background-color: white;")));
        filtersBtn.setOnAction(e -> showFiltersPopup(sceneManager.getStage()));
        
        Region spacer = new Region();
        spacer.setMinWidth(40);
        
        // Profile Icon
        Label profileIcon = new Label("👤");
        profileIcon.setStyle("-fx-font-size: 22px; -fx-background-color: white; -fx-background-radius: 50; -fx-padding: 8 12; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        profileIcon.setOnMouseEntered(e -> profileIcon.setStyle(profileIcon.getStyle() + "-fx-background-color: #e0e0e0;"));
        profileIcon.setOnMouseExited(e -> profileIcon.setStyle(profileIcon.getStyle().replace("-fx-background-color: #e0e0e0;", "-fx-background-color: white;")));
        profileIcon.setOnMouseClicked(e -> sceneManager.switchView("ManageProfile"));
        
        // Notification Bell
        Label bellIcon = new Label("🔔");
        bellIcon.setStyle("-fx-font-size: 22px; -fx-background-color: white; -fx-background-radius: 50; -fx-padding: 8 12; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        bellIcon.setOnMouseEntered(e -> bellIcon.setStyle(bellIcon.getStyle() + "-fx-background-color: #e0e0e0;"));
        bellIcon.setOnMouseExited(e -> bellIcon.setStyle(bellIcon.getStyle().replace("-fx-background-color: #e0e0e0;", "-fx-background-color: white;")));
        
        ContextMenu notificationsMenu = new ContextMenu();
        notificationsMenu.setStyle("-fx-base: white; -fx-padding: 5;");
        
        MenuItem notif1 = new MenuItem("✅ Swap with Syaheem complete! Click to rate.");
        notif1.setOnAction(e -> showRateUserPopup(sceneManager.getStage(), "Syaheem"));
        MenuItem notif2 = new MenuItem("📥 Ahmad sent you a swap request.");
        notif2.setOnAction(e -> sceneManager.switchView("IncomingRequests"));
        MenuItem notif3 = new MenuItem("📤 Your request to Fatimah was accepted!");
        notif3.setOnAction(e -> sceneManager.switchView("TradeHistory"));
        
        notificationsMenu.getItems().addAll(notif1, notif2, notif3);
        bellIcon.setOnMouseClicked(e -> notificationsMenu.show(bellIcon, javafx.geometry.Side.BOTTOM, 0, 5));
        
        topBar.getChildren().addAll(searchField, filtersBtn, spacer, profileIcon, bellIcon);
        return topBar;
    }

    private HBox createTagsBox() {
        HBox tagsBox = new HBox(15);
        String tagBaseStyle = "-fx-padding: 8 16; -fx-background-radius: 20; -fx-cursor: hand; -fx-font-size: 13px; -fx-font-weight: bold; ";
        String tagInactive = tagBaseStyle + "-fx-background-color: white; -fx-text-fill: #555; -fx-border-color: #ccc; -fx-border-radius: 20;";
        String tagActive = tagBaseStyle + "-fx-background-color: #008080; -fx-text-fill: white; -fx-border-color: #008080; -fx-border-radius: 20;";

        Button[] tags = {
            new Button("All"), new Button("Kulliyyah: KOE"), 
            new Button("Kulliyyah: KICT"), new Button("Kulliyyah: KENMS"), 
            new Button("Kulliyyah: KAED")
        };
        
        tags[0].setStyle(tagActive);
        for (int i = 1; i < tags.length; i++) {
            tags[i].setStyle(tagInactive);
        }

        for (Button tag : tags) {
            tag.setOnAction(e -> {
                for (Button t : tags) t.setStyle(tagInactive);
                tag.setStyle(tagActive);
                
                // Filter the grid based on tag
                filterGrid(tag.getText());
            });
            tagsBox.getChildren().add(tag);
        }
        return tagsBox;
    }

    private void filterGrid(String filterText) {
        cardsGrid.getChildren().clear();
        for (SkillCard card : allCards) {
            boolean matches = false;
            String cardTitle = card.courseCode.toUpperCase();
            
            if (filterText.equals("All")) {
                matches = true;
            } else if (filterText.contains("KICT") && (cardTitle.startsWith("INFO") || cardTitle.startsWith("CSCI") || cardTitle.startsWith("BICS") || cardTitle.startsWith("BIIT"))) {
                matches = true;
            } else if (filterText.contains("KOE") && (cardTitle.startsWith("ENGR") || cardTitle.startsWith("MECH") || cardTitle.startsWith("ELEC") || cardTitle.startsWith("MFG") || cardTitle.startsWith("AERO") || cardTitle.startsWith("MATL") || cardTitle.startsWith("CIVE"))) {
                matches = true;
            } else if (filterText.contains("KENMS") && (cardTitle.startsWith("ECON") || cardTitle.startsWith("ACC") || cardTitle.startsWith("MGT") || cardTitle.startsWith("FIN") || cardTitle.startsWith("MKT") || cardTitle.startsWith("ISF"))) {
                matches = true;
            } else if (filterText.contains("KAED") && (cardTitle.startsWith("ARCH") || cardTitle.startsWith("AAD") || cardTitle.startsWith("LARC") || cardTitle.startsWith("URP"))) {
                matches = true;
            }

            if (matches) {
                cardsGrid.getChildren().add(card);
            }
        }
    }

    private void showFiltersPopup(javafx.stage.Stage stage) {
        VBox popup = new VBox(15);
        popup.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 20, 0, 0, 10);");
        popup.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("⚙ Filter Courses");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-padding: 0 0 10 0;");
        
        ComboBox<String> kulliyyahFilter = new ComboBox<>();
        kulliyyahFilter.getItems().addAll("All Kulliyyahs", "KICT", "KOE", "KENMS", "KAED");
        kulliyyahFilter.getSelectionModel().selectFirst();
        kulliyyahFilter.setMaxWidth(Double.MAX_VALUE);
        kulliyyahFilter.setStyle("-fx-padding: 5; -fx-font-size: 14px;");

        CheckBox cb1 = new CheckBox("Available for Swap Now");
        CheckBox cb2 = new CheckBox("Minimum 4-Star Rating");
        CheckBox cb3 = new CheckBox("Verified Tutors Only");
        
        Button applyBtn = new Button("Apply Filters");
        applyBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 14px;");
        applyBtn.setMaxWidth(Double.MAX_VALUE);
        applyBtn.setOnAction(e -> {
            ((javafx.stage.Stage)applyBtn.getScene().getWindow()).close();
            ModalUtil.showSuccessAlert("Filters successfully applied to Dashboard.");
        });
        
        popup.getChildren().addAll(title, new Label("Select Kulliyyah:"), kulliyyahFilter, cb1, cb2, cb3, new Region(), applyBtn);
        ModalUtil.showModal(stage, popup);
    }

    private void showRateUserPopup(javafx.stage.Stage stage, String userToRate) {
        VBox popup = new VBox(15);
        popup.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 10; -fx-min-width: 300px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 20, 0, 0, 10);");
        popup.setAlignment(Pos.CENTER);
        
        Label title = new Label("⭐ Rate " + userToRate);
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        HBox starsBox = new HBox(10);
        starsBox.setAlignment(Pos.CENTER);
        Label[] stars = new Label[5];
        for(int i=0; i<5; i++) {
            final int idx = i;
            stars[i] = new Label("☆");
            stars[i].setStyle("-fx-font-size: 40px; -fx-text-fill: #f5a623; -fx-cursor: hand;");
            stars[i].setOnMouseClicked(e -> {
                for(int j=0; j<=idx; j++) stars[j].setText("★");
                for(int j=idx+1; j<5; j++) stars[j].setText("☆");
            });
            starsBox.getChildren().add(stars[i]);
        }
        
        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Write a public review...");
        commentArea.setPrefRowCount(3);
        commentArea.setStyle("-fx-font-size: 14px;");

        Button submitBtn = new Button("Submit Review");
        submitBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 14px;");
        submitBtn.setMaxWidth(Double.MAX_VALUE);
        submitBtn.setOnAction(evt -> {
            ((javafx.stage.Stage)submitBtn.getScene().getWindow()).close();
            ModalUtil.showSuccessAlert("Thank you! Your review for " + userToRate + " has been submitted.");
        });
        
        popup.getChildren().addAll(title, starsBox, commentArea, submitBtn);
        ModalUtil.showModal(stage, popup);
    }
}
