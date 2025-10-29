package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends MovableObject {

    private double radius;

    public Ball(double x, double y, double radius, double dx, double dy) {
        // x, y là tâm của quả bóng
        // super() cần tọa độ (x, y) góc trên bên trái
        super(x - radius, y - radius, radius * 2, radius * 2, dx, dy);
        this.radius = radius;
    }

    @Override
    public void update() {
        move(); // Gọi phương thức move() từ lớp cha MovableObject
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        // fillOval vẽ từ góc trên bên trái
        gc.fillOval(x, y, width, height);
    }

    // Bạn có thể thêm các phương thức khác ở đây (ví dụ: bounce, setSpeed, v.v.)
    public double getRadius() {
        return radius;
    }

    // Ghi đè setters để đảm bảo x, y luôn là góc trên trái
    public void setCenterX(double centerX) {
        this.x = centerX - radius;
    }

    public void setCenterY(double centerY) {
        this.y = centerY - radius;
    }
}