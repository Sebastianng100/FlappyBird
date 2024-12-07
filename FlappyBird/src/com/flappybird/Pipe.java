package com.flappybird;

import java.awt.*;

public class Pipe {
    private int x, y, width, height;

    public Pipe(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move() {
        x -= 5; // Move pipe left
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

    public boolean collidesWith(int birdX, int birdY, int birdWidth, int birdHeight) {
        return birdX < x + width && birdX + birdWidth > x && birdY < y + height && birdY + birdHeight > y;
    }
}
