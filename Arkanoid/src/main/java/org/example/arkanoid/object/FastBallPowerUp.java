package org.example.arkanoid.object;

// Đảm bảo bạn đã import 2 dòng này
import org.example.arkanoid.control.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FastBallPowerUp extends PowerUp {

    public FastBallPowerUp(double x, double y) {
        super(x, y, 30, 15, "FAST_BALL", 5000);
    }

    // Đây là phương thức applyEffect ĐÚNG
    @Override
    public void applyEffect(GameManager manager) {
        Ball ball = manager.getBall();
        ball.setDx(ball.getDx() * 1.5);
        ball.setDy(ball.getDy() * 1.5);
    }

    // Đây là phương thức removeEffect ĐÚNG
    @Override
    public void removeEffect(GameManager manager) {
        Ball ball = manager.getBall();
        ball.setDx(ball.getDx() / 1.5);
        ball.setDy(ball.getDy() / 1.5);
    }

    // Phương thức render
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, width, height);
    }
}