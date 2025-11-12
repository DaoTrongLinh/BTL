package org.example.arkanoid.control;

import org.example.arkanoid.main.ArkanoidApp;

/**
 * Controller cho màn hình Ranking.
 */
public class RankingController {

    private final ArkanoidApp arkanoidApp;

    public RankingController(ArkanoidApp arkanoidApp) {
        this.arkanoidApp = arkanoidApp;
    }

    /**
     * Được gọi khi bấm nút "Quay lại".
     */
    public void handleBackButton() {
        arkanoidApp.showMainMenu();
    }
}