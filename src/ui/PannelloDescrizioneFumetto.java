package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import technicalService.GestoreDataBase;
import domain.AppManager;
import domain.Fumetto;
import domain.Lettore;
import domain.VisualizzatoreCapitoli;
import domain.Volume;
import downloader.ImmagineNonPresenteException;

@SuppressWarnings("serial")
public class PannelloDescrizioneFumetto extends JPanel
{
	private int indiceVolumi = 0;
	private Image scaledImage;
	private Text nome;
	private JLabel forImage;
	private ImageIcon imageFumetto;
	private Text autore;
	private Text artista;
	private JTextArea descrizione;
	private Text eCompleto;
	private Text eOccidentale;	

	private MyPanel panel;
	private StarRater starRated;
	private JButton bottoneIndietro;
	private JButton bottoneAvantiVolumi;
	private JButton bottoneIndietroVolumi;
	private JButton bottonePreferiti;
	private JButton bottoneDaLeggere;
	private ImageIcon imagePrev = new ImageIcon("image/prev-icon.png");
	private ImageIcon imageAvanti = new ImageIcon("image/next.png");
	private ImageIcon imagePiu = new ImageIcon("image/toRead.png");
	private ImageIcon imageMeno = new ImageIcon("image/notToRead.png");
	
	private ArrayList<BottoneCapitolo> bottoniCapitoli = new ArrayList<>();
	
	private ArrayList<BottoneVolume> bottoniVolumi = new ArrayList<>();
	private ArrayList<Text> nomiVolumi = new ArrayList<>();
	private Text stringaVolumi;
	private Text stringaCapitoli;
	
	private Fumetto fumetto;
	private MyListener listener;
	
	private Image immagineCopertinaFumetto;
	private Lettore lettore;
	private Lettore ultimoLettoreVisto;
	private Volume volumi[];
	
	private String ultimoPannelloInstanziato;
	
	public PannelloDescrizioneFumetto(Fumetto fumetto, Image immagineCopertinaFumetto, int panelWidth, int panelHeight,final MyPanel panel) 
	{
		super();
		
		this.panel = panel;
		this.fumetto = fumetto;
		this.immagineCopertinaFumetto = immagineCopertinaFumetto;
		this.lettore = AppManager.getLettore();
		
		this.setBackground(Color.GRAY);		
		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		this.setBounds(10, 10, (int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight());
		this.setLayout(null);
		
		ultimoPannelloInstanziato = new String();
		
		listener = new MyListener();
		
		nome = new Text(fumetto.getNome(), 45, Color.DARK_GRAY);
		nome.setBounds(10,10, (int)nome.getPreferredSize().getWidth(), (int)nome.getPreferredSize().getHeight());
		add(nome);
		
		scaledImage = immagineCopertinaFumetto;
		
		imageFumetto = new ImageIcon(scaledImage);
		forImage = new JLabel();
		forImage.setIcon(imageFumetto);
		forImage.setPreferredSize(new Dimension(200, 300));
		forImage.setBounds(10, 10+nome.getY()+(int)nome.getPreferredSize().getHeight(), (int)forImage.getPreferredSize().getWidth(), (int)forImage.getPreferredSize().getHeight());
		forImage.setBorder(BorderFactory.createLineBorder(Color.black,3));
		add(forImage);
		
		Text stringaAutore = new Text("Autore", 18, Color.DARK_GRAY);
		stringaAutore.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,2 + nome.getY() +(int)nome.getPreferredSize().getHeight(), (int)stringaAutore.getPreferredSize().getWidth(), (int)stringaAutore.getPreferredSize().getHeight());
		add(stringaAutore);
		autore = new Text(fumetto.getAutore(), 16, Color.WHITE);
		autore.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,1+stringaAutore.getY()+(int)stringaAutore.getPreferredSize().getHeight(), (int)autore.getPreferredSize().getWidth(), (int)autore.getPreferredSize().getHeight());
		add(autore);
	
		Text stringaArtista = new Text("Artista", 18, Color.DARK_GRAY);
		stringaArtista.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 2 + autore.getY() +(int)autore.getPreferredSize().getHeight(), (int)stringaArtista.getPreferredSize().getWidth(), (int)stringaArtista.getPreferredSize().getHeight());
		add(stringaArtista);
		artista = new Text(fumetto.getArtista(), 16, Color.WHITE);
		artista.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,1 + stringaArtista.getY() +(int)stringaArtista.getPreferredSize().getHeight(), (int)artista.getPreferredSize().getWidth(), (int)artista.getPreferredSize().getHeight());
		add(artista);
		
		Text stringaStato = new Text("Stato", 18, Color.DARK_GRAY);
		stringaStato.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,2+ artista.getY()+(int)artista.getPreferredSize().getHeight(), (int)stringaStato.getPreferredSize().getWidth(), (int)stringaStato.getPreferredSize().getHeight());
		add(stringaStato);
		if (fumetto.isEcompleto())
			eCompleto = new Text("Completo", 16, Color.WHITE);
		else
			eCompleto = new Text("Non completo",  16, Color.WHITE);
		eCompleto.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,1 + stringaStato.getY() +(int)stringaStato.getPreferredSize().getHeight(), (int)eCompleto.getPreferredSize().getWidth(), (int)eCompleto.getPreferredSize().getHeight());
		add(eCompleto);
		
		Text stringaTipo = new Text("Tipo", 18, Color.DARK_GRAY);
		stringaTipo.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,2 + eCompleto.getY() + (int)eCompleto.getPreferredSize().getHeight(), (int)stringaTipo.getPreferredSize().getWidth(), (int)stringaTipo.getPreferredSize().getHeight());
		add(stringaTipo);
		if (fumetto.isOccidentale())
			eOccidentale = new Text("Occidentale", 16, Color.WHITE);
		else
			eOccidentale = new Text("Orientale", 16, Color.WHITE);
		eOccidentale.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 1+ stringaTipo.getY() + (int) stringaTipo.getPreferredSize().getHeight(), (int)eOccidentale.getPreferredSize().getWidth(), (int)eOccidentale.getPreferredSize().getHeight());
		add(eOccidentale);
		
		Text stringaGenere = new Text("Genere", 18, Color.DARK_GRAY);
		stringaGenere.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 2+ eOccidentale.getY() + (int) eOccidentale.getPreferredSize().getHeight(), (int)stringaGenere.getPreferredSize().getWidth(), (int)stringaGenere.getPreferredSize().getHeight());
		add(stringaGenere);
	
		GestoreDataBase.connetti();

		fumetto.apriFumetto();
	
		String genere = new String("");
		
		String generiFumetto[] = fumetto.getGeneri();
		for (int i = 0; i < generiFumetto.length; i++)
		{
			if (i < generiFumetto.length - 1)
				genere += generiFumetto[i] +", ";
			else
				genere += generiFumetto[i];
		}
		
		Text generi = new Text(genere, 16, Color.WHITE);
		generi.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 1+ stringaGenere.getY() + (int) stringaGenere.getPreferredSize().getHeight(), (int)generi.getPreferredSize().getWidth(), (int)generi.getPreferredSize().getHeight());
		add(generi);

		Text stringaValutazione = new Text("Valutazione", 18, Color.DARK_GRAY);
		stringaValutazione.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 2+ generi.getY() + (int) generi.getPreferredSize().getHeight(), (int)stringaValutazione.getPreferredSize().getWidth(), (int)stringaValutazione.getPreferredSize().getHeight());
		add(stringaValutazione);
		starRated = new StarRater(lettore, fumetto);
		starRated.setRating((float)fumetto.getValutazioneMedia());
		starRated.setPreferredSize(new Dimension(300, 10));
		starRated.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 1+ stringaValutazione.getY() + (int) stringaValutazione.getPreferredSize().getHeight(), (int)starRated.getPreferredSize().getWidth(), (int)starRated.getPreferredSize().getHeight());
		add(starRated);
		
		bottonePreferiti = new JButton();
		if (!lettore.eInPreferiti(fumetto))
			setBottone(bottonePreferiti, imagePiu, 25, 25);
		else
			setBottone(bottonePreferiti, imageMeno, 25, 25);
		bottonePreferiti.setText("Preferiti");
		bottonePreferiti.setFont(new Font("Caladea", Font.BOLD, 14));
		bottonePreferiti.setForeground(Color.DARK_GRAY);
		bottonePreferiti.setBounds(5+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 2+ starRated.getY() + (int) starRated.getPreferredSize().getHeight(), (int)bottonePreferiti.getPreferredSize().getWidth(), (int)bottonePreferiti.getPreferredSize().getHeight());
		
		bottonePreferiti.addActionListener(listener);
		add(bottonePreferiti);
		
		bottoneDaLeggere = new JButton();
		if (!lettore.eInDaLeggere(fumetto))
			setBottone(bottoneDaLeggere, imagePiu, 25, 25);
		else
			setBottone(bottoneDaLeggere, imageMeno, 25, 25);
		bottoneDaLeggere.setText("Da Leggere");
		bottoneDaLeggere.setFont(new Font("Caladea", Font.BOLD, 14));
		bottoneDaLeggere.setForeground(Color.DARK_GRAY);
		bottoneDaLeggere.setBounds(10+bottonePreferiti.getX()+(int)bottonePreferiti.getPreferredSize().getWidth(), 2+ starRated.getY() + (int) starRated.getPreferredSize().getHeight(), (int)bottoneDaLeggere.getPreferredSize().getWidth(), (int)bottoneDaLeggere.getPreferredSize().getHeight());
		bottoneDaLeggere.addActionListener(listener);
		add(bottoneDaLeggere);
		
		descrizione = new JTextArea(fumetto.getDescrizione());
		descrizione.setFont(new Font("Caladea", Font.BOLD, 14));
		descrizione.setOpaque(false);
		descrizione.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth() - (int)this.getPreferredSize().getHeight()/8, (int)this.getPreferredSize().getHeight() / 2));
//		descrizione.setLineWrap(true);
//		descrizione.setWrapStyleWord(true);
		descrizione.setBounds(10, 10 + forImage.getY() +(int)forImage.getPreferredSize().getHeight(), (int)descrizione.getPreferredSize().getWidth(), descrizione.getLineCount()*20);
		descrizione.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth() - (int)this.getPreferredSize().getHeight()/8, descrizione.getLineCount()*20));
		
		add(descrizione);

		stringaVolumi = new Text("Volumi", 24, Color.DARK_GRAY);
		stringaVolumi.setBounds(10,10 + descrizione.getY()+(int)descrizione.getPreferredSize().getHeight(), (int)stringaVolumi.getPreferredSize().getWidth(), (int)stringaVolumi.getPreferredSize().getHeight());
		add(stringaVolumi);
		
		this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(), (int)this.getPreferredSize().getHeight()+descrizione.getY()+(int)descrizione.getPreferredSize().getHeight()));
	
		if (fumetto.getNumeroVolumi() != 0)
		{
			bottoneAvantiVolumi = new JButton();
			setBottone(bottoneAvantiVolumi, imageAvanti, 30, 30);
			bottoneAvantiVolumi.setBounds(stringaVolumi.getX()+((int)this.getPreferredSize().getWidth()- (int)bottoneAvantiVolumi.getPreferredSize().getWidth()-10), stringaVolumi.getY(), 30, 30);
			add(bottoneAvantiVolumi);
			
			bottoneIndietroVolumi = new JButton();
			setBottone(bottoneIndietroVolumi, imagePrev, 30, 30);
			bottoneIndietroVolumi.setBounds(stringaVolumi.getX()+((int)this.getPreferredSize().getWidth()-(int)bottoneIndietroVolumi.getPreferredSize().getWidth() - (int)bottoneAvantiVolumi.getPreferredSize().getWidth()-10), stringaVolumi.getY(), 30, 30);
			add(bottoneIndietroVolumi);
			
			volumi = fumetto.getVolumi();
			disegnaCopertineVolumi(0, 4, 0);
			bottoneAvantiVolumi.addActionListener(listener);
			bottoneIndietroVolumi.addActionListener(listener);
			
			stringaCapitoli = new Text("Capitoli", 24, Color.DARK_GRAY);
			stringaCapitoli.setBounds(10, 15 + nomiVolumi.get(0).getY() + (int)nomiVolumi.get(0).getPreferredSize().getHeight(), (int) stringaCapitoli.getPreferredSize().getWidth(), (int) stringaCapitoli.getPreferredSize().getHeight() );
			
			for (BottoneVolume bottoneVolumi : bottoniVolumi) {
				bottoneVolumi.addActionListener(listener);
			}
		}
		else
		{
			Text volumiNonPresenti = new Text("Non sono presenti Volumi", 18, Color.WHITE);
			volumiNonPresenti.setBounds(10, 10+stringaVolumi.getY()+
					(int)stringaVolumi.getPreferredSize().getHeight(), 
					(int)volumiNonPresenti.getPreferredSize().getWidth(), 
					(int)volumiNonPresenti.getPreferredSize().getHeight());
			
			add(volumiNonPresenti);
		}
		
		bottoneIndietro = new JButton();
		setBottone(bottoneIndietro, imagePrev, 30, 30);
		bottoneIndietro.setBounds(nome.getX()+((int)this.getPreferredSize().getWidth()-(int)bottoneIndietro.getPreferredSize().getWidth()-20), nome.getY()*2, 30, 30);
		add(bottoneIndietro);
		bottoneIndietro.addActionListener(listener);
	}
	
	public Fumetto getFumetto()
	{
		return fumetto;
	}
	
	public void setUltimoPannelloInstanziato(String nomePannello)
	{
		this.ultimoPannelloInstanziato = nomePannello;
	}
	
	private void setBottone(JButton bottone, ImageIcon imageIcon, int larghezza, int altezza)
	{
		Image imageScaled = imageIcon.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		imageIcon.setImage(imageScaled);
		
		bottone.setIcon(imageIcon);
		bottone.setPressedIcon(imageIcon);
		bottone.setBorderPainted(false);
		bottone.setFocusPainted(false);
		bottone.setContentAreaFilled(false);
		bottone.setBackground(this.getBackground());
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		for (BottoneVolume bottoneFumetto : bottoniVolumi) 
		{
			g.drawImage(bottoneFumetto.getImageScaled(), 0,0, this);
		}
		super.paintComponent(g);
	}
	
	public void getCapitoliVolume(Volume volume)
	{
		if (!stringaCapitoli.equals(null))
		{
			remove(stringaCapitoli);
			for (BottoneCapitolo myButton : bottoniCapitoli)
			{
				remove(myButton);
			}
			bottoniCapitoli.clear();
		}
		
		add(stringaCapitoli);
		
		volume.caricaCapitoli();
		
		int altezza = 0;
		for (int j = 0; j < volume.getCapitoli().length; j++) 
		{			
			BottoneCapitolo nomeCapitolo = new BottoneCapitolo(
					"Capitolo "+volume.getCapitoli()[j].getNumero()+ ": "+volume.getCapitoli()[j].getTitolo(), 
					18, Color.WHITE, volume, immagineCopertinaFumetto,
					volume.getCapitoli()[j].getNumero(), panel, fumetto, lettore);
			
			bottoniCapitoli.add(nomeCapitolo);
			
			if (j == 0)
				bottoniCapitoli.get(j).setBounds(10, 10+stringaCapitoli.getY()+(int)stringaCapitoli.getPreferredSize().getHeight(), (int)bottoniCapitoli.get(j).getPreferredSize().getWidth(), (int)bottoniCapitoli.get(j).getPreferredSize().getHeight());
			else
				bottoniCapitoli.get(j).setBounds(10, 10+bottoniCapitoli.get(j-1).getY()+(int)bottoniCapitoli.get(j-1).getPreferredSize().getHeight(), (int)bottoniCapitoli.get(j).getPreferredSize().getWidth(), (int)bottoniCapitoli.get(j).getPreferredSize().getHeight());
			
			add(bottoniCapitoli.get(j));

			altezza += bottoniCapitoli.get(j).getPreferredSize().getWidth();
			if (altezza > (int) getPreferredSize().getHeight())
			{
				setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight()+(int)bottoniCapitoli.get(j-1).getPreferredSize().getHeight()));
			}
		}
	}
	
	public void disegnaCopertineVolumi(int indiceIniziale, int indiceFinale, int tipo)
	{
		fumetto.caricaProssimeCopertine();
		
		if (indiceFinale >= volumi.length)
		{
			indiceFinale = volumi.length;
			indiceVolumi = volumi.length;
		}
		for (int j = indiceIniziale; j < indiceFinale; j++) 
		{
			Text nomeVolume = new Text(fumetto.getVolumi()[j].getNumero()+" - "+fumetto.getVolumi()[j].getNome(), 12, Color.WHITE);
			nomiVolumi.add(nomeVolume);
			
			BottoneVolume bottoneVolume = new BottoneVolume(volumi[j].getCopertina(), volumi[j], fumetto, this);
			bottoneVolume.setBorder(BorderFactory.createLineBorder(Color.black,3));
			bottoniVolumi.add(bottoneVolume);
			
			bottoniVolumi.get(j).setPreferredSize(new Dimension(200,300));
			
			if (j == indiceIniziale)
				bottoniVolumi.get(j).setBounds(30, 20 + stringaVolumi.getY() +(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(j).getPreferredSize().getWidth(), (int)bottoniVolumi.get(j).getPreferredSize().getHeight());
			else
				bottoniVolumi.get(j).setBounds(25+bottoniVolumi.get(j-1).getX()+(int)bottoniVolumi.get(j-1).getPreferredSize().getWidth(),20 + stringaVolumi.getY()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(j).getPreferredSize().getWidth(), (int)bottoniVolumi.get(j).getPreferredSize().getHeight());
		
			nomiVolumi.get(j).setBounds(bottoniVolumi.get(j).getX(), 10 + bottoniVolumi.get(j).getY() + (int)bottoniVolumi.get(j).getPreferredSize().getHeight(), (int)nomiVolumi.get(j).getPreferredSize().getWidth(), (int)nomiVolumi.get(j).getPreferredSize().getHeight());

			add(bottoniVolumi.get(j));
			add(nomiVolumi.get(j));
		}

		if (tipo == 0)
		{
			if (indiceVolumi < volumi.length - 4)
				indiceVolumi += 4;
		}
				
		if (indiceVolumi >= volumi.length)
			bottoneAvantiVolumi.setEnabled(false);
	}
	
	public void setUltimoLettoreVisto(Lettore lettoreVisto)
	{
		this.ultimoLettoreVisto = lettoreVisto;
	}
	
	private class MyListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if (source == bottoneIndietro)  //BOTTONE INDIETRO
			{
				if (ultimoPannelloInstanziato.equals("Discover"))
					panel.PremiPerDiscover();
				else if (ultimoPannelloInstanziato.equals("Ricerca"))
					panel.getPannelloRicerca();
				else if (ultimoPannelloInstanziato.equals("Profilo"))
					panel.PremiPerProfiloUtente();
				else if (ultimoPannelloInstanziato.equals("PiuLetti"))
					panel.premiPerAverePiuLetti();
				else if (ultimoPannelloInstanziato.equals("PiuVotati"))
					panel.premiPerAverePiuVotati();
				else if (ultimoPannelloInstanziato.equals("ProfiloAltroUtente"))
					panel.premiPerAvereProfiloDiAltroUtente(ultimoLettoreVisto);
			}
			else if (source == bottoneIndietroVolumi)   //BOTTONE INDIETRO VOLUMI
			{
				bottoneAvantiVolumi.setEnabled(true);
				
				if (indiceVolumi > 0)
				{
					for (int i = indiceVolumi-4; i < indiceVolumi; i++)
					{
						remove(bottoniVolumi.get(i));
						remove(nomiVolumi.get(i));
					}
				}
				indiceVolumi -= 4;
				if (indiceVolumi <= 4)
				{
					bottoneIndietroVolumi.setEnabled(false);
					indiceVolumi = 4;
				}
				if (indiceVolumi >= volumi.length)
					indiceVolumi -= volumi.length;
					
				disegnaCopertineVolumi(indiceVolumi-4, indiceVolumi, 1);
				repaint();
				
			}
			else if (source == bottoneAvantiVolumi)  //BOTTONE AVANTI VOLUMI
			{
				bottoneIndietroVolumi.setEnabled(true);
				
				if (indiceVolumi >= 4)
				{
					for (int i = indiceVolumi - 4; i < indiceVolumi; i++)
					{
						remove(bottoniVolumi.get(i));
						remove(nomiVolumi.get(i));
					}
				}
				disegnaCopertineVolumi(indiceVolumi, indiceVolumi+4, 0);
				repaint();
			}
			
			if (source == bottonePreferiti)
			{
				if (bottonePreferiti.getIcon() == imagePiu)
				{
					panel.getLettore().inserisciPreferiti(fumetto);
					setBottone(bottonePreferiti, imageMeno, 25, 25);
				}
				else
				{
					panel.getLettore().rimuoviPreferiti(fumetto);
					setBottone(bottonePreferiti, imagePiu, 25, 25);
				}
				
			}
			else if (source == bottoneDaLeggere)
			{
				if (bottoneDaLeggere.getIcon() == imagePiu)
				{
					panel.getLettore().inserisciDaLeggere(fumetto);
					setBottone(bottoneDaLeggere, imageMeno, 25, 25);
				}
				else
				{
					panel.getLettore().rimuoviDaLeggere(fumetto);
					setBottone(bottoneDaLeggere, imagePiu, 25, 25);
				}
			}
			
			repaint();
		}
		
	}
	
}
