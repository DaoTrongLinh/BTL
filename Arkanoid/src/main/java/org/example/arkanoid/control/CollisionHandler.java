package org.example.arkanoid.control;

import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick;
import org.example.arkanoid.object.Bullet;
import org.example.arkanoid.object.Paddle;

import java.util.List;

/**
 * Xử lý tất cả logic va chạm giữa các đối tượng trong game.
 */
public class CollisionHandler {

    /**
     * Xử lý va chạm giữa Bóng và Thanh trượt (ĐÃ NÂNG CẤP).
     */
    public void handleBallPaddleCollision(Ball ball, Paddle paddle) {
        if (ball.getBounds().intersects(paddle.getBounds())) {

            ball.reverseDy();
            ball.setY(paddle.getY() - ball.getHeight());

            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
            double ballCenterX = ball.getX() + ball.getWidth() / 2; // Dùng getWidth() vì ball là AABB
            double hitPosition = ballCenterX - paddleCenterX;
            double normalizedHitPosition = hitPosition / (paddle.getWidth() / 2);
            double MAX_DX_SPEED = 5.0;
            double newDx = normalizedHitPosition * MAX_DX_SPEED;

            ball.setDx(newDx);
        }
    }

    /**
     * --- SỬA LOGIC VA CHẠM GẠCH TẠI ĐÂY ---
     * Xử lý va chạm giữa Bóng và một viên Gạch.
     * @param ball Quả bóng
     * @param brick Viên gạch
     * @return true nếu có va chạm, false nếu không.
     */
    public boolean handleBallBrickCollision(Ball ball, Brick brick) {
        if (brick.isDestroyed()) {
            return false;
        }

        if (ball.getBounds().intersects(brick.getBounds())) {

            // Va chạm đã xảy ra. Giờ tìm xem đó là cạnh nào.

            // 1. Lấy tâm của bóng và gạch
            double ballCenterX = ball.getX() + ball.getWidth() / 2;
            double ballCenterY = ball.getY() + ball.getHeight() / 2;
            double brickCenterX = brick.getX() + brick.getWidth() / 2;
            double brickCenterY = brick.getY() + brick.getHeight() / 2;

            // 2. Tính toán độ "chồng lấn" (overlap)
            // (Nửa chiều rộng của cả hai) - (Khoảng cách giữa tâm của cả hai)
            double overlapX = (ball.getWidth() / 2 + brick.getWidth() / 2)
                    - Math.abs(ballCenterX - brickCenterX);

            double overlapY = (ball.getHeight() / 2 + brick.getHeight() / 2)
                    - Math.abs(ballCenterY - brickCenterY);

            // 3. Quyết định hướng nảy
            // Nếu overlapX < overlapY, va chạm xảy ra ở CẠNH BÊN (trái/phải).
            // Nếu overlapY <= overlapX, va chạm xảy ra ở CẠNH TRÊN/DƯỚI.

            if (overlapX < overlapY) {
                // Va chạm ngang -> Đảo chiều X
                ball.reverseDx();

                // Đẩy bóng ra khỏi gạch (tránh lỗi dính)
                if (ballCenterX < brickCenterX) { // Đập vào bên trái
                    ball.setX(brick.getX() - ball.getWidth());
                } else { // Đập vào bên phải
                    ball.setX(brick.getX() + brick.getWidth());
                }

            } else {
                // Va chạm dọc -> Đảo chiều Y
                ball.reverseDy();

                // Đẩy bóng ra khỏi gạch (tránh lỗi dính)
                if (ballCenterY < brickCenterY) { // Đập vào bên trên
                    ball.setY(brick.getY() - ball.getHeight());
                } else { // Đập vào bên dưới
                    ball.setY(brick.getY() + brick.getHeight());
                }
            }

            // 4. Trả về true để GameManager biết và cộng điểm
            return true;
        }
        return false; // Không va chạm
    }

    /**
     * Xử lý va chạm VẬT LÝ giữa MỘT viên đạn và TẤT CẢ các viên gạch.
     * @param bullet Viên đạn đang kiểm tra.
     * @param bricks Danh sách tất cả gạch.
     * @return Viên gạch bị trúng (để GameManager xử lý), hoặc null nếu không trúng.
     */
    public Brick handleBulletBrickCollision(Bullet bullet, List<Brick> bricks) {
        // Lặp qua tất cả gạch
        for (Brick brick : bricks) {
            // Nếu gạch còn sống VÀ đạn va chạm vào nó
            if (!brick.isDestroyed() && bullet.getBounds().intersects(brick.getBounds())) {
                // Va chạm xảy ra! Trả về viên gạch bị trúng.
                return brick;
            }
        }
        // Không trúng viên gạch nào
        return null;
    }
}
