package com.skillswapp.views.trades;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;
import com.skillswapp.core.DataStore;
import com.skillswapp.core.DataStore.TradeHistory;

public class SharedResourcesView extends VBox {
    private SceneManager sceneManager;
    private TradeHistory currentSwap;
    private VBox filesContainer;
    private TextArea notesArea;
    private TextField driveLinkField;
    private TextField whatsappLinkField;

    public SharedResourcesView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.currentSwap = DataStore.getInstance().currentViewingSwap;

        this.setStyle("-fx-background-color: #f4f9f7;");
        this.setSpacing(25);
        this.setPadding(new Insets(30, 40, 30, 40));

        // In case there is no swap selected (fallback)
        if (currentSwap == null) {
            Label errorLabel = new Label("No swap selected. Please return to History.");
            errorLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
            Button backBtn = new Button("Back to History");
            backBtn.setOnAction(e -> sceneManager.switchView("TradeHistory"));
            this.getChildren().addAll(errorLabel, backBtn);
            return;
        }

        // Header Section with Back Button
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        Button backBtn = new Button("← Back to History");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #555; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        backBtn.setOnAction(e -> sceneManager.switchView("TradeHistory"));
        
        Label pageTitle = new Label("📂 Shared Swap Resources");
        pageTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #999;");
        
        topBar.getChildren().addAll(backBtn, pageTitle);

        // Success Swap Info Card
        VBox infoCard = new VBox(10);
        infoCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 15, 0, 0, 5);");
        
        Label swapSummary = new Label("Successful Swap Details");
        swapSummary.setStyle("-fx-font-size: 14px; -fx-text-fill: #008080; -fx-font-weight: bold;");
        
        Label detailLabel = new Label("You swapped '" + currentSwap.gave + "' with " + currentSwap.user + " for '" + currentSwap.got + "'.");
        detailLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        detailLabel.setWrapText(true);

        Label dateLabel = new Label("Swap Date: " + currentSwap.date);
        dateLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #777;");

        infoCard.getChildren().addAll(swapSummary, detailLabel, dateLabel);

        // Split Layout: Left Side (Files & Links), Right Side (Notes & Contact Details)
        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(25);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        // --- LEFT COLUMN: SHARED FILES & LINKS ---
        VBox leftColumn = new VBox(20);
        
        // Files Card
        VBox filesCard = new VBox(15);
        filesCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 15, 0, 0, 5);");
        
        Label filesHeader = new Label("📄 Shared Files & Materials");
        filesHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        filesContainer = new VBox(10);
        loadSharedFiles();

        HBox addFileBox = new HBox(10);
        addFileBox.setAlignment(Pos.CENTER_LEFT);
        TextField newFileField = new TextField();
        newFileField.setPromptText("Enter resource name or file...");
        newFileField.setStyle("-fx-padding: 8; -fx-background-radius: 6; -fx-border-color: #ccc; -fx-border-radius: 6;");
        HBox.setHgrow(newFileField, Priority.ALWAYS);

        Button addFileBtn = new Button("➕ Share");
        addFileBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 6; -fx-cursor: hand;");
        addFileBtn.setOnAction(e -> {
            String val = newFileField.getText().trim();
            if (!val.isEmpty()) {
                currentSwap.sharedFiles.add(val);
                DataStore.getInstance().saveToFile();
                loadSharedFiles();
                newFileField.clear();
                ModalUtil.showSuccessAlert("Resource added successfully!");
            }
        });
        addFileBox.getChildren().addAll(newFileField, addFileBtn);

        filesCard.getChildren().addAll(filesHeader, filesContainer, addFileBox);

        // Share External Links Card
        VBox linksCard = new VBox(15);
        linksCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 15, 0, 0, 5);");
        
        Label linksHeader = new Label("🔗 Shared Platform Links");
        linksHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label driveLbl = new Label("Google Drive folder for sharing materials:");
        driveLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555; -fx-font-size: 12px;");
        driveLinkField = new TextField(currentSwap.driveLink);
        driveLinkField.setStyle("-fx-padding: 8; -fx-background-radius: 6; -fx-border-color: #ccc; -fx-border-radius: 6;");

        Label waLbl = new Label("WhatsApp / Discord Chat invite link:");
        waLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555; -fx-font-size: 12px;");
        whatsappLinkField = new TextField(currentSwap.whatsappLink);
        whatsappLinkField.setStyle("-fx-padding: 8; -fx-background-radius: 6; -fx-border-color: #ccc; -fx-border-radius: 6;");

        Button saveLinksBtn = new Button("💾 Save Share Links");
        saveLinksBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 6; -fx-cursor: hand;");
        saveLinksBtn.setOnAction(e -> {
            currentSwap.driveLink = driveLinkField.getText().trim();
            currentSwap.whatsappLink = whatsappLinkField.getText().trim();
            DataStore.getInstance().saveToFile();
            ModalUtil.showSuccessAlert("Shared platform links updated successfully!");
        });

        linksCard.getChildren().addAll(linksHeader, driveLbl, driveLinkField, waLbl, whatsappLinkField, saveLinksBtn);
        leftColumn.getChildren().addAll(filesCard, linksCard);

        // --- RIGHT COLUMN: NOTES & CONTACT INFO ---
        VBox rightColumn = new VBox(20);

        // Shared Notes Card
        VBox notesCard = new VBox(12);
        notesCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 15, 0, 0, 5);");
        
        Label notesHeader = new Label("✏️ Peer-to-Peer Shared Notes");
        notesHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        notesArea = new TextArea(currentSwap.notesText);
        notesArea.setPrefRowCount(8);
        notesArea.setWrapText(true);
        notesArea.setStyle("-fx-padding: 8; -fx-background-radius: 6; -fx-border-color: #ccc; -fx-border-radius: 6; -fx-font-size: 14px;");

        Button saveNotesBtn = new Button("💾 Update Shared Notes");
        saveNotesBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 6; -fx-cursor: hand;");
        saveNotesBtn.setOnAction(e -> {
            currentSwap.notesText = notesArea.getText();
            DataStore.getInstance().saveToFile();
            ModalUtil.showSuccessAlert("Shared notes updated successfully!");
        });

        notesCard.getChildren().addAll(notesHeader, notesArea, saveNotesBtn);

        // Partner Contact Details Card
        VBox contactCard = new VBox(12);
        contactCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 15, 0, 0, 5);");
        
        Label contactHeader = new Label("📞 Peer Partner Contact Details");
        contactHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label contactName = new Label("Name: " + currentSwap.user);
        contactName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #555;");

        Label contactEmail = new Label("Email: " + currentSwap.user.toLowerCase() + "@live.iium.edu.my");
        contactEmail.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        Label contactPhone = new Label("Phone: +60 11-234 5678");
        contactPhone.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        contactCard.getChildren().addAll(contactHeader, contactName, contactEmail, contactPhone);
        rightColumn.getChildren().addAll(notesCard, contactCard);

        grid.add(leftColumn, 0, 0);
        grid.add(rightColumn, 1, 0);

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        this.getChildren().addAll(topBar, infoCard, scroll);
    }

    private void loadSharedFiles() {
        filesContainer.getChildren().clear();
        for (String file : currentSwap.sharedFiles) {
            HBox fileRow = new HBox(10);
            fileRow.setAlignment(Pos.CENTER_LEFT);
            fileRow.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10 15; -fx-background-radius: 8; -fx-border-color: #eee; -fx-border-radius: 8;");

            Label fileIcon = new Label("📄");
            Label fileName = new Label(file);
            fileName.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
            
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Button downloadBtn = new Button("📥 Download");
            downloadBtn.setStyle("-fx-background-color: #e2f4ee; -fx-text-fill: #008080; -fx-font-weight: bold; -fx-padding: 5 12; -fx-background-radius: 6; -fx-cursor: hand;");
            downloadBtn.setOnAction(e -> ModalUtil.showSuccessAlert("Started download of: " + file));

            Button removeBtn = new Button("🗑️ Remove");
            removeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #e74c3c; -fx-cursor: hand;");
            removeBtn.setOnAction(e -> {
                currentSwap.sharedFiles.remove(file);
                DataStore.getInstance().saveToFile();
                loadSharedFiles();
            });

            fileRow.getChildren().addAll(fileIcon, fileName, spacer, downloadBtn, removeBtn);
            filesContainer.getChildren().add(fileRow);
        }
        if (currentSwap.sharedFiles.isEmpty()) {
            filesContainer.getChildren().add(new Label("No shared materials uploaded yet. Use the tool below to add."));
        }
    }
}
