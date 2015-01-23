package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PannelloProvaOffline extends JPanel
{
	private BufferedImage immagineCorrente;

	public PannelloProvaOffline(File file)
	{
		super();
		setBackground(Color.GRAY);
		setLayout(null);
		
		try
		{
			immagineCorrente = ImageIO.read(file);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setFocusable(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		immagineCorrente = immagineCorrente.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
		
		g.drawImage(immagineCorrente, 0, 0, this);
	}
}
