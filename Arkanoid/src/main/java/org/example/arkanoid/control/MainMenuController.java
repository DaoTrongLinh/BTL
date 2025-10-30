package org.example.arkanoid.control;

import org.example.arkanoid.main.ArkanoidApp;

/**
 * Lớp này xử lý các sự kiện từ MainMenu (View).
 * Nó nhận lệnh từ View và ra lệnh cho ArkanoidApp (lớp chính)
 * để thực hiện hành động (ví dụ: chuyển cảnh).
 */
public class MainMenuController {

    private ArkanoidApp arkanoidApp; // Tham chiếu đến lớp App chính

    /**
     * Constructor nhận vào lớp ArkanoidApp để có thể gọi các hàm của nó.
     */
    public MainMenuController(ArkanoidApp arkanoidApp) {
        this.arkanoidApp = arkanoidApp;
    }

    /**
     * Được gọi khi người chơi bấm nút "Start Game".
     */
    public void handleStartButton() {
        // Ra lệnh cho ArkanoidApp bắt đầu màn chơi game
        arkanoidApp.startGameScene();
    }

    /**
     * Được gọi khi người chơi bấm nút "Settings".
     */
    public void handleSettingsButton() {
        // Tạm thời chưa làm gì
        System.out.println("Settings clicked - no action yet.");
    }
}
