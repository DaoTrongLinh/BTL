package org.example.arkanoid.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.arkanoid.control.Renderer;
import org.example.arkanoid.object.GameManager;

public class ArkanoidGame extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Arkanoid - Week 6");
        stage.show();

        // Khởi tạo game manager và renderer
        GameManager gm = new GameManager();
        Renderer renderer = new Renderer(root);

        // Lắng nghe input
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> gm.setLeftPressed(true);
                case RIGHT -> gm.setRightPressed(true);
            }
        });
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT -> gm.setLeftPressed(false);
                case RIGHT -> gm.setRightPressed(false);
            }
        });

        // 🌀 Game loop: update + render mỗi frame
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gm.update();
                renderer.render(gm);
            }
        }.start();
    }

    public static void main(String[] args) {
        launch();
    }
}

