package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NormalBrick extends Brick {

    // Constructor đơn giản cho gạch 1 hit
    public NormalBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, 10); // 10 điểm
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y, width, height);

        // (Tùy chọn) Thêm viền cho đẹp
        gc.setStroke(Color.WHITE);
        gc.strokeRect(x, y, width, height);

    }
}