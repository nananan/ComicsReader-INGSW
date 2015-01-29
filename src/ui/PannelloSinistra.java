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
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.crypto.Data;

import com.sun.org.apache.xml.internal.utils.NSInfo;

import domain.AppManager;
import domain.Fumetto;
import domain.Lettore;
import technicalService.GestoreDataBase;


public class PannelloSinistra extends JPanel
{
	MyPanel panel;
	
	private static Color COLORE = new Color(35,148,188);
	
	MyButton buttonDiscover = new MyButton("Scopri", COLORE);
	MyButton buttonTopRead = new MyButton("Top Read", COLORE);
	MyButton buttonTopRated = new MyButton("Top Rated Comics", COLORE);
	MyButton buttonFileLocali = new MyButton("File Locali", COLORE);
	MyButton buttonAmici = new MyButton("Amici", COLORE);
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 7;
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() ;
	
	private MyButton tornaIndietro;
	private JButton bottoneIndietro;
	private JLabel copertinaFumetto;
	private JButton bottoneSegnalibro;
	private MyButton inserisciSegnalibro;

	public Fumetto fumetto;
	public Image immaginePerFumetto;
	private MyListener listener;
	
	public PannelloSinistra(final MyPanel panel) 
	{
		super();
		this.panel = panel;
		
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension(larghezza+10, altezza));
//		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		buttonDiscover.setBounds(5, 10, (int)buttonDiscover.getPreferredSize().getWidth(), (int)buttonDiscover.getPreferredSize().getHeight());
		add(buttonDiscover);
		
		buttonTopRead.setBounds(buttonDiscover.getX(), 3+buttonDiscover.getY()+(int)buttonDiscover.getPreferredSize().getHeight(), (int)buttonTopRead.getPreferredSize().getWidth(), (int)buttonTopRead.getPreferredSize().getHeight());
		add(buttonTopRead);
		  
		buttonTopRated.setBounds(buttonDiscover.getX(), 3+buttonTopRead.getY()+(int)buttonTopRead.getPreferredSize().getHeight(), (int)buttonTopRated.getPreferredSize().getWidth(), (int)buttonTopRated.getPreferredSize().getHeight());
		add(buttonTopRated);

		buttonFileLocali.setBounds(buttonDiscover.getX(), 3+buttonTopRated.getY()+(int)buttonTopRated.getPreferredSize().getHeight(), (int)buttonFileLocali.getPreferredSize().getWidth(), (int)buttonFileLocali.getPreferredSize().getHeight());
		add(buttonFileLocali);

		buttonAmici.setBounds(buttonDiscover.getX(), 3+buttonFileLocali.getY()+(int)buttonFileLocali.getPreferredSize().getHeight(), (int)buttonAmici.getPreferredSize().getWidth(), (int)buttonAmici.getPreferredSize().getHeight());
		add(buttonAmici);
		
		listener = new MyListener();
		
		buttonDiscover.addActionListener(listener);
		buttonTopRead.addActionListener(listener);
		buttonTopRated.addActionListener(listener);
		buttonFileLocali.addActionListener(listener);
		buttonAmici.addActionListener(listener);
	
	}
	
	public void aggiungiBottoneVolume(Image immagineCopertinaFumetto, final Fumetto fumetto)
	{
		this.fumetto = fumetto;
		this.immaginePerFumetto = immagineCopertinaFumetto.getScaledInstance(larghezza, 300, Image.SCALE_SMOOTH);
		
		copertinaFumetto = new JLabel(new ImageIcon(immagineCopertinaFumetto));
		
		copertinaFumetto.setBounds(0, (int)this.getPreferredSize().getHeight()-
	    		(int)copertinaFumetto.getPreferredSize().getHeight() - 70 -
	    		(int)copertinaFumetto.getPreferredSize().getHeight()/2, 
	    		(int)copertinaFumetto.getPreferredSize().getWidth(), 
	    		(int)copertinaFumetto.getPreferredSize().getHeight());
		copertinaFumetto.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		bottoneSegnalibro = new JButton();
		setImmagineBottone(bottoneSegnalibro, "image/toSegnalibro.png");
		
		bottoneSegnalibro.setBounds(this.getInsets().bottom, copertinaFumetto.getY()+
				(int)copertinaFumetto.getPreferredSize().getHeight(), 
				(int)bottoneSegnalibro.getPreferredSize().getWidth(), 
				(int)bottoneSegnalibro.getPreferredSize().getHeight());
		
		inserisciSegnalibro = new MyButton("Segnalibro", 14, new Color(91,84,84), COLORE);
		inserisciSegnalibro.setBounds(bottoneSegnalibro.getX()+(int)bottoneSegnalibro.getPreferredSize().getWidth()-10,
				copertinaFumetto.getY()+(int)copertinaFumetto.getPreferredSize().getHeight()+3, 
				(int)inserisciSegnalibro.getPreferredSize().getWidth(), 
				(int)inserisciSegnalibro.getPreferredSize().getHeight());
		
		bottoneIndietro = new JButton();
		setImmagineBottone(bottoneIndietro, "image/reading.png");

		bottoneIndietro.setBounds(this.getInsets().bottom, bottoneSegnalibro.getY()+
				(int)bottoneSegnalibro.getPreferredSize().getHeight(), 
				(int)bottoneIndietro.getPreferredSize().getWidth(), 
				(int)bottoneIndietro.getPreferredSize().getHeight());
		
		tornaIndietro = new MyButton("Torna al Fumetto", 14, new Color(91,84,84), COLORE);
		tornaIndietro.setBounds(bottoneIndietro.getX()+(int)bottoneIndietro.getPreferredSize().getWidth()-10,
				bottoneSegnalibro.getY()+(int)bottoneSegnalibro.getPreferredSize().getHeight()+3, 
				(int)tornaIndietro.getPreferredSize().getWidth(), 
				(int)tornaIndietro.getPreferredSize().getHeight());
		
		bottoneIndietro.addActionListener(listener);
		tornaIndietro.addActionListener(listener);
		bottoneSegnalibro.addActionListener(listener);
		inserisciSegnalibro.addActionListener(listener);
		
		this.add(copertinaFumetto);
		this.add(bottoneIndietro);
		this.add(tornaIndietro);
		this.add(bottoneSegnalibro);
		this.add(inserisciSegnalibro);
		repaint();
	}
	
	public void rimuoviBottoniDelVolume()
	{
		if (copertinaFumetto != null && bottoneIndietro != null && tornaIndietro != null
				&& bottoneSegnalibro != null && inserisciSegnalibro != null)
		{
			this.remove(copertinaFumetto);
			this.remove(bottoneIndietro);
			this.remove(tornaIndietro);
			this.remove(bottoneSegnalibro);
			this.remove(inserisciSegnalibro);
		}
		repaint();
	}
	
	public void deselezionaBottoni()
	{
		buttonDiscover.setForeground(Color.WHITE);
		buttonTopRead.setForeground(Color.WHITE);
		buttonTopRated.setForeground(Color.WHITE);
		buttonFileLocali.setForeground(Color.WHITE);
		buttonAmici.setForeground(Color.WHITE);
	}
	
	public void setColorBottoneDiscover(Color color)
	{	
		setPremutoBottoni();
		buttonDiscover.setForeground(color);
	}
	public void setColorBottoneTopRead(Color color)
	{
		setPremutoBottoni();
		buttonTopRead.setForeground(color);
	}
	public void setColorBottoneTopRated(Color color)
	{
		setPremutoBottoni();
		buttonTopRated.setForeground(color);
	}
	
	private void setPremutoBottoni()
	{
		buttonDiscover.setPremuto();
		buttonTopRead.setPremuto();
		buttonTopRated.setPremuto();
		buttonFileLocali.setPremuto();
	}
	
	private void setImmagineBottone(JButton bottone, String pathImmagine)
	{
		ImageIcon imageMenu = new ImageIcon(pathImmagine);
		Image imageScaled = imageMenu.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imageMenu.setImage(imageScaled);
		
		bottone.setIcon(imageMenu);
		bottone.setPressedIcon(imageMenu);
		bottone.setBorderPainted(false);
		bottone.setFocusPainted(false);
		bottone.setContentAreaFilled(false);
		bottone.setBackground(this.getBackground());
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	}
	
	private class MyListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			GestoreDataBase.connetti();
			
			if (source == buttonDiscover)
				panel.PremiPerDiscover();
			else if (source == buttonTopRead)
				panel.premiPerAverePiuLetti();
			else if (source == buttonTopRated)
				panel.premiPerAverePiuVotati();
			else if (source == buttonFileLocali)
			{
				FileLocali x = new FileLocali();
		        File folder = new File(x.fileChooser.getSelectedFile().toString());
		        
		        File[] listOfFiles = folder.listFiles();
		        
		        Arrays.sort(listOfFiles, new Comparator<File>()
				{
		        	public int compare(File file1, File file2) {
		        		return Integer.parseInt(file1.getName()) - Integer.parseInt(file2.getName());
		        	};
				});
		        
		        panel.premiPerPannelloVisualizzazioneOffline(listOfFiles);
		        
			}
			else if (source == bottoneIndietro || source == tornaIndietro)
			{
				panel.PremiPerFumetto(fumetto, immaginePerFumetto);
			}
			else if (source == bottoneSegnalibro || source == inserisciSegnalibro)
			{
				setImmagineBottone(bottoneSegnalibro, "image/segnalibro.png");
				panel.premiPerInserireSegnalibro(fumetto);
			}
			else if (source == buttonAmici)
			{
				try
				{
					panel.PremiPerAmici();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
}
