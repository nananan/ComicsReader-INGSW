import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class PannelloSotto extends JPanel
{
	ImageIcon image = new ImageIcon("/home/eliana/Interfacce grafiche ed eventi/javaWorkspace/INGSW/src/buttonPlay.jpg");
	Image scaledImage = null;
	
	JButton play = new JButton();
	
	public PannelloSotto() 
	{
//		int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//		
//		setBounds(180, 30, larghezza-200, altezza-50);
		
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 50));
		
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		try
		{
//			image = ImageIO.read(new File("/home/eliana/Interfacce grafiche ed eventi/javaWorkspace/INGSW/src/buttonPlay.jpg"));
//			scaledImage = image.getScaledInstance(50,50, Image.SCALE_AREA_AVERAGING);
			
		}
		catch (Exception e){}
		
//		play.setIcon(image);
//		play.setPressedIcon(image);
		play.setBackground(Color.BLACK);
		play.setBounds((int)getPreferredSize().getWidth()/2, (int)getPreferredSize().getHeight(), 30, 30);
		add(play);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		g.drawImage(image, 0,0, this);
	}
}
