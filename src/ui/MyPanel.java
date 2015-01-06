package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Scrollbar;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import domain.Fumetto;

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
	PannelloProfilo pannelloProfilo;
	
	private PannelloScrollPane pannelloScrollCapitoli;
	private HashMap<String,PannelloScrollPane> arrayPannelli = new HashMap<>();
	
	public MyPanel(String name) throws IOException 
	{
		super();
		this.setLayout(new BorderLayout());
		pannelloSopra = new PannelloSopra(pannelloCentro, this, name, pannelloDestro);
		pannelloCentro.setDimensioniPannello(pannelloDestro, pannelloSinistro);
		
		this.add(pannelloDestro,BorderLayout.EAST);
		this.add(pannelloSinistro,BorderLayout.WEST);
		this.add(pannelloSotto, BorderLayout.SOUTH);
		this.add(pannelloSopra, BorderLayout.NORTH);
		this.add(pannelloCentro, BorderLayout.CENTER);
		
		pannelloSotto.setVisible(false);
		
		pannelloDiscover.setPannelloCentrale(pannelloCentro, this);
		pannelloProfilo = new PannelloProfilo(name, pannelloCentro);
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
		
		Iterator it = arrayPannelli.entrySet().iterator();
	    while (it.hasNext())
	    {
	        Map.Entry pairs = (Map.Entry)it.next();
	        if (it != null)
	        	remove(arrayPannelli.get(pairs.getKey()));
	    }
		
		this.add(pannelloScrollDiscover, BorderLayout.CENTER);
		this.validate();
//		pannelloScrollDiscover.setVisible(true);
		repaint();
	}
	
	public void PremiPerFumetto(Fumetto fumetto) throws MalformedURLException, SQLException
	{
		remove(pannelloScrollDiscover);
		
		if (arrayPannelli.isEmpty())
		{
			arrayPannelli.put(fumetto.getNome(), new PannelloScrollPane(new PannelloDescrizioneFumetto(fumetto, pannelloCentro, this), null));
			this.add(arrayPannelli.get(fumetto.getNome()), BorderLayout.CENTER);
		}
		else
		{
			if(arrayPannelli.containsKey(fumetto.getNome()))
				this.add(arrayPannelli.get(fumetto.getNome()), BorderLayout.CENTER);
			else
			{
				arrayPannelli.put(fumetto.getNome(), new PannelloScrollPane(new PannelloDescrizioneFumetto(fumetto, pannelloCentro, this), null));
				this.add(arrayPannelli.get(fumetto.getNome()), BorderLayout.CENTER);
			}
		}
		
		this.validate();
//		pannelloScrollDescrizione.setVisible(true);
		repaint();
	}	
	
	public void PremiPerCapitolo() 
	{
		Iterator it = arrayPannelli.entrySet().iterator();
	    while (it.hasNext())
	    {
	        Map.Entry pairs = (Map.Entry)it.next();
	        if (it != null)
	        	remove(arrayPannelli.get(pairs.getKey()));
	    }
	    
		pannelloScrollCapitoli = new PannelloScrollPane(new PannelloPaginaCapitolo(), null);
		this.add(pannelloScrollCapitoli, BorderLayout.CENTER);
		this.validate();
//		pannelloScrollCapitoli.setVisible(true);
		repaint();
	}

	public void PremiPerProfiloUtente()
	{
		remove(pannelloCentro);
		this.add(pannelloProfilo,BorderLayout.CENTER);
		this.validate();
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		pannelloCentro.getGraphics().drawImage(image, 0,0, pannelloCentro);		
	}

}
