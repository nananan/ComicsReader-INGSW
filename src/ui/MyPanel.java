package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import technicalService.GestoreDataBase;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import domain.Fumetto;
import domain.Lettore;
import domain.Volume;

public class MyPanel extends JPanel
{		
	PannelloSopra pannelloSopra;
	PannelloDestro pannelloDestro = new PannelloDestro();
	PannelloSinistra pannelloSinistro = new PannelloSinistra(this);
	PannelloCentrale pannelloCentrale;
	PannelloFiltraggio pannelloFiltraggio;
	PannelloScrollPane pannelloScrollDiscover;
	PannelloProfilo pannelloProfilo;
	
	private PannelloScrollPane pannelloScrollCapitoli;
	private HashMap<String,PannelloScrollPane> arrayPannelli = new HashMap<>();
	private HashMap<String,PannelloCentrale> mapPannelliCentrali = new HashMap<>();
	private PannelloScrollPane pannelloScrollFiltraggio;
	private PannelloScrollPane pannelloScrollProfilo;
	
	private Lettore lettore;
	private PannelloSotto pannelloSotto;
	private boolean eStatoRichiestoIlDiscover;
	private boolean eStatoRichiestoLaRicerca;
	private boolean eStatoRichiestoIlProfilo;
	
	public MyPanel(Lettore lettore) throws IOException 
	{
		super();
		this.setLayout(new BorderLayout());
		pannelloSopra = new PannelloSopra(this, lettore, (int)pannelloDestro.getPreferredSize().getWidth());
		
		this.lettore = lettore;
		
		this.add(pannelloDestro,BorderLayout.EAST);
		this.add(pannelloSinistro,BorderLayout.WEST);
		this.add(pannelloSopra, BorderLayout.NORTH);
		PremiPerDiscover();
	
		this.add(mapPannelliCentrali.get("Discover"), BorderLayout.CENTER);
		
	}
	
	public void PremiPerFiltraggio()
	{
		GestoreDataBase.connetti();
		
		this.remove(pannelloSinistro);
		pannelloFiltraggio = new PannelloFiltraggio(this, (int)mapPannelliCentrali.get("Discover").getPreferredSize().getHeight());
		pannelloSopra.setBooleanaPerBottoneFiltro(false);
		pannelloScrollFiltraggio = new PannelloScrollPane(pannelloFiltraggio, null);
		pannelloScrollFiltraggio.getVerticalScrollBar().setUnitIncrement(15);
		pannelloScrollFiltraggio.getVerticalScrollBar().setUI(new MyScrollBarUI().setColor(new Color(91,84,84)));
		pannelloScrollFiltraggio.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(pannelloScrollFiltraggio, BorderLayout.WEST);
		this.validate();
		repaint();
	}
	
	public void PremiPerPannelloSinistro()
	{
		GestoreDataBase.connetti();
	
		pannelloSopra.setBooleanaPerBottoneFiltro(true);
		
		ArrayList<String> filtri = pannelloFiltraggio.getArrayDiFiltri();
		remove(pannelloScrollFiltraggio);
		this.add(pannelloSinistro,BorderLayout.WEST);
//
//		pannelloDiscover = new PannelloDiscover(pannelloCentro, this, filtri, 
//						pannelloFiltraggio.getTipoFumetto(), pannelloFiltraggio.getStatoFumetto());
//		
//		this.add(pannelloCentrale, BorderLayout.CENTER);
		
		this.validate();
		repaint();
	}
	
	public void PremiPerAvereRisultatiDellaRicerca(String tipoDaCercare, String nomeDaCercare)
	{
		eStatoRichiestoLaRicerca = true;
		eStatoRichiestoIlDiscover = false;
		eStatoRichiestoIlProfilo = false;
		
		if (pannelloScrollProfilo != null)
			remove(pannelloScrollProfilo);
		Iterator it = arrayPannelli.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pairs = (Map.Entry)it.next();
			if (it != null)
				remove(arrayPannelli.get(pairs.getKey()));
		}

		if (mapPannelliCentrali.get("Discover") != null)
			remove(mapPannelliCentrali.get("Discover"));
		
		if (!mapPannelliCentrali.containsKey("Ricerca"))
		{
			mapPannelliCentrali.put("Ricerca", new PannelloCentrale(this, 
					(int)pannelloDestro.getPreferredSize().getWidth()));
			mapPannelliCentrali.get("Ricerca").setRicerca(tipoDaCercare, nomeDaCercare);
			this.add(mapPannelliCentrali.get("Ricerca"), BorderLayout.CENTER);
			this.validate();
		}
		else
		{
			mapPannelliCentrali.get("Ricerca").rimuoviImmaginiPresenti();
			mapPannelliCentrali.get("Ricerca").setRicerca(tipoDaCercare, nomeDaCercare);
			this.add(mapPannelliCentrali.get("Ricerca"), BorderLayout.CENTER);
		}
		repaint();
	}
	
	public void getPannelloRicerca()
	{
		if (pannelloScrollProfilo != null)
			remove(pannelloScrollProfilo);
		Iterator it = arrayPannelli.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pairs = (Map.Entry)it.next();
			if (it != null)
				remove(arrayPannelli.get(pairs.getKey()));
		}

		if (mapPannelliCentrali.get("Discover") != null)
			remove(mapPannelliCentrali.get("Discover"));
		
		this.add(mapPannelliCentrali.get("Ricerca"), BorderLayout.CENTER);
		repaint();
	}
	
	public void PremiPerDiscover()
	{
		eStatoRichiestoIlDiscover = true;
		eStatoRichiestoLaRicerca = false;
		eStatoRichiestoIlProfilo = false;
		
		pannelloSinistro.rimuoviBottoniDelVolume();
		
		if (pannelloScrollProfilo != null)
			remove(pannelloScrollProfilo);
		Iterator it = arrayPannelli.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pairs = (Map.Entry)it.next();
			if (it != null)
				remove(arrayPannelli.get(pairs.getKey()));
		}

		if (mapPannelliCentrali.get("Ricerca") != null)
			remove(mapPannelliCentrali.get("Ricerca"));
		if (!mapPannelliCentrali.containsKey("Discover"))
		{
			mapPannelliCentrali.put("Discover", new PannelloCentrale(this, 
					(int)pannelloDestro.getPreferredSize().getWidth()));
			mapPannelliCentrali.get("Discover").setDiscover();
			this.add(mapPannelliCentrali.get("Discover"), BorderLayout.CENTER);
			this.validate();
		}
		else
		{
			mapPannelliCentrali.get("Discover").setDiscover();
			this.add(mapPannelliCentrali.get("Discover"), BorderLayout.CENTER);
		}	
			
//			pannelloScrollDiscover = new PannelloScrollPane(pannelloDiscover, new File("image/manga1.jpg"));
//			pannelloScrollDiscover.getVerticalScrollBar().setUnitIncrement(15);
//		    pannelloScrollDiscover.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		if (pannelloSotto != null)
		{
			remove(pannelloSotto);
			this.validate();
		}
		
		repaint();
	}
	
	private void setIndietro(String nomeFumetto)
	{
		if (eStatoRichiestoIlDiscover)
			((PannelloDescrizioneFumetto) arrayPannelli.get(nomeFumetto).getPanel()).setUltimoPannelloInstanziato("Discover");
		else if (eStatoRichiestoLaRicerca)
			((PannelloDescrizioneFumetto) arrayPannelli.get(nomeFumetto).getPanel()).setUltimoPannelloInstanziato("Ricerca");
		else if (eStatoRichiestoIlProfilo)
			((PannelloDescrizioneFumetto) arrayPannelli.get(nomeFumetto).getPanel()).setUltimoPannelloInstanziato("Profilo");
	}
	
	public void PremiPerFumetto(Fumetto fumetto, Image immagineCopertinaFumetto) 
	{
		if (pannelloScrollProfilo != null)
			remove(pannelloScrollProfilo);
		if (pannelloScrollCapitoli != null)
		{
			remove(pannelloScrollCapitoli);
			remove(pannelloSotto);
			pannelloSinistro.rimuoviBottoniDelVolume();
		}
		if (mapPannelliCentrali.get("Ricerca") != null)
			remove(mapPannelliCentrali.get("Ricerca"));
		if (mapPannelliCentrali.get("Discover") != null)
			remove(mapPannelliCentrali.get("Discover"));
		
		if (arrayPannelli.isEmpty())
		{
			arrayPannelli.put(fumetto.getNome(), new PannelloScrollPane(
					new PannelloDescrizioneFumetto(fumetto, immagineCopertinaFumetto, 
							mapPannelliCentrali.get("Discover").getWidth(),
							mapPannelliCentrali.get("Discover").getHeight(), 
							this, lettore), null));
			
			arrayPannelli.get(fumetto.getNome()).getVerticalScrollBar().setUnitIncrement(15);
			arrayPannelli.get(fumetto.getNome()).getVerticalScrollBar().setUI(new MyScrollBarUI().setColor(Color.GRAY));
			arrayPannelli.get(fumetto.getNome()).setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			setIndietro(fumetto.getNome());
			
			this.add(arrayPannelli.get(fumetto.getNome()), BorderLayout.CENTER);
		}
		else
		{
			if(arrayPannelli.containsKey(fumetto.getNome()))
			{
				this.add(arrayPannelli.get(fumetto.getNome()), BorderLayout.CENTER);
				setIndietro(fumetto.getNome());
			}
			else
			{
				arrayPannelli.put(fumetto.getNome(), new PannelloScrollPane(
						new PannelloDescrizioneFumetto(fumetto, immagineCopertinaFumetto, 
								mapPannelliCentrali.get("Discover").getWidth(),
								mapPannelliCentrali.get("Discover").getHeight(), 
								this, lettore), null));
				
				arrayPannelli.get(fumetto.getNome()).getVerticalScrollBar().setUnitIncrement(15);
				arrayPannelli.get(fumetto.getNome()).getVerticalScrollBar().setUI(new MyScrollBarUI().setColor(Color.GRAY));
				arrayPannelli.get(fumetto.getNome()).setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				setIndietro(fumetto.getNome());
				
				this.add(arrayPannelli.get(fumetto.getNome()), BorderLayout.CENTER);
			}
		}
		this.validate();
//		pannelloScrollDescrizione.setVisible(true);
		repaint();
	}	
	
	public void PremiPerCapitolo(Volume volume, Fumetto fumetto, int numeroCapitolo, Image immagineCopertinaFumetto) 
	{
		Iterator it = arrayPannelli.entrySet().iterator();
	    while (it.hasNext())
	    {
	        Map.Entry pairs = (Map.Entry)it.next();
	        if (it != null)
	        	remove(arrayPannelli.get(pairs.getKey()));
	    }
	    
		pannelloScrollCapitoli = new PannelloScrollPane(new PannelloVisualizzatore(mapPannelliCentrali.get("Discover").getWidth(), 
				mapPannelliCentrali.get("Discover").getHeight()), null);
		pannelloScrollCapitoli.getVerticalScrollBar().setUnitIncrement(15);
		pannelloScrollCapitoli.getVerticalScrollBar().setUI(new MyScrollBarUI().setColor(Color.GRAY));
		((PannelloVisualizzatore) pannelloScrollCapitoli.getPanel()).avviaVisualizzazione(volume,numeroCapitolo,1);
		this.add(pannelloScrollCapitoli, BorderLayout.CENTER);
	    pannelloScrollCapitoli.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    pannelloSotto = new PannelloSotto(((PannelloVisualizzatore) pannelloScrollCapitoli.getPanel()));
	    this.add(pannelloSotto, BorderLayout.SOUTH);
	    
	    pannelloSinistro.aggiungiBottoneVolume(immagineCopertinaFumetto, fumetto);
	    
		this.validate();
		repaint();
	}

	public void PremiPerProfiloUtente()
	{
		eStatoRichiestoIlProfilo = true;
		eStatoRichiestoIlDiscover = false;
		eStatoRichiestoLaRicerca = false;
		
		if (mapPannelliCentrali.get("Ricerca") != null)
			remove(mapPannelliCentrali.get("Ricerca"));
		if (mapPannelliCentrali.get("Discover") != null)
			remove(mapPannelliCentrali.get("Discover"));
		
		if (pannelloScrollCapitoli != null)
		{
			remove(pannelloScrollCapitoli);
			remove(pannelloSotto);
			pannelloSinistro.rimuoviBottoniDelVolume();
		}
		
		Iterator it = arrayPannelli.entrySet().iterator();
	    while (it.hasNext())
	    {
	        Map.Entry pairs = (Map.Entry)it.next();
	        if (it != null)
	        	remove(arrayPannelli.get(pairs.getKey()));
	    }
		if (pannelloScrollProfilo == null)
		{
			pannelloScrollProfilo = new PannelloScrollPane(new PannelloProfilo(lettore, this, 
					(int)mapPannelliCentrali.get("Discover").getPreferredSize().getWidth()), null);
			pannelloScrollProfilo.getVerticalScrollBar().setUnitIncrement(15);
			pannelloScrollProfilo.getVerticalScrollBar().setUI(new MyScrollBarUI().setColor(Color.GRAY));
		    pannelloScrollProfilo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
			this.add(pannelloScrollProfilo,BorderLayout.CENTER);
			this.validate();
		}
		else
			this.add(pannelloScrollProfilo,BorderLayout.CENTER);
			
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	}

	public Lettore getLettore()
	{
		return lettore;
	}
	
	public PannelloProfilo getPannelloProfiloUtente()
	{
		return pannelloProfilo;
	}

}
