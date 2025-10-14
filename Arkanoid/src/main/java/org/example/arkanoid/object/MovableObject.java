package org.example.arkanoid.object;

public class MovableObject extends GameObject {
    protected int dx;
    protected int dy;

    @Override
    public void update() {
        x += dx;
        y += dy;
    }

    public void move(){}
}
