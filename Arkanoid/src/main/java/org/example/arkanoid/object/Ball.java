package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ball extends MovableObject {

    private double radius;
    private static Image BALL_IMAGE;
    static {
        try {
            // Tải ảnh "Ball.png" từ thư mục /Image/
            BALL_IMAGE = new Image(Ball.class.getResourceAsStream("/Image/Ball.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải ảnh Ball.png!");
        }
    }

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
        if (BALL_IMAGE != null) {
            gc.drawImage(BALL_IMAGE, x, y, width, height);
        }
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