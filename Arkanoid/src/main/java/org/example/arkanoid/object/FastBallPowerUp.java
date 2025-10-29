package org.example.arkanoid.object;

import org.example.arkanoid.control.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *  Yêu cầu GameManager lấy Ball và thay đổi tốc độ của nó
 */

public class FastBallPowerUp extends PowerUp {
    public FastBallPowerUp(double x, double y) {
        super(x, y, 30, 15, "FAST_BALL", 5000);
    }

    @Override
    public void applyEffect(GameManager manager) {
        Ball ball = manager.getBall();
        ball.setDx(ball.getDx() * 1.5);
        ball.setDy(ball.getDy() * 1.5);
    }

    @Override
    public void removeEffect(GameManager manager) {
        Ball ball = manager.getBall();
        ball.setDx(ball.getDx() / 1.5);
        ball.setDy(ball.getDy() / 1.5);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, width, height);
    }
}