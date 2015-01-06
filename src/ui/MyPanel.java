package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import technicalService.DataBase;
import domain.Fumetto;
import domain.Volume;


public class MyPanel extends JPanel
{		
	PannelloCentrale pannelloCentro = new PannelloCentrale();
	PannelloSotto pannelloSotto = new PannelloSotto();
	PannelloSopra pannelloSopra = null;
	PannelloDestro pannelloDestro = new PannelloDestro();
	PannelloFiltraggio pannelloFiltraggio = new PannelloFiltraggio(pannelloCentro, this);
	PannelloSinistra pannelloSinistro = new PannelloSinistra(pannelloCentro, this, pannelloFiltraggio);
	PannelloDiscover pannelloDiscover = new PannelloDiscover();
	PannelloScrollPane pannelloScrollDiscover = new PannelloScrollPane(pannelloDiscover, new File("image/manga1.jpg"));
	
	private Image image = null;
	private PannelloScrollPane pannelloScrollDescrizione;
	private PannelloScrollPane pannelloScrollCapitoli;
	private ArrayList<PannelloDescrizioneFumetto> arrayPannelli = new ArrayList<>();
	
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
		
		pannelloDiscover.setPannelloCentrale(pannelloCentro, this);
	}

	public void Premi()
	{
		pannelloSinistro.setVisible(false);
		this.add(pannelloFiltraggio,BorderLayout.WEST);
		this.validate();
		pannelloFiltraggio.setVisible(true);
		repaint();
	}
	
	public void PremiPerPannelloSinistro()
	{
		pannelloFiltraggio.setVisible(false);
		this.add(pannelloSinistro,BorderLayout.WEST);
		this.validate();
		pannelloSinistro.setVisible(true);
		repaint();
	}
	
	public void PremiPerDiscover() throws SQLException
	{
		remove(pannelloCentro);
		if (pannelloScrollDescrizione != null)
			remove(pannelloScrollDescrizione);
		this.add(pannelloScrollDiscover, BorderLayout.CENTER);
		this.validate();
		pannelloScrollDiscover.setVisible(true);
		repaint();
	}
	
	public void PremiPerFumetto(Fumetto fumetto) throws MalformedURLException, SQLException
	{
//		remove(pannelloScrollDiscover);
//		
//		arrayPannelli.add(new PannelloDescrizioneFumetto(fumetto, pannelloCentro, this));
//		
////		if (pannelloScrollDescrizione == null)
//		for (PannelloDescrizioneFumetto jPanel : arrayPannelli)
//		{
//			if (fumetto == jPanel.getFumetto())
//				
////			else
////				pannelloScrollDescrizione = new PannelloScrollPane(arrayPannelli.get(i), null);
//			
//		}
//		
//		this.add(pannelloScrollDescrizione, BorderLayout.CENTER);
//		this.validate();
//		pannelloScrollDescrizione.setVisible(true);
//		repaint();
	}	
	
	public void PremiPerCapitolo() 
	{
		remove(pannelloScrollDescrizione);
//		pannelloScrollCapitoli = new PannelloScrollPane(new PannelloCapitoli(volume, pannelloCentro, this), null);
		this.add(pannelloScrollCapitoli, BorderLayout.CENTER);
		this.validate();
		pannelloScrollCapitoli.setVisible(true);
		repaint();
	}

	
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		pannelloCentro.getGraphics().drawImage(image, 0,0, pannelloCentro);		
	}

}
