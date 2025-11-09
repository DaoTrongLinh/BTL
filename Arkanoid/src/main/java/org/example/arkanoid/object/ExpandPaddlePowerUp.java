package org.example.arkanoid.object;

import org.example.arkanoid.control.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * Yêu cầu GameManager lấy Paddle và thay đổi độ rộng của nó
 */

public class ExpandPaddlePowerUp extends PowerUp {
    private static Image EXPAND_IMAGE;
    static {
        try {
            // Tải ảnh 1 lần duy nhất
            EXPAND_IMAGE = new Image(ExpandPaddlePowerUp.class.getResourceAsStream("/Image/ExpandPowerUp.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải ảnh Expand PowerUp!");
        }
    }

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y, 30, 15, "EXPAND", 50000); // 30x15 là kích thước, 50000ms
    }

    @Override
    public void applyEffect(GameManager manager) {
        Paddle paddle = manager.getPaddle();
        paddle.setWidth(paddle.getOriginalWidth() * 1.5);
    }

    @Override
    public void removeEffect(GameManager manager) {
        Paddle paddle = manager.getPaddle();
        paddle.setWidth(paddle.getOriginalWidth());
    }

    @Override
    public void render(GraphicsContext gc) {
        if (EXPAND_IMAGE != null) {
            gc.drawImage(EXPAND_IMAGE, x, y, width, height);
        } else {
            gc.setFill(Color.AQUAMARINE);
            gc.fillRect(x, y, width, height);
        }
    }
}