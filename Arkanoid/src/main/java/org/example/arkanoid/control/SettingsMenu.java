package org.example.arkanoid.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.arkanoid.control.SettingsMenuController;

/**
 * Xây dựng giao diện (View) cho màn hình Cài đặt.
 */
public class SettingsMenu {

    private SettingsMenuController controller;

    // Thêm các biến này để lấy trạng thái hiện tại
    private double currentVolume;

    public SettingsMenu(SettingsMenuController controller, double currentVolume) {
        this.controller = controller;
        this.currentVolume = currentVolume;
    }

    public BorderPane createSettingsRoot() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1a1a2e;"); // Nền màu tối


        // Nhãn tiêu đề
        Label titleLabel = new Label("Cài Đặt");
        titleLabel.setFont(new Font("Arial", 32));
        titleLabel.setTextFill(Color.WHITE);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(40, 0, 0, 0));
        root.setTop(titleLabel);

        // Slider Âm lượng
        Label volumeLabel = new Label("Âm lượng");
        volumeLabel.setTextFill(Color.WHITE);

        // Slider có giá trị từ 0.0 đến 1.0, và giá trị mặc định là âm lượng hiện tại
        Slider volumeSlider = new Slider(0.0, 1.0, currentVolume);
        volumeSlider.setMaxWidth(200);


        // Nút Quay lại
        Button backButton = new Button("Quay lại");
        backButton.setPrefSize(150, 40);

        // Sắp xếp vào VBox
        VBox settingsLayout = new VBox(25); // Khoảng cách 25px
        settingsLayout.getChildren().addAll(volumeLabel, volumeSlider, backButton);
        settingsLayout.setAlignment(Pos.CENTER);
        root.setCenter(settingsLayout);

        //Gắn sự kiện cho Slider
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            controller.handleVolumeChange(newValue.doubleValue());
        });

        //Gắn sự kiện cho Nút Back
        backButton.setOnAction(event -> controller.handleBackButton());

        return root;
    }
}