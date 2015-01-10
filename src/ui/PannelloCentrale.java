package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class PannelloCentrale extends JPanel
{
	Image image = null;
	Image scaledImage = null;
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private File file =  new File("image/manga1.jpg");
	
	PannelloDiscover pannelloDiscover = null;
	
	public PannelloCentrale() 
	{
//		this.setLayout(new GridLayout());
	}
	
	public void setDimensioniPannello(PannelloDestro pannelloDestro, PannelloSinistra pannelloSinistra) throws IOException
	{
		larghezza = larghezza - (int) pannelloDestro.getPreferredSize().getWidth() - (int) pannelloSinistra.getPreferredSize().getWidth();
//		altezza = altezza - (int) pannelloDestro.getPreferredSize().getHeight() - (int) pannelloSinistra.getPreferredSize().getHeight();
		
		setBounds(0, 0, larghezza, altezza);
		System.out.println(larghezza+","+altezza);
		setBorder(BorderFactory.createLineBorder(Color.black,3));
		setLayout(null);
		
		try
		{
			image = ImageIO.read(file);
			scaledImage = image.getScaledInstance((int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight(), Image.SCALE_AREA_AVERAGING);
			
		}
		catch (Exception e){}
		
	}
	
	public int getLarghezza()
	{
		return larghezza;
	}
	
	public File getFile()
	{
		System.out.println(file.getPath());
		return file;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(scaledImage, 0,0, this);
	}
}
