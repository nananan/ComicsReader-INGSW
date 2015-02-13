package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.Fumetto;

public class BottoneFumetto extends JButton implements ActionListener
{
	private Image scaledImage;
	private Fumetto fumetto;
	private MyPanel panel;
	private Image scaledImageDaPassare;
	
	public BottoneFumetto(Image image, final Fumetto fumetto, MyPanel panel) 
	{
		super();
		this.panel = panel;
		
		this.scaledImageDaPassare = image;
		
		image = image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
		this.setBorder(BorderFactory.createLineBorder(Color.black,2));
		this.scaledImage = image;
		this.fumetto = fumetto;
		
		addMouseMotionListener(new MouseMotionAdapter() {
		      public void mouseMoved(MouseEvent event) {
		    	  BottoneFumetto.this.setBorder(BorderFactory.createLineBorder(new Color(35,148,188),3));
		      }
		    });
	    addMouseListener(new MouseAdapter() {
	      public void mouseExited(MouseEvent event) {
	    	  BottoneFumetto.this.setBorder(BorderFactory.createLineBorder(Color.black,2));
	        }
	      });
		
		addActionListener(this);
	}	
	
	public BottoneFumetto(Image image, final Fumetto fumetto, int larghezza, int altezza, MyPanel panel) 
	{
		super();
		this.panel = panel;
		
		this.scaledImageDaPassare = image;
		image = image.getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		this.setBorder(BorderFactory.createLineBorder(Color.black,2));
		this.scaledImage = image;
		this.fumetto = fumetto;
		
		addMouseMotionListener(new MouseMotionAdapter() {
		      public void mouseMoved(MouseEvent event) {
		    	  BottoneFumetto.this.setBorder(BorderFactory.createLineBorder(new Color(35,148,188),3));
		      }
		    });
	    addMouseListener(new MouseAdapter() {
	      public void mouseExited(MouseEvent event) {
	    	  BottoneFumetto.this.setBorder(BorderFactory.createLineBorder(Color.black,2));
	        }
	      });
		
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
		scaledImageDaPassare = scaledImageDaPassare.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
		panel.PremiPerFumetto(fumetto, scaledImageDaPassare);
	}
	
}
