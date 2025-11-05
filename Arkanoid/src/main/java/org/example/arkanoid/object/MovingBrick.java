package org.example.arkanoid.object;

import org.example.arkanoid.control.GameManager; // Cần để lấy WIDTH
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Gạch đặc biệt cho màn Penalty.
 * Nó tự di chuyển theo chiều ngang và nảy lại.
 */
public class MovingBrick extends Brick {

    private double dx; // Vận tốc ngang

    public MovingBrick(double x, double y, double width, double height) {
        // Gọi lớp cha: x, y, width, height
        // hits = 1 (1 lần va chạm để vỡ)
        // points = 250 (thưởng lớn khi thắng penalty)
        super(x, y, width, height, 1, 250);

        this.dx = 5.0; // Tốc độ di chuyển của gạch
    }

    /**
     * Ghi đè phương thức update() để gạch tự di chuyển.
     */
    @Override
    public void update() {
        this.x += dx; // Di chuyển

        // Kiểm tra va chạm tường
        // (Lưu ý: chúng ta dùng lề 50 như trong GameManager)
        double leftWall = 50;
        double rightWall = GameManager.WIDTH - 50;

        if (this.x <= leftWall) {
            this.x = leftWall; // Đặt lại vị trí
            this.dx = -this.dx; // Đảo chiều
        } else if (this.x + this.width >= rightWall) {
            this.x = rightWall - this.width; // Đặt lại vị trí
            this.dx = -this.dx; // Đảo chiều
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.GOLD); // Màu vàng cho gạch đặc biệt
        gc.fillRect(x, y, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, width, height);
    }
}