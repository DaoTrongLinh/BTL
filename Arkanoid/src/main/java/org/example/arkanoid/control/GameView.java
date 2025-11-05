package org.example.arkanoid.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.arkanoid.object.Brick;
import org.example.arkanoid.object.Bullet;
import org.example.arkanoid.object.PowerUp;

import javafx.scene.image.Image; //import để thêm ảnh

import javafx.scene.text.TextAlignment; //import để thêm màn hình endgame

/**
 * Chịu trách nhiệm cho tất cả việc VẼ lên màn hình.
 * Nó lấy dữ liệu từ GameManager và render chúng lên Canvas.
 */
public class GameView {

    private GraphicsContext gc;
    private double width;
    private double height;

    private Image backgroundImage; // <-- BIẾN MỚI ĐỂ LƯU ẢNH NỀN
    private Image gameOverImage; // Biến để lưu ảnh kết thúc
    private Image winImage; //Biến để lưu ảnh lúc thắng

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
        // --- THAY ĐỔI CÁCH VẼ NỀN ---

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
        // --- KẾT THÚC THAY ĐỔI ---

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
            renderGameInfo(manager.getScore(), manager.getLives());
        }
    }

    /**
     * Vẽ thông tin game (Điểm, Mạng) lên màn hình.
     */
    private void renderGameInfo(int score, int lives) {
        gc.setFill(Color.WHITE); // Đặt màu trắng để nổi bật
        gc.setFont(new Font("Arial", 20)); // Đặt font và cỡ chữ

        // Vẽ điểm
        gc.fillText("Score: " + score, 10, 25); // 10px từ trái, 25px từ trên

        // Vẽ mạng sống
        gc.fillText("Lives: " + lives, width - 80, 25); // Cách lề phải 80px
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
    }

    /**
     * Vẽ màn hình GAME OVER
     */
    private void renderGameOverScreen(int finalScore) {

        if (gameOverImage != null) {
            // Vẽ ảnh 'gameOverImage' lên toàn màn hình
            gc.drawImage(gameOverImage, 0, 0, width, height);
        }

        // 1. Vẽ một lớp nền đen mờ (cho đẹp)
        gc.setFill(new Color(0, 0, 0, 0.7)); // Đen, mờ 70%
        gc.fillRect(0, 0, width, height);

        // 2. Căn chữ ra giữa
        gc.setTextAlign(TextAlignment.CENTER);
        // 3. Vẽ chữ "GAME OVER"
        if (gameOverImage == null) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Arial", 72));
            gc.fillText("GAME OVER", width / 2, height / 2 - 40);
        }
        // 4. Vẽ điểm số cuối cùng
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 36));
        gc.fillText("Final Score: " + finalScore, width / 2, height / 2 + 20);

        // 5. Hướng dẫn quay về menu
        gc.setFont(new Font("Arial", 18));
        gc.fillText("Press ESC to Return to Menu", width / 2, height / 2 + 80);

        // 6. Reset lại căn lề
        gc.setTextAlign(TextAlignment.LEFT);
    }
}