package ui;

import java.awt.BorderLayout;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

import domain.Fumetto;
import domain.Lettore;
import domain.VisualizzatoreCapitoli;
import domain.Volume;
import downloader.ImmagineNonPresenteException;

@SuppressWarnings("serial")
public class PannelloDescrizioneFumetto extends JPanel
{
	private int indiceVolumi = 0;
	private File file;
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
	private MyButton bottonePreferiti;
	private MyButton bottoneDaLeggere;
	private ImageIcon imagePrev = new ImageIcon("image/prev-icon.png");
	private ImageIcon imageAvanti = new ImageIcon("image/next.png");
	
	private ArrayList<BottoneCapitolo> bottoniCapitoli = new ArrayList<>();
	
	private ArrayList<BottoneFumetto> bottoniVolumi = new ArrayList<>();
	private ArrayList<Text> nomiVolumi = new ArrayList<>();
	private Text stringaVolumi;
	private Text stringaCapitoli;
	
	private Fumetto fumetto;
	private MyListener listener;
	
	private Image immagineCopertinaFumetto;
	private Lettore lettore;
	private Volume volumi[];
	
	public PannelloDescrizioneFumetto(Fumetto fumetto, Image immagineCopertinaFumetto, int panelWidth, int panelHeight,final MyPanel panel, Lettore lettore) 
	{
		super();
		
		this.panel = panel;
		this.fumetto = fumetto;
		this.immagineCopertinaFumetto = immagineCopertinaFumetto;
		this.lettore = lettore;
		
		setBackground(Color.GRAY);		
		setPreferredSize(new Dimension(panelWidth, panelHeight));
		setBounds(10, 10, (int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight());
		setLayout(null);
				
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
		
		try
		{
			fumetto.apriFumetto();
		} catch (MalformedURLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String genere = new String("");
		
		try
		{
			String generiFumetto[] = fumetto.getGeneriFumetto();
			for (int i = 0; i < generiFumetto.length; i++)
			{
				if (i < generiFumetto.length - 1)
					genere += generiFumetto[i] +", ";
				else
					genere += generiFumetto[i];
			}
			
		} catch (SQLException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		Text generi = new Text(genere, 16, Color.WHITE);
		generi.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 1+ stringaGenere.getY() + (int) stringaGenere.getPreferredSize().getHeight(), (int)generi.getPreferredSize().getWidth(), (int)generi.getPreferredSize().getHeight());
		add(generi);

		Text stringaValutazione = new Text("Valutazione", 18, Color.DARK_GRAY);
		stringaValutazione.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 2+ generi.getY() + (int) generi.getPreferredSize().getHeight(), (int)stringaValutazione.getPreferredSize().getWidth(), (int)stringaValutazione.getPreferredSize().getHeight());
		add(stringaValutazione);
		starRated = new StarRater();
		starRated.setPreferredSize(new Dimension(300, 10));
		starRated.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 1+ stringaValutazione.getY() + (int) stringaValutazione.getPreferredSize().getHeight(), (int)starRated.getPreferredSize().getWidth(), (int)starRated.getPreferredSize().getHeight());
		add(starRated);
		
		bottonePreferiti = new MyButton("Preferiti", 16, Color.DARK_GRAY);
		bottonePreferiti.setForeground(Color.BLACK);
		bottonePreferiti.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 2+ starRated.getY() + (int) starRated.getPreferredSize().getHeight(), (int)bottonePreferiti.getPreferredSize().getWidth(), (int)bottonePreferiti.getPreferredSize().getHeight());
		
		bottonePreferiti.addMouseMotionListener(new MouseMotionAdapter() {
		      public void mouseMoved(MouseEvent event) {
		    	  bottonePreferiti.setForeground(Color.RED);
		      }
		});
		
		bottonePreferiti.addMouseListener(new MouseAdapter() {
		      public void mouseExited(MouseEvent event) {
		    	  bottonePreferiti.setForeground(Color.BLACK);
		      }
		});
		bottonePreferiti.addActionListener(listener);
		add(bottonePreferiti);
		
		bottoneDaLeggere = new MyButton("Da Leggere", 16, Color.DARK_GRAY);
		bottoneDaLeggere.setForeground(Color.BLACK);
		bottoneDaLeggere.setBounds(20+bottonePreferiti.getX()+(int)bottonePreferiti.getPreferredSize().getWidth(), 2+ starRated.getY() + (int) starRated.getPreferredSize().getHeight(), (int)bottonePreferiti.getPreferredSize().getWidth(), (int)bottonePreferiti.getPreferredSize().getHeight());
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
		
		bottoneAvantiVolumi = new JButton();
		Image imageScaled = imageAvanti.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		imageAvanti.setImage(imageScaled);
		
		bottoneAvantiVolumi.setIcon(imageAvanti);
		bottoneAvantiVolumi.setPressedIcon(imageAvanti);
		bottoneAvantiVolumi.setBorderPainted(false);
		bottoneAvantiVolumi.setFocusPainted(false);
		bottoneAvantiVolumi.setBackground(this.getBackground());
		bottoneAvantiVolumi.setBounds(stringaVolumi.getX()+((int)this.getPreferredSize().getWidth()- (int)bottoneAvantiVolumi.getPreferredSize().getWidth()-10), stringaVolumi.getY(), 30, 30);
		add(bottoneAvantiVolumi);
		
		bottoneIndietroVolumi = new JButton();
		setBottoneIndietro(bottoneIndietroVolumi);
		bottoneIndietroVolumi.setBounds(stringaVolumi.getX()+((int)this.getPreferredSize().getWidth()-(int)bottoneIndietroVolumi.getPreferredSize().getWidth() - (int)bottoneAvantiVolumi.getPreferredSize().getWidth()-10), stringaVolumi.getY(), 30, 30);
		add(bottoneIndietroVolumi);
		
		this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(), (int)this.getPreferredSize().getHeight()+descrizione.getY()+(int)descrizione.getPreferredSize().getHeight()));

		volumi = fumetto.getVolumi();
		System.out.println(volumi.length);
		disegnaCopertineVolumi(0, 4, 0);
		
		bottoneAvantiVolumi.addActionListener(listener);
		bottoneIndietroVolumi.addActionListener(listener);
		
		bottoneIndietro = new JButton();
		setBottoneIndietro(bottoneIndietro);
		bottoneIndietro.setBounds(nome.getX()+((int)this.getPreferredSize().getWidth()-(int)bottoneIndietro.getPreferredSize().getWidth()-20), nome.getY()*2, 30, 30);
		add(bottoneIndietro);
		
		bottoneIndietro.addActionListener(listener);
		
		stringaCapitoli = new Text("Capitoli", 24, Color.DARK_GRAY);
		stringaCapitoli.setBounds(10, 15 + nomiVolumi.get(0).getY() + (int)nomiVolumi.get(0).getPreferredSize().getHeight(), (int) stringaCapitoli.getPreferredSize().getWidth(), (int) stringaCapitoli.getPreferredSize().getHeight() );
		
		for (BottoneFumetto bottoneVolumi : bottoniVolumi) {
			bottoneVolumi.addActionListener(listener);
		}
	}
	
	public Fumetto getFumetto()
	{
		return fumetto;
	}
	
	private void setBottoneIndietro(JButton bottone)
	{
		Image imageScaled = imagePrev.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imagePrev.setImage(imageScaled);
		
		bottone.setIcon(imagePrev);
		bottone.setPressedIcon(imagePrev);
		bottone.setBorderPainted(false);
		bottone.setFocusPainted(false);
		bottone.setContentAreaFilled(false);
		bottone.setBackground(this.getBackground());
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		for (BottoneFumetto bottoneFumetto : bottoniVolumi) 
		{
			g.drawImage(bottoneFumetto.getImageScaled(), 0,0, this);
		}
		super.paintComponent(g);
	}
	
	private void getCapitoliVolume(Volume volume)
	{
		try {
			volume.caricaCapitoli();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
			
			BottoneFumetto bottoneVolume = new BottoneFumetto(volumi[j].getCopertina(),fumetto);
			bottoneVolume.setBorder(BorderFactory.createLineBorder(Color.black,3));
			bottoniVolumi.add(bottoneVolume);
			
			bottoniVolumi.get(j).setPreferredSize(new Dimension(200,300));
			
			if (j == indiceIniziale)
				bottoniVolumi.get(j).setBounds(10, 20 + stringaVolumi.getY() +(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(j).getPreferredSize().getWidth(), (int)bottoniVolumi.get(j).getPreferredSize().getHeight());
			else
				bottoniVolumi.get(j).setBounds(15+bottoniVolumi.get(j-1).getX()+(int)bottoniVolumi.get(j-1).getPreferredSize().getWidth(),20 + stringaVolumi.getY()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(j).getPreferredSize().getWidth(), (int)bottoniVolumi.get(j).getPreferredSize().getHeight());
		
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
	
	private class MyListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if (source == bottoneIndietro)  //BOTTONE INDIETRO
			{
				try {
					panel.PremiPerDiscover();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
				{
					indiceVolumi -= volumi.length;
					System.out.println("::::"+indiceVolumi);
				}
					
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
			
			for (int i = 0; i < bottoniVolumi.size(); i++)   //BOTTONE VOLUMI  ********
			{
				if (source == bottoniVolumi.get(i))
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
					getCapitoliVolume(fumetto.getVolumi()[i]);
				}
			}
			if (source == bottonePreferiti)
			{
				try
				{
					panel.getLettore().inserisciPreferiti(fumetto);
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
			else if (source == bottoneDaLeggere)
			{
				try
				{
					panel.getLettore().inserisciDaLeggere(fumetto);
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
			
			repaint();
		}
		
		private void aggiungiVolume()
		{
			for (int i = 0; i < indiceVolumi ; i++)
			{
				remove(bottoniVolumi.get(i));
				remove(nomiVolumi.get(i));
			}
			for (int i = indiceVolumi - 4; i < indiceVolumi ; i++)
			{
				if (i == indiceVolumi - 4)
					bottoniVolumi.get(i).setBounds(10,10+stringaVolumi.getY()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
				else
					bottoniVolumi.get(i).setBounds(15+bottoniVolumi.get(i-1).getX()+(int)bottoniVolumi.get(i-1).getPreferredSize().getWidth(),10+stringaVolumi.getY()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
				
				nomiVolumi.get(i).setBounds(bottoniVolumi.get(i).getX(),10+bottoniVolumi.get(i).getY()+(int)bottoniVolumi.get(i).getPreferredSize().getHeight(), (int)nomiVolumi.get(i).getPreferredSize().getWidth(), (int)nomiVolumi.get(i).getPreferredSize().getHeight());							
				add(nomiVolumi.get(i));
				add(bottoniVolumi.get(i));
			}
		}
	}
	
}
