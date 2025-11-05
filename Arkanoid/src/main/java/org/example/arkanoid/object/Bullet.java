package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Lớp đạn được bắn ra từ Paddle.
 * Kế thừa từ MovableObject vì nó có vận tốc.
 */
public class Bullet extends MovableObject {

    public Bullet(double x, double y) {
        // Gọi constructor của MovableObject:
        // x, y, width, height, dx, dy
        super(x, y, 5, 15, 0, -8.0); // Kích thước 5x15, dx=0, dy=-8 (bay lên)
    }

    /**
     * Cập nhật vị trí của đạn.
     * Triển khai phương thức abstract từ GameObject.
     */
    @Override
    public void update() {
        // Gọi phương thức move() từ MovableObject
        move();
    }

    /**
     * Vẽ viên đạn.
     * Triển khai phương thức abstract từ GameObject.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED); // Đạn màu đỏ
        gc.fillRect(x, y, width, height);
    }

    /**
     * Phương thức tiện ích để kiểm tra xem đạn đã bay ra khỏi màn hình chưa
     *
     * @param screenHeight Chiều cao màn hình (từ GameManager.HEIGHT)
     * @return true nếu đạn ra khỏi màn hình
     */
    public boolean isOffScreen(double screenHeight) {
        // Đạn bay ra khỏi cạnh trên
        return (this.y + this.height < 0);
    }
}