package org.example.arkanoid.control;

import javafx.scene.Group;
import org.example.arkanoid.object.GameObject;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;


public class Renderer {
    private Group root;

    public Renderer(Group root) {
        this.root = root;
    }

    public void draw(GameObject object) {
        if (object instanceof Ball ball) {
            root.getChildren().add(ball.getShape());
        } else if (object instanceof Paddle paddle) {
            root.getChildren().add(paddle.getShape());
        }
        // Có thể thêm xử lý cho Brick, Paddle, v.v.
    }

}
