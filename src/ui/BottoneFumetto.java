package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BottoneFumetto extends JButton
{
	private Image scaledImage = null;
	
	public BottoneFumetto(Image image) 
	{
		super();
		this.scaledImage = image;
	}	
	
	public Image getImageScaled()
	{
		return scaledImage;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(scaledImage, 0,0, this);
	}
	
}
