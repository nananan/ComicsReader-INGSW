package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JLabel;
import javax.swing.JPanel;

import technicalService.DataBase;
import technicalService.TabellaFumetto;
import domain.Fumetto;
import domain.Libreria;

public class PannelloDiscover extends JPanel
{
	private PannelloCentrale pannelloCentrale;
	File file;
	Image imageSfondo = null;
	Image scaledImage = null;
	
	private MyPanel panel;
	private static Libreria libreria = Libreria.getLibreria();
	
	HashMap<String,Fumetto> fumetti = new HashMap<>();
	HashMap<String,Fumetto> fumettiFiltrati = new HashMap<>();
	HashMap<String,Fumetto> fumettiCercati = new HashMap<>();
	
	ArrayList<BottoneFumetto> bottoniFumetti;
	
	public PannelloDiscover(PannelloCentrale pannelloCentrale, final MyPanel panel)
	{
		super();	
		this.pannelloCentrale = pannelloCentrale;
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
		setBounds(0, 0, (int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight());
		setLayout(null);
		this.panel = panel;
		
		bottoniFumetti = new ArrayList<>();

		libreria.fumettiCorrenti();

		aggiungiFumettoAlPannello(libreria.fumettiCorrenti());
	}
	
//	public PannelloDiscover(PannelloCentrale pannelloCentrale, final MyPanel panel, 
//			ArrayList<String> filtri, String tipoFumetto, String statoFumetto)
//	{
//		super();	
//		this.pannelloCentrale = pannelloCentrale;
//		setBackground(Color.GRAY);
//		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
//		setBounds(0, 0, (int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight());
//		setLayout(null);
//		
//		this.panel = panel;
//		
//		bottoniFumetti = new ArrayList<>();
//		
//		if (filtri.size() == 0)
//		{
//			try {
//				TabellaFumetto tupleFumetto = new TabellaFumetto("", tipoFumetto, statoFumetto);
//				
//				while(tupleFumetto.nextFumetto())
//				{
//					Fumetto fumetto = new Fumetto(tupleFumetto);
//					System.out.println(fumetto.getNome());
//					if (!fumettiFiltrati.containsKey(fumetto.getNome()))
//							fumettiFiltrati.put(fumetto.getNome(), fumetto);
//				}
//				if (fumettiFiltrati.size() == 0)
//					aggiungiStringaFumettoNonTrovato(tupleFumetto);
//				tupleFumetto.close();
//				
//				aggiungiFumettoAlPannello(fumettiFiltrati);
//				
//			}  catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		for (int i = 0; i < filtri.size(); i++)
//		{
//			try {
//				TabellaFumetto tupleFumetto = new TabellaFumetto(filtri.get(i), tipoFumetto, statoFumetto);
//				
//				while(tupleFumetto.nextFumetto())
//				{
//					Fumetto fumetto = new Fumetto(tupleFumetto);
//					System.out.println(fumetto.getNome());
//					if (!fumettiFiltrati.containsKey(fumetto.getNome()))
//							fumettiFiltrati.put(fumetto.getNome(), fumetto);
//				}
//				if (fumettiFiltrati.size() == 0)
//					aggiungiStringaFumettoNonTrovato(tupleFumetto);
//				tupleFumetto.close();
//				
//				aggiungiFumettoAlPannello(fumettiFiltrati);
//				
//			}  catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	public PannelloDiscover(PannelloCentrale pannelloCentrale, final MyPanel panel, String tipoDaCercare, String nomeDaCercare)
	{
		super();	
		this.pannelloCentrale = pannelloCentrale;
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
		setBounds(0, 0, (int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight());
		setLayout(null);
		
		this.panel = panel;
		
		bottoniFumetti = new ArrayList<>();
		
		try
		{
			DataBase.connect();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (tipoDaCercare.equals("Autore"))
			aggiungiFumettoAlPannello(libreria.caricaFumettiPerAutore(nomeDaCercare));
		else if (tipoDaCercare.equals("Artista"))
			aggiungiFumettoAlPannello(libreria.caricaFumettiPerArtista(nomeDaCercare));
//		else if (tipoDaCercare.equals("Fumetto"))
//			aggiungiFumettoAlPannello(libreria.caricaFumettiPerNome(nomeDaCercare));
	}
	
	private void aggiungiStringaFumettoNonTrovato()
	{
		Text fumettiNonTrovati = new Text("Fumetti non trovati", 20, Color.WHITE);
		fumettiNonTrovati.setBounds(20, 20, (int)fumettiNonTrovati.getPreferredSize().getWidth(),
				(int)fumettiNonTrovati.getPreferredSize().getHeight());
		add(fumettiNonTrovati);
	}
	
	private void aggiungiFumettoAlPannello(final Fumetto[] fumettiDaAggiungere)
	{
		int j=0, i=0;
		
		if (fumettiDaAggiungere[0] == null)
		{
			aggiungiStringaFumettoNonTrovato();
			return;
		}
		
		for(int z = 0; z < fumettiDaAggiungere.length; z++)
		{
			if (fumettiDaAggiungere[z] != null)
			{
				BottoneFumetto bottoneFumetto = new BottoneFumetto(
						fumettiDaAggiungere[z].getCopertina(), fumettiDaAggiungere[z]);
				
				bottoniFumetti.add(bottoneFumetto);
				bottoniFumetti.get(j).setPreferredSize(new Dimension(200,300));
				
				if (j == 0)
					bottoniFumetti.get(j).setBounds(10,10, 200,300);
				else
				{
					if (j % 4 == 0)
					{
						bottoniFumetti.get(j).setBounds(10,10+(int)bottoniFumetti.get(j-1).getPreferredSize().getHeight()+bottoniFumetti.get(j-1).getY(), 200,300);
						i += bottoniFumetti.get(j).getPreferredSize().getHeight()+10;
					}
					else
						bottoniFumetti.get(j).setBounds(10+(int)bottoniFumetti.get(j-1).getPreferredSize().getWidth()+bottoniFumetti.get(j-1).getX(),10+i, 200,300);
				}
				add(bottoneFumetto);
				
				if (i > (int)getPreferredSize().getHeight())
				{
					setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight()+i+(int)bottoniFumetti.get(j).getPreferredSize().getHeight()));
				}
				
				final int indicePerFumetto = j;
				final int indice = z;
				bottoniFumetti.get(j).addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e)
					{
						try {
							panel.PremiPerFumetto(fumettiDaAggiungere[indice], 
									bottoniFumetti.get(indicePerFumetto).getImageScaled());
						} catch (MalformedURLException | SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
				
				j++;
			}
		}		
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		for (BottoneFumetto bottoneFumetto : bottoniFumetti) 
		{
			g.drawImage(bottoneFumetto.getImageScaled(), 0,0, this);
		}
		super.paintComponent(g);
//		g.drawImage(scaledImage, 0,0, this);
	}
}
