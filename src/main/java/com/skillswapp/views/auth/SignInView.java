package com.skillswapp.views.auth;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.geometry.Insets;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;

public class SignInView extends HBox {
    public SignInView(SceneManager sceneManager) {
        this.setStyle("-fx-background-color: #f4f9f7;");
        this.setPrefSize(1200, 800);

        // --- Left Hero Section ---
        VBox heroSection = new VBox(20);
        heroSection.setAlignment(Pos.CENTER);
        heroSection.setPrefWidth(600);
        HBox.setHgrow(heroSection, Priority.ALWAYS);
        heroSection.setStyle("-fx-background-color: linear-gradient(to bottom right, #008080, #00b3b3);");

        Label heroLogo = new Label("🔄 SkillSwapp");
        heroLogo.setStyle("-fx-font-size: 56px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: 'Segoe UI', sans-serif;");

        Label heroSubtitle = new Label("Trade skills. Grow together.\nJoin the ultimate skill exchange community.");
        heroSubtitle.setStyle("-fx-font-size: 18px; -fx-text-fill: #e0f2f1; -fx-text-alignment: center; -fx-font-family: 'Segoe UI', sans-serif;");
        heroSubtitle.setWrapText(true);
        heroSubtitle.setMaxWidth(400);

        heroSection.getChildren().addAll(heroLogo, heroSubtitle);

        // --- Right Form Section ---
        VBox formSection = new VBox();
        formSection.setAlignment(Pos.CENTER);
        formSection.setPrefWidth(600);
        HBox.setHgrow(formSection, Priority.ALWAYS);
        formSection.setStyle("-fx-background-color: #f4f9f7;");

        // Form Card
        VBox formCard = new VBox(20);
        formCard.setAlignment(Pos.CENTER_LEFT);
        formCard.setMaxWidth(450);
        formCard.setPadding(new Insets(50));
        formCard.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15;");

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.05));
        shadow.setRadius(20);
        shadow.setOffsetY(10);
        formCard.setEffect(shadow);

        Label welcomeText = new Label("Welcome Back!");
        welcomeText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2d3748; -fx-font-family: 'Segoe UI', sans-serif;");

        Label instructionText = new Label("Please enter your details to sign in.");
        instructionText.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096; -fx-font-family: 'Segoe UI', sans-serif;");
        VBox.setMargin(instructionText, new Insets(-10, 0, 10, 0));

        VBox emailBox = new VBox(8);
        Label emailLabel = new Label("Email Address");
        emailLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #4a5568; -fx-font-family: 'Segoe UI', sans-serif;");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setStyle("-fx-font-size: 14px; -fx-padding: 12 15; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #cbd5e0; -fx-background-color: #ffffff; -fx-font-family: 'Segoe UI', sans-serif;");
        
        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                emailField.setStyle("-fx-font-size: 14px; -fx-padding: 12 15; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #008080; -fx-border-width: 2; -fx-background-color: #ffffff; -fx-font-family: 'Segoe UI', sans-serif;");
            } else {
                emailField.setStyle("-fx-font-size: 14px; -fx-padding: 12 15; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #cbd5e0; -fx-border-width: 1; -fx-background-color: #ffffff; -fx-font-family: 'Segoe UI', sans-serif;");
            }
        });
        emailBox.getChildren().addAll(emailLabel, emailField);

        VBox passwordBox = new VBox(8);
        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #4a5568; -fx-font-family: 'Segoe UI', sans-serif;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 12 15; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #cbd5e0; -fx-background-color: #ffffff; -fx-font-family: 'Segoe UI', sans-serif;");
        
        passwordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 12 15; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #008080; -fx-border-width: 2; -fx-background-color: #ffffff; -fx-font-family: 'Segoe UI', sans-serif;");
            } else {
                passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 12 15; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #cbd5e0; -fx-border-width: 1; -fx-background-color: #ffffff; -fx-font-family: 'Segoe UI', sans-serif;");
            }
        });

        HBox forgotPwdBox = new HBox();
        forgotPwdBox.setAlignment(Pos.CENTER_RIGHT);
        Hyperlink forgotPassword = new Hyperlink("Forgot Password?");
        forgotPassword.setStyle("-fx-text-fill: #008080; -fx-font-size: 13px; -fx-underline: false; -fx-font-family: 'Segoe UI', sans-serif;");
        forgotPassword.setOnAction(e -> {
            ModalUtil.showSuccessAlert("A password reset link has been sent to your email.");
        });
        forgotPwdBox.getChildren().add(forgotPassword);

        passwordBox.getChildren().addAll(passwordLabel, passwordField, forgotPwdBox);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #e53e3e; -fx-font-size: 13px; -fx-font-family: 'Segoe UI', sans-serif;");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        Button signInBtn = new Button("Sign In");
        signInBtn.setMaxWidth(Double.MAX_VALUE);
        signInBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 12 0; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-family: 'Segoe UI', sans-serif;");
        
        signInBtn.setOnMouseEntered(e -> signInBtn.setStyle("-fx-background-color: #006666; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 12 0; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-family: 'Segoe UI', sans-serif;"));
        signInBtn.setOnMouseExited(e -> signInBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 12 0; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-family: 'Segoe UI', sans-serif;"));

        ScaleTransition st = new ScaleTransition(Duration.millis(100), signInBtn);
        st.setByX(-0.02);
        st.setByY(-0.02);
        st.setAutoReverse(true);
        st.setCycleCount(2);

        signInBtn.setOnAction(e -> {
            st.playFromStart();
            st.setOnFinished(ev -> {
                if (emailField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()) {
                    errorLabel.setText("Please enter your email and password.");
                    errorLabel.setVisible(true);
                    errorLabel.setManaged(true);
                } else {
                    errorLabel.setVisible(false);
                    errorLabel.setManaged(false);
                    sceneManager.switchView("HomeDashboard");
                }
            });
        });

        HBox dividerBox = new HBox(10);
        dividerBox.setAlignment(Pos.CENTER);
        Region leftLine = new Region();
        HBox.setHgrow(leftLine, Priority.ALWAYS);
        leftLine.setStyle("-fx-background-color: #cbd5e0; -fx-max-height: 1;");
        Label orLabel = new Label("OR");
        orLabel.setStyle("-fx-text-fill: #a0aec0; -fx-font-size: 12px; -fx-font-family: 'Segoe UI', sans-serif;");
        Region rightLine = new Region();
        HBox.setHgrow(rightLine, Priority.ALWAYS);
        rightLine.setStyle("-fx-background-color: #cbd5e0; -fx-max-height: 1;");
        dividerBox.getChildren().addAll(leftLine, orLabel, rightLine);

        Button googleBtn = new Button("Sign in with Google");
        googleBtn.setMaxWidth(Double.MAX_VALUE);
        googleBtn.setStyle("-fx-background-color: white; -fx-text-fill: #4a5568; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 0; -fx-background-radius: 8; -fx-border-color: #cbd5e0; -fx-border-radius: 8; -fx-cursor: hand; -fx-font-family: 'Segoe UI', sans-serif;");
        googleBtn.setOnMouseEntered(e -> googleBtn.setStyle("-fx-background-color: #f7fafc; -fx-text-fill: #4a5568; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 0; -fx-background-radius: 8; -fx-border-color: #cbd5e0; -fx-border-radius: 8; -fx-cursor: hand; -fx-font-family: 'Segoe UI', sans-serif;"));
        googleBtn.setOnMouseExited(e -> googleBtn.setStyle("-fx-background-color: white; -fx-text-fill: #4a5568; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 0; -fx-background-radius: 8; -fx-border-color: #cbd5e0; -fx-border-radius: 8; -fx-cursor: hand; -fx-font-family: 'Segoe UI', sans-serif;"));
        googleBtn.setOnAction(e -> sceneManager.switchView("HomeDashboard"));

        Button registerBtn = new Button("Create an Account");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #008080; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 0; -fx-background-radius: 8; -fx-border-color: #008080; -fx-border-radius: 8; -fx-border-width: 2; -fx-cursor: hand; -fx-font-family: 'Segoe UI', sans-serif;");
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle("-fx-background-color: #e6f2f2; -fx-text-fill: #008080; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 0; -fx-background-radius: 8; -fx-border-color: #008080; -fx-border-radius: 8; -fx-border-width: 2; -fx-cursor: hand; -fx-font-family: 'Segoe UI', sans-serif;"));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #008080; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 0; -fx-background-radius: 8; -fx-border-color: #008080; -fx-border-radius: 8; -fx-border-width: 2; -fx-cursor: hand; -fx-font-family: 'Segoe UI', sans-serif;"));
        registerBtn.setOnAction(e -> sceneManager.switchView("Register"));

        formCard.getChildren().addAll(welcomeText, instructionText, emailBox, passwordBox, errorLabel, signInBtn, dividerBox, googleBtn, registerBtn);
        formSection.getChildren().add(formCard);

        this.getChildren().addAll(heroSection, formSection);
    }
}
