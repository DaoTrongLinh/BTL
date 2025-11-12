package org.example.arkanoid.control;

import org.example.arkanoid.main.ArkanoidApp;

/**
 * Controller cho màn hình Ranking.
 */
public class RankingController {

    private ArkanoidApp arkanoidApp;

    public RankingController(ArkanoidApp arkanoidApp) {
        this.arkanoidApp = arkanoidApp;
    }

    /**
     * Được gọi khi bấm nút "Quay lại".
     */
    public void handleBackButton() {
        arkanoidApp.showMainMenu();
    }

    // (Trong tương lai, bạn sẽ thêm hàm loadScores() ở đây
    // để lấy dữ liệu từ file và truyền cho RankingView)
}