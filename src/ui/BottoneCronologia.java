package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import domain.Fumetto;

public class BottoneCronologia extends JButton implements ActionListener
{
	private Image immagineCopertina;
	private Image immagineDaRestituire;
	private Fumetto fumetto;
	private MyPanel panel;
	
	public BottoneCronologia(Image immagineCopertina, Fumetto fumetto, 
			int larghezza, int altezza, int larghezzaBottone, MyPanel panel)
	{
		this.immagineDaRestituire = immagineCopertina;
		this.fumetto = fumetto;
		this.panel = panel;
		
		this.immagineCopertina = immagineCopertina.getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		this.setPreferredSize(new Dimension(larghezzaBottone-40, altezza+20));
		this.setBackground(Color.GRAY);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
				
		JLabel immagine = new JLabel(new ImageIcon(this.immagineCopertina));
		immagine.setBorder(BorderFactory.createLineBorder(Color.black,2));
		immagine.setPreferredSize(new Dimension(larghezza, altezza));
		immagine.setBounds(20, 10, (int)immagine.getPreferredSize().getWidth(),
				(int)immagine.getPreferredSize().getHeight());
		add(immagine);
		
		Text nomeFumetto = new Text(fumetto.getNome(), 24, Color.WHITE);
		nomeFumetto.setBounds(40+immagine.getX()+(int)immagine.getPreferredSize().getWidth(),
				immagine.getY()+(int)immagine.getPreferredSize().getHeight()/3, 
				(int)nomeFumetto.getPreferredSize().getWidth(), 
				(int)nomeFumetto.getPreferredSize().getHeight());
		add(nomeFumetto);
		
		Text dataLetturaFumetto = new Text(fumetto.getData(), 18, Color.WHITE);
		dataLetturaFumetto.setBounds(50+nomeFumetto.getX()+(int)nomeFumetto.getPreferredSize().getWidth(),
				nomeFumetto.getY(), (int)dataLetturaFumetto.getPreferredSize().getWidth(), 
				(int)nomeFumetto.getPreferredSize().getHeight());
		add(dataLetturaFumetto);
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	}

	public Fumetto getFumetto()
	{
		return fumetto;
	}

	public Image getImageScaled()
	{
		return immagineDaRestituire;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		panel.PremiPerFumetto(fumetto, immagineDaRestituire);
	}
	
}
