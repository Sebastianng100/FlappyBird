package com.flappybird;

import javax.swing.*;

public class FlappyBird {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Flappy Bird");
        GamePanel panel = new GamePanel();

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
	}

}