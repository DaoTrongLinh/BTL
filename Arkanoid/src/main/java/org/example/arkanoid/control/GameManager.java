package org.example.arkanoid.control;

import org.example.arkanoid.object.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class GameManager {

    // Kích thước màn chơi
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    // Các đối tượng game
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private List<PowerUp> powerUps; // Danh sách power-up đang rơi

    // Các lớp xử lý
    private CollisionHandler collisionHandler;
    private Level levelLoader;

    // Trạng thái game
    private int score;
    private int lives;
    private String gameState; // "PLAYING", "GAME_OVER", "WIN", "READY"

    public GameManager() {
        this.bricks = new ArrayList<>();
        this.powerUps = new ArrayList<>();

        // Khởi tạo CollisionHandler
        this.collisionHandler = new CollisionHandler();
        // this.levelLoader = new Level(); (Sẽ làm sau)

        startGame();
    }

    /**
     * Khởi tạo hoặc reset trò chơi về trạng thái ban đầu.
     */
    public void startGame() {
        this.score = 0;
        this.lives = 3;

        // Khởi tạo các đối tượng
        paddle = new Paddle(WIDTH / 2.0 - 50, HEIGHT - 50, 100, 15);
        // Vận tốc ban đầu là 0, 0
        ball = new Ball(WIDTH / 2.0, HEIGHT / 2.0, 10, 0, 0);

        // Tạm thời, tạo một vài viên gạch để test
        bricks.clear();

        // --- SỬA LỖI KHOẢNG CÁCH GẠCH Ở ĐÂY ---
        int numRows = 5;
        int numCols = 10;
        double startX = 50; // Lề bên trái
        double startY = 50; // Lề bên trên
        // Trừ đi lề 2 bên (startX * 2) và chia đều
        double brickWidth = (WIDTH - (startX * 2)) / numCols;
        double brickHeight = 35; // Chiều cao cố định

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // Đặt gạch sát nhau, không có khoảng cách
                double brickX = startX + (j * brickWidth);
                double brickY = startY + (i * brickHeight);
                bricks.add(new NormalBrick(brickX, brickY, brickWidth, brickHeight));
            }
        }
        // --- KẾT THÚC SỬA ---

        // Chuẩn bị quả bóng đầu tiên
        prepareNewBall();
    }

    /**
     * Phương thức cập nhật logic game chính, được gọi mỗi frame.
     */
    public void updateGame() {

        // Logic mới dựa trên gameState
        if (gameState.equals("PLAYING")) {
            // 1. Cập nhật vị trí bóng (CHỈ KHI ĐANG CHƠI)
            ball.update();

            // 2. Cập nhật Power-ups
            for (PowerUp pu : powerUps) {
                pu.update();
            }

            // 3. Kiểm tra va chạm
            checkCollisions();

            // 4. Kiểm tra va chạm tường
            checkWallCollisions();

            // 5. Kiểm tra thắng
            checkWinCondition();

        } else if (gameState.equals("READY")) {
            // Khi bóng đang "READY", BẮT BUỘC nó phải dính vào paddle
            ball.setCenterX(paddle.getX() + paddle.getWidth() / 2);
            ball.setCenterY(paddle.getY() - ball.getRadius()); // Đặt ngay trên paddle
        }
        // Nếu là "WIN" hoặc "GAME_OVER", không làm gì cả
    }

    /**
     * Sử dụng CollisionHandler để kiểm tra tất cả va chạm
     * giữa các đối tượng game.
     */
    private void checkCollisions() {
        // 1. Va chạm Bóng với Thanh trượt
        collisionHandler.handleBallPaddleCollision(ball, paddle);

        // 2. Va chạm Bóng với Gạch
        Iterator<Brick> brickIterator = bricks.iterator();
        while (brickIterator.hasNext()) {
            Brick brick = brickIterator.next();

            if (!brick.isDestroyed()) {
                if (collisionHandler.handleBallBrickCollision(ball, brick)) {
                    score += brick.getHitPoints(); // Cộng điểm

                    // (Tùy chọn) Thả Power-up tại đây
                    // if (Math.random() > 0.8) { // 20% tỉ lệ rơi
                    //     powerUps.add(new ExpandPaddlePowerUp(brick.getX(), brick.getY()));
                    // }
                }
            }
        }
    }

    /**
     * Kiểm tra điều kiện thắng game (phá hết gạch).
     */
    private void checkWinCondition() {
        if (!gameState.equals("PLAYING")) return;

        boolean allDestroyed = true;
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                allDestroyed = false;
                break;
            }
        }

        if (allDestroyed) {
            gameState = "WIN";
            System.out.println("YOU WIN!");
        }
    }

    /**
     * Di chuyển thanh trượt (được gọi từ ArkanoidApp khi chuột di chuyển).
     */
    public void movePaddle(double mouseX) {
        paddle.setX(mouseX - paddle.getWidth() / 2, WIDTH);
    }

    /**
     * Được gọi từ ArkanoidApp khi người chơi bấm SPACE
     */
    public void launchBall() {
        if (gameState.equals("READY")) {
            gameState = "PLAYING";
            ball.setDx(5);  // Phóng bóng
            ball.setDy(-5); // Phóng bóng
        }
    }

    /**
     * Xử lý va chạm tường cho bóng (SỬA ĐỔI)
     */
    private void checkWallCollisions() {
        // Va chạm tường trái/phải
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= WIDTH) {
            ball.reverseDx();
        }
        // Va chạm tường trên
        if (ball.getY() <= 0) {
            ball.reverseDy();
        }
        // Va chạm sàn (THUA)
        if (ball.getY() + ball.getHeight() >= HEIGHT) {
            lives--;
            if (lives <= 0) {
                gameState = "GAME_OVER";
                System.out.println("GAME OVER!");
            } else {
                // Chuyển về trạng thái READY
                prepareNewBall();
            }
        }
    }

    /**
     * Đặt paddle về giữa và đặt bóng dính vào paddle.
     */
    private void prepareNewBall() {
        gameState = "READY"; // Đặt trạng thái sẵn sàng

        // Reset paddle về giữa
        paddle.setX(WIDTH / 2.0 - 50, WIDTH);

        // Đặt vận tốc bóng bằng 0
        ball.setDx(0);
        ball.setDy(0);

        // Đặt bóng dính vào paddle (sẽ được update trong updateGame())
        ball.setCenterX(paddle.getX() + paddle.getWidth() / 2);
        ball.setCenterY(paddle.getY() - ball.getRadius());
    }


    // --- GETTERS ---
    // GameView sẽ dùng các getters này để VẼ đối tượng

    public Paddle getPaddle() { return paddle; }
    public Ball getBall() { return ball; }
    public List<Brick> getBricks() { return bricks; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public String getGameState() { return gameState; }

    public void activateMultiBall() {
    }
}