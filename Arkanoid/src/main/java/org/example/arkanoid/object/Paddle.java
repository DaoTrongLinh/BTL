package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {

    private double originalWidth; // Lưu chiều rộng ban đầu để reset power-up

    public Paddle(double x, double y, double width, double height) {
        // dx và dy ban đầu là 0 vì nó không tự di chuyển
        super(x, y, width, height, 0, 0);
        this.originalWidth = width;
    }

    @Override
    public void update() {
        // Để trống.
        // Vị trí của Paddle sẽ được cập nhật trực tiếp
        // bằng setX() từ GameManager hoặc ArkanoidApp (dựa trên input của chuột).
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, width, height);
    }

    // Các phương thức này sẽ được gọi bởi Power-up
    public void setWidth(double width) {
        // Khi thay đổi kích thước, giữ nó ở giữa
        double center = this.x + this.width / 2;
        this.width = width;
        this.x = center - this.width / 2;
    }

    public double getOriginalWidth() {
        return originalWidth;
    }

    // Ghi đè setX để ngăn thanh trượt ra khỏi màn hình
    public void setX(double x, double screenWidth) {
        if (x < 0) {
            this.x = 0;
        } else if (x + this.width > screenWidth) {
            this.x = screenWidth - this.width;
        } else {
            this.x = x;
        }
    }
}