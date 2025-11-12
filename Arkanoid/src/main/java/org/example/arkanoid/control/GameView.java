package org.example.arkanoid.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.arkanoid.object.Brick;
import org.example.arkanoid.object.Bullet;
import org.example.arkanoid.object.PowerUp;

import javafx.scene.image.Image; //import để thêm ảnh

import javafx.scene.text.TextAlignment; //import để thêm màn hình endgame
import javafx.scene.shape.Rectangle;
/**
 * Chịu trách nhiệm cho tất cả việc VẼ lên màn hình.
 * Nó lấy dữ liệu từ GameManager và render chúng lên Canvas.
 */
public class GameView {

    private GraphicsContext gc;
    private double width;
    private double height;

    private Image backgroundImage;
    private Image gameOverImage;
    private Image winImage;
    private Rectangle escButtonBounds;
    private Rectangle rankButtonBounds;

    public GameView(GraphicsContext gc) {
        this.gc = gc;
        this.width = gc.getCanvas().getWidth();
        this.height = gc.getCanvas().getHeight();

        // --- TẢI ẢNH NỀN CHO GAME TẠI ĐÂY ---
        try { //Start Screen
            backgroundImage = new Image(getClass().getResourceAsStream("/image/GalaxyGame.jpg"));
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh nền game: " + e.getMessage());
            backgroundImage = null; // Để null nếu không tải được
        }

        try { //GameOver Screen
            gameOverImage = new Image(getClass().getResourceAsStream("/image/GameOver.png"));
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh GAME OVER: " + e.getMessage());
            gameOverImage = null; // Để null nếu không tải được
        }

        try { //GameWin Screen
            winImage = new Image(getClass().getResourceAsStream("/image/YouWin.jpg"));
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh WIN: " + e.getMessage());
            winImage = null; // Để null nếu không tải được
        }

        // --- KẾT THÚC TẢI ẢNH ---
    }

    /**
     * Phương thức render chính, được gọi mỗi frame bởi AnimationTimer.
     *
     * @param manager GameManager chứa tất cả các đối tượng game.
     */
    public void render(GameManager manager) {

        // 1. Lấy trạng thái game (nhờ vào getter ta vừa thêm)
        String gameState = manager.getGameState();

        // 2. Vẽ ảnh nền (hoặc màu đen nếu ảnh lỗi)
        if (backgroundImage != null) {
            // Vẽ ảnh nền, kéo dãn ra cho vừa màn hình
            gc.drawImage(backgroundImage, 0, 0, width, height);
        } else {
            // Nếu không có ảnh, vẽ màu đen như cũ
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, width, height);
        }

        // 3. KIỂM TRA TRẠNG THÁI
        if (gameState.equals("GAME_OVER")) {

            // Nếu thua -> Vẽ màn hình GAME OVER
            renderGameOverScreen(manager.getScore());

        } else if (gameState.equals("WIN")) {
            // Nếu thắng -> Vẽ màn hình WIN
            renderWinScreen(manager.getScore());
        } else {

            // Nếu chưa thua (PLAYING, READY, v.v.) -> Vẽ game như bình thường

            // 2. Vẽ quả bóng
            if (manager.getBall() != null) {
                manager.getBall().render(gc);
            }

            // 3. Vẽ thanh trượt
            if (manager.getPaddle() != null) {
                manager.getPaddle().render(gc);
            }

            // 4. Vẽ tất cả các viên gạch
            if (manager.getBricks() != null) {
                for (Brick brick : manager.getBricks()) {
                    if (!brick.isDestroyed()) { // Chỉ vẽ gạch chưa bị phá
                        brick.render(gc);
                    }
                }
            }

            // 5. Vẽ tất cả các Power-up đang rơi
            if (manager.getPowerUps() != null) {
                // Lấy danh sách power-up từ manager
                for (PowerUp pu : manager.getPowerUps()) {
                    pu.render(gc); // Gọi hàm render của PowerUp
                }
            }

            // 6. Vẽ tất cả các viên Đạn (Bullet)
            if (manager.getBullets() != null) {
                for (Bullet bullet : manager.getBullets()) {
                    bullet.render(gc);
                }
            }

            // 7. Vẽ điểm số và mạng sống
            renderGameInfo(manager.getScore(), manager.getLives(), manager.getCurrentLevel());
            if (gameState.equals("PAUSED")) {
                renderPausedScreen(); // Gọi hàm vẽ màn hình "STOP"
            }
        }
    }

    /**
     * Vẽ thông tin game (Điểm, Mạng) lên màn hình.
     */
    private void renderGameInfo(int score, int lives, int level) {
        gc.setFill(Color.WHITE); // Đặt màu trắng
        gc.setFont(new Font("Arial", 20)); // Đặt font và cỡ chữ

        // Vẽ điểm
        gc.fillText("Score: " + score, 10, 25); // 10px từ trái, 25px từ trên

        // Vẽ mạng sống
        gc.fillText("Lives: " + lives, width - 80, 25); // Cách lề phải 80px

        String levelText = "Level: " + level;
        gc.fillText(levelText, width / 2 - 40, 25);
    }

    /**
     * Vẽ màn hình WIN
     */
    private void renderWinScreen(int finalScore) {
        if (winImage != null) {
            // Vẽ ảnh 'winImage' lên toàn màn hình
            gc.drawImage(winImage, 0, 0, width, height);
        } else {
            // Dự phòng: Nếu ảnh lỗi, vẽ nền đen và chữ
            gc.setFill(new Color(0, 0.1, 0.3, 0.8)); // Nền xanh đậm
            gc.fillRect(0, 0, width, height);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFill(Color.YELLOW);
            gc.setFont(new Font("Arial", 72));
            gc.fillText("YOU WIN!", width / 2, height / 2 - 40);
        }
            gc.setFill(new Color(0, 0, 0, 0.5)); // Đen mờ 50%
            gc.fillRect(0, height / 2 - 30, width, 250); // Một dải ở giữa

            //Căn chữ ra giữa
            gc.setTextAlign(TextAlignment.CENTER);

            //Vẽ điểm số cuối cùng
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Arial", 36));
            gc.fillText("Final Score: " + finalScore, width / 2, height / 2 + 20);

            //Vẽ các nút
                drawEndGameOptions(height / 2 + 60); // Gọi hàm mới

            //Reset lại căn lề
                gc.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Vẽ màn hình GAME OVER
     */
    private void renderGameOverScreen(int finalScore) {

        if (gameOverImage != null) {
            // Vẽ ảnh 'gameOverImage' lên toàn màn hình
            gc.drawImage(gameOverImage, 0, 0, width, height);
        }

        // Vẽ một lớp nền đen mờ (cho đẹp)
        gc.setFill(new Color(0, 0, 0, 0.7)); // Đen, mờ 70%
        gc.fillRect(0, 0, width, height);

        // Căn chữ ra giữa
        gc.setTextAlign(TextAlignment.CENTER);
        // Vẽ chữ "GAME OVER"
        if (gameOverImage == null) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Arial", 72));
            gc.fillText("GAME OVER", width / 2, height / 2 - 40);
        }
        // Vẽ điểm số cuối cùng
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 36));
        gc.fillText("Final Score: " + finalScore, width / 2, height / 2 + 20);

        // Hướng dẫn quay về menu
        gc.setFont(new Font("Arial", 18));
        drawEndGameOptions(height / 2 + 60);

        // Reset lại căn lề
        gc.setTextAlign(TextAlignment.LEFT);
    }
    /**
     * THÊM MỚI: Hàm trợ giúp để vẽ các tùy chọn cuối game
     */
    private void drawEndGameOptions(double startY) {
        double btnWidth = 200;
        double btnHeight = 40;
        double spacing = 15;
        double centerX = width / 2;

        // Nút "ESC to Back"
        double btn1Y = startY;
        double btn1X = centerX - btnWidth / 2;
        //  Lưu tọa độ nút
        this.escButtonBounds = new Rectangle(btn1X, btn1Y, btnWidth, btnHeight);
        // Vẽ nền nút
        gc.setFill(new Color(0.2, 0.2, 0.2, 0.8));
        gc.fillRoundRect(btn1X, btn1Y, btnWidth, btnHeight, 15, 15); // Góc bo tròn

        // Vẽ chữ
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 18));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("ESC to Back", centerX, btn1Y + 27); // Căn Y thủ công

        // Nút "Rank"
        double btn2Y = btn1Y + btnHeight + spacing;
        double btn2X = centerX - btnWidth / 2;
        //Lưu lại tọa độ nút
        this.rankButtonBounds = new Rectangle(btn2X, btn2Y, btnWidth, btnHeight);
        // Vẽ nền nút
        gc.setFill(new Color(0.2, 0.2, 0.2, 0.8));
        gc.fillRoundRect(btn2X, btn2Y, btnWidth, btnHeight, 15, 15);

        // Vẽ chữ
        gc.setFill(Color.WHITE);
        gc.fillText("Save Score", centerX, btn2Y + 27);

        // Reset lại căn lề (quan trọng)
        gc.setTextAlign(TextAlignment.LEFT);
    }
    /**
     * Vẽ màn hình "STOP" (PAUSED)
     */
    private void renderPausedScreen() {
        // 1. Vẽ một lớp nền mờ (che lên trên game)
        gc.setFill(new Color(0, 0, 0, 0.5)); // Đen, mờ 50%
        gc.fillRect(0, 0, width, height);

        // 2. Căn chữ ra giữa
        gc.setTextAlign(TextAlignment.CENTER);

        // 3. Vẽ chữ "STOP"
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 60));
        gc.fillText("PAUSE", width / 2, height / 2);

        // 4. Vẽ hướng dẫn
        gc.setFont(new Font("Arial", 18));
        gc.fillText("Nhấn ESC để tiếp tục", width / 2, height / 2 + 40);

        // 5. Reset lại căn lề
        gc.setTextAlign(TextAlignment.LEFT);
    }
    public Rectangle getEscButtonBounds() {
        return escButtonBounds;
    }

    public Rectangle getRankButtonBounds() {
        return rankButtonBounds;
    }
}