package org.example.arkanoid.control;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.example.arkanoid.object.*;

public class Renderer {
    private final Group root;

    public Renderer(Group root) {
        this.root = root;
    }

    public void render(GameManager gm) {
        root.getChildren().clear();

        Ball ball = gm.getBall();
        Paddle paddle = gm.getPaddle();

        // Bóng
        Circle circle = new Circle(ball.getX(), ball.getY(), ball.getRadius(), Color.RED);
        root.getChildren().add(circle);

        // Paddle
        Rectangle paddleRect = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        paddleRect.setFill(Color.BLUE);
        root.getChildren().add(paddleRect);

        // Gạch
        for (Brick brick : gm.getBricks()) {
            Rectangle rect = new Rectangle(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            rect.setFill(Color.ORANGE);
            rect.setStroke(Color.BLACK);
            root.getChildren().add(rect);
        }
    }
}


