package org.example.arkanoid.object;

import org.example.arkanoid.control.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y, 30, 15, "EXPAND", 5000); // 30x15 là kích thước, 5000ms
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
        gc.setFill(Color.AQUAMARINE);
        gc.fillRect(x, y, width, height);
    }
}