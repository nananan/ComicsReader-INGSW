package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.AppManager;
import domain.Fumetto;
import domain.Lettore;

public class BottoneUtente extends JButton implements ActionListener
{
	private Image scaledImage;
	private Lettore lettore;
	private MyPanel panel;
	
	public BottoneUtente(Image image, MyPanel panel, Lettore lettore) 
	{
		super();
		this.scaledImage = image;
		this.lettore = lettore;
		this.panel = panel;
		
		addActionListener(this);
	}	
	
	public BottoneUtente(String text, int larghezza, Color colore, Lettore lettore) 
	{
		super();
		setText(text);
		setBackground(colore);
		setBorderPainted(false);
		setFocusPainted(false);
		setForeground(Color.WHITE);
		setContentAreaFilled(false);
		setFont(new Font("Caladea", Font.HANGING_BASELINE, larghezza));
		this.lettore = lettore;
		
		addActionListener(this);
	}
	
	public Image getImageScaled()
	{
		return scaledImage;
	}
	
	public Lettore getLettore()
	{
		return lettore;
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
		panel.premiPerAvereProfiloDiAltroUtente(lettore);
	}
	
}
