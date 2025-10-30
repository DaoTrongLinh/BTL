package org.example.arkanoid.object;

import org.example.arkanoid.control.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Vật phẩm Power-up cho phép Paddle bắn đạn.
 * Kế thừa từ PowerUp.
 */
public class LaserPowerUp extends PowerUp {

    public LaserPowerUp(double x, double y) {
        // Gọi constructor của PowerUp:
        // x, y, width, height, type, duration
        super(x, y, 30, 15, "LASER", 0); // Duration = 0 (hiệu lực vĩnh viễn cho đến khi mất mạng)
    }

    /**
     * Kích hoạt khả năng bắn của Paddle.
     * Triển khai phương thức abstract từ PowerUp.
     */
    @Override
    public void applyEffect(GameManager manager) {
        // Báo cho Paddle biết nó có thể bắn
        // (Chúng ta sẽ thêm hàm setLaserActive() vào Paddle ở bước tiếp theo)
        manager.getPaddle().setLaserActive(true);
    }

    /**
     * Gỡ bỏ hiệu ứng.
     * (Việc gỡ bỏ sẽ do GameManager xử lý khi người chơi mất mạng)
     * Triển khai phương thức abstract từ PowerUp.
     */
    @Override
    public void removeEffect(GameManager manager) {
        // Có thể để trống, vì GameManager sẽ gọi
        // paddle.setLaserActive(false) khi reset màn
        manager.getPaddle().setLaserActive(false);
    }

    /**
     * Vẽ vật phẩm Power-up.
     * Triển khai phương thức abstract từ GameObject.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED); // Power-up màu đỏ
        gc.fillOval(x, y, width, height); // Vẽ hình oval cho khác biệt
    }
}