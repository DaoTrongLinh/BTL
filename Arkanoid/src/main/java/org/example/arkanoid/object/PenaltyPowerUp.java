package org.example.arkanoid.object;

import org.example.arkanoid.control.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * Vật phẩm kích hoạt chế độ Penalty Mode.
 */
public class PenaltyPowerUp extends PowerUp {
    private static Image PENALTY_IMAGE;
    static {
        try {
            // Tải ảnh 1 lần duy nhất
            PENALTY_IMAGE = new Image(PenaltyPowerUp.class.getResourceAsStream("/Image/PenaltyPowerUp.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải ảnh Penalty PowerUp!");
        }
    }

    public PenaltyPowerUp(double x, double y) {
        // x, y, width, height, type, duration
        super(x, y, 30, 15, "PENALTY", 0);
    }

    /**
     * Kích hoạt chế độ Penalty trong GameManager.
     */
    @Override
    public void applyEffect(GameManager manager) {
        manager.setPenaltyModePending();
    }

    @Override
    public void removeEffect(GameManager manager) {
        // Không cần làm gì, hiệu ứng xảy ra ngay lập tức
    }

    @Override
    public void render(GraphicsContext gc) {
        if (PENALTY_IMAGE != null) {
            gc.drawImage(PENALTY_IMAGE, x, y, width, height);
        } else {
            gc.setFill(Color.WHITE); // Power-up màu trắng
            gc.fillOval(x, y, width, height);
        }
    }
}