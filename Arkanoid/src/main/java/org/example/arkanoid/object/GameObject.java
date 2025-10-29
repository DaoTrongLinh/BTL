package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

/**
 * Lớp trừu tượng cơ sở cho tất cả các đối tượng trong game.
 * Định nghĩa vị trí, kích thước và các hành vi cơ bản.
 */
public abstract class GameObject {
    protected double x, y;
    protected double width, height;

    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Phương thức trừu tượng để cập nhật trạng thái của đối tượng (ví dụ: di chuyển).
     * Sẽ được gọi trong mỗi frame của game loop.
     */
    public abstract void update();

    /**
     * Phương thức trừu tượng để vẽ đối tượng lên màn hình.
     * @param gc GraphicsContext của Canvas để vẽ.
     */
    public abstract void render(GraphicsContext gc);

    /**
     * Lấy hình chữ nhật bao quanh đối tượng.
     * Rất hữu ích cho việc kiểm tra va chạm.
     * @return một đối tượng Rectangle2D đại diện cho biên của GameObject.
     */
    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    /**
     * Kiểm tra va chạm với một GameObject khác.
     */
    public boolean intersects(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }

    // Getters và Setters cơ bản
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}