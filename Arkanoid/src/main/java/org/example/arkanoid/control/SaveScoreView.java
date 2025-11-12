package org.example.arkanoid.control;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * View cho màn hình nhập tên và lưu điểm.
 */
public class SaveScoreView {

    private SaveScoreController controller;
    private int finalScore;
    private double width;
    private double height;
    private TextField nameField;

    public SaveScoreView(SaveScoreController controller, int finalScore, double width, double height) {
        this.controller = controller;
        this.finalScore = finalScore;
        this.width = width;
        this.height = height;
    }

    public BorderPane createSaveScoreRoot() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1a1a2e;"); // Nền màu tối

        // Tiêu đề
        Label titleLabel = new Label("GAME OVER");
        titleLabel.setFont(new Font("Arial", 40));
        titleLabel.setTextFill(Color.WHITE);

        // Hiển thị điểm
        Label scoreLabel = new Label("Điểm của bạn: " + finalScore);
        scoreLabel.setFont(new Font("Arial", 24));
        scoreLabel.setTextFill(Color.WHITE);

        // Ô nhập tên
        Label nameLabel = new Label("Nhập tên của bạn:");
        nameLabel.setTextFill(Color.WHITE);
        nameField = new TextField();
        nameField.setMaxWidth(250);
        nameField.setPromptText("Player");

        // Nút "Lưu và xem Rank"
        Button saveButton = new Button("Lưu và xem Rank");
        saveButton.setPrefSize(200, 40);

        // Gắn sự kiện cho nút
        saveButton.setOnAction(event -> {
            String playerName = nameField.getText().isEmpty() ? "Player" : nameField.getText();
            controller.handleSaveAndShowRank(playerName, finalScore);
        });

        // Sắp xếp vào VBox
        VBox layout = new VBox(20); // Khoảng cách 20px
        layout.getChildren().addAll(titleLabel, scoreLabel, nameLabel, nameField, saveButton);
        layout.setAlignment(Pos.CENTER);

        root.setCenter(layout);
        BorderPane.setMargin(layout, new Insets(50));

        return root;
    }
}