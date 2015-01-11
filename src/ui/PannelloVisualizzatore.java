package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import domain.Capitolo;
import domain.VisualizzatoreCapitoli;
import domain.Volume;

public class PannelloVisualizzatore extends JPanel
{
	private final VisualizzatoreCapitoli visualizzatoreCapitoli = VisualizzatoreCapitoli.getVisualizzatoreCapitoli();
	private Image immagineCorrente;
	private int panelWidth;
	private int npx;
	
	public PannelloVisualizzatore(int panelWidth) 
	{
		super();
		setBackground(Color.GREEN);
		setBorder(BorderFactory.createLineBorder(Color.black,3));
		setLayout(null);
		this.panelWidth = panelWidth;
	}
	
	public void avviaVisualizzazione(Volume volume,int numeroCapitoloDaLeggere, int primaPaginaDaVisualizzare)
	{
		System.out.println("creo il visualizzatore");
		visualizzatoreCapitoli.visualizzaCapitolo(volume.getCapitoli(), numeroCapitoloDaLeggere, primaPaginaDaVisualizzare);
		immagineCorrente = visualizzatoreCapitoli.visualizzaPaginaCorrente();

		int w = immagineCorrente.getWidth(this);
		if(w < this.panelWidth) {
			npx = (Math.abs(this.panelWidth - w)/2);
		}
		else {
			//TODO aggiungere la size più grande
			System.out.println("errore è più grande");
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
