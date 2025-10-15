package org.example.arkanoid.main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.arkanoid.control.Renderer;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;


public class ArkanoidGame extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Arkanoid");
        stage.show();

        Ball ball = new Ball(300, 300, 30, 4);
        Paddle paddle = new Paddle(350, 550, 100, 20, 20);


        Renderer renderer = new Renderer(root);
        renderer.draw(ball);
        renderer.draw(paddle);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> paddle.moveLeft();
                case RIGHT -> paddle.moveRight();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
