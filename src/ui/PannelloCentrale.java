package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import technicalService.GestoreDataBase;
import technicalService.TuplaFumetto;
import domain.Fumetto;
import domain.Libreria;

public class PannelloCentrale extends JPanel
{
	File file;
	Image imageSfondo = null;
	Image scaledImage = null;
	
	private MyPanel panel;
	private static Libreria libreria = Libreria.getIstanza();
	
	HashMap<String,Fumetto> fumetti = new HashMap<>();
	HashMap<String,Fumetto> fumettiFiltrati = new HashMap<>();
	HashMap<String,Fumetto> fumettiCercati = new HashMap<>();
	
	ArrayList<BottoneFumetto> bottoniFumetti;
	
	private int indiceFumetti = 0;
	
	private ImageIcon imagePrev;
	private ImageIcon imageAvanti;
	private JButton bottoneAvantiFumetti;
	private JButton bottoneIndietroFumetti;
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private Text textDiscover;
	
	private MyListener listener;
	
	public PannelloCentrale(final MyPanel panel, int larghezzaPannello)
	{
		super();	
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(larghezza, altezza-larghezzaPannello));
		setLayout(null);
		this.panel = panel;
		
		listener = new MyListener();
		
		bottoniFumetti = new ArrayList<>();
			GestoreDataBase.connetti();
		
		
//		libreria.fumettiCorrenti();

		textDiscover = new Text("Scopri", 32, Color.WHITE);
		textDiscover.setBounds(10, 10, (int)textDiscover.getPreferredSize().getWidth(), 
				(int)textDiscover.getPreferredSize().getHeight());
		add(textDiscover);
		
		aggiungiFumettoAlPannello(libreria.fumettiCorrenti());
		
		imagePrev = new ImageIcon("image/prev-icon.png");
		imageAvanti = new ImageIcon("image/next.png");
		
		bottoneAvantiFumetti = new JButton();
		Image imageScaled = imageAvanti.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imageAvanti.setImage(imageScaled);
		
		bottoneAvantiFumetti.setIcon(imageAvanti);
		bottoneAvantiFumetti.setPressedIcon(imageAvanti);
		bottoneAvantiFumetti.setBorderPainted(false);
		bottoneAvantiFumetti.setFocusPainted(false);
		bottoneAvantiFumetti.setBackground(this.getBackground());
		bottoneAvantiFumetti.setBounds((int)this.getPreferredSize().getWidth()-larghezzaPannello*2 -
				(int)bottoneAvantiFumetti.getPreferredSize().getWidth()/2-5, 
				(int) this.getPreferredSize().getHeight()/2, 30, 30);
		add(bottoneAvantiFumetti);
		
		bottoneAvantiFumetti.addActionListener(listener);
		
		imageScaled = imagePrev.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imagePrev.setImage(imageScaled);
		
		bottoneIndietroFumetti = new JButton();
		bottoneIndietroFumetti.setIcon(imagePrev);
		bottoneIndietroFumetti.setPressedIcon(imagePrev);
		bottoneIndietroFumetti.setBorderPainted(false);
		bottoneIndietroFumetti.setFocusPainted(false);
		bottoneIndietroFumetti.setBackground(this.getBackground());
		bottoneIndietroFumetti.setBounds(15, (int)this.getPreferredSize().getHeight()/2, 30, 30);
		add(bottoneIndietroFumetti);
		
		bottoneIndietroFumetti.addActionListener(listener);
		
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
	
	public PannelloCentrale(final MyPanel panel, String tipoDaCercare, String nomeDaCercare)
	{
		super();	
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(larghezza, altezza));
		setLayout(null);
		
		this.panel = panel;
		
		bottoniFumetti = new ArrayList<>();
		
		textDiscover = new Text("Ricerca", 32, Color.WHITE);
		textDiscover.setBounds(10, 10, (int)textDiscover.getPreferredSize().getWidth(), 
				(int)textDiscover.getPreferredSize().getHeight());
		add(textDiscover);
		
		GestoreDataBase gestoreDB = GestoreDataBase.getIstanza();
		gestoreDB.connetti();
		
		if (tipoDaCercare.equals("Autore"))
			aggiungiFumettoAlPannello(libreria.caricaFumettiPerAutore(nomeDaCercare));
		else if (tipoDaCercare.equals("Artista"))
			aggiungiFumettoAlPannello(libreria.caricaFumettiPerArtista(nomeDaCercare));
		else if (tipoDaCercare.equals("Fumetto"))
			aggiungiFumettoAlPannello(libreria.caricaFumettiPerNome(nomeDaCercare));
	}
	
	private void aggiungiStringaFumettoNonTrovato()
	{
		Text fumettiNonTrovati = new Text("Fumetti non trovati", 24, Color.WHITE);
		fumettiNonTrovati.setBounds(20, 20 +
				textDiscover.getY() + (int)textDiscover.getPreferredSize().getHeight(), 
				(int)fumettiNonTrovati.getPreferredSize().getWidth(),
				(int)fumettiNonTrovati.getPreferredSize().getHeight());
		add(fumettiNonTrovati);
	}
	
	private void aggiungiFumettoAlPannello(final Fumetto[] fumettiDaAggiungere)
	{
		int j=0, i=0;
		indiceFumetti = 0;
		bottoniFumetti.clear();
		if (fumettiDaAggiungere[0] == null)
		{
			aggiungiStringaFumettoNonTrovato();
			return;
		}
		
		for(int z = 0; z < fumettiDaAggiungere.length; z++, indiceFumetti++, j++)
		{
			System.out.println("Pannello Centrale: "+indiceFumetti);
			if (fumettiDaAggiungere[z] != null)
			{
				System.out.println(fumettiDaAggiungere[z].getNome());
				BottoneFumetto bottoneFumetto = new BottoneFumetto(
						fumettiDaAggiungere[z].getCopertina(), fumettiDaAggiungere[z]);
				
				bottoneFumetto.setPreferredSize(new Dimension(200,300));
				bottoniFumetti.add(bottoneFumetto);
				
				if (j == 0)
					bottoniFumetti.get(j).setBounds(64,10 + textDiscover.getX() +
							(int)textDiscover.getPreferredSize().getHeight(), 200,300);
				else
				{
					if (j % 4 == 0)
					{
						bottoniFumetti.get(j).setBounds(64,10 + 
								(int)bottoniFumetti.get(j-1).getPreferredSize().getHeight()+
								bottoniFumetti.get(j-1).getY(), 200,300);
						
						i += bottoniFumetti.get(j).getPreferredSize().getHeight()+10;
					}
					else
						bottoniFumetti.get(j).setBounds(10 + (int)bottoniFumetti.get(j-1).getPreferredSize().getWidth()+bottoniFumetti.get(j-1).getX(),10+textDiscover.getX() +
								(int)textDiscover.getPreferredSize().getHeight()+i, 200,300);
				}
				this.add(bottoniFumetti.get(indiceFumetti));
//				if (i > (int)getPreferredSize().getHeight())
//				{
//					setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight()+i+(int)bottoniFumetti.get(j).getPreferredSize().getHeight()));
//				}
				
				final int indicePerFumetto = j;
				final int indice = z;
				bottoniFumetti.get(j).addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e)
					{
					
							panel.PremiPerFumetto(fumettiDaAggiungere[indice], 
							bottoniFumetti.get(indicePerFumetto).getImageScaled());
				
					}
				});
			}
		}		
	}
	
	private class MyListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if (source == bottoneAvantiFumetti)
			{
				GestoreDataBase.connetti();
				for (int i = indiceFumetti-8; i < indiceFumetti; i++)
				{
					System.out.println("INDICE: "+i);
					remove(bottoniFumetti.get(i));
				}
			
				libreria.fumettiSuccessivi();
			
				System.out.println("INDICE FUMETTO: "+indiceFumetti);
				aggiungiFumettoAlPannello(libreria.fumettiCorrenti());
				repaint();
			}
			else if (source == bottoneIndietroFumetti)
			{
				for (int i = 0; i < indiceFumetti; i++)
					remove(bottoniFumetti.get(i));
				
				libreria.fumettiPrecedenti();
				
				System.out.println("INDICE FUMETTO: "+indiceFumetti);
				aggiungiFumettoAlPannello(libreria.fumettiCorrenti());
				repaint();
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
	}
}
