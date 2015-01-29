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
	private final VisualizzatoreCapitoli visualizzatoreCapitoli = VisualizzatoreCapitoli.getIstanza();
	private static final String PATH_GIF= "image/loading.gif";
	private static Image gif;
	private Image immagineCorrente;
	private int panelWidth;
	private int npx;
	private int panelHeight;
	private MyPanel panel;
	private int numeroCapitoli;
	private Volume volume;
	private int numeroCapitolo;
	
	public PannelloVisualizzatore(int panelWidth, int panelHeight, MyPanel panel) 
	{
		super();
		setBackground(Color.GRAY);
		setLayout(null);
		try {
			gif = ImageIO.read(new File(PATH_GIF));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		this.panel = panel;
		
		this.setFocusable(true);
		addKeyListener(this);
	}
	
	public void avviaVisualizzazione(Volume volume,int numeroCapitoloDaLeggere, int primaPaginaDaVisualizzare)
	{
		this.volume = volume;
		this.numeroCapitolo = numeroCapitoloDaLeggere;
		visualizzatoreCapitoli.visualizzaCapitoli(volume, numeroCapitoloDaLeggere, primaPaginaDaVisualizzare);
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
	public Image getImmagineCorrente() {
		return this.visualizzatoreCapitoli.visualizzaPaginaCorrente();
	}
	public void visualizzaPaginaCorrente(){
		if(visualizzatoreCapitoli.visualizzaPaginaCorrente()==null)
		{
			immagineCorrente=gif;
			controllaAltezzaPagina();
			repaint();
			ThreadPagina loading = new ThreadPagina(this);
			loading.start();
		}
		else{
			immagineCorrente=visualizzatoreCapitoli.visualizzaPaginaCorrente();
			controllaAltezzaPagina();
			repaint();
		}
	}
	public void vaiAPaginaSuccessiva()
	{
		if (visualizzatoreCapitoli.haPaginaSuccessiva()){
			visualizzatoreCapitoli.paginaSuccessiva();
			visualizzaPaginaCorrente();
		}
	}
	
	public void vaiAPaginaPrecedente()
	{
		if (visualizzatoreCapitoli.haPaginaPrecedente()){
			visualizzatoreCapitoli.paginaPrecedente();
			visualizzaPaginaCorrente();
		}
	}
	
	public boolean controllaPaginaSuccessiva()
	{
		return visualizzatoreCapitoli.haPaginaSuccessiva();
	}
	
	public boolean controllaPaginaPrecedente()
	{
		return visualizzatoreCapitoli.haPaginaPrecedente();
	}
	
	public int getNumeroPagina()
	{
		return visualizzatoreCapitoli.numeroPagina();
	}
	
	public int getNumeroCapitolo()
	{
		return numeroCapitolo;
	}
	
	public int getNumeroVolume()
	{
		return volume.getNumero();
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
		if (visualizzatoreCapitoli.haCapitoloPrecedente()){
			visualizzatoreCapitoli.capitoloPrecedente();
			visualizzaPaginaCorrente();
			numeroCapitolo--;
		}
	}
	
	public void vaiACapitoloSuccessivo()
	{
		if (visualizzatoreCapitoli.haCapitoloSuccessivo()){
			visualizzatoreCapitoli.capitoloSuccessivo();
			visualizzaPaginaCorrente();
			numeroCapitolo++;
		}
		
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
