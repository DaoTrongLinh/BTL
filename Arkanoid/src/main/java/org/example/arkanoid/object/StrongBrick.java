package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * Gạch cứng, vỡ sau 2 lần va chạm.
 * Kế thừa từ lớp Brick.
 */
public class StrongBrick extends Brick {

    private static Image TwoHit_IMAGE;
    private static Image OneHit_IMAGE;
    static {
        try {
            TwoHit_IMAGE = new Image(StrongBrick.class.getResourceAsStream("/Image/2hitBrick.png"));
            OneHit_IMAGE = new Image(StrongBrick.class.getResourceAsStream("/Image/1hitBrick.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải ảnh gạch cứng!");
        }
    }


    public StrongBrick(double x, double y, double width, double height) {
        // Gọi lớp cha (Brick) với:
        // hits = 2
        // points = 20
        super(x, y, width, height, 2, 20);
    }

    @Override
    public void render(GraphicsContext gc) {
        // Đổi màu dựa trên số 'hits' (độ bền) còn lại

        if (this.hits == 2) {
            // Trạng thái 1: Còn 2 hits (cứng)
            if (TwoHit_IMAGE != null) {
                gc.drawImage(TwoHit_IMAGE, x, y, width, height);
            } else {
                gc.setFill(Color.GRAY); // Dự phòng
                gc.fillRect(x, y, width, height);
            }
        } else {
            // Trạng thái 2 (hits == 1): Bị nứt
            if (OneHit_IMAGE != null) {
                gc.drawImage(OneHit_IMAGE, x, y, width, height);
            } else {
                gc.setFill(Color.LIGHTGRAY); // Dự phòng
                gc.fillRect(x, y, width, height);
            }
        }
    }
}