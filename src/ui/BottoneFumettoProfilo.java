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
import java.awt.event.MouseMotionAdapter;
import java.net.MalformedURLException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.sun.org.apache.bcel.internal.generic.DALOAD;

import domain.AppManager;
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
	private Lettore lettoreCorrente;
	private MyListener listener;
	
	private static final int PIACE = 0;
	private static final int NON_PIACE = 1;
	
	private static final int AGGIUNTO_DA_LEGGERE = 0;
	private static final int AGGIUNGERE_DA_LEGGERE = 1;
	private static final int INDEFINITO_DA_LEGGERE = 2;
	
	public BottoneFumettoProfilo(MyPanel panel, Image image, final Fumetto fumetto, int stato) 
	{
		super();
		this.scaledImage = image;
		this.fumetto = fumetto;
		this.panel = panel;
		this.lettoreCorrente = AppManager.getLettore();
		this.listener = new MyListener();
		
		this.setPreferredSize(new Dimension(200, 300));
		this.setBorder(BorderFactory.createLineBorder(Color.black,3));
		
		addMouseMotionListener(new MouseMotionAdapter() {
		      public void mouseMoved(MouseEvent event) {
		    	  BottoneFumettoProfilo.this.setBorder(BorderFactory.createLineBorder(new Color(35,148,188),3));
		      }
		    });
	    addMouseListener(new MouseAdapter() {
	      public void mouseExited(MouseEvent event) {
	    	  BottoneFumettoProfilo.this.setBorder(BorderFactory.createLineBorder(Color.black,3));
	        }
	      });
		
		if (stato == INDEFINITO_DA_LEGGERE)
		{
			if (lettoreCorrente.eInPreferiti(fumetto))
			{
				statoBottoneDaLeggere = PIACE;
				bottoneStatoPreferiti = new JButton();
				aggiungiBottone("image/favorite.png", bottoneStatoPreferiti);
				
				bottoneStatoPreferiti.setPreferredSize(new Dimension(40,40));
				bottoneStatoPreferiti.setBounds(this.getX()+(int)this.getPreferredSize().getWidth()-
						(int)bottoneStatoPreferiti.getPreferredSize().getWidth() - this.getInsets().bottom, 
						this.getY()+(int)this.getPreferredSize().getHeight()-
						(int)bottoneStatoPreferiti.getPreferredSize().getHeight() - this.getInsets().bottom, 
						(int)bottoneStatoPreferiti.getPreferredSize().getWidth(), 
						(int)bottoneStatoPreferiti.getPreferredSize().getHeight());
				
				bottoneStatoPreferiti.setBackground(Color.GRAY);
				bottoneStatoPreferiti.addActionListener(listener);
				add(bottoneStatoPreferiti);
			}
			else if (!lettoreCorrente.eInPreferiti(fumetto))
			{
				statoBottonePreferito = NON_PIACE;
				bottoneStatoPreferiti = new JButton();
				aggiungiBottone("image/notFavorite.png", bottoneStatoPreferiti);
				bottoneStatoPreferiti.setPreferredSize(new Dimension(40,40));
				bottoneStatoPreferiti.setBounds(this.getX()+(int)this.getPreferredSize().getWidth()-
						(int)bottoneStatoPreferiti.getPreferredSize().getWidth() - this.getInsets().bottom, 
						this.getY()+(int)this.getPreferredSize().getHeight()-
						(int)bottoneStatoPreferiti.getPreferredSize().getHeight() - this.getInsets().bottom, 
						(int)bottoneStatoPreferiti.getPreferredSize().getWidth(), 
						(int)bottoneStatoPreferiti.getPreferredSize().getHeight());
				
				bottoneStatoPreferiti.setBackground(Color.GRAY);
				bottoneStatoPreferiti.addActionListener(listener);
				add(bottoneStatoPreferiti);
			}
		}
		else
		{
			if (lettoreCorrente.eInDaLeggere(fumetto))
			{
				statoBottoneDaLeggere = AGGIUNTO_DA_LEGGERE;
				bottoneStatoDaLeggere = new JButton();
				aggiungiBottone("image/notToRead.png", bottoneStatoDaLeggere);
	
				bottoneStatoDaLeggere.setPreferredSize(new Dimension(40,40));
				bottoneStatoDaLeggere.setBounds(this.getX()+(int)this.getPreferredSize().getWidth()-
						(int)bottoneStatoDaLeggere.getPreferredSize().getWidth() - this.getInsets().bottom, 
						this.getY()+(int)this.getPreferredSize().getHeight()-
						(int)bottoneStatoDaLeggere.getPreferredSize().getHeight() - this.getInsets().bottom, 
						(int)bottoneStatoDaLeggere.getPreferredSize().getWidth(), 
						(int)bottoneStatoDaLeggere.getPreferredSize().getHeight());
				
				bottoneStatoDaLeggere.setBackground(Color.GRAY);
				bottoneStatoDaLeggere.addActionListener(listener);
				add(bottoneStatoDaLeggere);
				
			}
			else if (!lettoreCorrente.eInDaLeggere(fumetto))
			{
				statoBottoneDaLeggere = AGGIUNGERE_DA_LEGGERE;
				bottoneStatoDaLeggere = new JButton();
				aggiungiBottone("image/toRead.png", bottoneStatoDaLeggere);
	
				bottoneStatoDaLeggere.setPreferredSize(new Dimension(40,40));
				bottoneStatoDaLeggere.setBounds(this.getX()+(int)this.getPreferredSize().getWidth()-
						(int)bottoneStatoDaLeggere.getPreferredSize().getWidth() - this.getInsets().bottom, 
						this.getY()+(int)this.getPreferredSize().getHeight()-
						(int)bottoneStatoDaLeggere.getPreferredSize().getHeight() - this.getInsets().bottom, 
						(int)bottoneStatoDaLeggere.getPreferredSize().getWidth(), 
						(int)bottoneStatoDaLeggere.getPreferredSize().getHeight());
				
				bottoneStatoDaLeggere.setBackground(Color.GRAY);
				add(bottoneStatoDaLeggere);
				bottoneStatoDaLeggere.addActionListener(listener);
			}
		}
		this.addActionListener(this);
			
	}	
	
	public void aggiungiBottone(String path, JButton bottone)
	{
		imageMenu = new ImageIcon(path);
		imageScaled = imageMenu.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imageMenu.setImage(imageScaled);
		
		setIcon(imageMenu);
		setPressedIcon(imageMenu);
		
		bottone.setIcon(imageMenu);
		bottone.setBorderPainted(false);
		bottone.setFocusPainted(false);
		
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
		panel.PremiPerFumetto(fumetto, scaledImage);
	}
	
	private class MyListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			if (source == bottoneStatoDaLeggere)
			{
				if (statoBottoneDaLeggere == AGGIUNTO_DA_LEGGERE)
				{
					statoBottoneDaLeggere = AGGIUNGERE_DA_LEGGERE;
					aggiungiBottone("image/toRead.png", bottoneStatoDaLeggere);
				
					lettoreCorrente.rimuoviDaLeggere(fumetto);
				}
				else
				{
					statoBottoneDaLeggere = AGGIUNTO_DA_LEGGERE;
					aggiungiBottone("image/notToRead.png", bottoneStatoDaLeggere);
					
					lettoreCorrente.inserisciDaLeggere(fumetto);
				}
			}
			else if (source == bottoneStatoPreferiti)
			{
				if (statoBottonePreferito == 0)
				{
					statoBottonePreferito = NON_PIACE;
					aggiungiBottone("image/notFavorite.png", bottoneStatoPreferiti);
				
					lettoreCorrente.rimuoviPreferiti(fumetto);
				}
				else
				{
					statoBottonePreferito = PIACE;
					aggiungiBottone("image/favorite.png", bottoneStatoPreferiti);
			
					lettoreCorrente.inserisciPreferiti(fumetto);
				}
			}
			repaint();
		}
	}
}
