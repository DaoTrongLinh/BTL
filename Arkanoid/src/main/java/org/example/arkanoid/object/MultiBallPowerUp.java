package org.example.arkanoid.object;

import org.example.arkanoid.control.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * PowerUp này kích hoạt khi người chơi phá gạch ngẫu nhiên và số lượng bóng hiện tại trên màn hình
 * sẽ được x2 hoặc x3 bởi GameManager
 */

public class MultiBallPowerUp extends PowerUp {
    public MultiBallPowerUp(double x, double y) {
        // x, y, width, height, type, duration
        //duration = 0 do là hiệu ứng xảy ra ngay lập tức
        super(x, y,30,15,"MULTI_BALL", 0);
    }

    @Override
    public void applyEffect(GameManager manager) {
        manager.activateMultiBall(); //Cái này tạo trong GameManager
    }

    /**
     * Do là hiệu ứng mất khi bóng rơi nên ko cần remove
     */
    @Override
    public void removeEffect(GameManager manager) {
        //Nothing here
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.PURPLE);
        gc.fillRect(x, y, width, height);
    }
}
