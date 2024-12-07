package com.flappybird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private int birdY = 300;
    private int birdVelocity = 0;
    private final int GRAVITY = 1;
    private final int JUMP_STRENGTH = -15;
    private Timer timer;
    private ArrayList<Pipe> pipes;
    private boolean gameOver = false;
    private Random random = new Random();
    private int pipeSpawnTimer = 0;

    public GamePanel() {
        timer = new Timer(20, this);
        pipes = new ArrayList<>();

        addKeyListener(this);
        setFocusable(true);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            birdY += birdVelocity;
            birdVelocity += GRAVITY;

            // Spawn pipes periodically
            pipeSpawnTimer++;
            if (pipeSpawnTimer >= 100) { // Adjust this value to control spawn rate
                spawnPipe();
                pipeSpawnTimer = 0;
            }

            // Move pipes and check for collisions
            Iterator<Pipe> iterator = pipes.iterator();
            while (iterator.hasNext()) {
                Pipe pipe = iterator.next();
                pipe.move();

                // Remove pipes that go off-screen
                if (pipe.isOffScreen()) {
                    iterator.remove();
                }

                // Check for collision
                if (pipe.collidesWith(100, birdY, 30, 30)) {
                    gameOver = true;
                    timer.stop();
                }
            }

            // Check out-of-bounds for bird
            if (birdY > 550 || birdY < 0) {
                gameOver = true;
                timer.stop();
            }

            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 800, 600);

        // Draw bird
        g.setColor(Color.YELLOW);
        g.fillRect(100, birdY, 30, 30);

        // Draw pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        // Draw "Game Over" message
        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 300, 250);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press 'R' to Restart", 320, 300);
        }
    }

    private void resetGame() {
        birdY = 300;
        birdVelocity = 0;
        pipes.clear(); // Clear pipes on reset
        gameOver = false;
        pipeSpawnTimer = 0;
        timer.start();
    }

    private void spawnPipe() {
        int pipeHeight = random.nextInt(200) + 100; // Random height between 100 and 300
        int gap = 150; // Gap between pipes
        pipes.add(new Pipe(800, 0, 50, pipeHeight)); // Top pipe
        pipes.add(new Pipe(800, pipeHeight + gap, 50, 600 - pipeHeight - gap)); // Bottom pipe
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            birdVelocity = JUMP_STRENGTH;
        }
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
