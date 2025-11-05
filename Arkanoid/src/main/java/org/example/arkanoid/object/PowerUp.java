package org.example.arkanoid.object;

import org.example.arkanoid.control.GameManager;

/**
 * Lớp trừu tượng cho các vật phẩm Power-up.
 * Kế thừa từ GameObject và định nghĩa các hiệu ứng.
 */
public abstract class PowerUp extends GameObject {
    protected String type;
    protected int duration; // Thời gian hiệu lực (có thể dùng sau)

    private double fallSpeed = 2.0; // Tốc độ rơi

    public PowerUp(double x, double y, double width, double height, String type, int duration) {
        super(x, y, width, height);
        this.type = type;
        this.duration = duration;
    }

    /**
     * Cập nhật vị trí của PowerUp (làm cho nó rơi xuống).
     */
    @Override
    public void update() {
        y += fallSpeed; // Di chuyển xuống dưới
    }

    /**
     * Phương thức trừu tượng để áp dụng hiệu ứng lên thanh trượt (Paddle).
     *
     * @param gameManager Thanh trượt của người chơi.
     */
    public abstract void applyEffect(GameManager gameManager);

    /**
     * Phương thức trừu tượng để gỡ bỏ hiệu ứng khỏi thanh trượt.
     *
     * @param gameManager Thanh trượt của người chơi.
     */
    public abstract void removeEffect(GameManager gameManager);
}