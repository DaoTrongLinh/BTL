package org.example.arkanoid.object;

import org.example.arkanoid.control.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Vật phẩm kích hoạt chế độ Penalty Mode.
 */
public class PenaltyPowerUp extends PowerUp {
    public PenaltyPowerUp(double x, double y) {
        // x, y, width, height, type, duration
        super(x, y, 30, 15, "PENALTY", 0);
    }

    /**
     * Kích hoạt chế độ Penalty trong GameManager.
     */
    @Override
    public void applyEffect(GameManager manager) {
        manager.activatePenaltyMode();
    }

    @Override
    public void removeEffect(GameManager manager) {
        // Không cần làm gì, hiệu ứng xảy ra ngay lập tức
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.WHITE); // Power-up màu trắng
        gc.fillOval(x, y, width, height);
    }
}