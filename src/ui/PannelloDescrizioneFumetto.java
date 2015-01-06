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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import technicalService.DataBase;
import technicalService.TabellaFumetto;
import technicalService.TabellaVolume;
import domain.Fumetto;
import domain.Volume;

@SuppressWarnings("serial")
public class PannelloDescrizioneFumetto extends JPanel
{
	private int indiceVolumi = 0;
	private File file;
	private PannelloCentrale pannelloCentrale;
	private Image scaledImage;
	private Text nome;
	private JLabel forImage;
	private ImageIcon imageFumetto;
	private Text autore;
	private Text artista;
	private Text descrizione;
	private Text eCompleto;
	private Text eOccidentale;	

	private MyPanel panel;
	
	private JButton bottoneIndietro;
	private JButton bottoneAvantiVolumi;
	private JButton bottoneIndietroVolumi;
	private ImageIcon imagePrev = new ImageIcon("image/prev-icon.png");
	private ImageIcon imageAvanti = new ImageIcon("image/next.png");
	
	private ArrayList<MyButton> bottoniCapitoli = new ArrayList<>();
	
	private ArrayList<BottoneFumetto> bottoniVolumi = new ArrayList<>();
	private ArrayList<Text> nomiVolumi = new ArrayList<>();
	private Text stringaVolumi;
	private Text stringaCapitoli;
	private boolean bottoneCapitoliPressed = false;
	
	Fumetto fumetto;
	
	public PannelloDescrizioneFumetto(Fumetto fumetto, PannelloCentrale pannelloCentrale, final MyPanel panel) 
	{
		super();
		
		this.panel = panel;
		this.fumetto = fumetto;
		
		setBackground(Color.GRAY);		
		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
		setBounds(10, 10, (int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight());
		setLayout(null);
		
		MyListener listener = new MyListener();
		
		nome = new Text(fumetto.getNome(), 45, Color.DARK_GRAY);
		nome.setBounds(10,10, (int)nome.getPreferredSize().getWidth(), (int)nome.getPreferredSize().getHeight());
		add(nome);
		
		try {
			scaledImage = getURL(fumetto.getUrl(),200,300);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		imageFumetto = new ImageIcon(scaledImage);
		forImage = new JLabel();
		forImage.setIcon(imageFumetto);
		forImage.setPreferredSize(new Dimension(200, 300));
		forImage.setBounds(10, 10+nome.getY()+(int)nome.getPreferredSize().getHeight(), (int)forImage.getPreferredSize().getWidth(), (int)forImage.getPreferredSize().getHeight());
		forImage.setBorder(BorderFactory.createLineBorder(Color.black,3));
		add(forImage);
		
		Text stringaAutore = new Text("Autore", 18, Color.DARK_GRAY);
		stringaAutore.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,3 + nome.getY() +(int)nome.getPreferredSize().getHeight(), (int)stringaAutore.getPreferredSize().getWidth(), (int)stringaAutore.getPreferredSize().getHeight());
		add(stringaAutore);
		autore = new Text(fumetto.getAutore(), 16, Color.WHITE);
		autore.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,2+stringaAutore.getY()+(int)stringaAutore.getPreferredSize().getHeight(), (int)autore.getPreferredSize().getWidth(), (int)autore.getPreferredSize().getHeight());
		add(autore);
	
		Text stringaArtista = new Text("Artista", 18, Color.DARK_GRAY);
		stringaArtista.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 3 + autore.getY() +(int)autore.getPreferredSize().getHeight(), (int)stringaArtista.getPreferredSize().getWidth(), (int)stringaArtista.getPreferredSize().getHeight());
		add(stringaArtista);
		artista = new Text(fumetto.getArtista(), 16, Color.WHITE);
		artista.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,2 + stringaArtista.getY() +(int)stringaArtista.getPreferredSize().getHeight(), (int)artista.getPreferredSize().getWidth(), (int)artista.getPreferredSize().getHeight());
		add(artista);
		
		Text stringaStato = new Text("Stato", 18, Color.DARK_GRAY);
		stringaStato.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,3+ artista.getY()+(int)artista.getPreferredSize().getHeight(), (int)stringaStato.getPreferredSize().getWidth(), (int)stringaStato.getPreferredSize().getHeight());
		add(stringaStato);
		if (fumetto.isEcompleto())
			eCompleto = new Text("Completo", 16, Color.WHITE);
		else
			eCompleto = new Text("Non completo",  16, Color.WHITE);
		eCompleto.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,2 + stringaStato.getY() +(int)stringaStato.getPreferredSize().getHeight(), (int)eCompleto.getPreferredSize().getWidth(), (int)eCompleto.getPreferredSize().getHeight());
		add(eCompleto);
		
		Text stringaTipo = new Text("Tipo", 18, Color.DARK_GRAY);
		stringaTipo.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,3 + eCompleto.getY() + (int)eCompleto.getPreferredSize().getHeight(), (int)stringaTipo.getPreferredSize().getWidth(), (int)stringaTipo.getPreferredSize().getHeight());
		add(stringaTipo);
		if (fumetto.isOccidentale())
			eOccidentale = new Text("Occidentale", 16, Color.WHITE);
		else
			eOccidentale = new Text("Orientale", 16, Color.WHITE);
		eOccidentale.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom, 2+ stringaTipo.getY() + (int) stringaTipo.getPreferredSize().getHeight(), (int)eOccidentale.getPreferredSize().getWidth(), (int)eOccidentale.getPreferredSize().getHeight());
		add(eOccidentale);
		
		descrizione = new Text(fumetto.getDescrizione(), 14, Color.WHITE);
		System.out.println((int)this.getPreferredSize().getWidth());
		descrizione.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth() - (int)this.getPreferredSize().getHeight()/8, (int)this.getPreferredSize().getHeight() / 2));
		descrizione.setBounds(10, 10 + forImage.getY() +(int)forImage.getPreferredSize().getHeight(), (int)descrizione.getPreferredSize().getWidth(), 200);
		descrizione.setLineWrap(true);
		descrizione.setWrapStyleWord(true);
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
		
		try {
			fumetto.caricaVolumi();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		for (int j = 0; j < fumetto.getVolumi().length; j++) 
		{
			Text nomeVolume = new Text(fumetto.getVolumi()[j].getNumero()+" - "+fumetto.getVolumi()[j].getNome(), 12, Color.WHITE);
			nomiVolumi.add(nomeVolume);
			
			try {
				BottoneFumetto bottoneFumetto = new BottoneFumetto(getURL(fumetto.getVolumi()[j].getUrlCopertina(),200,300),fumetto);
				bottoneFumetto.setBorder(BorderFactory.createLineBorder(Color.black,3));
				bottoniVolumi.add(bottoneFumetto);
				
				bottoniVolumi.get(j).setPreferredSize(new Dimension(200,300));
				
				if (j == 0)
					bottoniVolumi.get(j).setBounds(10, 20 + stringaVolumi.getY() +(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(j).getPreferredSize().getWidth(), (int)bottoniVolumi.get(j).getPreferredSize().getHeight());
				else
					bottoniVolumi.get(j).setBounds(15+bottoniVolumi.get(j-1).getX()+(int)bottoniVolumi.get(j-1).getPreferredSize().getWidth(),20 + stringaVolumi.getY()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(j).getPreferredSize().getWidth(), (int)bottoniVolumi.get(j).getPreferredSize().getHeight());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			nomiVolumi.get(j).setBounds(bottoniVolumi.get(j).getX(), 10 + bottoniVolumi.get(j).getY() + (int)bottoniVolumi.get(j).getPreferredSize().getHeight(), (int)bottoniVolumi.get(j).getPreferredSize().getWidth(), (int)bottoniVolumi.get(j).getPreferredSize().getHeight());

			if (j < 4)
			{
				indiceVolumi = j;
				add(bottoniVolumi.get(j));
				add(nomiVolumi.get(j));
			}
		}
		
		bottoneAvantiVolumi.addActionListener(listener);
		
		bottoneIndietroVolumi.addActionListener(listener);
		
		bottoneIndietro = new JButton();
		setBottoneIndietro(bottoneIndietro);
		bottoneIndietro.setBounds(nome.getX()+((int)this.getPreferredSize().getWidth()-(int)bottoneIndietro.getPreferredSize().getWidth()-10), nome.getY() + (int)nome.getPreferredSize().getWidth() / 2, 30, 30);
		add(bottoneIndietro);
		
		bottoneIndietro.addActionListener(listener);
		
		stringaCapitoli = new Text("Capitoli", 24, Color.DARK_GRAY);
		stringaCapitoli.setBounds(10, 10 + nomiVolumi.get(0).getY() + (int)nomiVolumi.get(0).getPreferredSize().getHeight(), (int) stringaCapitoli.getPreferredSize().getWidth(), (int) stringaCapitoli.getPreferredSize().getHeight() );
		
		for (BottoneFumetto bottoneFumetto : bottoniVolumi) {
			bottoneFumetto.addActionListener(listener);
		}
		
		if (bottoneCapitoliPressed)
		{
			this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(), (int)this.getPreferredSize().getHeight()+(int)stringaCapitoli.getPreferredSize().getHeight()));
		}
	}
	
	private void setBottoneIndietro(JButton bottone)
	{
		Image imageScaled = imagePrev.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		imagePrev.setImage(imageScaled);
		
		bottone.setIcon(imagePrev);
		bottone.setPressedIcon(imagePrev);
		bottone.setBorderPainted(false);
		bottone.setFocusPainted(false);
		bottone.setBackground(this.getBackground());
	}
	
	public Image getURL(String stringa, int w, int h) throws IOException
	{
		URL url = new URL(stringa);
		BufferedImage bufferedImage = ImageIO.read(url);
		
		String stringhe [] = new String[14];
		stringhe = url.toString().split("/");
		
		File dir = (new File("Comics Reader"));
		
		if (dir.exists())
			file = new File(dir.getName()+"/"+stringhe[stringhe.length-1]);
		else
		{
			dir.mkdir();
			file = new File(dir.getName()+"/"+stringhe[stringhe.length-1]);
		}
		
		ImageOutputStream imageOutputStream = new FileImageOutputStream(file); 
		ImageIO.write(bufferedImage, "jpg", imageOutputStream);
		
		bufferedImage = ImageIO.read(url);
		Image scaledImageFumetto = bufferedImage.getScaledInstance(w, h, bufferedImage.SCALE_AREA_AVERAGING);
		
		file.deleteOnExit();
		
		return scaledImageFumetto;
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
	
	private void getCapitoli(Volume volume)
	{
		try {
			volume.caricaCapitoli();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (int j = 0; j < volume.getCapitoli().length; j++) 
		{			
			MyButton nomeCapitolo = new MyButton("Capitolo: "+volume.getCapitoli()[j].getNumero());
			bottoniCapitoli.add(nomeCapitolo);
			
			if (j == 0)
				bottoniCapitoli.get(j).setBounds(10, 10+stringaCapitoli.getY()+(int)stringaCapitoli.getPreferredSize().getHeight(), (int)bottoniCapitoli.get(j).getPreferredSize().getWidth(), (int)bottoniCapitoli.get(j).getPreferredSize().getHeight());
			else
				bottoniCapitoli.get(j).setBounds(10+bottoniCapitoli.get(j-1).getY()+(int)bottoniCapitoli.get(j-1).getPreferredSize().getHeight(), 10+stringaCapitoli.getY()+(int)stringaCapitoli.getPreferredSize().getHeight(), (int)bottoniCapitoli.get(j).getPreferredSize().getWidth(), (int)bottoniCapitoli.get(j).getPreferredSize().getHeight());
				
			add(bottoniCapitoli.get(j));
		}
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
//			else if (source == bottoneIndietroVolumi)   //BOTTONE INDIETRO VOLUMI
//			{
//				System.out.println(indiceVolumi);
//				if (indiceVolumi > 4)
//				{
//					indiceVolumi -= 4;
//					for (int i = indiceVolumi; i < indiceVolumi + 4 ; i++)
//						remove(bottoniVolumi.get(i));
//					for (int i = indiceVolumi; i > indiceVolumi - 4 ; i--)
//					{
//						if (i == indiceVolumi)
//							bottoniVolumi.get(i).setBounds(10 + (int)bottoniVolumi.get(i).getPreferredSize().getHeight()*2,20+stringaVolumi.getY()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
//						
//						else if (i == indiceVolumi - 4 )
//						{
//							bottoniVolumi.get(i).setBounds(10,35+(int)nome.getPreferredSize().getHeight()+(int)forImage.getPreferredSize().getHeight()+(int)descrizione.getPreferredSize().getHeight()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
//						}
//						else
//						{
//							bottoniVolumi.get(i).setBounds(15+bottoniVolumi.get(i+1).getX()+(int)bottoniVolumi.get(i+1).getPreferredSize().getWidth(),35+(int)nome.getPreferredSize().getHeight()+(int)forImage.getPreferredSize().getHeight()+(int)descrizione.getPreferredSize().getHeight()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
//							
//						}
//						
//						add(bottoniVolumi.get(i));
//					}
//				}
//				
//				if (indiceVolumi == 0)
//				{
//					System.out.println("aaaaaaaaaaaah");
//					bottoneIndietroVolumi.setEnabled(false);
//					bottoneAvantiVolumi.setEnabled(true);
//				}
//				repaint();
//			}
			else if (source == bottoneAvantiVolumi)  //BOTTONE AVANTI VOLUMI
			{
				if (bottoniVolumi.size() - indiceVolumi < 4)
				{
					indiceVolumi += bottoniVolumi.size() - indiceVolumi;
					aggiungiVolume();
				}
				else
				{
					if (indiceVolumi <= bottoniVolumi.size())
					{
						indiceVolumi += 4;
						if (indiceVolumi == 4)
						{
							for (int i = 0; i < indiceVolumi ; i++)
							{
								remove(bottoniVolumi.get(i));
								remove(nomiVolumi.get(i));
							}
							
							for (int i = indiceVolumi; i < indiceVolumi + 4 ; i++)
							{
								if (i == indiceVolumi)
								{
									bottoniVolumi.get(i).setBounds(10,10+stringaVolumi.getY()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
								}
								else
								{
									bottoniVolumi.get(i).setBounds(15+bottoniVolumi.get(i-1).getX()+(int)bottoniVolumi.get(i-1).getPreferredSize().getWidth(),10+stringaVolumi.getY()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
								}
								
								nomiVolumi.get(i).setBounds(bottoniVolumi.get(i).getX(),10+bottoniVolumi.get(i).getY()+(int)bottoniVolumi.get(i).getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());							
								add(nomiVolumi.get(i));
								add(bottoniVolumi.get(i));
							}
						}
						else
						{
							aggiungiVolume();
						}
					}
				}
				if (indiceVolumi == bottoniVolumi.size())
				{
					bottoneAvantiVolumi.setEnabled(false);
					bottoneIndietroVolumi.setEnabled(true);
				}
				
				repaint();
			}
			for (int i = 0; i < bottoniVolumi.size(); i++)   //BOTTONE VOLUMI  ********
			{
				if (source == bottoniVolumi.get(i))
				{
					bottoneCapitoliPressed = true;
					if (!stringaCapitoli.equals(null))
					{
						remove(stringaCapitoli);
					}
					
					add(stringaCapitoli);
					getCapitoli(fumetto.getVolumi()[i]);
					repaint();
				}
			}
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
				{
					bottoniVolumi.get(i).setBounds(10,10+stringaVolumi.getY()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
					nomiVolumi.get(i).setBounds(bottoniVolumi.get(i).getX(),10+bottoniVolumi.get(i).getY()+(int)bottoniVolumi.get(i).getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
				}
				else
				{
					bottoniVolumi.get(i).setBounds(15+bottoniVolumi.get(i-1).getX()+(int)bottoniVolumi.get(i-1).getPreferredSize().getWidth(),10+stringaVolumi.getY()+(int)stringaVolumi.getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
					nomiVolumi.get(i).setBounds(bottoniVolumi.get(i-1).getX(),10+bottoniVolumi.get(i).getY()+(int)bottoniVolumi.get(i).getPreferredSize().getHeight(), (int)bottoniVolumi.get(i).getPreferredSize().getWidth(), (int)bottoniVolumi.get(i).getPreferredSize().getHeight());
				}
				
				add(nomiVolumi.get(i));
				add(bottoniVolumi.get(i));
			}
		}
	}
	
}
