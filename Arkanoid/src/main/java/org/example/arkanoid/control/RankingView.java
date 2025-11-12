package org.example.arkanoid.control;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.List;
import org.example.arkanoid.control.RankingManager;
import org.example.arkanoid.control.ScoreEntry;
/**
 * View cho màn hình Bảng Xếp Hạng.
 */
public class RankingView {

    private RankingController controller;
    private double width;
    private double height;

    public RankingView(RankingController controller, double width, double height) {
        this.controller = controller;
        this.width = width;
        this.height = height;
    }

    public BorderPane createRankingRoot() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1a1a2e;"); // Nền màu tối

        // Tiêu đề
        Label titleLabel = new Label("Bảng Xếp Hạng");
        titleLabel.setFont(new Font("Arial", 32));
        titleLabel.setTextFill(Color.WHITE);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(40, 0, 0, 0));
        root.setTop(titleLabel);

        // --- Khu vực hiển thị bảng xếp hạng ---
        VBox rankingBox = new VBox(10); // Khoảng cách 10px
        rankingBox.setAlignment(Pos.CENTER);
        rankingBox.setMaxWidth(400);

        // CSS cho viền trắng chữ trắng
        rankingBox.setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-padding: 20;");

// 1. Tải điểm từ file bằng RankingManager
        List<ScoreEntry> scores = RankingManager.loadScores();

        // 2. Kiểm tra xem danh sách có rỗng không
        if (scores.isEmpty()) {
            Label noScoresLabel = new Label("Chưa có điểm nào được lưu!");
            noScoresLabel.setTextFill(Color.WHITE);
            noScoresLabel.setFont(new Font("Arial", 18));
            rankingBox.getChildren().add(noScoresLabel);
        } else {
            // 3. Lặp qua danh sách điểm và thêm vào VBox
            int rank = 1;
            for (ScoreEntry entry : scores) {
                Label scoreLabel = new Label(
                        rank + ". " + entry.getName() + " : " + entry.getScore()
                );
                scoreLabel.setTextFill(Color.WHITE);
                scoreLabel.setFont(new Font("Arial", 18));
                rankingBox.getChildren().add(scoreLabel);

                rank++;

                // Chỉ hiển thị Top 10 cho đỡ dài
                if (rank > 10) {
                    break;
                }
            }
        }

        root.setCenter(rankingBox);

        // Nút Quay lại
        Button backButton = new Button("Quay về Menu");
        backButton.setPrefSize(150, 40);
        backButton.setOnAction(event -> controller.handleBackButton());

        BorderPane.setAlignment(backButton, Pos.CENTER);
        BorderPane.setMargin(backButton, new Insets(0, 0, 40, 0));
        root.setBottom(backButton);

        return root;
    }
}