package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;

/**
 * Lớp trừu tượng cho Gạch (Brick).
 * Kế thừa từ GameObject và thêm logic về điểm, độ bền (hitPoints).
 */
public abstract class Brick extends GameObject {
    protected int hits;         // Độ bền: Cần bao nhiêu va chạm để vỡ
    protected int points;       // Điểm số: Nhận được bao nhiêu điểm khi vỡ
    protected boolean destroyed;

    public Brick(double x, double y, double width, double height, int hits, int points) {
        super(x, y, width, height);
        this.hits = hits;
        this.points = points;
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
     * Xử lý khi gạch bị bóng va chạm.
     * Trừ 1 'hit' (độ bền).
     *
     * @return true nếu gạch bị phá hủy (hết 'hits'), false nếu chỉ bị hư hại.
     */
    public boolean hit() {
        this.hits--; // Giảm độ bền
        if (this.hits <= 0) {
            this.destroyed = true;
            return true; // Báo cho GameManager biết "tôi đã bị phá hủy"
        }
        return false; // Báo cho GameManager biết "tôi vẫn còn sống"
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Lấy số điểm mà viên gạch này mang lại.
     *
     * @return số điểm.
     */
    public int getPoints() {
        return points;
    }

    @Override
    public abstract void render(GraphicsContext gc);
}