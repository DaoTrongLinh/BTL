package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;

/**
 * Lớp trừu tượng cho Gạch (Brick).
 * Kế thừa từ GameObject và thêm logic về điểm, độ bền (hitPoints).
 */
public abstract class Brick extends GameObject {
    protected int hitPoints;
    protected boolean destroyed;

    public Brick(double x, double y, double width, double height, int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.destroyed = false;
    }

    /**
     * Gạch không di chuyển, vì vậy phương thức update() sẽ rỗng.
     * Lớp con có thể override nếu cần (ví dụ: gạch có hiệu ứng).
     */
    @Override
    public void update() {
        // Gạch tĩnh không cần cập nhật vị trí
    }

    /**
     * Phương thức trừu tượng xử lý khi gạch bị bóng va vào.
     * Lớp con (như NormalBrick) sẽ định nghĩa chi tiết.
     */
    public abstract void takeHit();

    /**
     * Kiểm tra xem gạch đã bị phá hủy chưa.
     * @return true nếu gạch đã bị phá hủy, ngược lại false.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Lấy số điểm mà viên gạch này mang lại.
     * @return số điểm.
     */
    public int getHitPoints() {
        return hitPoints;
    }
}