package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Fumetto;
import domain.Volume;

@SuppressWarnings("serial")
public class PannelloDescrizioneFumetto extends JPanel
{
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
		
	private ArrayList<BottoneFumetto> bottoniFumetti = new ArrayList<>();
	
	public PannelloDescrizioneFumetto(Fumetto fumetto, PannelloCentrale pannelloCentrale) 
	{
		super();
		setBackground(Color.GRAY);
		
		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
		setBounds(10, 10, (int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight());
		setLayout(null);
		
		nome = new Text(fumetto.getNome());
		nome.setFont(new Font("Caladea", Font.BOLD, 45));
		nome.setForeground(Color.DARK_GRAY);
		nome.setPreferredSize(new Dimension(300,75));
		nome.setBounds(10,10, (int)nome.getPreferredSize().getWidth(), (int)nome.getPreferredSize().getHeight());
		add(nome);
		
		try {
			scaledImage = getURL(fumetto.getUrl());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		imageFumetto = new ImageIcon(scaledImage);
		forImage = new JLabel();
		forImage.setIcon(imageFumetto);
		forImage.setPreferredSize(new Dimension(200, 300));
		forImage.setBounds(10, 20+(int)nome.getPreferredSize().getHeight(), (int)forImage.getPreferredSize().getWidth(), (int)forImage.getPreferredSize().getHeight());
		forImage.setBorder(BorderFactory.createLineBorder(Color.black,3));
		add(forImage);
		
		Text stringaAutore = new Text("Autore");
		stringaAutore.setFont(new Font("Caladea", Font.BOLD, 18));
		stringaAutore.setForeground(Color.DARK_GRAY);
		stringaAutore.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,20+(int)nome.getPreferredSize().getHeight(), (int)stringaAutore.getPreferredSize().getWidth(), (int)stringaAutore.getPreferredSize().getHeight());
		add(stringaAutore);
		autore = new Text(fumetto.getAutore());
		autore.setFont(new Font("Caladea", Font.BOLD, 16));
		autore.setForeground(Color.WHITE);
		autore.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,20+(int)stringaAutore.getPreferredSize().getHeight()+(int)nome.getPreferredSize().getHeight(), (int)autore.getPreferredSize().getWidth(), (int)autore.getPreferredSize().getHeight());
		add(autore);
	
		Text stringaArtista = new Text("Artista");
		stringaArtista.setFont(new Font("Caladea", Font.BOLD, 18));
		stringaArtista.setForeground(Color.DARK_GRAY);
		stringaArtista.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,25+(int)nome.getPreferredSize().getHeight()+(int)stringaAutore.getPreferredSize().getHeight()+(int)autore.getPreferredSize().getHeight(), (int)stringaArtista.getPreferredSize().getWidth(), (int)stringaArtista.getPreferredSize().getHeight());
		add(stringaArtista);
		artista = new Text(fumetto.getArtista());
		artista.setFont(new Font("Caladea", Font.BOLD, 16));
		artista.setForeground(Color.WHITE);
		artista.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,25+(int)nome.getPreferredSize().getHeight()+(int)stringaAutore.getPreferredSize().getHeight()+(int)autore.getPreferredSize().getHeight()+(int)stringaArtista.getPreferredSize().getHeight(), (int)artista.getPreferredSize().getWidth(), (int)artista.getPreferredSize().getHeight());
		add(artista);
		
		Text stringaStato = new Text("Stato");
		stringaStato.setFont(new Font("Caladea", Font.BOLD, 18));
		stringaStato.setForeground(Color.DARK_GRAY);
		stringaStato.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,25+(int)nome.getPreferredSize().getHeight()+(int)stringaAutore.getPreferredSize().getHeight()+(int)autore.getPreferredSize().getHeight()+(int)stringaArtista.getPreferredSize().getHeight()+(int)artista.getPreferredSize().getHeight(), (int)stringaStato.getPreferredSize().getWidth(), (int)stringaStato.getPreferredSize().getHeight());
		add(stringaStato);
		if (fumetto.isEcompleto())
			eCompleto = new Text("Completo");
		else
			eCompleto = new Text("Non completo");
		eCompleto.setFont(new Font("Caladea", Font.BOLD, 16));
		eCompleto.setForeground(Color.WHITE);
		eCompleto.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,25+(int)nome.getPreferredSize().getHeight()+(int)stringaAutore.getPreferredSize().getHeight()+(int)autore.getPreferredSize().getHeight()+(int)stringaArtista.getPreferredSize().getHeight()+(int)artista.getPreferredSize().getHeight()+(int)stringaStato.getPreferredSize().getHeight(), (int)eCompleto.getPreferredSize().getWidth(), (int)eCompleto.getPreferredSize().getHeight());
		add(eCompleto);
		
		Text stringaTipo = new Text("Tipo");
		stringaTipo.setFont(new Font("Caladea", Font.BOLD, 18));
		stringaTipo.setForeground(Color.DARK_GRAY);
		stringaTipo.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,25+(int)nome.getPreferredSize().getHeight()+(int)stringaAutore.getPreferredSize().getHeight()+(int)autore.getPreferredSize().getHeight()+(int)stringaArtista.getPreferredSize().getHeight()+(int)artista.getPreferredSize().getHeight()+(int)stringaStato.getPreferredSize().getHeight()+(int)eCompleto.getPreferredSize().getHeight(), (int)stringaTipo.getPreferredSize().getWidth(), (int)stringaTipo.getPreferredSize().getHeight());
		add(stringaTipo);
		if (fumetto.isOccidentale())
			eOccidentale = new Text("Occidentale");
		else
			eOccidentale = new Text("Orientale");
		eOccidentale.setFont(new Font("Caladea", Font.BOLD, 16));
		eOccidentale.setForeground(Color.WHITE);
		eOccidentale.setBounds(20+(int)forImage.getPreferredSize().getWidth()+(int)forImage.getInsets().bottom,25+(int)nome.getPreferredSize().getHeight()+(int)stringaAutore.getPreferredSize().getHeight()+(int)autore.getPreferredSize().getHeight()+(int)stringaArtista.getPreferredSize().getHeight()+(int)artista.getPreferredSize().getHeight()+(int)stringaStato.getPreferredSize().getHeight()+(int)eCompleto.getPreferredSize().getHeight()+(int)stringaTipo.getPreferredSize().getHeight(), (int)eOccidentale.getPreferredSize().getWidth(), (int)eOccidentale.getPreferredSize().getHeight());
		add(eOccidentale);
		
		descrizione = new Text(fumetto.getDescrizione());
		descrizione.setFont(new Font("Caladea", Font.BOLD, 14));
		descrizione.setForeground(Color.WHITE);
		System.out.println((int)this.getPreferredSize().getWidth());
		descrizione.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth() - (int)this.getPreferredSize().getHeight()/8, (int)this.getPreferredSize().getHeight() / 2));
		descrizione.setBounds(10, 35+(int)nome.getPreferredSize().getHeight()+(int)forImage.getPreferredSize().getHeight(), (int)descrizione.getPreferredSize().getWidth(), 200);
		descrizione.setLineWrap(true);
		descrizione.setWrapStyleWord(true);
		add(descrizione);
		
		Text stringaVolumi = new Text("Volumi");
		stringaVolumi.setFont(new Font("Caladea", Font.BOLD, 18));
		stringaVolumi.setForeground(Color.DARK_GRAY);
		stringaVolumi.setBounds(10,25+(int)nome.getPreferredSize().getHeight()+(int)forImage.getPreferredSize().getHeight()+(int)descrizione.getPreferredSize().getHeight(), (int)stringaVolumi.getPreferredSize().getWidth(), (int)stringaVolumi.getPreferredSize().getHeight());
		add(stringaVolumi);
		
		for (int i = 0; i < fumetto.getVolumi().length; i++) 
		{
			
		}
		
		
		this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(), (int)this.getPreferredSize().getHeight()+(int)descrizione.getPreferredSize().getHeight()));
		
	}
	
	public Image getURL(String stringa) throws IOException
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
		Image scaledImageFumetto = bufferedImage.getScaledInstance(200,300, bufferedImage.SCALE_AREA_AVERAGING);
		
		file.deleteOnExit();
		
		return scaledImageFumetto;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	}
	
}
