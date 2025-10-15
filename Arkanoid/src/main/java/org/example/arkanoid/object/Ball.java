package org.example.arkanoid.object;

public class Ball {
    private int x, y, radius;
    private int dx, dy;

    public Ball(int x, int y, int radius, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void checkWallCollision(int width, int height) {
        if (x - radius <= 0 || x + radius >= width) {
            dx = -dx;
        }
        if (y - radius <= 0) {
            dy = -dy;
        }
        // Nếu rơi xuống dưới thì reset
        if (y - radius > height) {
            x = width / 2;
            y = height / 2;
            dx = 4;
            dy = -4;
        }
    }

    public void checkPaddleCollision(Paddle paddle) {
        if (x + radius >= paddle.getX() &&
                x - radius <= paddle.getX() + paddle.getWidth() &&
                y + radius >= paddle.getY() &&
                y - radius <= paddle.getY() + paddle.getHeight()) {
            dy = -dy;
        }
    }

    // ✅ Các getter để Renderer.java gọi
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    // ✅ Getter & Setter cho dx, dy (nếu cần sau này)
    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
    public boolean checkBrickCollision(Brick brick) {
        int brickX = brick.getX();
        int brickY = brick.getY();
        int brickW = brick.getWidth();
        int brickH = brick.getHeight();

        // Nếu hình tròn (ball) giao với hình chữ nhật (brick)
        if (x + radius >= brickX && x - radius <= brickX + brickW &&
                y + radius >= brickY && y - radius <= brickY + brickH) {
            dy = -dy; // Bóng bật ngược lại
            return true;
        }
        return false;
    }

}
