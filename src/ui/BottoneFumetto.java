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
	
	public BottoneFumetto(Image image, final MyPanel panel, final Fumetto fumetto) 
	{
		super();
		this.scaledImage = image;
		this.fumetto = fumetto;
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				panel.PremiPerFumetto(fumetto);
				System.out.println(fumetto.getNome());
			}
		 });
		
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
