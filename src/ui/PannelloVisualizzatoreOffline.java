package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import domain.VisualizzatoreCapitoli;
import domain.VisualizzatoreCapitoloOffline;
import domain.Volume;

public class PannelloVisualizzatoreOffline extends JPanel
{
	private final VisualizzatoreCapitoloOffline visualizzatoreOffline = VisualizzatoreCapitoloOffline.getVisualizzatoreCapitoli();
	private Image immagineCorrente;
	private MyPanel panel;
	private int panelWidth;
	private int panelHeight;
	private int npx;

	public PannelloVisualizzatoreOffline(MyPanel panel, int panelWidth, int panelHeight)
	{
		super();
		setBackground(Color.GRAY);
		setLayout(null);
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		this.panel = panel;
		
		this.setFocusable(true);
	}
	
	public void avviaVisualizzazione(File[] file, int primaPaginaDaVisualizzare)
	{
		for (File files : file)
		{
			System.out.println(files.getName());
		}
		
		visualizzatoreOffline.visualizzaCapitoli(file, primaPaginaDaVisualizzare);
		immagineCorrente = visualizzatoreOffline.visualizzaPaginaCorrente();

		int w = immagineCorrente.getWidth(this);
		if(w < this.panelWidth) {
			npx = (Math.abs(this.panelWidth - w)/2);
		}
		else {
			//TODO aggiungere la size più grande
			System.out.println("errore è più grande");
		}
		
		controllaAltezzaPagina();
	}
	
	public void vaiAPaginaSuccessiva()
	{
		if (visualizzatoreOffline.paginaSuccesiva())
			immagineCorrente = visualizzatoreOffline.visualizzaPaginaCorrente();
		
		controllaAltezzaPagina();
		
		repaint();
	}
	
	public void vaiAPaginaPrecedente()
	{
		if (visualizzatoreOffline.paginaPrecedente())
			immagineCorrente = visualizzatoreOffline.visualizzaPaginaCorrente();
		
		controllaAltezzaPagina();
		
		repaint();
	}
	
	public VisualizzatoreCapitoloOffline getVisualizzatoreCapitoli()
	{
		return visualizzatoreOffline;
	}
	
	private void controllaAltezzaPagina()
	{
		if (immagineCorrente.getHeight(this) > this.panelHeight)
		{
			this.panelHeight = immagineCorrente.getHeight(this);
			this.setPreferredSize(new Dimension(this.panelWidth, this.panelHeight));
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		immagineCorrente = immagineCorrente.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
		
		g.drawImage(immagineCorrente, this.npx, 0, this);
	}
}
