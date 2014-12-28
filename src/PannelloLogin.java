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
	
	public PannelloLogin() 
	{
		int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		setBounds(0, 0, larghezza-200, altezza-50);
		setBorder(BorderFactory.createLineBorder(Color.black,3));
		setLayout(null);
		
		try
		{
			image = ImageIO.read(new File("image/yoda.jpg"));
			scaledImage = image.getScaledInstance((int)getPreferredSize().getWidth()/2-50, (int)getPreferredSize().getHeight()/4, Image.SCALE_AREA_AVERAGING);
			
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
