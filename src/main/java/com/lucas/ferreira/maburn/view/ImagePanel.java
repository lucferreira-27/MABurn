package com.lucas.ferreira.maburn.view;

import com.lucas.ferreira.maburn.util.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = -7403114327544119366L;
	BufferedImage img;

	public ImagePanel() {
		setOpaque(false);
		setLayout(new GridBagLayout());
		try {
			System.out.println(Resources.getResourcePath("/template/frame.png"));
			InputStream in = Resources.getResourceAsStream("/template/frame.png");
			img = ImageIO.read(in);
		} catch (IOException ex) {
			Logger.getLogger(CircleScreen.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(546, 230);
	}
}
