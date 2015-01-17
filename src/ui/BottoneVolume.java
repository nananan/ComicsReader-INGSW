package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import domain.Fumetto;
import domain.Volume;

public class BottoneVolume extends JButton implements ActionListener
{
	private Image scaledImage;
	private Fumetto fumetto;
	private Volume volume;
	private PannelloDescrizioneFumetto pannelloDescrizioneFumetto;
	
	public BottoneVolume(Image image, Volume volume, final Fumetto fumetto, PannelloDescrizioneFumetto pannelloDescrizioneFumetto) 
	{
		super();
		image = image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
		this.setBorder(BorderFactory.createLineBorder(Color.black,2));
		this.scaledImage = image;
		this.fumetto = fumetto;
		this.volume = volume;
		
		this.pannelloDescrizioneFumetto = pannelloDescrizioneFumetto;
		
		addActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		pannelloDescrizioneFumetto.getCapitoliVolume(volume);
	}
		
}
