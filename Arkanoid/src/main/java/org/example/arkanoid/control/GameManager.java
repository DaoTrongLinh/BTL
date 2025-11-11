package org.example.arkanoid.control;

import org.example.arkanoid.object.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class GameManager {

    // Kích thước màn chơi
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    // Thời gian (nano giây) của lần bắn cuối, tính cooldown
    private long lastShotTime = 0;

    // Khoảng cách mỗi lần bắn (nano giây)
    private static final long SHOOT_COOLDOWN = 1_000_000_000L;

    // Các đối tượng game
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private List<PowerUp> powerUps; // Danh sách power-up đang rơi
    private List<Bullet> bullets;
    private List<Brick> originalBricksBackup = new ArrayList<>();

    // Các lớp xử lý
    private CollisionHandler collisionHandler;
    private Level levelLoader;
    private int currentLevel;
    /**
     * Cờ báo hiệu cần kích hoạt Penalty Mode.
     * Dùng để tránh lỗi ConcurrentModificationException.
     */
    private boolean activatePenaltyModePending = false;

    private AudioManager audioManager; // Thêm biến để lưu AudioManager

    // Trạng thái game
    private int score;
    private int lives;
    private String gameState; // "PLAYING", "GAME_OVER", "WIN", "READY", "PENALTY_READY", "PENALTY_PLAYING"

    public GameManager(AudioManager audioManager) { // <-- Nhận AudioManager
        this.bricks = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.audioManager = audioManager; // <-- Lưu nó lại

        // Khởi tạo CollisionHandler
        this.collisionHandler = new CollisionHandler();
        this.levelLoader = new Level();

        startGame();
    }

    /**
     * Được gọi bởi PenaltyPowerUp để yêu cầu kích hoạt Penalty.
     */
    public void setPenaltyModePending() {
        this.activatePenaltyModePending = true;
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
     */
    private void loadLevel(int levelNumber) {
        // Dọn dẹp màn chơi cũ
        bricks.clear();
        powerUps.clear();
        bullets.clear();

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
     * Được gọi bởi ArkanoidApp
     */
    public void paddleShoot() {
        // Chỉ bắn khi đang chơi VÀ paddle có power-up
        if (gameState.equals("PLAYING") && paddle.isLaserActive()) {

            long currentTime = System.nanoTime(); // Lấy thời gian hiện tại

            // Kiểm tra xem (thời gian hiện tại - lần bắn cuối)
            // có lớn hơn 1 giây không
            if ((currentTime - lastShotTime) > SHOOT_COOLDOWN) {
                // Tạo 2 viên đạn ở 2 đầu paddle
                double bulletX1 = paddle.getX() + 5; // Hơi thụt vào 1 chút
                double bulletX2 = paddle.getX() + paddle.getWidth() - 10; // (5 + 5)
                double bulletY = paddle.getY() - 10; // Bắn từ phía trên paddle

                bullets.add(new Bullet(bulletX1, bulletY));
                bullets.add(new Bullet(bulletX2, bulletY));

                lastShotTime = currentTime; // Cập nhật thời gian bắn cuối
            }
        }
    }

    /**
     * Kích hoạt chế độ Penalty.
     * Được gọi bởi PenaltyPowerUp.
     */
    public void activatePenaltyMode() {
        this.originalBricksBackup.clear();
        this.originalBricksBackup.addAll(this.bricks);
        // 1. Dọn dẹp màn hình
        bricks.clear();
        bullets.clear();
        powerUps.clear();

        // 2. Tạo thủ môn (gạch di động)
        Brick penaltyBrick = new MovingBrick(WIDTH / 2.0 - 40, 100, 80, 20);
        bricks.add(penaltyBrick);

        // 3. Reset bóng và paddle (hàm này sẽ đặt gameState = "READY")
        prepareNewBall();

        // 4. Ghi đè trạng thái thành "PENALTY_READY"
        gameState = "PENALTY_READY";
    }

    /**
     * Xử lý khi sút hỏng Penalty.
     * Tải lại level hiện tại.
     */
    private void failPenaltyShot() {
        audioManager.playHitSound();
        this.bricks.clear();
        this.bricks.addAll(this.originalBricksBackup);
        this.originalBricksBackup.clear();
        // Tải lại level hiện tại
        prepareNewBall();
    }

    /**
     * Phương thức cập nhật logic game chính, được gọi mỗi frame.
     */
    public void updateGame() {

        // Phải cập nhật gạch ngay cả khi đang "READY" (cho chế độ penalty)
        if (gameState.equals("PENALTY_READY") || gameState.equals("PENALTY_PLAYING")) {
            for (Brick brick : bricks) {
                brick.update(); // Gọi hàm update() của MovingBrick
            }
        }

        // Logic mới dựa trên gameState
        if (gameState.equals("PLAYING") || gameState.equals("PENALTY_PLAYING")) {
            // 1. Cập nhậcollisionHandler.handleBallPaddleCollision(ball, paddle);t vị trí bóng (CHỈ KHI ĐANG CHƠI)
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
                    pu.applyEffect(this); // Áp dụng hiệu ứng
                    powerUpIterator.remove(); // Xóa power-up khỏi danh sách
                }
                //  Dọn dẹp power-up nếu rơi ra khỏi màn hình
                else if (pu.getY() > HEIGHT) {
                    powerUpIterator.remove(); // Xóa power-up
                }
            }

            // Kiểm tra cờ SAU KHI vòng lặp đã kết thúc
            if (activatePenaltyModePending) {
                activatePenaltyMode(); // Kích hoạt Penalty
                activatePenaltyModePending = false; // Reset cờ

                // Dừng hàm update ngay lập tức vì trạng thái game (và các danh sách) đã thay đổi
                return;
            }

            Iterator<Bullet> bulletIterator = bullets.iterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                bullet.update(); // 1. Cho đạn bay

                // 2. Dọn dẹp đạn bay ra khỏi màn hình
                if (bullet.isOffScreen(HEIGHT)) {
                    bulletIterator.remove();
                    continue; // Bỏ qua, xét viên đạn tiếp theo
                }

                // 3. Yêu cầu CollisionHandler kiểm tra va chạm
                Brick hitBrick = collisionHandler.handleBulletBrickCollision(bullet, bricks);

                // 4. Xử lý logic game NẾU có va chạm
                if (hitBrick != null) {
                    // CollisionHandler đã báo có va chạm
                    boolean wasDestroyed = hitBrick.hit();
                    if (wasDestroyed) {
                        score += hitBrick.getPoints(); // Cộng điểm
                    }

                    // Xóa viên đạn
                    bulletIterator.remove();
                }
            }

            // 3. Kiểm tra va chạm
            checkCollisions();

            // 4. Kiểm tra va chạm tường
            checkWallCollisions();

            // 5. Kiểm tra thắng
            checkWinCondition();

        } else if (gameState.equals("READY") || gameState.equals("PENALTY_READY")) {
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
        if (collisionHandler.handleBallPaddleCollision(ball, paddle)) {
            audioManager.playHitSound();
        }

        // 2. Va chạm Bóng với Gạch

        // <<< SỬA LỖI : THÊM BIẾN CỜ NÀY >>>
        boolean penaltyWon = false;

        Iterator<Brick> brickIterator = bricks.iterator();
        while (brickIterator.hasNext()) {
            Brick brick = brickIterator.next();

            if (!brick.isDestroyed()) {
                if (collisionHandler.handleBallBrickCollision(ball, brick)) {
                    // Hàm 'hit()' sẽ tự trừ độ bền và trả về 'true' nếu gạch bị vỡ
                    audioManager.playHitSound();
                    boolean wasDestroyed = brick.hit();

                    // Chỉ cộng điểm & thả power-up NẾU gạch BỊ VỠ
                    if (gameState.equals("PENALTY_PLAYING")) {
                        // --- TRẠNG THÁI THẮNG PENALTY ---
                        if (wasDestroyed) {
                            System.out.println("PENALTY GOAL!");
                            score += brick.getPoints(); // Thưởng điểm
                            currentLevel++; // Tăng level

                            // <<< SỬA LỖI : KHÔNG GỌI loadLevel NGAY LẬP TỨC >>>
                            penaltyWon = true;
                            originalBricksBackup.clear();
                        }
                    } else {
                        // --- TRẠNG THÁI GAME THƯỜNG ---
                        if (wasDestroyed) {
                            score += brick.getPoints();
                            // Kích hoạt Power-up rơi ra, tỉ lệ 20% rơi
                            if (Math.random() < 0.2) {
                                double puX = brick.getX() + (brick.getWidth() / 2) - 15;
                                double puY = brick.getY();
                                double powerUpType = Math.random();

                                if (powerUpType < 0.4 && paddle.getWidth() == paddle.getOriginalWidth()) {
                                    powerUps.add(new ExpandPaddlePowerUp(puX, puY));
                                } else if (powerUpType < 0.8 && !paddle.isLaserActive()) {
                                    powerUps.add(new LaserPowerUp(puX, puY));
                                } else {
                                    powerUps.add(new PenaltyPowerUp(puX, puY));
                                }
                            }
                        }
                    }
                }
            }
        }

        if (penaltyWon) {
            loadLevel(currentLevel); // Tải level mới một cách an toàn
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
            originalBricksBackup.clear();
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
            ball.setDx(0);  // Phóng bóng
            ball.setDy(-5); // Phóng bóng
        }
        else if (gameState.equals("PENALTY_READY")) {
            gameState = "PENALTY_PLAYING";
            ball.setDx(0);  // Bắn thẳng
            ball.setDy(-5);
        }
    }

    /**
     * Xử lý va chạm tường cho bóng (SỬA ĐỔI)
     */
    private void checkWallCollisions() {
        if (gameState.equals("PENALTY_PLAYING")) {
            // Nếu bóng chạm trái || trên || phải TRƯỚC khi chạm gạch -> THUA
            if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= WIDTH || ball.getY() <= 0) {
                failPenaltyShot();
                return; // Dừng hàm ngay lập tức
            }
        }

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

        // Gỡ bỏ hiệu ứng Power-up
        paddle.setWidth(paddle.getOriginalWidth()); // Reset Expand
        paddle.setLaserActive(false); // Reset Laser
        bullets.clear(); // Xóa hết đạn

        // Đặt vận tốc bóng bằng 0
        ball.setDx(0);
        ball.setDy(0);

        // Đặt bóng dính vào paddle (sẽ được update trong updateGame())
        ball.setCenterX(paddle.getX() + paddle.getWidth() / 2);
        ball.setCenterY(paddle.getY() - ball.getRadius());
    }

    // --- GETTERS ---
    // GameView sẽ dùng các getters này để VẼ đối tượng

    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public List<Bullet> getBullets() {
        return this.bullets;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public String getGameState() {
        return gameState;
    }
}