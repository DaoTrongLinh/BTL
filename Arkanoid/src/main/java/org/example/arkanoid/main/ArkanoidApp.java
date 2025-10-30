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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

// THÊM IMPORT NÀY
import javafx.scene.input.KeyCode;

//Thêm import làm ảnh
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Thêm import làm nhạc
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

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

    private MediaPlayer backgroundMusicPlayer; //thêm biến làm nhạc

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // 1. Tạo một Pane gốc (BorderPane) và Scene
        // Chúng ta sẽ dùng BorderPane để dễ dàng căn giữa menu
        BorderPane root = new BorderPane();
        this.scene = new Scene(root, WIDTH, HEIGHT);

        //2. Khởi tạo và thêm nhạc
        initMusic();
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }

        // 3. Hiển thị Main Menu trước
        showMainMenu();

        // 4. Thiết lập Stage (Cửa sổ)
        primaryStage.setTitle("Arkanoid");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Tải tệp nhạc nền và thiết lập MediaPlayer.
     */
    private void initMusic() {
        try {
            // Đổi "background_music.mp3" thành tên tệp nhạc của bạn
            String musicFile = "/Music/BrainRotRap.mp3";
            URL resource = getClass().getResource(musicFile);

            if (resource == null) {
                System.err.println("Không tìm thấy tệp nhạc: " + musicFile);
                return;
            }

            Media media = new Media(resource.toString());
            backgroundMusicPlayer = new MediaPlayer(media);

            // Cài đặt cho nhạc nền
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp lại vô hạn
            backgroundMusicPlayer.setVolume(0.5); // Đặt âm lượng (0.0 đến 1.0)

        } catch (Exception e) {
            System.err.println("Lỗi khi tải nhạc: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Tạo và hiển thị Main Menu.
     */
    private void showMainMenu() {
        // --- Bắt đầu phần THÊM ẢNH ---
        // 1. Tải ảnh
        Image backgroundImage = null;
        try {
            // Cách tải ảnh từ thư mục resources
            // "images/arkanoid_background.png" là đường dẫn tương đối trong thư mục resources
            // Hoặc nếu bạn đặt ảnh ngay trong package main, có thể dùng:
            // getClass().getResourceAsStream("arkanoid_background.png")
            backgroundImage = new Image(getClass().getResourceAsStream("/image/GoatMessi.jpg"));
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh nền: " + e.getMessage());
            // Có thể đặt một ảnh mặc định hoặc chỉ dùng màu nền nếu ảnh không tải được
        }

        // 2. Tạo ImageView từ ảnh đã tải
        ImageView backgroundImageView = null;
        if (backgroundImage != null) {
            backgroundImageView = new ImageView(backgroundImage);
            // (Tùy chọn) Điều chỉnh kích thước ảnh cho phù hợp với cửa sổ
            backgroundImageView.setFitWidth(WIDTH);
            backgroundImageView.setFitHeight(HEIGHT);
            // (Tùy chọn) Giữ tỷ lệ khung hình
            backgroundImageView.setPreserveRatio(false);
        }
        // --- Kết thúc phần THÊM ẢNH ---


        // 1. Tạo các nút
        Button startButton = new Button("Start Game");
        Button settingsButton = new Button("Settings");

        // (Tùy chọn) Đặt kích thước cho nút
        startButton.setPrefSize(150, 40);
        settingsButton.setPrefSize(150, 40);

        // 2. Tạo layout cho menu
        VBox menuLayout = new VBox(20); // 20px khoảng cách giữa các nút
        menuLayout.getChildren().addAll(startButton, settingsButton);
        menuLayout.setAlignment(Pos.CENTER); // Căn giữa các nút

        // --- Bắt đầu phần ĐẶT ẢNH VÀO NỀN ---
        // 3. Đặt layout vào một BorderPane để căn giữa toàn bộ
        BorderPane menuRoot = new BorderPane();

        // Nếu có ảnh nền, thêm nó vào layer phía dưới
        if (backgroundImageView != null) {
            menuRoot.getChildren().add(backgroundImageView); // Thêm ảnh nền vào menuRoot
        }
        menuRoot.setCenter(menuLayout); // Đặt các nút menu lên trên ảnh nền
        // --- Kết thúc phần ĐẶT ẢNH VÀO NỀN ---


        // menuRoot.setStyle("-fx-background-color: #2B2B2B;"); // Bỏ hoặc giữ dòng này tùy theo bạn muốn dùng ảnh hay màu nền

        // 4. Gắn sự kiện cho nút
        startButton.setOnAction(event -> {
            startGameScene(); // Gọi hàm để bắt đầu game
        });

        settingsButton.setOnAction(event -> {
            System.out.println("Settings clicked - no action yet.");
            // Tạm thời chưa làm gì cả
        });

        // 5. Đặt menu làm gốc của Scene
        scene.setRoot(menuRoot);

        // Xóa các sự kiện input cũ (nếu có)
        scene.setOnMouseMoved(null);
        scene.setOnKeyPressed(null);
    }

    /**
     * Khởi tạo và chuyển sang cảnh game.
     * Đây là toàn bộ logic cũ từ phương thức start() của bạn.
     */
    private void startGameScene() {
        // 1. Thiết lập Pane và Canvas cho game
        Pane gameRoot = new Pane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gameRoot.getChildren().add(canvas);

        // Lấy GraphicsContext (cây cọ) từ Canvas
        gc = canvas.getGraphicsContext2D();

        // 2. Khởi tạo "Bộ não" và "Người vẽ"
        gameManager = new GameManager();
        gameView = new GameView(gc);

        // 3. Tạo Game Loop (Vòng lặp game)
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // a. Cập nhật toàn bộ logic game
                gameManager.updateGame();

                // b. Vẽ lại mọi thứ lên màn hình
                gameView.render(gameManager);
            }
        };

        // 4. Gắn sự kiện input vào Scene cho game
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

        // 5. ĐẶT CẢNH GAME LÀM GỐC (Đây là bước "chuyển cảnh")
        scene.setRoot(gameRoot);

        // 6. Bắt đầu vòng lặp game
        gameLoop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}