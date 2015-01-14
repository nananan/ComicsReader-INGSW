package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.Fumetto;

public class BottoneFumetto extends JButton
{
	private Image scaledImage;
	private Fumetto fumetto;
	
	public BottoneFumetto(Image image, final Fumetto fumetto) 
	{
		super();
		image = image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
		this.scaledImage = image;
		this.fumetto = fumetto;
	}	
	
	public Image getImageScaled()
	{
		return scaledImage;
	}
	
	public Fumetto getFumetto()
	{
		return fumetto;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(scaledImage, 0,0, this);
	}
	
}
