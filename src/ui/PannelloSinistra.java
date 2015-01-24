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

import domain.Fumetto;
import technicalService.GestoreDataBase;


public class PannelloSinistra extends JPanel
{
	MyPanel panel;
	
	MyButton buttonDiscover = new MyButton("Scopri");
	MyButton buttonTopRead = new MyButton("Top Read");
	MyButton buttonTopRated = new MyButton("Top Rated Comics");
	MyButton buttonFileLocali = new MyButton("File Locali");
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 7;
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() ;
	
	private MyButton tornaIndietro;
	private JButton bottoneIndietro;
	private JLabel copertinaFumetto;
	
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
		
		MyListener listener = new MyListener();
		
		buttonDiscover.addActionListener(listener);
		buttonTopRead.addActionListener(listener);
		buttonTopRated.addActionListener(listener);
		buttonFileLocali.addActionListener(listener);
	
	}
	
	public void aggiungiBottoneVolume(Image immagineCopertinaFumetto, final Fumetto fumetto)
	{
		immagineCopertinaFumetto = immagineCopertinaFumetto.getScaledInstance(larghezza, 300, Image.SCALE_SMOOTH);
		
		copertinaFumetto = new JLabel(new ImageIcon(immagineCopertinaFumetto));
		
		copertinaFumetto.setBounds(0, (int)this.getPreferredSize().getHeight()-
	    		(int)copertinaFumetto.getPreferredSize().getHeight() - 40 -
	    		(int)copertinaFumetto.getPreferredSize().getHeight()/2, 
	    		(int)copertinaFumetto.getPreferredSize().getWidth(), 
	    		(int)copertinaFumetto.getPreferredSize().getHeight());
		copertinaFumetto.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		
		bottoneIndietro = new JButton();
		ImageIcon imageMenu = new ImageIcon("image/reading.png");
		Image imageScaled = imageMenu.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		imageMenu.setImage(imageScaled);
		
		bottoneIndietro .setIcon(imageMenu);
		bottoneIndietro .setPressedIcon(imageMenu);
		bottoneIndietro .setBorderPainted(false);
		bottoneIndietro .setFocusPainted(false);
		bottoneIndietro .setContentAreaFilled(false);
		bottoneIndietro .setBackground(this.getBackground());
		
		bottoneIndietro.setBounds(this.getInsets().bottom, copertinaFumetto.getY()+
				(int)copertinaFumetto.getPreferredSize().getHeight(), 
				(int)bottoneIndietro.getPreferredSize().getWidth(), 
				(int)bottoneIndietro.getPreferredSize().getHeight());
		
		tornaIndietro = new MyButton("Torna al Fumetto", 14, new Color(91,84,84));
		tornaIndietro.setBounds(bottoneIndietro.getX()+(int)bottoneIndietro.getPreferredSize().getWidth()-10,
				copertinaFumetto.getY()+(int)copertinaFumetto.getPreferredSize().getHeight()+3, 
				(int)tornaIndietro.getPreferredSize().getWidth(), 
				(int)tornaIndietro.getPreferredSize().getHeight());
		
		final Image immaginePerFumetto = immagineCopertinaFumetto;
		tornaIndietro.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				super.mouseReleased(e);
				panel.PremiPerFumetto(fumetto, immaginePerFumetto);
			}
		});
		
		bottoneIndietro.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				super.mouseReleased(e);
				panel.PremiPerFumetto(fumetto, immaginePerFumetto);
			}
		});
		
		this.add(copertinaFumetto);
		this.add(bottoneIndietro);
		this.add(tornaIndietro);
		repaint();
	}
	
	public void rimuoviBottoniDelVolume()
	{
		if (copertinaFumetto != null && bottoneIndietro != null && tornaIndietro != null)
		{
			this.remove(copertinaFumetto);
			this.remove(bottoneIndietro);
			this.remove(tornaIndietro);
		}
		repaint();
	}
	
	public void deselezionaBottoni()
	{
		buttonDiscover.setForeground(Color.WHITE);
		buttonTopRead.setForeground(Color.WHITE);
		buttonTopRated.setForeground(Color.WHITE);
	}
	
	public void setColorBottoneDiscover(Color color)
	{
		buttonDiscover.setForeground(color);
	}
	public void setColorBottoneTopRead(Color color)
	{
		buttonTopRead.setForeground(color);
	}
	public void setColorBottoneTopRated(Color color)
	{
		buttonTopRated.setForeground(color);
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
		}
	}
	
}
