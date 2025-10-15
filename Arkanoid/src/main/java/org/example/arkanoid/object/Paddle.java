package org.example.arkanoid.object;

public class Paddle {
    private int x, y, width, height;
    private int speed = 6;

    public Paddle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveLeft() {
        x = Math.max(0, x - speed);
    }

    public void moveRight() {
        x = Math.min(800 - width, x + speed); // Giới hạn trong khung 800px
    }

    // ✅ Getter cho Renderer và Ball sử dụng
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}


