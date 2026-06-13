package com.skillswapp.views.profile;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;
import com.skillswapp.core.DataStore;

public class ManageProfileView extends VBox {
    private SceneManager sceneManager;
    private Label bioLabel;

    public ManageProfileView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // General Styling
        this.setStyle("-fx-background-color: #f4f9f7;");
        this.setSpacing(25);
        this.setPadding(new Insets(30, 40, 30, 40));
        this.setAlignment(Pos.TOP_CENTER);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("👤 My Profile");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #222;");
        header.getChildren().add(title);

        VBox card = new VBox(20);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 40; -fx-max-width: 600px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 20, 0, 0, 5); -fx-alignment: center;");
        
        // Avatar Section
        VBox avatarBox = new VBox(10);
        avatarBox.setAlignment(Pos.CENTER);
        ImageView av = null;
        try {
            String avatarPath = DataStore.getInstance().myAvatarPath;
            if (avatarPath != null && !avatarPath.isEmpty()) {
                try {
                    av = new ImageView(new Image(avatarPath));
                } catch (Exception ex2) {
                    av = new ImageView(new Image(getClass().getResourceAsStream("/images/avatar.png")));
                }
            } else {
                av = new ImageView(new Image(getClass().getResourceAsStream("/images/avatar.png")));
            }
            av.setFitWidth(120); av.setFitHeight(120); av.setPreserveRatio(true);
            Rectangle cl = new Rectangle(120, 120); cl.setArcWidth(120); cl.setArcHeight(120); av.setClip(cl);
        } catch (Exception ex) {}
        javafx.scene.Node avatar = av != null ? av : new Label("👤");
        if (av == null) avatar.setStyle("-fx-font-size: 80px; -fx-text-fill: #ccc;");
        
        Button editPicBtn = new Button("📷 Edit Picture");
        editPicBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #555; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 20; -fx-cursor: hand; -fx-font-size: 12px;");
        editPicBtn.setOnMouseEntered(e -> editPicBtn.setStyle(editPicBtn.getStyle() + "-fx-background-color: #e0e0e0;"));
        editPicBtn.setOnMouseExited(e -> editPicBtn.setStyle(editPicBtn.getStyle().replace("-fx-background-color: #e0e0e0;", "-fx-background-color: #f0f0f0;")));
        editPicBtn.setOnAction(e -> showEditPicturePopup());
        
        avatarBox.getChildren().addAll(avatar, editPicBtn);

        // User Details Section
        VBox detailsBox = new VBox(5);
        detailsBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("John Doe");
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        Label emailLabel = new Label(DataStore.getInstance().myEmail);
        emailLabel.setStyle("-fx-text-fill: #777; -fx-font-size: 14px;");
        
        Label kullLabel = new Label(DataStore.getInstance().myKulliyyah + " | Matric: " + DataStore.getInstance().myMatric);
        kullLabel.setStyle("-fx-text-fill: #008080; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 5 0 0 0;");
        
        detailsBox.getChildren().addAll(nameLabel, emailLabel, kullLabel);

        // Bio Section
        VBox bioBox = new VBox(10);
        bioBox.setAlignment(Pos.CENTER);
        bioBox.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 20; -fx-background-radius: 8; -fx-border-color: #eee; -fx-border-radius: 8; -fx-border-width: 1; -fx-min-width: 400px;");
        
        bioLabel = new Label(DataStore.getInstance().myBio);
        bioLabel.setWrapText(true);
        bioLabel.setStyle("-fx-text-fill: #555; -fx-font-size: 14px; -fx-text-alignment: center;");
        
        bioBox.getChildren().add(bioLabel);

        // Stats row
        HBox statsRow = new HBox(40); 
        statsRow.setAlignment(Pos.CENTER);
        statsRow.setPadding(new Insets(10, 0, 15, 0));
        
        int totalSwaps = DataStore.getInstance().tradeHistory.size();
        
        statsRow.getChildren().addAll(
            makeStat(String.valueOf(DataStore.getInstance().myCourses.size()), "Courses Offered"), 
            makeStat(String.valueOf(totalSwaps), "Completed Swaps"), 
            makeStat("4.7", "Avg Rating")
        );

        // Edit Bio Button
        Button editBioBtn = new Button("✏️ Edit Bio");
        String bioStyle = "-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 14px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 5);";
        editBioBtn.setStyle(bioStyle);
        editBioBtn.setOnMouseEntered(e -> editBioBtn.setStyle(bioStyle.replace("#008080", "#006666")));
        editBioBtn.setOnMouseExited(e -> editBioBtn.setStyle(bioStyle));
        editBioBtn.setMaxWidth(Double.MAX_VALUE);
        editBioBtn.setOnAction(e -> showEditBioPopup());

        card.getChildren().addAll(avatarBox, detailsBox, bioBox, statsRow, editBioBtn);
        this.getChildren().addAll(header, card);
    }

    private VBox makeStat(String value, String label) {
        VBox v = new VBox(2); 
        v.setAlignment(Pos.CENTER);
        Label val = new Label(value);
        val.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #008080;");
        Label lab = new Label(label);
        lab.setStyle("-fx-font-size: 12px; -fx-text-fill: #777; -fx-font-weight: bold;");
        v.getChildren().addAll(val, lab);
        return v;
    }

    private void showEditPicturePopup() {
        VBox p = new VBox(20);
        p.setStyle("-fx-background-color: white; -fx-padding: 35; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 25, 0, 0, 10); -fx-min-width: 350px;");
        p.setAlignment(Pos.CENTER);
        
        Label t = new Label("Upload New Picture"); 
        t.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        Label preview = new Label("📷"); 
        preview.setStyle("-fx-font-size: 64px; -fx-padding: 20; -fx-background-color: #f0f0f0; -fx-background-radius: 60; -fx-min-width: 120px; -fx-min-height: 120px; -fx-alignment: center;");
        
        final java.io.File[] selectedFileWrapper = new java.io.File[1];
        
        Button uploadBtn = new Button("📂 Choose File...");
        uploadBtn.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10 20; -fx-cursor: hand; -fx-text-fill: #555; -fx-font-weight: bold;");
        uploadBtn.setOnAction(e -> {
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Choose Profile Picture");
            fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            java.io.File selectedFile = fileChooser.showOpenDialog(((javafx.stage.Stage)uploadBtn.getScene().getWindow()));
            if (selectedFile != null) {
                selectedFileWrapper[0] = selectedFile;
                uploadBtn.setText("✅ " + selectedFile.getName());
                uploadBtn.setStyle("-fx-background-color: #e8f5e9; -fx-border-color: #4CAF50; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10 20; -fx-cursor: hand; -fx-text-fill: #2E7D32; -fx-font-weight: bold;");
                try {
                    ImageView miniPreview = new ImageView(new Image(selectedFile.toURI().toString()));
                    miniPreview.setFitWidth(100);
                    miniPreview.setFitHeight(100);
                    miniPreview.setPreserveRatio(true);
                    Rectangle clip = new Rectangle(100, 100);
                    clip.setArcWidth(100);
                    clip.setArcHeight(100);
                    miniPreview.setClip(clip);
                    preview.setGraphic(miniPreview);
                    preview.setText("");
                } catch (Exception ex) {
                    preview.setText("📷");
                    preview.setGraphic(null);
                }
            }
        });
        
        HBox btns = new HBox(15);
        btns.setAlignment(Pos.CENTER);
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> ((javafx.stage.Stage)cancelBtn.getScene().getWindow()).close());
        
        Button saveBtn = new Button("Save Picture"); 
        saveBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;");
        saveBtn.setOnAction(e -> { 
            if (selectedFileWrapper[0] != null) {
                try {
                    java.io.File destFile = new java.io.File("user_avatar.png");
                    java.nio.file.Files.copy(
                        selectedFileWrapper[0].toPath(),
                        destFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                    );
                    DataStore.getInstance().myAvatarPath = destFile.toURI().toString();
                    DataStore.getInstance().saveToFile();
                    
                    ((javafx.stage.Stage)saveBtn.getScene().getWindow()).close(); 
                    ModalUtil.showSuccessAlert("Profile picture updated successfully!"); 
                    sceneManager.switchView("ManageProfile");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ModalUtil.showSuccessAlert("Error saving file: " + ex.getMessage());
                }
            } else {
                ((javafx.stage.Stage)saveBtn.getScene().getWindow()).close(); 
            }
        });
        
        btns.getChildren().addAll(cancelBtn, saveBtn);
        p.getChildren().addAll(t, preview, uploadBtn, btns);
        ModalUtil.showModal(sceneManager.getStage(), p);
    }

    private void showEditBioPopup() {
        VBox p = new VBox(15);
        p.setStyle("-fx-background-color: white; -fx-padding: 35; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 25, 0, 0, 10); -fx-min-width: 400px;");
        p.setAlignment(Pos.CENTER_LEFT);
        
        Label t = new Label("✏️ Edit Bio"); 
        t.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-padding: 0 0 10 0;");
        
        TextArea bio = new TextArea(DataStore.getInstance().myBio);
        bio.setPrefRowCount(4);
        bio.setStyle("-fx-padding: 10; -fx-background-radius: 6; -fx-border-color: #ccc; -fx-border-radius: 6; -fx-font-size: 14px; -fx-focus-color: #008080; -fx-faint-focus-color: transparent;");
        
        Label charCount = new Label(bio.getText().length() + "/200 characters");
        charCount.setStyle("-fx-text-fill: #999; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        bio.textProperty().addListener((o,ov,nv) -> {
            if (nv.length() > 200) {
                bio.setText(ov);
                charCount.setText("200/200 characters (Max reached)");
                charCount.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px; -fx-font-weight: bold;");
            } else {
                charCount.setText(nv.length() + "/200 characters");
                charCount.setStyle("-fx-text-fill: #999; -fx-font-size: 12px; -fx-font-weight: bold;");
            }
        });
        
        HBox charCountBox = new HBox();
        charCountBox.setAlignment(Pos.CENTER_RIGHT);
        charCountBox.getChildren().add(charCount);

        HBox btns = new HBox(15);
        btns.setAlignment(Pos.CENTER_RIGHT);
        btns.setPadding(new Insets(10, 0, 0, 0));
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> ((javafx.stage.Stage)cancelBtn.getScene().getWindow()).close());
        
        Button saveBtn = new Button("Update Bio"); 
        String saveStyle = "-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;";
        saveBtn.setStyle(saveStyle);
        saveBtn.setOnMouseEntered(e -> saveBtn.setStyle(saveStyle.replace("#008080", "#006666")));
        saveBtn.setOnMouseExited(e -> saveBtn.setStyle(saveStyle));
        saveBtn.setOnAction(e -> { 
            ((javafx.stage.Stage)saveBtn.getScene().getWindow()).close(); 
            DataStore.getInstance().myBio = bio.getText();
            DataStore.getInstance().saveToFile();
            bioLabel.setText(DataStore.getInstance().myBio);
            ModalUtil.showSuccessAlert("Bio updated successfully!"); 
        });
        
        btns.getChildren().addAll(cancelBtn, saveBtn);
        p.getChildren().addAll(t, bio, charCountBox, btns);
        ModalUtil.showModal(sceneManager.getStage(), p);
    }
}
