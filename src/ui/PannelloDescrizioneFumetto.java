package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;

import domain.Fumetto;

@SuppressWarnings("serial")
public class PannelloDescrizioneFumetto extends JPanel
{
	private File file;
	private PannelloCentrale pannelloCentrale;
	private BufferedImage image;
	private Image scaledImage;
	private Text nome;
	
	public PannelloDescrizioneFumetto(Fumetto fumetto, PannelloCentrale pannelloCentrale) 
	{
		super();
		setBackground(Color.GRAY);
		
		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
		setBounds(10, 10, (int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight());
		setLayout(null);
		
		nome = new Text(fumetto.getNome());
		nome.setPreferredSize(new Dimension(500,200));
		nome.setBounds(10,10, (int)nome.getPreferredSize().getWidth(), (int)nome.getPreferredSize().getHeight());
//		try {
//			image = ImageIO.read(pannelloCentrale.getFile());
////			System.out.println((int)getPreferredSize().getWidth()+","+ (int)getPreferredSize().getHeight());
//			scaledImage = image.getScaledInstance(200,300, image.SCALE_AREA_AVERAGING);
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		}
		
		add(nome);
//		
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		g.drawImage(scaledImage, 0,0, this);
	}
	
}
