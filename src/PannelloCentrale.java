import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class PannelloCentrale extends JPanel
{
	Image image = null;
	Image scaledImage = null;
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public PannelloCentrale() 
	{
		
	}
	
	public void setDimensioniPannello(PannelloDestro pannelloDestro, PannelloSinistra pannelloSinistra)
	{
		larghezza = larghezza - (int) pannelloDestro.getPreferredSize().getWidth() - (int) pannelloSinistra.getPreferredSize().getWidth();
//		altezza = altezza - (int) pannelloDestro.getPreferredSize().getHeight() - (int) pannelloSinistra.getPreferredSize().getHeight();
		
		setBounds(0, 0, larghezza, altezza);
		setBorder(BorderFactory.createLineBorder(Color.black,3));
		setLayout(null);
		
		try
		{
			image = ImageIO.read(new File("image/manga1.jpg"));
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
