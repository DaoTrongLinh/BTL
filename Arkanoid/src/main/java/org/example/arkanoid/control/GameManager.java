package org.example.arkanoid.control;

import java.util.ArrayList;
import java.util.Iterator;

import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick;
import org.example.arkanoid.object.Paddle;

public class GameManager {
    private final Paddle paddle;
    private final Ball ball;
    private final ArrayList<Brick> bricks;
    private boolean leftPressed, rightPressed;

    public GameManager() {
        paddle = new Paddle(350, 550, 100, 15);
        ball = new Ball(400, 300, 10, 4, -4);
        bricks = new ArrayList<>();
        initBricks();
    }

    private void initBricks() {
        for (int i = 0; i < 8; i++) {
            bricks.add(new Brick(80 * i + 40, 50, 60, 20));
        }
    }

    public void update() {
        if (leftPressed) paddle.moveLeft();
        if (rightPressed) paddle.moveRight();

        ball.update();
        ball.checkWallCollision(800, 600);
        ball.checkPaddleCollision(paddle);

        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            if (ball.checkBrickCollision(brick)) {
                it.remove();
            }
        }
    }

    public Paddle getPaddle() { return paddle; }
    public Ball getBall() { return ball; }
    public ArrayList<Brick> getBricks() { return bricks; }

    // Setter cho input
    public void setLeftPressed(boolean val) { this.leftPressed = val; }
    public void setRightPressed(boolean val) { this.rightPressed = val; }
}

