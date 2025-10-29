package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Gạch cứng, vỡ sau 2 lần va chạm.
 * Kế thừa từ lớp Brick.
 */
public class StrongBrick extends Brick {

    public StrongBrick(double x, double y, double width, double height) {
        // Gọi lớp cha (Brick) với:
        // hits = 2 (2 lần va chạm để vỡ)
        // points = 25 (nhận 25 điểm khi vỡ)
        super(x, y, width, height, 2, 25);
    }

    @Override
    public void render(GraphicsContext gc) {
        // Đổi màu dựa trên số 'hits' (độ bền) còn lại

        if (this.hits == 2) {
            gc.setFill(Color.GRAY); // Trạng thái 1: Còn 2 hits (cứng)
        } else {
            gc.setFill(Color.LIGHTGRAY); // Trạng thái 2: Còn 1 hit (bị nứt)
        }

        gc.fillRect(x, y, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, width, height);
    }
}