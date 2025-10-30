package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {

    private double originalWidth; // Lưu chiều rộng ban đầu để reset power-up
    private boolean laserActive = false; // Biến cờ để kiểm tra trạng thái bắn

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
        if (laserActive) {
            gc.setFill(Color.CRIMSON); // Màu đỏ khi có laser
        } else {
            gc.setFill(Color.BLUE); // Màu bình thường
        }
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

    /**
     * Kích hoạt hoặc vô hiệu hóa khả năng bắn laser
     * (Được gọi bởi LaserPowerUp)
     */
    public void setLaserActive(boolean active) {
        this.laserActive = active;
    }

    /**
     * Kiểm tra xem paddle có đang bắn laser không
     * (Được gọi bởi GameManager khi bắn)
     */
    public boolean isLaserActive() {
        return this.laserActive;
    }
}