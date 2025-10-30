package org.example.arkanoid.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.arkanoid.object.Brick;
import org.example.arkanoid.object.PowerUp;

import javafx.scene.image.Image; //import để thêm ảnh

/**
 * Chịu trách nhiệm cho tất cả việc VẼ lên màn hình.
 * Nó lấy dữ liệu từ GameManager và render chúng lên Canvas.
 */
public class GameView {

    private GraphicsContext gc;
    private double width;
    private double height;

    private Image backgroundImage; // <-- BIẾN MỚI ĐỂ LƯU ẢNH NỀN

    public GameView(GraphicsContext gc) {
        this.gc = gc;
        this.width = gc.getCanvas().getWidth();
        this.height = gc.getCanvas().getHeight();

        // --- TẢI ẢNH NỀN CHO GAME TẠI ĐÂY ---
        try {
            // Đảm bảo bạn có ảnh "game_background.png" trong thư mục "/images/"
            backgroundImage = new Image(getClass().getResourceAsStream("/image/GalaxyGame.jpg"));
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh nền game: " + e.getMessage());
            backgroundImage = null; // Để null nếu không tải được
        }
        // --- KẾT THÚC TẢI ẢNH ---
    }

    /**
     * Phương thức render chính, được gọi mỗi frame bởi AnimationTimer.
     * @param manager GameManager chứa tất cả các đối tượng game.
     */
    public void render(GameManager manager) {
        // --- THAY ĐỔI CÁCH VẼ NỀN ---
        // 1. Vẽ ảnh nền (hoặc màu đen nếu ảnh lỗi)
        if (backgroundImage != null) {
            // Vẽ ảnh nền, kéo dãn ra cho vừa màn hình
            gc.drawImage(backgroundImage, 0, 0, width, height);
        } else {
            // Nếu không có ảnh, vẽ màu đen như cũ
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, width, height);
        }
        // --- KẾT THÚC THAY ĐỔI ---


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

        // 6. Vẽ điểm số và mạng sống
        renderGameInfo(manager.getScore(), manager.getLives());
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
}