package org.example.arkanoid.main;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.arkanoid.control.MainMenuController; // Import controller

/**
 * Lớp này chịu trách nhiệm xây dựng giao diện (View) cho Main Menu.
 * Nó không chứa logic (logic nằm trong MainMenuController).
 */
public class MainMenu {

    private MainMenuController controller;
    private double width;
    private double height;

    public MainMenu(MainMenuController controller, double width, double height) {
        this.controller = controller;
        this.width = width;
        this.height = height;
    }

    /**
     * Xây dựng và trả về root Node (BorderPane) cho cảnh menu.
     */
    public BorderPane createMenuRoot() {
        // --- Tải ảnh nền ---
        Image backgroundImage = null;
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/image/GoatMessi.jpg"));
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh nền: " + e.getMessage());
        }

        ImageView backgroundImageView = null;
        if (backgroundImage != null) {
            backgroundImageView = new ImageView(backgroundImage);
            backgroundImageView.setFitWidth(width);
            backgroundImageView.setFitHeight(height);
            backgroundImageView.setPreserveRatio(false);
        }

        // --- Tạo các nút ---
        Button startButton = new Button("Start Game");
        Button settingsButton = new Button("Settings");
        startButton.setPrefSize(150, 40);
        settingsButton.setPrefSize(150, 40);

        // --- Tạo layout cho menu ---
        VBox menuLayout = new VBox(20); // 20px khoảng cách
        menuLayout.getChildren().addAll(startButton, settingsButton);
        menuLayout.setAlignment(Pos.CENTER);

        // --- Đặt layout vào BorderPane (để căn giữa) ---
        BorderPane menuRoot = new BorderPane();
        if (backgroundImageView != null) {
            menuRoot.getChildren().add(backgroundImageView); // Thêm ảnh nền (lớp dưới)
        }
        menuRoot.setCenter(menuLayout); // Đặt các nút lên trên

        // --- Gắn sự kiện cho nút (Quan trọng) ---
        // Khi bấm nút, nó sẽ gọi đến phương thức trong Controller
        startButton.setOnAction(event -> controller.handleStartButton());
        settingsButton.setOnAction(event -> controller.handleSettingsButton());

        return menuRoot;
    }
}