package org.example.arkanoid.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.arkanoid.object.Brick;

/**
 * Chịu trách nhiệm cho tất cả việc VẼ lên màn hình.
 * Nó lấy dữ liệu từ GameManager và render chúng lên Canvas.
 */
public class GameView {

    private GraphicsContext gc;
    private double width;
    private double height;

    public GameView(GraphicsContext gc) {
        this.gc = gc;
        this.width = gc.getCanvas().getWidth();
        this.height = gc.getCanvas().getHeight();
    }

    /**
     * Phương thức render chính, được gọi mỗi frame bởi AnimationTimer.
     * @param manager GameManager chứa tất cả các đối tượng game.
     */
    public void render(GameManager manager) {
        // 1. Xóa toàn bộ màn hình
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

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

        // 5. Vẽ điểm số và mạng sống
        renderGameInfo(manager.getScore(), manager.getLives());
    }

    /**
     * Vẽ thông tin game (Điểm, Mạng) lên màn hình.
     */
    private void renderGameInfo(int score, int lives) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));

        // Hiển thị điểm
        gc.fillText("Score: " + score, 10, 25);

        // Hiển thị mạng sống
        gc.fillText("Lives: " + lives, width - 80, 25);
    }
}