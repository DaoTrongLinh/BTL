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
    private int currentLevel;;

    // Trạng thái game
    private int score;
    private int lives;
    private String gameState; // "PLAYING", "GAME_OVER", "WIN", "READY"

    public GameManager() {
        this.bricks = new ArrayList<>();
        this.powerUps = new ArrayList<>();

        // Khởi tạo CollisionHandler
        this.collisionHandler = new CollisionHandler();
        this.levelLoader = new Level();

        startGame();
    }

    /**
     * Khởi tạo hoặc reset trò chơi về trạng thái ban đầu.
     */
    public void startGame() {
        this.score = 0;
        this.lives = 3;
        this.currentLevel = 1;

        // Khởi tạo các đối tượng
        paddle = new Paddle(WIDTH / 2.0 - 50, HEIGHT - 50, 100, 15);
        // Vận tốc ban đầu là 0, 0
        ball = new Ball(WIDTH / 2.0, HEIGHT / 2.0, 10, 0, 0);

        loadLevel(currentLevel);
    }

    /**
     * Tải một màn chơi cụ thể.
     * @param levelNumber Số thứ tự của màn chơi.
     */
    private void loadLevel(int levelNumber) {
        // Dọn dẹp màn chơi cũ
        bricks.clear();
        powerUps.clear();

        // Xây dựng đường dẫn file resource
        String levelPath = "/levels/level" + levelNumber + ".txt";

        // Gọi LevelLoader để tải gạch
        this.bricks = levelLoader.loadLevel(levelPath, WIDTH, 50, 50); // Lề 50, 50

        // Kiểm tra xem còn level không
        if (this.bricks.isEmpty()) {
            // Nếu loadLevel trả về danh sách rỗng (hết level hoặc lỗi)
            gameState = "WIN"; // THẮNG GAME
            System.out.println("YOU WIN! (No more levels)");
        } else {
            // Nếu tải level thành công, reset bóng
            prepareNewBall();
        }
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
            // Phải dùng Iterator để có thể xóa PowerUp một cách an toàn
            // khi bắt được hoặc khi rơi ra ngoài
            Iterator<PowerUp> powerUpIterator = powerUps.iterator();
            while (powerUpIterator.hasNext()) {
                PowerUp pu = powerUpIterator.next();
                pu.update(); //  Cho nó rơi xuống

                //  Kiểm tra va chạm với Paddle
                if (pu.intersects(paddle)) {
                    pu.applyEffect(this); // Áp dụng hiệu ứng (làm paddle to ra)
                    powerUpIterator.remove(); // Xóa power-up khỏi danh sách
                }
                //  Dọn dẹp power-up nếu rơi ra khỏi màn hình
                else if (pu.getY() > HEIGHT) {
                    powerUpIterator.remove(); // Xóa power-up
                }
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
                    // Hàm 'hit()' sẽ tự trừ độ bền và trả về 'true' nếu gạch bị vỡ
                    boolean wasDestroyed = brick.hit();
                    // Chỉ cộng điểm & thả power-up NẾU gạch BỊ VỠ
                    if (wasDestroyed) {
                        score += brick.getPoints();

                        // Kích hoạt Power-up rơi ra
                        // Tỉ lệ 20% rơi
                        if (Math.random() < 0.2) {
                            // (Khi bạn thêm power-up khác, bạn sẽ thêm logic chọn lựa ở đây)

                            // Chỉ thả ExpandPaddle nếu paddle đang ở kích thước gốc
                            if (paddle.getWidth() == paddle.getOriginalWidth()) {
                                double puX = brick.getX() + (brick.getWidth() / 2) - 15;
                                double puY = brick.getY();
                                powerUps.add(new ExpandPaddlePowerUp(puX, puY));
                            }
                        }
                    }
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
            currentLevel++; // Tăng level
            System.out.println("Level Clear! Loading Level " + currentLevel);
            loadLevel(currentLevel); // Tải level mới
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

        // Gỡ bỏ hiệu ứng Power-up (reset paddle về kích thước cũ)
        paddle.setWidth(paddle.getOriginalWidth());

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
    public List<PowerUp> getPowerUps() { return powerUps; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public String getGameState() { return gameState; }

    public void activateMultiBall() {
    }
}