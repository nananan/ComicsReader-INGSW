package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class PannelloVisualizzatore extends JPanel implements KeyListener
{
	private final VisualizzatoreCapitoli visualizzatoreCapitoli = VisualizzatoreCapitoli.getVisualizzatoreCapitoli();
	private Image immagineCorrente;
	private int panelWidth;
	private int npx;
	private int panelHeight;
	private MyPanel panel;
	private int numeroCapitoli;
	
	public PannelloVisualizzatore(int panelWidth, int panelHeight, MyPanel panel) 
	{
		super();
		setBackground(Color.GRAY);
		setLayout(null);
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		this.panel = panel;
		
		this.setFocusable(true);
		addKeyListener(this);
	}
	
	public void avviaVisualizzazione(Volume volume,int numeroCapitoloDaLeggere, int primaPaginaDaVisualizzare)
	{
		visualizzatoreCapitoli.visualizzaCapitoli(volume.getCapitoli(), numeroCapitoloDaLeggere, primaPaginaDaVisualizzare);
		immagineCorrente = visualizzatoreCapitoli.visualizzaPaginaCorrente();
		this.numeroCapitoli = volume.getNumeroCapitoli();
		
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
		if (visualizzatoreCapitoli.paginaSuccesiva())
			immagineCorrente = visualizzatoreCapitoli.visualizzaPaginaCorrente();
		
		controllaAltezzaPagina();
		
		repaint();
	}
	
	public void vaiAPaginaPrecedente()
	{
		if (visualizzatoreCapitoli.paginaPrecedente())
			immagineCorrente = visualizzatoreCapitoli.visualizzaPaginaCorrente();
		
		controllaAltezzaPagina();
		
		repaint();
	}
	
	public boolean controllaPaginaSuccessiva()
	{
		return visualizzatoreCapitoli.haPaginaSuccessiva();
	}
	
	public boolean controllaPaginaPrecedente()
	{
		return visualizzatoreCapitoli.haPaginaPrecedente();
	}
	
	public int getPagina()
	{
		return visualizzatoreCapitoli.getNumeroPagina();
	}
	
	public VisualizzatoreCapitoli getVisualizzatoreCapitoli()
	{
		return visualizzatoreCapitoli;
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

	@Override
	public void keyPressed(KeyEvent e)
	{
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyChar() == KeyEvent.VK_RIGHT){ System.out.println("DESTRA");
			panel.premiPerPaginaSuccessiva();}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
	}

	public void vaiACapitoloPrecedente()
	{
		if (visualizzatoreCapitoli.capitoloPrecedente())
			immagineCorrente = visualizzatoreCapitoli.visualizzaPaginaCorrente();
		
		controllaAltezzaPagina();
		
		repaint();
	}
	
	public void vaiACapitoloSuccessivo()
	{
		if (visualizzatoreCapitoli.capitoloSuccessivo())
			immagineCorrente = visualizzatoreCapitoli.visualizzaPaginaCorrente();
		
		controllaAltezzaPagina();
		
		repaint();
	}

	public boolean controllaCapitoloSuccessivo()
	{
		return visualizzatoreCapitoli.haCapitoloSuccessivo();
	}
	
	public boolean controllaCapitoloPrecedente()
	{
		return visualizzatoreCapitoli.haCapitoloPrecedente();
	}
}
