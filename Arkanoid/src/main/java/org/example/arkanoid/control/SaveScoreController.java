package org.example.arkanoid.control;

import org.example.arkanoid.main.ArkanoidApp;

/**
 * Controller cho màn hình SaveScore.
 */
public class SaveScoreController {

    private ArkanoidApp arkanoidApp;

    public SaveScoreController(ArkanoidApp arkanoidApp) {
        this.arkanoidApp = arkanoidApp;
    }

    /**
     * Được gọi khi bấm nút "Lưu và xem Rank".
     */
    public void handleSaveAndShowRank(String playerName, int score) {
        //Logic lưu điểm

        RankingManager.saveScore(playerName, score);

        // Ra lệnh cho App chính chuyển sang màn hình Ranking
        arkanoidApp.showRankingScreen();
    }
}