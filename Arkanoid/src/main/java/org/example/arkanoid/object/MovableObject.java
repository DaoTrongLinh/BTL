package org.example.arkanoid.object;

/**
 * Lớp trừu tượng cho các đối tượng có thể di chuyển.
 * Kế thừa từ GameObject và thêm các thuộc tính vận tốc (dx, dy).
 */
public abstract class MovableObject extends GameObject {
    protected double dx, dy; // Vận tốc theo trục x và y

    public MovableObject(double x, double y, double width, double height, double dx, double dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Phương thức di chuyển đối tượng dựa trên vận tốc dx, dy.
     * Thường được gọi bên trong phương thức update().
     */
    public void move() {
        x += dx;
        y += dy;
    }

    // Getters và Setters cho vận tốc
    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void reverseDx() {
        this.dx = -this.dx;
    }

    public void reverseDy() {
        this.dy = -this.dy;
    }
}