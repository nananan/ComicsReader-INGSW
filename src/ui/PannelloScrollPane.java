package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PannelloScrollPane extends JScrollPane
{
	private BufferedImage imageSfondo;
	private Image scaledImage;

	public PannelloScrollPane(JPanel panel, File file) 
	{
		super(panel);
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(Color.black,3));

//		try {
//			imageSfondo = ImageIO.read(file);
//			System.out.println((int)getPreferredSize().getWidth()+","+ (int)getPreferredSize().getHeight());
//			scaledImage = imageSfondo.getScaledInstance((int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight(), imageSfondo.SCALE_AREA_AVERAGING);
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		}
				
	}
	
	@Override
	public void paintComponents(Graphics g)
	{
		super.paintComponents(g);
//		g.drawImage(scaledImage, 0, 0, this);
	}
	
}
