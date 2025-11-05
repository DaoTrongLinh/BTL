package org.example.arkanoid.control;

import org.example.arkanoid.main.ArkanoidApp;
/**
 * Controller cho màn hình Cài đặt.
 * Xử lý logic cho Slider âm lượng, CheckBox toàn màn hình và nút Quay lại.
 */
public class SettingsMenuController {

    private ArkanoidApp arkanoidApp;
    private AudioManager audioManager;

    public SettingsMenuController(ArkanoidApp arkanoidApp, AudioManager audioManager) {
        this.arkanoidApp = arkanoidApp;
        this.audioManager = audioManager;
    }

    /**
     * Được gọi khi Slider âm lượng thay đổi giá trị.
     *
     * @param volume Giá trị mới từ Slider (0.0 đến 1.0)
     */
    public void handleVolumeChange(double volume) {

        audioManager.setVolume(volume);
    }

    /**
     * Được gọi khi bấm nút "Quay lại".
     * Ra lệnh cho App chính quay về Main Menu.
     */
    public void handleBackButton() {
        arkanoidApp.showMainMenu(); // Gọi hàm
    }
}
