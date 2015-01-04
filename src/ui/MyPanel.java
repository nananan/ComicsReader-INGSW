package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class MyPanel extends JPanel
{		
	PannelloCentrale pannelloCentro = new PannelloCentrale();
	PannelloSotto pannelloSotto = new PannelloSotto();
	PannelloSopra pannelloSopra = null;
	PannelloDestro pannelloDestro = new PannelloDestro();
	PannelloFiltraggio pannelloFiltraggio = new PannelloFiltraggio(pannelloCentro, this);
	PannelloSinistra pannelloSinistro = new PannelloSinistra(pannelloCentro, this, pannelloFiltraggio);
	PannelloDiscover pannelloDiscover = new PannelloDiscover();
	PannelloScrollPane pane = new PannelloScrollPane(pannelloDiscover, new File("image/manga1.jpg"));
	
	private Image image = null;
	
	public MyPanel(String name) throws IOException 
	{
		super();
//		this.setBackground(new Color(137,130,130));
		this.setLayout(new BorderLayout());
		pannelloSopra = new PannelloSopra(pannelloCentro, this, name, pannelloDestro);
//		this.add(pannelloFiltraggio,BorderLayout.WEST);
		pannelloCentro.setDimensioniPannello(pannelloDestro, pannelloSinistro);
		
		this.add(pannelloDestro,BorderLayout.EAST);
		this.add(pannelloSinistro,BorderLayout.WEST);
		this.add(pannelloSotto, BorderLayout.SOUTH);
		this.add(pannelloSopra, BorderLayout.NORTH);
		this.add(pannelloCentro, BorderLayout.CENTER);
		
		pannelloSotto.setVisible(false);
		
		pannelloDiscover.setPannelloCentrale(pannelloCentro);
	}

	public void Premi()
	{
		pannelloSinistro.setVisible(false);
		this.add(pannelloFiltraggio,BorderLayout.WEST);
		pannelloFiltraggio.setVisible(true);
		repaint();
	}
	
	public void PremiPerPannelloSinistro()
	{
		pannelloFiltraggio.setVisible(false);
		this.add(pannelloSinistro,BorderLayout.WEST);
		pannelloSinistro.setVisible(true);
		repaint();
	}
	
	public void PremiPerDiscover()
	{
		pannelloCentro.setVisible(false);
		this.add(pane,BorderLayout.CENTER);
		pane.setVisible(true);
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		pannelloCentro.getGraphics().drawImage(image, 0,0, pannelloCentro);
//		System.out.println("ciao");
		
	}
}
