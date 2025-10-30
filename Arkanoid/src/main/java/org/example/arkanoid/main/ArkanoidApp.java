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

//Thêm import làm menu
import javafx.scene.layout.BorderPane;

// THÊM IMPORT NÀY
import javafx.scene.input.KeyCode;

//Thêm import làm nhạc
import org.example.arkanoid.control.AudioManager;
//Thêm import Menu
import org.example.arkanoid.control.MainMenuController;

/**
 * Lớp Application chính của JavaFX.
 * Chịu trách nhiệm thiết lập cửa sổ (Stage), Scene
 * và quản lý việc chuyển đổi giữa Main Menu và Game Scene.
 */
public class ArkanoidApp extends Application {

    // Lấy kích thước từ GameManager để đồng bộ
    private static final int WIDTH = GameManager.WIDTH;
    private static final int HEIGHT = GameManager.HEIGHT;

    // Các thành phần của game (đưa lên làm biến thành viên)
    private GameManager gameManager;
    private GameView gameView;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;

    // Các thành phần cốt lõi của JavaFX
    private Stage primaryStage;
    private Scene scene;
    private AudioManager audioManager;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // 1. Tạo một Pane gốc (BorderPane) và Scene
        // Chúng ta sẽ dùng BorderPane để dễ dàng căn giữa menu
        BorderPane root = new BorderPane();
        this.scene = new Scene(root, WIDTH, HEIGHT);

        this.audioManager = new AudioManager();

        // 3. Hiển thị Main Menu trước
        showMainMenu();

        // 4. Thiết lập Stage (Cửa sổ)
        primaryStage.setTitle("Arkanoid");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Tạo và hiển thị Main Menu.
     */
    /**
     * Tạo và hiển thị Main Menu. (ĐÃ REFACTOR)
     */
    private void showMainMenu() {
        // 1. Tạo Controller, truyền 'this' (chính ArkanoidApp) vào
        MainMenuController controller = new MainMenuController(this);

        // 2. Tạo View (MainMenu), truyền controller và kích thước vào
        MainMenu mainMenuView = new MainMenu(controller, WIDTH, HEIGHT);

        // 3. Lấy root pane (BorderPane) từ MainMenu view
        BorderPane menuRoot = mainMenuView.createMenuRoot();

        // 4. Đặt menu làm gốc của Scene
        scene.setRoot(menuRoot);

        // 5. Xóa các sự kiện input cũ (nếu có)
        scene.setOnMouseMoved(null);
        scene.setOnKeyPressed(null);
    }

    /**
     * Khởi tạo và chuyển sang cảnh game.
     * Đây là toàn bộ logic cũ từ phương thức start() của bạn.
     */
    public void startGameScene() {
        // 1.Bắt đầu phát nhạc
        if (audioManager != null) {
            audioManager.playBackgroundMusic();
        }
        // 2. Thiết lập Pane và Canvas cho game
        Pane gameRoot = new Pane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gameRoot.getChildren().add(canvas);

        // Lấy GraphicsContext (cây cọ) từ Canvas
        gc = canvas.getGraphicsContext2D();

        // 3. Khởi tạo "Bộ não" và "Người vẽ"
        gameManager = new GameManager();
        gameView = new GameView(gc);

        // 4. Tạo Game Loop (Vòng lặp game)
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // a. Cập nhật toàn bộ logic game
                gameManager.updateGame();

                // b. Vẽ lại mọi thứ lên màn hình
                gameView.render(gameManager);
            }
        };

        // 5. Gắn sự kiện input vào Scene cho game
        scene.setOnMouseMoved(event -> {
            if (gameManager != null) {
                gameManager.movePaddle(event.getX());
            }
        });

        scene.setOnKeyPressed(event -> {
            if (gameManager != null && event.getCode() == KeyCode.SPACE) {
                gameManager.launchBall();
            }
        });

        // 6. ĐẶT CẢNH GAME LÀM GỐC (Đây là bước "chuyển cảnh")
        scene.setRoot(gameRoot);

        // 7. Bắt đầu vòng lặp game
        gameLoop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}