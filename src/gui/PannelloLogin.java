package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class PannelloLogin extends JPanel
{
	Image image = null;
	public Image scaledImage = null;
	
	final int costanteDivisione = 3;
	
	public PannelloLogin(int larghezza, int altezza) 
	{
//		int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/costanteDivisione;
//		int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/(costanteDivisione+1);
		
		setBounds(0, 0, larghezza, altezza);
		setBorder(BorderFactory.createLineBorder(Color.black,3));
		setLayout(null);
		
		try
		{
			image = ImageIO.read(new File("image/yoda.jpg"));
			scaledImage = image.getScaledInstance((int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight(), Image.SCALE_AREA_AVERAGING);
			
		}
		catch (Exception e){}
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(scaledImage, 0,0, this);
	}
}
