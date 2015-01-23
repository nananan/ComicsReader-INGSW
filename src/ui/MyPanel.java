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

import domain.AppManager;
import domain.Fumetto;
import domain.Lettore;
import domain.Volume;

public class MyPanel extends JPanel
{		
	PannelloSopra pannelloSopra;
	PannelloDestro pannelloDestro;
	PannelloSinistra pannelloSinistro = new PannelloSinistra(this);
	PannelloCentrale pannelloCentrale;
	PannelloFiltraggio pannelloFiltraggio;
	PannelloScrollPane pannelloScrollDiscover;
	PannelloProfilo pannelloProfilo;
	PannelloVisualizzatore pannelloVisualizzatore;
	
	private PannelloScrollPane pannelloScrollCapitoli;
	private HashMap<String,PannelloScrollPane> arrayPannelli = new HashMap<>();
	private HashMap<String,PannelloCentrale> mapPannelliCentrali = new HashMap<>();
	private HashMap<String,PannelloProfilo> mapPannelliProfilo = new HashMap<>();
	private PannelloScrollPane pannelloScrollFiltraggio;
	
	private Lettore lettore;
	private PannelloSotto pannelloSotto;
	private boolean eStatoRichiestoIlDiscover;
	private boolean eStatoRichiestoLaRicerca;
	private boolean eStatoRichiestoIlProfilo;
	private boolean eStatoRichiestoPiuLetti;
	private boolean eStatoRichiestoPiuVotati;
	private boolean eStatoRichiestoIlFiltro;
	private boolean eStatoRichiestoIlProfiloAltroUtente;
	private Lettore lettoreVisto;
	private PannelloScrollPane pannelloScrollDestro;
	
	private static Color COLORE = Color.GRAY;
	
	public MyPanel() throws IOException 
	{
		super();
		this.setLayout(new BorderLayout());
		pannelloScrollDestro = new PannelloScrollPane(new PannelloDestro(this), 0, new Color(91,84,84));
		pannelloSopra = new PannelloSopra(this, (int)pannelloScrollDestro.getPreferredSize().getWidth());
		
		this.lettore = AppManager.getLettore();
		
		this.add(pannelloScrollDestro,BorderLayout.EAST);
		this.add(pannelloSinistro,BorderLayout.WEST);
		this.add(pannelloSopra, BorderLayout.NORTH);
		PremiPerDiscover();
	
		this.add(mapPannelliCentrali.get("Discover"), BorderLayout.CENTER);
		
		this.validate();
		
	}
	
	public void PremiPerFiltraggio()
	{
		GestoreDataBase.connetti();
		
		this.remove(pannelloSinistro);
		pannelloFiltraggio = new PannelloFiltraggio(this, (int)mapPannelliCentrali.get("Discover").getPreferredSize().getHeight());
		pannelloSopra.setBooleanaPerBottoneFiltro(false);
		pannelloScrollFiltraggio = new PannelloScrollPane(pannelloFiltraggio, 0, new Color(91,84,84));
		this.add(pannelloScrollFiltraggio, BorderLayout.WEST);
		this.validate();
		repaint();
	}
	
	public void PremiPerPannelloSinistro()
	{
		GestoreDataBase.connetti();
	
		pannelloSopra.setBooleanaPerBottoneFiltro(true);
		
		rimuoviPrecedenti();
		rimuoviBooleani();
		pannelloSopra.setBooleanaPerBottoneFiltro(true);
		eStatoRichiestoIlFiltro = true;
		
		ArrayList<String> filtri = pannelloFiltraggio.getArrayDiFiltri();
		int statoFumetto = pannelloFiltraggio.getStatoFumetto();
		int tipoFumetto = pannelloFiltraggio.getTipoFumetto();
		remove(pannelloScrollFiltraggio);
		this.add(pannelloSinistro,BorderLayout.WEST);
		
		if (!mapPannelliCentrali.containsKey("Filtri"))
		{
			mapPannelliCentrali.put("Filtri", new PannelloCentrale(this, 
					(int)pannelloScrollDestro.getPreferredSize().getWidth()));
			mapPannelliCentrali.get("Filtri").setRicercaFiltri(filtri, statoFumetto, tipoFumetto);
			this.add(mapPannelliCentrali.get("Filtri"), BorderLayout.CENTER);
			this.validate();
		}
		else
		{
			mapPannelliCentrali.get("Filtri").rimuoviImmaginiPresenti();
			mapPannelliCentrali.get("Filtri").setRicercaFiltri(filtri, statoFumetto, tipoFumetto);
			this.add(mapPannelliCentrali.get("Filtri"), BorderLayout.CENTER);
		}
		
		repaint();
	}
	
	public void PremiPerAvereRisultatiDellaRicerca(String tipoDaCercare, String nomeDaCercare)
	{
		rimuoviPrecedenti();
		rimuoviBooleani();
		eStatoRichiestoLaRicerca = true;
		
		if (!mapPannelliCentrali.containsKey("Ricerca"))
		{
			mapPannelliCentrali.put("Ricerca", new PannelloCentrale(this, 
					(int)pannelloScrollDestro.getPreferredSize().getWidth()));
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
		if (pannelloProfilo != null)
			remove(pannelloProfilo);
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
		rimuoviPrecedenti();
		rimuoviBooleani();
		pannelloSopra.setBooleanaPerBottoneFiltro(true);
		pannelloSinistro.setColorBottoneDiscover(new Color(35,148,188));
		eStatoRichiestoIlDiscover = true;
		if (!mapPannelliCentrali.containsKey("Discover"))
		{
			mapPannelliCentrali.put("Discover", new PannelloCentrale(this, 
					(int)pannelloScrollDestro.getPreferredSize().getWidth()));
			mapPannelliCentrali.get("Discover").setDiscover();
			this.add(mapPannelliCentrali.get("Discover"), BorderLayout.CENTER);
			this.validate();
		}
		else
		{
//			mapPannelliCentrali.get("Discover").setDiscover();
			this.add(mapPannelliCentrali.get("Discover"), BorderLayout.CENTER);
		}	
			
//			pannelloScrollDiscover = new PannelloScrollPane(pannelloDiscover, new File("image/manga1.jpg"));
//			pannelloScrollDiscover.getVerticalScrollBar().setUnitIncrement(15);
//		    pannelloScrollDiscover.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		if (pannelloSotto != null)
			remove(pannelloSotto);
		
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
		else if (eStatoRichiestoPiuLetti)
			((PannelloDescrizioneFumetto) arrayPannelli.get(nomeFumetto).getPanel()).setUltimoPannelloInstanziato("PiuLetti");
		else if (eStatoRichiestoPiuVotati)
			((PannelloDescrizioneFumetto) arrayPannelli.get(nomeFumetto).getPanel()).setUltimoPannelloInstanziato("PiuVotati");
		else if (eStatoRichiestoIlFiltro)
			((PannelloDescrizioneFumetto) arrayPannelli.get(nomeFumetto).getPanel()).setUltimoPannelloInstanziato("Filtri");
		else if (eStatoRichiestoIlProfiloAltroUtente)
		{
			((PannelloDescrizioneFumetto) arrayPannelli.get(nomeFumetto).getPanel()).setUltimoPannelloInstanziato("ProfiloAltroUtente");
			((PannelloDescrizioneFumetto) arrayPannelli.get(nomeFumetto).getPanel()).setUltimoLettoreVisto(lettoreVisto);
		}
	}
	
	private void setUltimoLettoreVisto(Lettore lettoreVisto)
	{
		this.lettoreVisto = lettoreVisto;
	}
	
	public void PremiPerFumetto(Fumetto fumetto, Image immagineCopertinaFumetto) 
	{
		rimuoviPrecedenti();
		
		if (arrayPannelli.isEmpty())
		{
			arrayPannelli.put(fumetto.getNome(), new PannelloScrollPane(
					new PannelloDescrizioneFumetto(fumetto, immagineCopertinaFumetto, 
							mapPannelliCentrali.get("Discover").getWidth(),
							mapPannelliCentrali.get("Discover").getHeight(), this), 0, COLORE));
			
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
								mapPannelliCentrali.get("Discover").getHeight(), this), 0, COLORE));
				
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
	    
	    pannelloVisualizzatore = new PannelloVisualizzatore(mapPannelliCentrali.get("Discover").getWidth(), 
				mapPannelliCentrali.get("Discover").getHeight(), this);
	    
		pannelloScrollCapitoli = new PannelloScrollPane(pannelloVisualizzatore, 0, COLORE);
		((PannelloVisualizzatore) pannelloScrollCapitoli.getPanel()).avviaVisualizzazione(volume,numeroCapitolo,1);
		this.add(pannelloScrollCapitoli, BorderLayout.CENTER);
	    pannelloSotto = new PannelloSotto(this);
	    this.add(pannelloSotto, BorderLayout.SOUTH);
	    
	    pannelloSinistro.aggiungiBottoneVolume(immagineCopertinaFumetto, fumetto);
	    
		this.validate();
		repaint();
	}

	public void PremiPerProfiloUtente()
	{
		rimuoviPrecedenti();
		rimuoviBooleani();
		
		eStatoRichiestoIlProfilo = true;
		if (mapPannelliProfilo.get(lettore.getIdFacebook()) == null)
		{
			mapPannelliProfilo.put(lettore.getIdFacebook(), new PannelloProfilo(this, 
					(int)mapPannelliCentrali.get("Discover").getPreferredSize().getWidth(),this.lettore));
			
			this.add(mapPannelliProfilo.get(lettore.getIdFacebook()),BorderLayout.CENTER);
			this.validate();
		}
		else
			this.add(mapPannelliProfilo.get(lettore.getIdFacebook()),BorderLayout.CENTER);
			
		repaint();
	}
	
	public void premiPerAverePiuLetti()
	{
		rimuoviPrecedenti();
		rimuoviBooleani();
		pannelloSinistro.setColorBottoneTopRead(new Color(35,148,188));
		eStatoRichiestoPiuLetti = true;
		if (!mapPannelliCentrali.containsKey("PiuLetti"))
		{
			mapPannelliCentrali.put("PiuLetti", new PannelloCentrale(this, 
					(int)pannelloDestro.getPreferredSize().getWidth()));
			mapPannelliCentrali.get("PiuLetti").setTopRead();
			this.add(mapPannelliCentrali.get("PiuLetti"), BorderLayout.CENTER);
			this.validate();
		}
		else
		{
//			mapPannelliCentrali.get("PiuLetti").setTopRead();
			this.add(mapPannelliCentrali.get("PiuLetti"), BorderLayout.CENTER);
		}	
		
		repaint();
	}
	
	public void premiPerAverePiuVotati()
	{
		rimuoviPrecedenti();
		rimuoviBooleani();
		pannelloSinistro.setColorBottoneTopRated(new Color(35,148,188));
		eStatoRichiestoPiuVotati = true;
		
		if (!mapPannelliCentrali.containsKey("PiuVotati"))
		{
			mapPannelliCentrali.put("PiuVotati", new PannelloCentrale(this, 
					(int)pannelloScrollDestro.getPreferredSize().getWidth()));
			mapPannelliCentrali.get("PiuVotati").setTopRated();
			this.add(mapPannelliCentrali.get("PiuVotati"), BorderLayout.CENTER);
			this.validate();
		}
		else
//			mapPannelliCentrali.get("PiuVotati").setTopRead();
			this.add(mapPannelliCentrali.get("PiuVotati"), BorderLayout.CENTER);
		
		repaint();
	}
	
	public void premiPerPaginaSuccessiva()
	{
		pannelloVisualizzatore.vaiAPaginaSuccessiva();
	}
	public void premiPerPaginaPrecedente()
	{
		pannelloVisualizzatore.vaiAPaginaPrecedente();
	}
	
	public void premiPerAvereProfiloDiAltroUtente(Lettore utente)
	{
		rimuoviPrecedenti();
		rimuoviBooleani();
		
		eStatoRichiestoIlProfiloAltroUtente = true;
		setUltimoLettoreVisto(utente);
		
		if (mapPannelliProfilo.get(utente.getIdFacebook()) == null)
		{
			mapPannelliProfilo.put(utente.getIdFacebook(), new PannelloProfilo(this, 
					(int)mapPannelliCentrali.get("Discover").getPreferredSize().getWidth(),utente));
			this.add(mapPannelliProfilo.get(utente.getIdFacebook()),BorderLayout.CENTER);
			this.validate();
		}
		else
			this.add(mapPannelliProfilo.get(utente.getIdFacebook()),BorderLayout.CENTER);
			
		repaint();
	}
	
	private void rimuoviBooleani()
	{
		eStatoRichiestoIlProfilo = false;
		eStatoRichiestoIlDiscover = false;
		eStatoRichiestoLaRicerca = false;
		eStatoRichiestoPiuLetti = false;
		eStatoRichiestoPiuVotati = false;
		eStatoRichiestoIlFiltro = false;
		eStatoRichiestoIlProfiloAltroUtente = false;
	}
	
	private void rimuoviPrecedenti()
	{
		if (mapPannelliCentrali.get("Ricerca") != null)
			remove(mapPannelliCentrali.get("Ricerca"));
		if (mapPannelliCentrali.get("Discover") != null)
			remove(mapPannelliCentrali.get("Discover"));
		if (mapPannelliCentrali.get("PiuLetti") != null)
			remove(mapPannelliCentrali.get("PiuLetti"));
		if (mapPannelliCentrali.get("PiuVotati") != null)
			remove(mapPannelliCentrali.get("PiuVotati"));
		if (mapPannelliCentrali.get("Filtri") != null)
			remove(mapPannelliCentrali.get("Filtri"));
		
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
	    
	    Iterator itProfilo = mapPannelliProfilo.entrySet().iterator();
	    while (itProfilo.hasNext())
	    {
	        Map.Entry pairs = (Map.Entry)itProfilo.next();
	        if (itProfilo != null)
	        	remove(mapPannelliProfilo.get(pairs.getKey()));
	    }
	    
	    pannelloSinistro.deselezionaBottoni();
		pannelloSopra.setBooleanaPerBottoneFiltro(false);

	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		pannelloScrollDestro.getPanel().paintComponents(g);
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
