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
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	MyButton buttonUtentsRated = new MyButton("Utents Rated");
	
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
		setPreferredSize(new Dimension(larghezza, altezza));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		buttonDiscover.setBounds(5, 10, (int)buttonDiscover.getPreferredSize().getWidth(), (int)buttonDiscover.getPreferredSize().getHeight());
		add(buttonDiscover);
		
		buttonTopRead.setBounds(buttonDiscover.getX(), 3+buttonDiscover.getY()+(int)buttonDiscover.getPreferredSize().getHeight(), (int)buttonTopRead.getPreferredSize().getWidth(), (int)buttonTopRead.getPreferredSize().getHeight());
		add(buttonTopRead);
		  
		buttonTopRated.setBounds(buttonDiscover.getX(), 3+buttonTopRead.getY()+(int)buttonTopRead.getPreferredSize().getHeight(), (int)buttonTopRated.getPreferredSize().getWidth(), (int)buttonTopRated.getPreferredSize().getHeight());
		add(buttonTopRated);

		buttonUtentsRated.setBounds(buttonDiscover.getX(), 3+buttonTopRated.getY()+(int)buttonTopRated.getHeight(), (int)buttonUtentsRated.getPreferredSize().getWidth(), (int)buttonUtentsRated.getPreferredSize().getHeight());
		add(buttonUtentsRated);
		
		MyListener listener = new MyListener();
		
		buttonDiscover.addActionListener(listener);
	
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
		this.remove(copertinaFumetto);
		this.remove(bottoneIndietro);
		this.remove(tornaIndietro);
		repaint();
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
			{
				
					panel.PremiPerDiscover();
			}
		}
	}
	
}
