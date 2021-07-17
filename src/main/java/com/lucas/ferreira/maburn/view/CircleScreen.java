package com.lucas.ferreira.maburn.view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class CircleScreen {

    public CircleScreen(JFrame frame) {
        frame.getContentPane().add(new ImagePanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setVisible(true);
    }
    public CircleScreen() {
		
	}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CircleScreen(new JFrame());
            }
        });
    }


}
