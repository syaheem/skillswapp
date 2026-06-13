package com.skillswapp.views.skills;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.skillswapp.core.SceneManager;
import com.skillswapp.core.ModalUtil;
import com.skillswapp.components.SkillCard;
import com.skillswapp.core.DataStore;
import com.skillswapp.core.DataStore.Course;

public class ManageSkillsView extends VBox {
    private SceneManager sceneManager;
    private TilePane skillsGrid;

    public ManageSkillsView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // General Styling
        this.setStyle("-fx-background-color: #f4f9f7;");
        this.setSpacing(25);
        this.setPadding(new Insets(30, 40, 30, 40));

        // Header Section
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        
        VBox titleBox = new VBox(5);
        Label title = new Label("📚 Manage My Courses");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #222;");
        
        Label subtitle = new Label("Courses you are actively offering for swap");
        subtitle.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");
        titleBox.getChildren().addAll(title, subtitle);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addSkillBtn = new Button("➕ Add New Course");
        addSkillBtn.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 24; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 14px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 5);");
        addSkillBtn.setOnMouseEntered(e -> addSkillBtn.setStyle(addSkillBtn.getStyle().replace("-fx-background-color: #008080;", "-fx-background-color: #006666;")));
        addSkillBtn.setOnMouseExited(e -> addSkillBtn.setStyle(addSkillBtn.getStyle().replace("-fx-background-color: #006666;", "-fx-background-color: #008080;")));
        addSkillBtn.setOnAction(e -> showAddSkillPopup());

        header.getChildren().addAll(titleBox, spacer, addSkillBtn);

        // Grid Section
        skillsGrid = new TilePane(25, 25);
        skillsGrid.setPrefColumns(4);
        skillsGrid.setAlignment(Pos.TOP_LEFT);
        
        loadCourses();

        ScrollPane scrollPane = new ScrollPane(skillsGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        this.getChildren().addAll(header, scrollPane);
    }

    private void loadCourses() {
        skillsGrid.getChildren().clear();
        for (Course c : DataStore.getInstance().myCourses) {
            addCourseCard(c);
        }
    }

    private void addCourseCard(Course course) {
        SkillCard card = new SkillCard(course.icon, course.code, course.fullName, course.desc, 0, 0);
        
        // Override SkillCard's default request button area with Edit/Delete buttons
        // To do this nicely without changing SkillCard too much, we remove the last child (Request Button)
        if (card.getChildren().size() > 0) {
            card.getChildren().remove(card.getChildren().size() - 1);
        }
        
        HBox actionBtns = new HBox(15);
        actionBtns.setAlignment(Pos.CENTER);
        
        Button editBtn = new Button("✏️ Edit");
        String editStyle = "-fx-background-color: #f5a623; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 13px;";
        editBtn.setStyle(editStyle);
        editBtn.setOnMouseEntered(e -> editBtn.setStyle(editStyle + "-fx-opacity: 0.8;"));
        editBtn.setOnMouseExited(e -> editBtn.setStyle(editStyle));
        editBtn.setOnAction(e -> showEditSkillPopup(course));
        
        Button deleteBtn = new Button("🗑️ Delete");
        String deleteStyle = "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 13px;";
        deleteBtn.setStyle(deleteStyle);
        deleteBtn.setOnMouseEntered(e -> deleteBtn.setStyle(deleteStyle + "-fx-opacity: 0.8;"));
        deleteBtn.setOnMouseExited(e -> deleteBtn.setStyle(deleteStyle));
        deleteBtn.setOnAction(e -> showDeletePopup(card, course));
        
        HBox.setHgrow(editBtn, Priority.ALWAYS);
        HBox.setHgrow(deleteBtn, Priority.ALWAYS);
        editBtn.setMaxWidth(Double.MAX_VALUE);
        deleteBtn.setMaxWidth(Double.MAX_VALUE);
        
        actionBtns.getChildren().addAll(editBtn, deleteBtn);
        card.getChildren().add(actionBtns);
        
        skillsGrid.getChildren().add(card);
    }

    private void showDeletePopup(SkillCard card, Course course) {
        VBox popup = new VBox(20);
        popup.setStyle("-fx-background-color: white; -fx-padding: 35; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 25, 0, 0, 10); -fx-min-width: 350px;");
        popup.setAlignment(Pos.CENTER);
        
        Label title = new Label("Delete Course?");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        Label message = new Label("Are you sure you want to remove '" + course.code + "' from your offerings? This cannot be undone.");
        message.setStyle("-fx-font-size: 14px; -fx-text-fill: #666; -fx-text-alignment: center;");
        message.setWrapText(true);
        
        HBox btns = new HBox(15);
        btns.setAlignment(Pos.CENTER);
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> ((javafx.stage.Stage)cancelBtn.getScene().getWindow()).close());
        
        Button confirmBtn = new Button("Yes, Delete");
        confirmBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;");
        confirmBtn.setOnAction(e -> {
            ((javafx.stage.Stage)confirmBtn.getScene().getWindow()).close();
            DataStore.getInstance().myCourses.remove(course);
            DataStore.getInstance().saveToFile();
            skillsGrid.getChildren().remove(card);
            ModalUtil.showSuccessAlert("Course deleted successfully!");
        });
        
        btns.getChildren().addAll(cancelBtn, confirmBtn);
        popup.getChildren().addAll(title, message, btns);
        ModalUtil.showModal(sceneManager.getStage(), popup);
    }

    private void showAddSkillPopup() {
        VBox popup = new VBox(15);
        popup.setStyle("-fx-background-color: white; -fx-padding: 35; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 25, 0, 0, 10); -fx-min-width: 400px;");
        popup.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("➕ Add New Course");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #222; -fx-padding: 0 0 10 0;");
        
        String fieldStyle = "-fx-padding: 10; -fx-background-radius: 6; -fx-border-color: #ccc; -fx-border-radius: 6; -fx-font-size: 14px;";
        
        Label nameLbl = new Label("Course Code");
        nameLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextField skillName = new TextField();
        skillName.setPromptText("e.g. INFO 3305");
        skillName.setStyle(fieldStyle);
        
        Label fullNameLbl = new Label("Full Name");
        fullNameLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextField skillFullName = new TextField();
        skillFullName.setPromptText("e.g. Web Development");
        skillFullName.setStyle(fieldStyle);
        
        Label descLbl = new Label("Description");
        descLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextArea skillDesc = new TextArea();
        skillDesc.setPromptText("Describe what you can teach...");
        skillDesc.setPrefRowCount(3);
        skillDesc.setStyle(fieldStyle);

        Label levelLbl = new Label("Proficiency Level");
        levelLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        ComboBox<String> levelCombo = new ComboBox<>();
        levelCombo.getItems().addAll("Beginner", "Intermediate", "Advanced");
        levelCombo.setPromptText("Select level");
        levelCombo.setMaxWidth(Double.MAX_VALUE);
        levelCombo.setStyle(fieldStyle);

        Button saveBtn = new Button("Save Course");
        String saveStyle = "-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 14px;";
        saveBtn.setStyle(saveStyle);
        saveBtn.setOnMouseEntered(e -> saveBtn.setStyle(saveStyle.replace("#008080", "#006666")));
        saveBtn.setOnMouseExited(e -> saveBtn.setStyle(saveStyle));
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setOnAction(e -> {
            if (skillName.getText().trim().isEmpty()) {
                ModalUtil.showSuccessAlert("Please enter a course code.");
                return;
            }
            ((javafx.stage.Stage)saveBtn.getScene().getWindow()).close();
            
            Course newCourse = new Course("📘", skillName.getText(), skillFullName.getText(), skillDesc.getText());
            DataStore.getInstance().myCourses.add(newCourse);
            DataStore.getInstance().saveToFile();
            addCourseCard(newCourse);
            
            ModalUtil.showSuccessAlert("New course '" + skillName.getText() + "' added successfully!");
        });
        
        popup.getChildren().addAll(title, nameLbl, skillName, fullNameLbl, skillFullName, descLbl, skillDesc, levelLbl, levelCombo, new Region(), saveBtn);
        ModalUtil.showModal(sceneManager.getStage(), popup);
    }

    private void showEditSkillPopup(Course course) {
        VBox popup = new VBox(15);
        popup.setStyle("-fx-background-color: white; -fx-padding: 35; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 25, 0, 0, 10); -fx-min-width: 400px;");
        popup.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("✏️ Edit Course");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #222; -fx-padding: 0 0 10 0;");
        
        String fieldStyle = "-fx-padding: 10; -fx-background-radius: 6; -fx-border-color: #ccc; -fx-border-radius: 6; -fx-font-size: 14px;";
        
        Label nameLbl = new Label("Course Code");
        nameLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextField skillName = new TextField(course.code);
        skillName.setStyle(fieldStyle);
        
        Label fullNameLbl = new Label("Full Name");
        fullNameLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextField skillFullName = new TextField(course.fullName);
        skillFullName.setStyle(fieldStyle);
        
        Label descLbl = new Label("Description");
        descLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextArea skillDesc = new TextArea(course.desc);
        skillDesc.setPrefRowCount(3);
        skillDesc.setStyle(fieldStyle);

        Label levelLbl = new Label("Proficiency Level");
        levelLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        ComboBox<String> levelCombo = new ComboBox<>();
        levelCombo.getItems().addAll("Beginner", "Intermediate", "Advanced");
        levelCombo.getSelectionModel().select("Intermediate");
        levelCombo.setMaxWidth(Double.MAX_VALUE);
        levelCombo.setStyle(fieldStyle);

        Button saveBtn = new Button("Update Course");
        String saveStyle = "-fx-background-color: #f5a623; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 14px;";
        saveBtn.setStyle(saveStyle);
        saveBtn.setOnMouseEntered(e -> saveBtn.setStyle(saveStyle + "-fx-opacity: 0.8;"));
        saveBtn.setOnMouseExited(e -> saveBtn.setStyle(saveStyle));
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        
        saveBtn.setOnAction(e -> {
            ((javafx.stage.Stage)saveBtn.getScene().getWindow()).close();
            course.code = skillName.getText();
            course.fullName = skillFullName.getText();
            course.desc = skillDesc.getText();
            DataStore.getInstance().saveToFile();
            loadCourses();
            ModalUtil.showSuccessAlert("Course '" + skillName.getText() + "' updated successfully!");
        });
        
        popup.getChildren().addAll(title, nameLbl, skillName, fullNameLbl, skillFullName, descLbl, skillDesc, levelLbl, levelCombo, new Region(), saveBtn);
        ModalUtil.showModal(sceneManager.getStage(), popup);
    }
}
