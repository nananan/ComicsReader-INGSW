package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import technicalService.GestoreDataBase;
import domain.Lettore;

public class PannelloProfilo extends JPanel
{
	Text nomeAccount;
	MyButton bottoneFollows;
	MyButton bottoneFollower;
	MyButton bottonePreferiti;
	MyButton bottoneDaLeggere;
	MyButton bottoneCronologia;
	private JSeparator jseparator;
	
	private static Color COLORE = Color.DARK_GRAY;
	
	private ArrayList<BottoneFumettoProfilo> bottoniFumettiPreferiti;
	private ArrayList<BottoneFumettoProfilo> bottoniDaLeggere;
	private ArrayList<BottoneCronologia> bottoniCronologia;

	private ArrayList<BottoneFollow> listaBottoniFollowCorrente;
	private ArrayList<BottoneFollow> listaBottoniFollowerCorrente;
	
	private Lettore lettore;
	private ImageIcon imageFumetto;
	private Image scaledImage;
	private File file;
	private JLabel forImage;
	private boolean aggiungi = false;
	private boolean aggiungiFollows = false;
	private boolean aggiungiFollowers = false;
	private boolean aggiungiDaLeggere = false;
	
	private MyListener listener;
	private MyPanel panel;
	
	private int larghezza;
	public boolean aggiungiCronologia;
	private HashMap<String, PannelloScrollPane> pannelloDelProfilo;
	
	private static Color SFONDO = Color.GRAY;
	
	public PannelloProfilo(MyPanel panel, int larghezza, Lettore lettore)
	{
		super();
		setBackground(Color.GRAY);
//		setBorder(BorderFactory.createLineBorder(Color.black,3));
		setPreferredSize(new Dimension(larghezza, 0));
		setLayout(null);
		
		this.panel = panel;
		this.larghezza = larghezza - 100;
		
		listener = new MyListener();
		
		GestoreDataBase.connetti();
		
		this.lettore = lettore;
		
		try {
			scaledImage = getURL(lettore.getUrlFoto(),150,150);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		imageFumetto = new ImageIcon(scaledImage);
		forImage = new JLabel();
		forImage.setIcon(imageFumetto);
		forImage.setPreferredSize(new Dimension(150,150));
		forImage.setBounds(20, 20, (int)forImage.getPreferredSize().getWidth(), (int)forImage.getPreferredSize().getHeight());
		forImage.setBorder(BorderFactory.createLineBorder(Color.black,3));
		add(forImage);
		
		Text nome = new Text("Utente", 20, Color.DARK_GRAY);
		nome.setBounds(30 + forImage.getX() + (int)forImage.getPreferredSize().getWidth(), forImage.getY(), (int)nome.getPreferredSize().getWidth(), (int)nome.getPreferredSize().getHeight());
		add(nome);
		nomeAccount = new Text(lettore.getNome(), 35, Color.WHITE);
		nomeAccount.setBounds(30 + forImage.getX() + (int)forImage.getPreferredSize().getWidth(), 10 + nome.getY() + (int)nome.getPreferredSize().getHeight(), (int)nomeAccount.getPreferredSize().getWidth(), (int)nomeAccount.getPreferredSize().getHeight());
		add(nomeAccount);
		
		bottoneFollows = new MyButton("Follows("+lettore.getNumFollow()+")", 20, Color.WHITE, COLORE);
		bottoneFollows.setBounds(forImage.getX(), 15+forImage.getY()+(int)forImage.getPreferredSize().getHeight(), (int)bottoneFollows.getPreferredSize().getWidth(),(int)bottoneFollows.getPreferredSize().getHeight());
		add(bottoneFollows);
		bottoneFollows.addActionListener(listener);
		
		bottoneFollower = new MyButton("Follower("+lettore.getNumFollower()+")", 20, Color.WHITE, COLORE);
		bottoneFollower.setBounds(25+bottoneFollows.getX()+(int)bottoneFollows.getPreferredSize().getWidth(), bottoneFollows.getY(), (int)bottoneFollower.getPreferredSize().getWidth(),(int)bottoneFollower.getPreferredSize().getHeight());
		add(bottoneFollower);
		bottoneFollower.addActionListener(listener);
		
		bottonePreferiti = new MyButton("Preferiti", 20, Color.WHITE, COLORE);
		bottonePreferiti.setBounds(25+bottoneFollower.getX()+(int)bottoneFollower.getPreferredSize().getWidth(), bottoneFollows.getY(), (int)bottonePreferiti.getPreferredSize().getWidth(),(int)bottonePreferiti.getPreferredSize().getHeight());
		add(bottonePreferiti);
		bottonePreferiti.addActionListener(listener);
		
		bottoneDaLeggere = new MyButton("Da Leggere", 20, Color.WHITE, COLORE);
		bottoneDaLeggere.setBounds(25+bottonePreferiti.getX()+(int)bottonePreferiti.getPreferredSize().getWidth(), bottoneFollows.getY(), (int)bottoneDaLeggere.getPreferredSize().getWidth(),(int)bottoneDaLeggere.getPreferredSize().getHeight());
		add(bottoneDaLeggere);
		bottoneDaLeggere.addActionListener(listener);
		
		bottoneCronologia = new MyButton("Cronologia", 20, Color.WHITE, COLORE);
		bottoneCronologia.setBounds(25+bottoneDaLeggere.getX()+(int)bottoneDaLeggere.getPreferredSize().getWidth(), bottoneFollows.getY(),(int)bottoneCronologia.getPreferredSize().getWidth(),(int)bottoneCronologia.getPreferredSize().getHeight());
		add(bottoneCronologia);
		bottoneCronologia.addActionListener(listener);
		
		jseparator = new JSeparator(JSeparator.HORIZONTAL);
		jseparator.setBackground(Color.BLACK);
		jseparator.setForeground(Color.BLACK);
		jseparator.setPreferredSize(new Dimension((int)this.getPreferredSize().getHeight(),
				(int)jseparator.getPreferredSize().getHeight()));
		jseparator.setBounds(10, 5+bottoneFollows.getY()+(int)bottoneFollows.getPreferredSize().getHeight(), 
				(int)this.getPreferredSize().getWidth()-13, (int)jseparator.getPreferredSize().getHeight());
		add(jseparator);

		pannelloDelProfilo = new HashMap<>();
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
	
	public void prendiPreferiti()
	{
		bottoniFumettiPreferiti = new ArrayList<>();
		
		rimuoviComponentiPrecedenti();
		bottonePreferiti.setForeground(Color.DARK_GRAY);
		if (pannelloDelProfilo.get("Preferiti") == null)
		{
			Iterator it = lettore.getPreferiti().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
		        try
		        {
		        	bottoniFumettiPreferiti.add(new BottoneFumettoProfilo(panel, getURL(lettore.getPreferiti().get(pairs.getKey()).getUrl(),200,300), lettore.getPreferiti().get(pairs.getKey()), 2));
		        } catch (IOException e1)
				{
					e1.printStackTrace();
				}
		    }

			pannelloDelProfilo.put("Preferiti", new PannelloScrollPane(new PannelloBottoniProfilo(this.larghezza+100), 5, SFONDO));
			((PannelloBottoniProfilo) pannelloDelProfilo.get("Preferiti").getPanel()).setPreferiti_DaLeggere(bottoniFumettiPreferiti);
			
			pannelloDelProfilo.get("Preferiti").setBounds(0, 5 + jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), 
					(int)pannelloDelProfilo.get("Preferiti").getPreferredSize().getWidth(), 
					(int)pannelloDelProfilo.get("Preferiti").getPreferredSize().getHeight());
		}
		add(pannelloDelProfilo.get("Preferiti"));
		this.validate();
		repaint();
	}
	
	public void prendiFollows() throws SQLException, IOException
	{
		listaBottoniFollowCorrente = new ArrayList<>();

		rimuoviComponentiPrecedenti();
		bottoneFollows.setForeground(Color.DARK_GRAY);
		if (pannelloDelProfilo.get("Follow") == null)
		{
			Iterator it = lettore.getFollows().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
	
	        	BottoneFollow bottoneFollowCorrente = new BottoneFollow(lettore.getFollows().get(pairs.getKey()), larghezza, 1, this.lettore, 150, panel);
				
				listaBottoniFollowCorrente.add(bottoneFollowCorrente);
		    }
		    
			pannelloDelProfilo.put("Follow", new PannelloScrollPane(new PannelloBottoniProfilo(this.larghezza+100), 5, SFONDO));
		    ((PannelloBottoniProfilo) pannelloDelProfilo.get("Follow").getPanel()).setFollow_Follower(listaBottoniFollowCorrente);
			
			pannelloDelProfilo.get("Follow").setBounds(0, 5 + jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), 
					(int)pannelloDelProfilo.get("Follow").getPreferredSize().getWidth(), 
					(int)pannelloDelProfilo.get("Follow").getPreferredSize().getHeight());
		}
		add(pannelloDelProfilo.get("Follow"));
		this.validate();
		repaint();	
		
	}
	
	public void prendiFollowers() throws SQLException, IOException
	{
		listaBottoniFollowerCorrente = new ArrayList<>();
		
		rimuoviComponentiPrecedenti();
		bottoneFollower.setForeground(Color.DARK_GRAY);
		if (pannelloDelProfilo.get("Follower") == null)
		{
			Iterator it = lettore.getFollower().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
	
	        	BottoneFollow bottoneFollowerCorrente = new BottoneFollow(lettore.getFollower().get(pairs.getKey()), larghezza, 0, this.lettore, 150, panel);
	        	listaBottoniFollowerCorrente.add(bottoneFollowerCorrente);
		    }
	
			pannelloDelProfilo.put("Follower", new PannelloScrollPane(new PannelloBottoniProfilo(this.larghezza+100), 5, SFONDO));
			((PannelloBottoniProfilo) pannelloDelProfilo.get("Follower").getPanel()).setFollow_Follower(listaBottoniFollowerCorrente);
			
			pannelloDelProfilo.get("Follower").setBounds(0, 5 + jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), 
					(int)pannelloDelProfilo.get("Follower").getPreferredSize().getWidth(), 
					(int)pannelloDelProfilo.get("Follower").getPreferredSize().getHeight());
		}
		add(pannelloDelProfilo.get("Follower"));
		this.validate();
		repaint();	
	}
	
	public void prendiDaLeggere()
	{
		bottoniDaLeggere = new ArrayList<>();

		rimuoviComponentiPrecedenti();
		bottoneDaLeggere.setForeground(Color.DARK_GRAY);
		if (pannelloDelProfilo.get("DaLeggere") == null)
		{
			Iterator it = lettore.getDaLeggere().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
		        try
		        {
		        	bottoniDaLeggere.add(new BottoneFumettoProfilo(panel, getURL(lettore.getDaLeggere().get(pairs.getKey()).getUrl(),200,300), lettore.getDaLeggere().get(pairs.getKey()), 0));
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
		    }
		    
			pannelloDelProfilo.put("DaLeggere", new PannelloScrollPane(new PannelloBottoniProfilo(this.larghezza+100), 5, SFONDO));
			((PannelloBottoniProfilo) pannelloDelProfilo.get("DaLeggere").getPanel()).setPreferiti_DaLeggere(bottoniDaLeggere);
			
			pannelloDelProfilo.get("DaLeggere").setBounds(0, 5 + jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), 
					(int)pannelloDelProfilo.get("DaLeggere").getPreferredSize().getWidth(), 
					(int)pannelloDelProfilo.get("DaLeggere").getPreferredSize().getHeight());
		}
		add(pannelloDelProfilo.get("DaLeggere"));
		this.validate();
		repaint();
	    
	}
	
	public void prendiCronologia()
	{
		bottoniCronologia = new ArrayList<>();

		rimuoviComponentiPrecedenti();
		bottoneCronologia.setForeground(Color.DARK_GRAY);
		
		if (pannelloDelProfilo.get("Cronologia") == null)
		{
			Iterator it = lettore.getCronologia().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
	        	try
				{
					bottoniCronologia.add(new BottoneCronologia(getURL(lettore.getCronologia().get(pairs.getKey()).getUrl(),200, 300), 
							lettore.getCronologia().get(pairs.getKey()), 
							120, 150, (int)this.getPreferredSize().getWidth(), panel));
				} catch (IOException e)
				{
					e.printStackTrace();
				}
		    }
		
			pannelloDelProfilo.put("Cronologia", new PannelloScrollPane(new PannelloBottoniProfilo(this.larghezza+100), 5, SFONDO));
			((PannelloBottoniProfilo) pannelloDelProfilo.get("Cronologia").getPanel()).setCronologia(bottoniCronologia);
			
			pannelloDelProfilo.get("Cronologia").setBounds(0, 5 + jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), 
					(int)pannelloDelProfilo.get("Cronologia").getPreferredSize().getWidth(), 
					(int)pannelloDelProfilo.get("Cronologia").getPreferredSize().getHeight());
		}
		add(pannelloDelProfilo.get("Cronologia"));
		this.validate();
		repaint();
		
	}
	
	private void rimuoviComponentiPrecedenti()
	{
		bottonePreferiti.setPremuto();
		bottoneFollows.setPremuto();
		bottoneFollower.setPremuto();
		bottoneDaLeggere.setPremuto();
		bottoneCronologia.setPremuto();
		
		if (pannelloDelProfilo.get("Preferiti") != null)
			remove(pannelloDelProfilo.get("Preferiti"));
		if (pannelloDelProfilo.get("DaLeggere") != null)
			remove(pannelloDelProfilo.get("DaLeggere"));
		if (pannelloDelProfilo.get("Cronologia") != null)
			remove(pannelloDelProfilo.get("Cronologia"));
		if (pannelloDelProfilo.get("Follower") != null)
			remove(pannelloDelProfilo.get("Follower"));
		if (pannelloDelProfilo.get("Follow") != null)
			remove(pannelloDelProfilo.get("Follow"));
	}
	
	private class MyListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			if (source == bottoneFollows)   //BOTTONE FOLLOWS
			{
				aggiungiFollows = true;
				try
				{
					prendiFollows();
				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			else if (source == bottoneFollower)   //BOTTONE FOLLOWER
			{
				aggiungiFollowers = true;
				try
				{
					prendiFollowers();
				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (source == bottonePreferiti)   //BOTTONE PREFERITI
			{
				aggiungi = true;
				prendiPreferiti();
			}
			else if (source == bottoneDaLeggere)   //BOTTONE DA LEGGERE
			{
				aggiungiDaLeggere = true;
				prendiDaLeggere();
			}
			else if (source == bottoneCronologia)   //BOTTONE CRONOLOGIA
			{
				aggiungiCronologia = true;
				prendiCronologia();
			}
			repaint();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		if (aggiungi)
			for (BottoneFumettoProfilo bottone : bottoniFumettiPreferiti)
				g.drawImage(bottone.getImageScaled(), 0,0, this);
		if (aggiungiFollows)
			for (BottoneFollow bottone : listaBottoniFollowCorrente)
				g.drawImage(bottone.getScaledImage(), 0, 0, this);
		if (aggiungiFollowers)
			for (BottoneFollow bottone : listaBottoniFollowerCorrente)
				g.drawImage(bottone.getScaledImage(), 0, 0, this);
		if (aggiungiDaLeggere)
			for (BottoneFumettoProfilo bottone : bottoniDaLeggere)
				g.drawImage(bottone.getImageScaled(), 0,0, this);
		super.paintComponent(g);
	}
	
}
