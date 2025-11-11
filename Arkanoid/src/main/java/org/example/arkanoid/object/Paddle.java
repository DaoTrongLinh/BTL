package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Paddle extends MovableObject {

    private double originalWidth; // Lưu chiều rộng ban đầu để reset power-up
    private boolean laserActive = false; // Biến cờ để kiểm tra trạng thái bắn
    private static Image PADDLE_NORMAL_IMAGE;
    private static Image PADDLE_LASER_IMAGE; // Ảnh khi có laser
    static {
        try {
            // Tải ảnh paddle thường
            PADDLE_NORMAL_IMAGE = new Image(Paddle.class.getResourceAsStream("/Image/Paddle.jpg"));

            // Tải ảnh paddle khi có laser
            PADDLE_LASER_IMAGE = new Image(Paddle.class.getResourceAsStream("/Image/PaddleLaser.png"));

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải ảnh Paddle!");
        }
    }

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
            if (PADDLE_LASER_IMAGE != null) {
                gc.drawImage(PADDLE_LASER_IMAGE, x, y, width, height);
            }
        } else {
            if (PADDLE_NORMAL_IMAGE != null) {
                gc.drawImage(PADDLE_NORMAL_IMAGE, x, y, width, height);
            }
        }
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