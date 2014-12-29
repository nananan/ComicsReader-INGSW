import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PannelloFiltraggio extends JPanel
{
	Text textGenere = new Text("Genere");
	Text textStato = new Text("Stato");
	Text textManga = new Text("Manga");
	Text textFumetti = new Text("Fumetti");

	MyButton buttonManga = new MyButton("Manga");
	MyButton buttonFumetti = new MyButton("Fumetti");
	MyButton buttonStatoCompletoManga = new MyButton("Completi");
	MyButton buttonStatoIncompletoManga = new MyButton("Incompleti");
	MyButton buttonStatoCompletoFumetto = new MyButton("Completi");
	MyButton buttonStatoIncompletoFumetto = new MyButton("Incompleti");
	MyButton clean = new MyButton("Clean");
	MyButton fine = new MyButton("Fine");
	MyButton manga[] = new MyButton[14];
	String nameButtonManga[] = {"Aniparo", "CyberPunk", "Kodomo", "Shonen", "Seinen", "Gekiga", "Gore", "Josei", "Maho Shojo", "Mecha", "Meitantei", "Romakome", "Spokon", "Suriraa"};
	
	MyButton fumetti[] = new MyButton[11];
	String nameButtonFumetti[] = {"Fantascienza", "Western", "Poliziesco", "Horror", "Avventura", "Fantasy", "Comico", "Satira", "Pulp e Trash", "Sportivo", "Romantico"};
	
	Font font = new Font("Caladea", Font.BOLD, 14);
	
	ArrayList<String> filtriManga = new ArrayList<>();
	ArrayList<String> filtriFumetto = new ArrayList<>();
	
	boolean filtraggioManga = false;
	boolean filtraggioFumetto = false;
	boolean putCompletoManga = false;
	boolean putIncompletoManga = false;
	boolean putCompletoFumetto = false;
	boolean putIncompletoFumetto = false;
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 6;
	
	public PannelloFiltraggio(final JPanel pannelloCentro, final MyPanel panel) 
	{
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension(larghezza, 0));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		final JPanel panelSec = this;
		
		textManga.setFont(font);
		textFumetti.setFont(font);
		
		textGenere.setForeground(new Color(51,47,47));
		textGenere.setFont(font);
		textStato.setForeground(new Color(51,47,47));
		textStato.setFont(font);

		buttonManga.setDimension(this, pannelloCentro, 20);
		add(buttonManga);
		
		buttonFumetti.setDimension(this, pannelloCentro, buttonManga.getY()+25);
		add(buttonFumetti);
		
		//BUTTON FINE, TORNA AL MENU PRINCIPALE
		fine.setPreferredSize(new Dimension(70, 20));
		fine.setBounds((int)this.getPreferredSize().getWidth()-(int)fine.getPreferredSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-150, (int)fine.getPreferredSize().getWidth()-pannelloCentro.getInsets().bottom,20);
		add(fine);
		
		fine.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if (filtriFumetto.size() != 0)
				{
					System.out.println("FUMETTI");
					for (String string : filtriFumetto) {
						System.out.println(string);
					}
				}
				if (filtriManga.size() != 0)
				{
					System.out.println("MANGA");
					for (String string2 : filtriManga) {
						System.out.println(string2);
					}	
				}
				
				panel.PremiPerPannelloSinistro();
				
				//TODO implementare poi le query che cercheranno i fumetti con i filtri
			}
		 });
		
		
		//BUTTON PER CANCELLARE I FILTRI
		clean.setPreferredSize(new Dimension(70, 20));
		clean.setBounds(this.getInsets().bottom, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-150, (int)clean.getPreferredSize().getWidth()+this.getInsets().bottom,20);
		add(clean);
		
		clean.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if (filtraggioFumetto)
				{
					for (String string : filtriFumetto) {
						for (int i = 0; i < fumetti.length; i++)
						{
							if (fumetti[i].getText().equals(string))
							{
								fumetti[i].setForeground(Color.WHITE);
							}
						}
					}
					
					buttonStatoCompletoFumetto.setForeground(Color.WHITE);
					buttonStatoIncompletoFumetto.setForeground(Color.WHITE);
					
					filtriFumetto.clear();	
				}
				
				if (filtraggioManga)
				{
					for (String string : filtriManga) {
						for (int i = 0; i < manga.length; i++)
						{
							if (manga[i].getText().equals(string))
							{
								manga[i].setForeground(Color.WHITE);
							}
						}
					}
					
					buttonStatoCompletoManga.setForeground(Color.WHITE);
					buttonStatoIncompletoManga.setForeground(Color.WHITE);
					
					filtriManga.clear();	
				}
					
			}
		 });
		
		
		for (int i = 0; i < manga.length; i++)
			manga[i] = new MyButton(nameButtonManga[i]);
		
		for (int i = 0; i < fumetti.length; i++)
			fumetti[i] = new MyButton(nameButtonFumetti[i]);
		
		buttonManga.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				filtraggioManga = true;
				if (filtraggioFumetto)
				{
					removeFumetti();
					filtraggioFumetto = false;
				}
				textManga.setBounds(panelSec.getInsets().bottom+(int)getPreferredSize().getWidth()/3, 0, buttonManga.getWidth(), buttonManga.getHeight());
//				buttonManga.setEnabled(false);
				remove(buttonManga);
				add(textManga);
				addManga(pannelloCentro);
				buttonFumetti.setBounds(panelSec.getInsets().bottom, fine.getY()-25, buttonFumetti.getWidth(), buttonFumetti.getHeight());
//				buttonFumetti.setEnabled(true);
				add(buttonFumetti);
				repaint();
			}
		 });
		
		buttonFumetti.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				filtraggioFumetto = true;
				if (filtraggioManga)
				{
					removeManga();
					filtraggioManga = false;
				}
				textFumetti.setBounds(panelSec.getInsets().bottom+(int)getPreferredSize().getWidth()/3, 0, buttonFumetti.getWidth(), buttonFumetti.getHeight());
//				buttonFumetti.setEnabled(false);
				remove(buttonFumetti);
				add(textFumetti);
				addFumetto(pannelloCentro);
				buttonManga.setBounds(panelSec.getInsets().bottom, fine.getY()-25, buttonManga.getWidth(), buttonManga.getHeight());
//				buttonManga.setEnabled(true);
				add(buttonManga);
				repaint();
			}
		 });
	}
	
	
	//AGGIUNTA FILTRAGGI MANGA
	public void addManga(JPanel pannelloCentro)
	{
		int width = (int) this.getPreferredSize().getWidth()-pannelloCentro.getInsets().bottom;
		int height = 20;

		textStato.setPreferredSize(new Dimension(100, 20));
		textStato.setBounds(this.getInsets().bottom, textManga.getY()+25, 100,20);
		add(textStato);
		
		buttonStatoCompletoManga.setPreferredSize(new Dimension(width, height));
		buttonStatoCompletoManga.setBounds(this.getInsets().bottom, textStato.getY()+25, width, height);
		add(buttonStatoCompletoManga);
		
		buttonStatoCompletoManga.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if (buttonStatoIncompletoManga.getForeground() == Color.RED)
				{
					buttonStatoIncompletoManga.setForeground(Color.WHITE);
					filtriManga.remove(buttonStatoIncompletoManga.getText());
				}
				if (putCompletoManga)
				{
					buttonStatoCompletoManga.setForeground(Color.WHITE);
//					if (!filtriManga.get(0).equals("All"))
//						filtriManga.add(0,"All");
					filtriManga.remove(buttonStatoCompletoManga.getText());
					putCompletoManga = false;
				}
				else
				{
					buttonStatoCompletoManga.setForeground(Color.RED);
					filtriManga.add(0, buttonStatoCompletoManga.getText());
//					filtriManga.remove("All");
					putCompletoManga = true;
				}
			}
		});
		
		buttonStatoIncompletoManga.setPreferredSize(new Dimension(width, height));
		buttonStatoIncompletoManga.setBounds(this.getInsets().bottom, buttonStatoCompletoManga.getY()+25, width, height);
		add(buttonStatoIncompletoManga);
		
		buttonStatoIncompletoManga.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if (buttonStatoCompletoManga.getForeground() == Color.RED)
				{
					buttonStatoCompletoManga .setForeground(Color.WHITE);
					filtriManga.remove(buttonStatoCompletoManga.getText());
				}
				if (putIncompletoManga)
				{
					buttonStatoIncompletoManga.setForeground(Color.WHITE);
//					if (!filtriManga.get(0).equals("All"))
//						filtriManga.add(0,"All");
					filtriManga.remove(buttonStatoIncompletoManga.getText());
					putIncompletoManga = false;
				}
				else
				{
					buttonStatoIncompletoManga.setForeground(Color.RED);
					filtriManga.add(0, buttonStatoIncompletoManga.getText());
//					filtriManga.remove("All");
					putIncompletoManga = true;
				}
			}
		});
		
		textGenere.setPreferredSize(new Dimension(100, 20));
		textGenere.setBounds(this.getInsets().bottom, buttonStatoIncompletoManga.getY()+25, 100,20);
		add(textGenere);
		
		for (int i=0; i < manga.length; i++)
		{
			manga[i].setPreferredSize(new Dimension(width, height));
			if (i != 0) 
				manga[i].setBounds(this.getInsets().bottom, manga[i-1].getY()+25, width, height);
			else
				manga[i].setBounds(this.getInsets().bottom, textGenere.getY()+25, width, height);
			
			add(manga[i]);
			
			final int indice = i;
			manga[indice].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e)
				{
					if (manga[indice].getForeground() == Color.RED)
					{
						manga[indice].setForeground(Color.WHITE);
						filtriManga.remove(nameButtonManga[indice]);
					}
					else
					{
						manga[indice].setForeground(Color.RED);
						filtriManga.add(nameButtonManga[indice]);
					}
				}
			 });
		}
	}
	
	
	//AGGIUNTA FILTRAGGI FUMETTO
	public void addFumetto(JPanel pannelloCentro)
	{
		int width = (int) this.getPreferredSize().getWidth()-pannelloCentro.getInsets().bottom;
		int height = 20;

		textStato.setPreferredSize(new Dimension(100, 20));
		textStato.setBounds(this.getInsets().bottom, textFumetti.getY()+25, 100,20);
		add(textStato);
		
		buttonStatoCompletoFumetto.setPreferredSize(new Dimension(width, height));
		buttonStatoCompletoFumetto.setBounds(this.getInsets().bottom, textStato.getY()+25, width, height);
		add(buttonStatoCompletoFumetto);
		
		buttonStatoCompletoFumetto.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if (buttonStatoIncompletoFumetto.getForeground() == Color.RED)
				{
					buttonStatoIncompletoFumetto.setForeground(Color.WHITE);
					filtriFumetto.remove(buttonStatoIncompletoFumetto.getText());
				}
				if (putCompletoFumetto)
				{
					buttonStatoCompletoFumetto.setForeground(Color.WHITE);
//					if (!filtriFumetto.get(0).equals("All"))
//						filtriFumetto.add(0,"All");
					filtriFumetto.remove(buttonStatoCompletoFumetto.getText());
					putCompletoFumetto = false;
				}
				else
				{
					buttonStatoCompletoFumetto.setForeground(Color.RED);
					filtriFumetto.add(0, buttonStatoCompletoFumetto.getText());
//					filtriFumetto.remove("All");
					putCompletoFumetto = true;
				}
			}
		});
		
		buttonStatoIncompletoFumetto.setPreferredSize(new Dimension(width, height));
		buttonStatoIncompletoFumetto.setBounds(this.getInsets().bottom, buttonStatoCompletoFumetto.getY()+25, width, height);
		add(buttonStatoIncompletoFumetto);
		
		buttonStatoIncompletoFumetto.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if (buttonStatoCompletoFumetto.getForeground() == Color.RED)
				{
					buttonStatoCompletoFumetto.setForeground(Color.WHITE);
					filtriFumetto.remove(buttonStatoCompletoFumetto.getText());
				}
				if (putIncompletoFumetto)
				{
					buttonStatoIncompletoFumetto.setForeground(Color.WHITE);
//					if (!filtriFumetto.get(0).equals("All"))
//						filtriFumetto.add(0,"All");
					filtriFumetto.remove(buttonStatoIncompletoFumetto.getText());
					putIncompletoFumetto = false;
				}
				else
				{
					buttonStatoIncompletoFumetto.setForeground(Color.RED);
					filtriFumetto.add(0, buttonStatoIncompletoFumetto.getText());
//					filtriFumetto.remove("All");
					putIncompletoFumetto = true;
				}
			}
		});
		
		textGenere.setPreferredSize(new Dimension(100, 20));
		textGenere.setBounds(this.getInsets().bottom, buttonStatoIncompletoFumetto.getY()+25, 100,20);
		add(textGenere);
		
		
		for (int i=0; i < fumetti.length; i++)
		{
			fumetti[i].setPreferredSize(new Dimension(width, height));
			if (i != 0) 
				fumetti[i].setBounds(this.getInsets().bottom, fumetti[i-1].getY()+25, width, height);
			else
				fumetti[i].setBounds(this.getInsets().bottom, textGenere.getY()+25, width, height);
			
			add(fumetti[i]);
			
			final int indice = i;
			fumetti[indice].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e)
				{
					
					if (fumetti[indice].getForeground() == Color.RED)
					{
						fumetti[indice].setForeground(Color.WHITE);
						filtriFumetto.remove(nameButtonFumetti[indice]);
					}
					else
					{
						fumetti[indice].setForeground(Color.RED);
						filtriFumetto.add(nameButtonFumetti[indice]);
					}
				}
			 });
		}
	}	
	
	public void removeManga()
	{
		remove(textStato);
		remove(buttonStatoCompletoManga);
		remove(buttonStatoIncompletoManga);
		remove(textGenere);
		remove(textManga);
		
		for (int i= 0; i < manga.length; i++)
		{
			remove(manga[i]);
		}
	}
	
	public void removeFumetti()
	{
		remove(textStato);
		remove(buttonStatoCompletoFumetto);
		remove(buttonStatoIncompletoFumetto);
		remove(textGenere);
		remove(textFumetti);
		
		for (int i=0; i < fumetti.length; i++)
		{
			remove(fumetti[i]);
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		g.drawImage(image, 0,0, this);
	}
}
