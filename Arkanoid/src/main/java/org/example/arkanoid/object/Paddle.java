package org.example.arkanoid.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends MovableObject {
    private int speed;
    private int currentPowerUp;
    private Rectangle shape;

    public Paddle(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.currentPowerUp = 0;

        shape = new Rectangle(width, height, Color.BLUE);
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }

    public Rectangle getShape() {
        return shape;
    }

    public void moveLeft(){
        if (x > 0) {
            x -= speed;
            shape.setTranslateX(x);
        }
    }

    public void moveRight(){
        if (x + width < 800) {
            x += speed;
            shape.setTranslateX(x);
        }
    }

    public void applyPowerUp(){
        if (currentPowerUp == 1) {
            width += 30;
            shape.setWidth(width);
        }
        // Có thể thêm nhiều loại power up khác
    }

    @Override
    public void update() {
        // Có thể thêm điều khiển bằng phím sau
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }
    @Override
    public void render() {}
}
