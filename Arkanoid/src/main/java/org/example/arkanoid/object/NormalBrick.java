package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class NormalBrick extends Brick {

    private static Image BRICK_IMAGE;
    static {
        try {
            // Tải ảnh từ thư mục resources
            BRICK_IMAGE = new Image(NormalBrick.class.getResourceAsStream("/Image/NormalBrick.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải ảnh gạch thường!");
        }
    }

    // Constructor đơn giản cho gạch 1 hit
    public NormalBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, 10); // 10 điểm
    }

    @Override
    public void render(GraphicsContext gc) {
        if (BRICK_IMAGE != null) {
            // Vẽ ảnh thay vì vẽ hình chữ nhật
            gc.drawImage(BRICK_IMAGE, x, y, width, height);
        } else {
            gc.setFill(Color.GREEN);
            gc.fillRect(x, y, width, height);
        }
    }
}