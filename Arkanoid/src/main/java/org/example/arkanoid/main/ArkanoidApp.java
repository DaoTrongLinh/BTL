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
//Thêm import SettingsMenu
import org.example.arkanoid.control.SettingsMenuController;
import org.example.arkanoid.main.SettingsMenu;
/**
 * Lớp Application chính của JavaFX.
 * Chịu trách nhiệm thiết lập cửa sổ (Stage), Scene
 * và quản lý việc chuyển đổi giữa Main Menu và Game Scene.
 */
public class ArkanoidApp extends Application {

    // Lấy kích thước từ GameManager để đồng bộ
    private static final int WIDTH = GameManager.WIDTH;
    private static final int HEIGHT = GameManager.HEIGHT;

    // Các thành phần của game
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

        // Tạo một Pane gốc (BorderPane) và Scene
        // Chúng ta sẽ dùng BorderPane để dễ dàng căn giữa menu
        BorderPane root = new BorderPane();
        this.scene = new Scene(root, WIDTH, HEIGHT);

        this.audioManager = new AudioManager();

        //Hiển thị Main Menu trước
        showMainMenu();

        //Thiết lập Stage (Cửa sổ)
        primaryStage.setTitle("Arkanoid");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Tạo và hiển thị Main Menu.
     */
    public void showMainMenu() {
        // Tạo Controller, truyền ArkanoidApp vào
        MainMenuController controller = new MainMenuController(this);

        // Tạo View (MainMenu), truyền controller và kích thước vào
        MainMenu mainMenuView = new MainMenu(controller, WIDTH, HEIGHT);

        // Lấy root pane (BorderPane) từ MainMenu view
        BorderPane menuRoot = mainMenuView.createMenuRoot();

        // Đặt menu làm gốc của Scene
        scene.setRoot(menuRoot);

        // Xóa các sự kiện input cũ (nếu có)
        scene.setOnMouseMoved(null);
        scene.setOnKeyPressed(null);
        scene.setOnMouseClicked(null);
    }

    /**
     *Tạo và hiển thị Main Menu
     */
    public void showSettingsMenu() {
        // Lấy trạng thái hiện tại
        double currentVolume = audioManager.getVolume();

        // Tạo Controller, truyền Stage và Audio vào
        SettingsMenuController controller = new SettingsMenuController(this, audioManager);

        // Tạo View, truyền trạng thái hiện tại vào
        SettingsMenu settingsView = new SettingsMenu(controller,currentVolume);

        // Lấy root pane
        BorderPane settingsRoot = settingsView.createSettingsRoot();

        // Đặt settings làm gốc của Scene
        scene.setRoot(settingsRoot);

        // Xóa các sự kiện input cũ
        scene.setOnMouseMoved(null);
        scene.setOnKeyPressed(null);
        scene.setOnMouseClicked(null);
    }
    /**
     * Khởi tạo và chuyển sang cảnh game.
     * Đây là toàn bộ logic cũ từ phương thức start() của bạn.
     */
    public void startGameScene() {
        //Bắt đầu phát nhạc
        if (audioManager != null) {
            audioManager.playBackgroundMusic();
        }
        //Thiết lập Pane và Canvas cho game
        Pane gameRoot = new Pane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gameRoot.getChildren().add(canvas);

        // Lấy GraphicsContext từ Canvas
        gc = canvas.getGraphicsContext2D();

        // Khởi tạo "Bộ não" và "Người vẽ"
        gameManager = new GameManager();
        gameView = new GameView(gc);

        // Tạo Game Loop (Vòng lặp game)
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // a. Cập nhật toàn bộ logic game
                gameManager.updateGame();

                // b. Vẽ lại mọi thứ lên màn hình
                gameView.render(gameManager);
            }
        };

        // Gắn sự kiện input vào Scene cho game
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
        scene.setOnKeyPressed(event -> {
            if (gameManager == null) return; // Kiểm tra an toàn

            // 1. Logic phím SPACE (Phóng bóng)
            if (event.getCode() == KeyCode.SPACE) {
                gameManager.launchBall();
            }

            // 2. THÊM MỚI: Logic phím ESC (Quay về Menu)
            else if (event.getCode() == KeyCode.ESCAPE) {
                // Lấy trạng thái game
                String state = gameManager.getGameState();

                // Chỉ hành động nếu đang ở màn hình Win hoặc Thua
                if (state.equals("GAME_OVER") || state.equals("WIN")) {

                    // Dừng vòng lặp game
                    gameLoop.stop();

                    // Dừng nhạc
                    if (audioManager != null) {
                        audioManager.stopBackgroundMusic();
                    }

                    // Quay về menu chính
                    showMainMenu();
                }
            }
        });
        scene.setOnMouseClicked(event -> {
            if (gameManager != null) {
                gameManager.paddleShoot();
            }
        });

        // ĐẶT CẢNH GAME LÀM GỐC
        scene.setRoot(gameRoot);

        // Bắt đầu vòng lặp game
        gameLoop.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}