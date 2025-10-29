package org.example.arkanoid.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.arkanoid.control.GameManager;
import org.example.arkanoid.control.GameView;

// THÊM IMPORT NÀY
import javafx.scene.input.KeyCode;

/**
 * Lớp Application chính của JavaFX.
 * Chịu trách nhiệm thiết lập cửa sổ (Stage), Scene, Canvas
 * và khởi chạy Game Loop (AnimationTimer).
 */
public class ArkanoidApp extends Application {

    // Lấy kích thước từ GameManager để đồng bộ
    private static final int WIDTH = GameManager.WIDTH;
    private static final int HEIGHT = GameManager.HEIGHT;

    private GameManager gameManager;
    private GameView gameView;
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        // 1. Thiết lập Pane (layout) và Canvas (bảng vẽ)
        Pane root = new Pane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        // Lấy GraphicsContext (cây cọ) từ Canvas
        gc = canvas.getGraphicsContext2D();

        // 2. Khởi tạo "Bộ não" và "Người họa sĩ"
        gameManager = new GameManager();
        gameView = new GameView(gc);

        // 3. Tạo Game Loop (Vòng lặp game)
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Đây là trái tim của game, chạy 60 lần/giây

                // a. Cập nhật toàn bộ logic game
                gameManager.updateGame();

                // b. Vẽ lại mọi thứ lên màn hình
                gameView.render(gameManager);
            }
        };

        // 4. Thiết lập Scene và gắn sự kiện
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Gắn sự kiện di chuyển chuột vào Scene
        scene.setOnMouseMoved(event -> {
            // Khi chuột di chuyển, gọi GameManager để di chuyển thanh trượt
            gameManager.movePaddle(event.getX());
        });

        // <<< THÊM SỰ KIỆN BẤM PHÍM MỚI NÀY >>>
        scene.setOnKeyPressed(event -> {
            // Nếu bấm phím SPACE
            if (event.getCode() == KeyCode.SPACE) {
                // Gọi hàm phóng bóng
                gameManager.launchBall();
            }
        });

        // 5. Thiết lập Stage (Cửa sổ)
        primaryStage.setTitle("Arkanoid");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Không cho phép thay đổi kích thước cửa sổ
        primaryStage.show();

        // 6. Bắt đầu Game Loop
        gameLoop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}