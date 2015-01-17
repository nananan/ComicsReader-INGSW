package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import domain.Fumetto;
import domain.Lettore;

public class BottoneFumettoProfilo extends JButton implements ActionListener
{
	private Image scaledImage;
	private Fumetto fumetto;
	private int statoBottonePreferito;
	private int statoBottoneDaLeggere;
	private JButton bottoneStatoPreferiti;
	private JButton bottoneStatoDaLeggere;
	private Image imageScaled;
	private ImageIcon imageMenu;
	
	private MyPanel panel;
	
	private static final int PIACE = 0;
	private static final int NON_PIACE = 1;
	private static final int INDEFINITO_PREFERITI = 2;
	
	private static final int AGGIUNTO_DA_LEGGERE = 0;
	private static final int AGGIUNGERE_DA_LEGGERE = 1;
	private static final int INDEFINITO_DA_LEGGERE = 2;
	
	public BottoneFumettoProfilo(MyPanel panel, Image image, final Fumetto fumetto, int stato, final int statoDaLeggere, final Lettore lettore) 
	{
		super();
		this.scaledImage = image;
		this.fumetto = fumetto;
		this.statoBottonePreferito = stato;
		this.statoBottoneDaLeggere = statoDaLeggere;
		this.panel = panel;
		
		this.setPreferredSize(new Dimension(200, 300));
		this.setBorder(BorderFactory.createLineBorder(Color.black,3));
		
		if (statoBottonePreferito != INDEFINITO_PREFERITI && statoBottoneDaLeggere == INDEFINITO_DA_LEGGERE)
		{
			imageMenu = new ImageIcon("image/favorite.png");
			imageScaled = imageMenu.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			imageMenu.setImage(imageScaled);
			
			setIcon(imageMenu);
			setPressedIcon(imageMenu);
			
			bottoneStatoPreferiti = new JButton(imageMenu);
			
			bottoneStatoPreferiti.setIcon(imageMenu);
			bottoneStatoPreferiti.setBorderPainted(false);
			bottoneStatoPreferiti.setFocusPainted(false);
			
			bottoneStatoPreferiti.setPreferredSize(new Dimension(40,40));
			bottoneStatoPreferiti.setBounds(this.getX()+(int)this.getPreferredSize().getWidth()-
					(int)bottoneStatoPreferiti.getPreferredSize().getWidth() - this.getInsets().bottom, 
					this.getY()+(int)this.getPreferredSize().getHeight()-
					(int)bottoneStatoPreferiti.getPreferredSize().getHeight() - this.getInsets().bottom, 
					(int)bottoneStatoPreferiti.getPreferredSize().getWidth(), 
					(int)bottoneStatoPreferiti.getPreferredSize().getHeight());
			
			
			bottoneStatoPreferiti.setBackground(Color.GRAY);
			
			bottoneStatoPreferiti.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseReleased(MouseEvent e)
				{
					super.mouseReleased(e);
					if (statoBottonePreferito == PIACE)
					{
						statoBottonePreferito = NON_PIACE;
						aggiungiBottone("image/notFavorite.png", bottoneStatoPreferiti);
					
							lettore.rimuoviPreferiti(fumetto);
			
					}
					else
					{
						statoBottonePreferito = PIACE;
						aggiungiBottone("image/favorite.png", bottoneStatoPreferiti);
				
							lettore.inserisciPreferiti(fumetto);
					
					}
				}
			});
			
			add(bottoneStatoPreferiti);
		}
		else if (statoDaLeggere != INDEFINITO_DA_LEGGERE && statoBottonePreferito == INDEFINITO_PREFERITI)
		{
			imageMenu = new ImageIcon("image/notToRead.png");
			imageScaled = imageMenu.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			imageMenu.setImage(imageScaled);
			
			setIcon(imageMenu);
			setPressedIcon(imageMenu);
			
			bottoneStatoDaLeggere = new JButton(imageMenu);
			
			bottoneStatoDaLeggere.setIcon(imageMenu);
			bottoneStatoDaLeggere.setBorderPainted(false);
			bottoneStatoDaLeggere.setFocusPainted(false);
			
			bottoneStatoDaLeggere.setPreferredSize(new Dimension(40,40));
			bottoneStatoDaLeggere.setBounds(this.getX()+(int)this.getPreferredSize().getWidth()-
					(int)bottoneStatoDaLeggere.getPreferredSize().getWidth() - this.getInsets().bottom, 
					this.getY()+(int)this.getPreferredSize().getHeight()-
					(int)bottoneStatoDaLeggere.getPreferredSize().getHeight() - this.getInsets().bottom, 
					(int)bottoneStatoDaLeggere.getPreferredSize().getWidth(), 
					(int)bottoneStatoDaLeggere.getPreferredSize().getHeight());
			
			
			bottoneStatoDaLeggere.setBackground(Color.GRAY);
			
			bottoneStatoDaLeggere.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseReleased(MouseEvent e)
				{
					super.mouseReleased(e);
					if (statoBottoneDaLeggere == AGGIUNTO_DA_LEGGERE)
					{
						statoBottoneDaLeggere = AGGIUNGERE_DA_LEGGERE;
						aggiungiBottone("image/toRead.png", bottoneStatoDaLeggere);
					
							lettore.rimuoviDaLeggere(fumetto);
			
						
					}
					else
					{
						statoBottoneDaLeggere = AGGIUNTO_DA_LEGGERE;
						aggiungiBottone("image/notToRead.png", bottoneStatoDaLeggere);
						
							lettore.inserisciDaLeggere(fumetto);
						
					}
				}
			});
			
			add(bottoneStatoDaLeggere);
			
		}
		
		addActionListener(this);
			
	}	
	
	public void aggiungiBottone(String path, JButton bottone)
	{
		bottone.removeAll();

		imageMenu = new ImageIcon(path);
		imageScaled = imageMenu.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imageMenu.setImage(imageScaled);
		
		setIcon(imageMenu);
		setPressedIcon(imageMenu);
		
		bottone.setIcon(imageMenu);
		repaint();
	}
	
	public Image getImageScaled()
	{
		return scaledImage;
	}
	
	public Fumetto getFumetto()
	{
		return fumetto;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(scaledImage, 0, 0, this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		panel.PremiPerFumetto(fumetto, scaledImage, "Profilo");
	}
}
