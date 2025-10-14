package org.example.arkanoid.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Ball extends MovableObject {
    private Circle shape;
    private int speed;
    private int directionX;
    private int directionY;

    public Ball(int x, int y, int radius, int speed) {
        this.x = x;
        this.y = y;
        this.width = radius * 2;
        this.height = radius * 2;
        this.speed = speed;
        this.directionX = 1;
        this.directionY = -1;

        shape = new Circle(radius, Color.BLACK);
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }

    public Circle getShape() {
        return shape;
    }

    @Override
    public void update() {
        x += directionX * speed;
        y += directionY * speed;

        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }

    public void bounceX() {
        directionX *= -1;
    }

    public void bounceY() {
        directionY *= -1;
    }

    //phan xa
    public void bounceOff(GameObject other){}
    //va cham
    public void checkCollision(GameObject other){}

    @Override
    public void render() {}
}
