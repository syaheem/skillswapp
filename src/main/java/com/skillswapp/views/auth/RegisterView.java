package com.skillswapp.views.auth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;

public class RegisterView extends StackPane {
    public RegisterView(SceneManager sceneManager) {
        // Full screen background gradient
        this.setStyle("-fx-background-color: linear-gradient(to bottom right, #005a5a, #e2f4ee);");

        // The main auth card container
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(420);
        card.setMaxHeight(Region.USE_PREF_SIZE);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 40; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 25, 0, 0, 10);");

        // Header / Logo Area
        VBox headerBox = new VBox(5);
        headerBox.setAlignment(Pos.CENTER);
        
        Label logo = new Label("🔄 SkillSwapp");
        logo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #008080;");
        
        Label title = new Label("Create an Account");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-padding: 10 0 0 0;");
        
        Label subtitle = new Label("Join the ultimate skill exchange community");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #777;");
        
        headerBox.getChildren().addAll(logo, title, subtitle);
        VBox.setMargin(headerBox, new Insets(0, 0, 10, 0));

        // Form Fields Styling
        String fieldStyle = "-fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-color: #fafafa; -fx-font-size: 14px;";
        
        TextField nameField = new TextField();
        nameField.setPromptText("👤 Full Name");
        nameField.setStyle(fieldStyle);

        TextField emailField = new TextField();
        emailField.setPromptText("📧 Email Address");
        emailField.setStyle(fieldStyle);

        // Matric & Kulliyyah side by side
        HBox matricKulliyyahBox = new HBox(10);
        
        TextField matricField = new TextField();
        matricField.setPromptText("🎓 Matric Number");
        matricField.setStyle(fieldStyle);
        HBox.setHgrow(matricField, Priority.ALWAYS);

        ComboBox<String> kulliyyahCombo = new ComboBox<>();
        kulliyyahCombo.getItems().addAll("KICT", "KOE", "KENMS", "KAED", "KIRKHS", "KOS", "KOPH");
        kulliyyahCombo.setPromptText("🏛 Kulliyyah");
        kulliyyahCombo.setStyle(fieldStyle);
        kulliyyahCombo.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(kulliyyahCombo, Priority.ALWAYS);
        
        matricKulliyyahBox.getChildren().addAll(matricField, kulliyyahCombo);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("🔒 Password");
        passwordField.setStyle(fieldStyle);

        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("🔒 Confirm Password");
        confirmField.setStyle(fieldStyle);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 5; -fx-background-color: #ffebee; -fx-background-radius: 5;");
        errorLabel.setMaxWidth(Double.MAX_VALUE);
        errorLabel.setAlignment(Pos.CENTER);
        errorLabel.setVisible(false);
        errorLabel.setManaged(false); // So it doesn't take up space when hidden

        Button registerBtn = new Button("Sign Up");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        String btnDefault = "-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 15px;";
        String btnHover = "-fx-background-color: #006666; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 15px;";
        registerBtn.setStyle(btnDefault);
        
        // Hover effects
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle(btnHover));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle(btnDefault));

        // Form Validation & Registration
        registerBtn.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()
                || matricField.getText().trim().isEmpty()
                || passwordField.getText().trim().isEmpty() || confirmField.getText().trim().isEmpty()) {
                showError(errorLabel, "⚠ Please fill in all required fields.");
            } else if (kulliyyahCombo.getValue() == null) {
                showError(errorLabel, "⚠ Please select your Kulliyyah.");
            } else if (!passwordField.getText().equals(confirmField.getText())) {
                showError(errorLabel, "⚠ Passwords do not match.");
            } else {
                errorLabel.setVisible(false);
                errorLabel.setManaged(false);
                ModalUtil.showSuccessAlert("Account created successfully!\nWelcome to SkillSwapp, " + nameField.getText().trim() + ".");
                sceneManager.switchView("HomeDashboard");
            }
        });

        // Bottom login link
        HBox loginBox = new HBox(5);
        loginBox.setAlignment(Pos.CENTER);
        Label haveAccountLabel = new Label("Already have an account?");
        haveAccountLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 13px;");
        
        Hyperlink loginLink = new Hyperlink("Sign In");
        loginLink.setStyle("-fx-text-fill: #008080; -fx-font-weight: bold; -fx-font-size: 13px; -fx-underline: false;");
        loginLink.setOnAction(e -> sceneManager.switchView("SignIn"));
        loginBox.getChildren().addAll(haveAccountLabel, loginLink);

        // Add all components to card
        card.getChildren().addAll(
            headerBox, 
            nameField, 
            emailField, 
            matricKulliyyahBox, 
            passwordField, 
            confirmField, 
            errorLabel, 
            registerBtn, 
            loginBox
        );

        this.getChildren().add(card);
    }
    
    private void showError(Label errorLabel, String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
}
