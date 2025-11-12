package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * Gạch tàng hình 3 hit
 */
public class InvisibleBrick extends Brick {
    private static Image VISIBLE_IMAGE;
    private static Image CRACKED_IMAGE;
    static {
        try {
            VISIBLE_IMAGE = new Image(InvisibleBrick.class.getResourceAsStream("/Image/VisibleBrick.png"));
            CRACKED_IMAGE = new Image(InvisibleBrick.class.getResourceAsStream("/Image/CrackedBrick.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải ảnh gạch tàng hình!");
        }
    }

    public InvisibleBrick(double x, double y, double width, double height) {
        // Gọi lớp cha (Brick) với:
        // hits = 3 (3 lần va chạm để vỡ)
        // points = 25
        super(x, y, width, height, 3, 25);
    }

    /**
     * Ghi đè phương thức render() để xử lý logic tàng hình.
     */
    @Override
    public void render(GraphicsContext gc) {

        // --- LOGIC TÀNG HÌNH ---
        // GameView vẫn gọi render() vì gạch chưa bị phá hủy (isDestroyed = false)
        // Chúng ta sẽ chủ động KHÔNG VẼ GÌ CẢ nếu hits == 3.
        if (this.hits == 3) {
            // Không làm gì, gạch vẫn vô hình
            return;
        }
        // --- KẾT THÚC LOGIC ---

        // Nếu 'hits' không phải là 3, gạch sẽ được vẽ:
        if (this.hits == 2) {
            // Trạng thái 1: Vừa hiện hình
            if (VISIBLE_IMAGE != null) {
                gc.drawImage(VISIBLE_IMAGE, x, y, width, height);
            } else {
                gc.setFill(Color.CYAN); // Dự phòng
                gc.fillRect(x, y, width, height);
            }
        } else {
            // Trạng thái 2 (hits == 1): Bị nứt
            if (CRACKED_IMAGE != null) {
                gc.drawImage(CRACKED_IMAGE, x, y, width, height);
            } else {
                gc.setFill(Color.LIGHTCYAN); // Dự phòng
                gc.fillRect(x, y, width, height);
            }
        }
    }

    @Override
    public boolean isVisiblyActive() {
        // Chỉ "active" (tính vào điều kiện thắng) NẾU nó đã bị va chạm ít nhất 1 lần (hits < 3)
        return (this.hits < 3);
    }
}