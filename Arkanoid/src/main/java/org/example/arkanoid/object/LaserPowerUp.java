package org.example.arkanoid.object;

import org.example.arkanoid.control.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * Vật phẩm Power-up cho phép Paddle bắn đạn.
 * Kế thừa từ PowerUp.
 */
public class LaserPowerUp extends PowerUp {

    private static Image LASER_IMAGE;
    static {
        try {
            // Tải ảnh 1 lần duy nhất
            LASER_IMAGE = new Image(LaserPowerUp.class.getResourceAsStream("/Image/LaserPowerUp.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải ảnh Laser PowerUp!");
        }
    }

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
        if (LASER_IMAGE != null) {
            gc.drawImage(LASER_IMAGE, x, y, width, height);
        } else {
            // Dự phòng: Nếu không tải được ảnh, vẽ màu như cũ
            gc.setFill(Color.RED);
            gc.fillOval(x, y, width, height);
        }
    }
}