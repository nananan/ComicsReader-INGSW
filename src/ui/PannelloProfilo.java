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
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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

import technicalService.DataBase;
import domain.Lettore;

public class PannelloProfilo extends JPanel
{
	Text nomeAccount;
	MyButton bottoneFollows;
	MyButton bottoneFollower;
	MyButton bottonePreferiti;
	MyButton bottoneDaLeggere;
	MyButton bottoneCronologia;
	private BottoneUtente bottoneSegui;
	private JSeparator jseparator;
	
	private ArrayList<BottoneFumetto> bottoniFumettiPreferiti;
	private ArrayList<BottoneUtente> bottoniFollows;
	private ArrayList<BottoneUtente> bottoniFollowers;
	private ArrayList<BottoneFumetto> bottoniDaLeggere;
	private ArrayList<Text> nomiFollower;
	private ArrayList<Text> numeroFollowsDelFollower;
	private ArrayList<Text> numeroFollowerDelFollower;
	private ArrayList<Text> nomiFollows;
	private ArrayList<Text> numeroFollowsDelFollows;
	private ArrayList<Text> numeroFollowerDelFollows;

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
	
	public PannelloProfilo(Lettore lettore, MyPanel panel)
	{
		super();
		setBackground(Color.GRAY);
//		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		this.panel = panel;
		
		listener = new MyListener();
		
		try
		{
			DataBase.connect();
		} catch (ClassNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		
		bottoneFollows = new MyButton("Follows("+lettore.getNumFollow()+")", 20, Color.WHITE);
		bottoneFollows.setBounds(forImage.getX(), 15+forImage.getY()+(int)forImage.getPreferredSize().getHeight(), (int)bottoneFollows.getPreferredSize().getWidth(),(int)bottoneFollows.getPreferredSize().getHeight());
		add(bottoneFollows);
		bottoneFollows.addActionListener(listener);
		
		bottoneFollower = new MyButton("Follower("+lettore.getNumFollower()+")", 20, Color.WHITE);
		bottoneFollower.setBounds(25+bottoneFollows.getX()+(int)bottoneFollows.getPreferredSize().getWidth(), bottoneFollows.getY(), (int)bottoneFollower.getPreferredSize().getWidth(),(int)bottoneFollower.getPreferredSize().getHeight());
		add(bottoneFollower);
		bottoneFollower.addActionListener(listener);
		
		bottonePreferiti = new MyButton("Preferiti", 20, Color.WHITE);
		bottonePreferiti.setBounds(25+bottoneFollower.getX()+(int)bottoneFollower.getPreferredSize().getWidth(), bottoneFollows.getY(), (int)bottonePreferiti.getPreferredSize().getWidth(),(int)bottonePreferiti.getPreferredSize().getHeight());
		add(bottonePreferiti);
		bottonePreferiti.addActionListener(listener);
		
		bottoneDaLeggere = new MyButton("Da Leggere", 20, Color.WHITE);
		bottoneDaLeggere.setBounds(25+bottonePreferiti.getX()+(int)bottonePreferiti.getPreferredSize().getWidth(), bottoneFollows.getY(), (int)bottoneDaLeggere.getPreferredSize().getWidth(),(int)bottoneDaLeggere.getPreferredSize().getHeight());
		add(bottoneDaLeggere);
		bottoneDaLeggere.addActionListener(listener);
		
		bottoneCronologia = new MyButton("Cronologia", 20, Color.WHITE);
		bottoneCronologia.setBounds(25+bottoneDaLeggere.getX()+(int)bottoneDaLeggere.getPreferredSize().getWidth(), bottoneFollows.getY(),(int)bottoneCronologia.getPreferredSize().getWidth(),(int)bottoneCronologia.getPreferredSize().getHeight());
		add(bottoneCronologia);
		bottoneCronologia.addActionListener(listener);
		
		jseparator = (new JSeparator(JSeparator.HORIZONTAL));
		jseparator.setBackground(Color.BLACK);
		jseparator.setForeground(Color.BLACK);
		jseparator.setBounds(0, bottoneFollows.getY()+(int)bottoneFollows.getPreferredSize().getHeight(), (int)jseparator.getPreferredSize().getWidth(), 12);
		add(jseparator);
		
		bottoniFumettiPreferiti = new ArrayList<>();
		bottoniFollows = new ArrayList<>();
		bottoniFollowers = new ArrayList<>();
		bottoniDaLeggere = new ArrayList<>();
		
		nomiFollower = new ArrayList<>();
		nomiFollows = new ArrayList<>();
		
		numeroFollowerDelFollower = new ArrayList<>();
		numeroFollowsDelFollower = new ArrayList<>();
		numeroFollowsDelFollows = new ArrayList<>();
		numeroFollowerDelFollows = new ArrayList<>();
		
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
		for (BottoneUtente bottoneFumetto : bottoniFollows)
		{
			if (bottoneFumetto != null)
				remove(bottoneFumetto);
		}
		
		for (BottoneUtente bottoneFumetto : bottoniFollowers)
		{
			if (bottoneFumetto != null)
				remove(bottoneFumetto);
		}
		
		if (bottoniFumettiPreferiti.size() == 0)
		{
			Iterator it = lettore.getPreferiti().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
		        try
		        {
		        	bottoniFumettiPreferiti.add(new BottoneFumetto(getURL(lettore.getPreferiti().get(pairs.getKey()).getUrl(),200,300), lettore.getPreferiti().get(pairs.getKey())));
		        } catch (IOException e1)
				{
					e1.printStackTrace();
				}
		    }
		}
		    
	    for (int i = 0; i < bottoniFumettiPreferiti.size(); i++)
	    {
			bottoniFumettiPreferiti.get(i).setPreferredSize(new Dimension(200,300));
			bottoniFumettiPreferiti.get(i).setBorder(BorderFactory.createLineBorder(Color.black,3));
			if (i == 0)
				bottoniFumettiPreferiti.get(i).setBounds(20, 20+ jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniFumettiPreferiti.get(i).getPreferredSize().getWidth(), (int)bottoniFumettiPreferiti.get(i).getPreferredSize().getHeight());
			else
				bottoniFumettiPreferiti.get(i).setBounds(15 + bottoniFumettiPreferiti.get(i-1).getX() + (int)bottoniFumettiPreferiti.get(i-1).getPreferredSize().getWidth(), 20 + jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniFumettiPreferiti.get(i).getPreferredSize().getWidth(), (int)bottoniFumettiPreferiti.get(i).getPreferredSize().getHeight());

			bottoniFumettiPreferiti.get(i).addActionListener(listener);
			
			add(bottoniFumettiPreferiti.get(i));
	    }
		repaint();
	}
	
	public void prendiFollows() throws SQLException
	{
		for (BottoneUtente bottone : bottoniFollowers)
		{
			if (bottone != null)
				remove(bottone);
		}
		
		for (BottoneFumetto bottoneFumetto : bottoniFumettiPreferiti)
		{
			if (bottoneFumetto != null)
				remove(bottoneFumetto);
		}
		
		if (bottoniFollows.size() == 0)
		{
			Iterator it = lettore.getFollows().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
		        try
		        {
		        	bottoniFollows.add(new BottoneUtente(getURL(lettore.getFollows().get(pairs.getKey()).getUrlFoto(),150,150), lettore.getFollows().get(pairs.getKey())));
		        	nomiFollows.add(new Text(lettore.getFollows().get(pairs.getKey()).getNome(), 22, Color.WHITE));
		        	numeroFollowsDelFollows.add(new Text(new Integer(lettore.getFollows().get(pairs.getKey()).getNumeroFollowAltroLettore()).toString()+" Follows", 18, Color.WHITE));
		        	numeroFollowerDelFollows.add(new Text(new Integer(lettore.getFollows().get(pairs.getKey()).getNumeroFollowerAltroLettore()).toString()+" Follower", 18, Color.WHITE));
		        } catch (IOException e1)
				{
					e1.printStackTrace();
				}
		    }
		}
		    
	    for (int i = 0; i < bottoniFollows.size(); i++)
	    {
			bottoniFollows.get(i).setPreferredSize(new Dimension(150,150));
			bottoniFollows.get(i).setBorder(BorderFactory.createLineBorder(Color.black,3));
			if (i == 0)
				bottoniFollows.get(i).setBounds(20, 20+ jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniFollows.get(i).getPreferredSize().getWidth(), (int)bottoniFollows.get(i).getPreferredSize().getHeight());
			else
				bottoniFollows.get(i).setBounds(bottoniFollows.get(i-1).getX(), 20 + bottoniFollows.get(i-1).getY() + (int)bottoniFollows.get(i-1).getPreferredSize().getHeight(), (int)bottoniFollows.get(i).getPreferredSize().getWidth(), (int)bottoniFollows.get(i).getPreferredSize().getHeight());

			nomiFollows.get(i).setBounds(20 + bottoniFollows.get(i).getX() + (int)bottoniFollows.get(i).getPreferredSize().getWidth(), bottoniFollows.get(i).getY() + 10, (int)nomiFollows.get(i).getPreferredSize().getWidth(), (int)nomiFollows.get(i).getPreferredSize().getHeight() );
			numeroFollowsDelFollows.get(i).setBounds(20 + bottoniFollows.get(i).getX() + (int)bottoniFollows.get(i).getPreferredSize().getWidth(), 5 + nomiFollows.get(i).getY()+(int)nomiFollows.get(i).getPreferredSize().getHeight(), (int)numeroFollowsDelFollows.get(i).getPreferredSize().getWidth(), (int)numeroFollowsDelFollows.get(i).getPreferredSize().getHeight() );
			numeroFollowerDelFollows.get(i).setBounds(20 + bottoniFollows.get(i).getX() + (int)bottoniFollows.get(i).getPreferredSize().getWidth(), 5 + numeroFollowsDelFollows.get(i).getY()+(int)numeroFollowsDelFollows.get(i).getPreferredSize().getHeight(), (int)numeroFollowerDelFollows.get(i).getPreferredSize().getWidth(), (int)numeroFollowerDelFollows.get(i).getPreferredSize().getHeight() );
			
			add(bottoniFollows.get(i));
			add(nomiFollows.get(i));
			add(numeroFollowsDelFollows.get(i));
			add(numeroFollowerDelFollows.get(i));
			caricaBottonePerSeguire(bottoniFollows.get(i).getY(), bottoniFollows.get(i).getLettore());
	    }
	    
		repaint();
	}
	
	public void prendiFollowers() throws SQLException
	{
		for (BottoneUtente bottone : bottoniFollows)
		{
			if (bottone != null)
				remove(bottone);
		}
		
		for (BottoneFumetto bottoneFumetto : bottoniFumettiPreferiti)
		{
			if (bottoneFumetto != null)
				remove(bottoneFumetto);
		}
		
		if (bottoniFollowers.size() == 0)
		{
			Iterator it = lettore.getFollower().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
		        try
		        {
		        	bottoniFollowers.add(new BottoneUtente(getURL(lettore.getFollower().get(pairs.getKey()).getUrlFoto(),150,150), lettore.getFollower().get(pairs.getKey())));
		        	nomiFollower.add(new Text(lettore.getFollower().get(pairs.getKey()).getNome(), 22, Color.WHITE));
		        	numeroFollowsDelFollower.add(new Text(new Integer(lettore.getFollower().get(pairs.getKey()).getNumeroFollowAltroLettore()).toString()+" Follows", 18, Color.WHITE));
		        	numeroFollowerDelFollower.add(new Text(new Integer(lettore.getFollower().get(pairs.getKey()).getNumeroFollowerAltroLettore()).toString()+" Follower", 18, Color.WHITE));
		        } catch (IOException e1)
				{
					e1.printStackTrace();
				}
		    }
		}
		    
	    for (int i = 0; i < bottoniFollowers.size(); i++)
	    {
			bottoniFollowers.get(i).setPreferredSize(new Dimension(150,150));
			bottoniFollowers.get(i).setBorder(BorderFactory.createLineBorder(Color.black,3));
			if (i == 0)
				bottoniFollowers.get(i).setBounds(20, 20+ jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniFollowers.get(i).getPreferredSize().getWidth(), (int)bottoniFollowers.get(i).getPreferredSize().getHeight());
			else
				bottoniFollowers.get(i).setBounds(bottoniFollowers.get(i-1).getX(), 20 + bottoniFollowers.get(i-1).getY() + (int)bottoniFollowers.get(i-1).getPreferredSize().getHeight(), (int)bottoniFollowers.get(i).getPreferredSize().getWidth(), (int)bottoniFollowers.get(i).getPreferredSize().getHeight());
			
			nomiFollower.get(i).setBounds(20 + bottoniFollowers.get(i).getX() + (int)bottoniFollowers.get(i).getPreferredSize().getWidth(), bottoniFollowers.get(i).getY() + 10, (int)nomiFollower.get(i).getPreferredSize().getWidth(), (int)nomiFollower.get(i).getPreferredSize().getHeight() );
			numeroFollowsDelFollower.get(i).setBounds(20 + bottoniFollowers.get(i).getX() + (int)bottoniFollowers.get(i).getPreferredSize().getWidth(), 5 + nomiFollower.get(i).getY()+(int)nomiFollower.get(i).getPreferredSize().getHeight(), (int)numeroFollowsDelFollower.get(i).getPreferredSize().getWidth(), (int)numeroFollowsDelFollower.get(i).getPreferredSize().getHeight() );
			numeroFollowerDelFollower.get(i).setBounds(20 + bottoniFollowers.get(i).getX() + (int)bottoniFollowers.get(i).getPreferredSize().getWidth(), 5 + numeroFollowsDelFollower.get(i).getY()+(int)numeroFollowsDelFollower.get(i).getPreferredSize().getHeight(), (int)numeroFollowerDelFollower.get(i).getPreferredSize().getWidth(), (int)numeroFollowerDelFollower.get(i).getPreferredSize().getHeight() );
			add(bottoniFollowers.get(i));
			add(nomiFollower.get(i));
			add(numeroFollowsDelFollower.get(i));
			add(numeroFollowerDelFollower.get(i));

	    }
	    
		repaint();
	}
	
	public void prendiDaLeggere()
{
		for (BottoneUtente bottone : bottoniFollows)
		{
			if (bottone != null)
				remove(bottone);
		}
		
		for (BottoneUtente bottone : bottoniFollowers)
		{
			if (bottone != null)
				remove(bottone);
		}
		
		for (BottoneFumetto bottoneFumetto : bottoniFumettiPreferiti)
		{
			if (bottoneFumetto != null)
				remove(bottoneFumetto);
		}
		
		if (bottoniDaLeggere.size() == 0)
		{
			Iterator it = lettore.getPreferiti().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
		        try
		        {
		        	bottoniDaLeggere.add(new BottoneFumetto(getURL(lettore.getDaLeggere().get(pairs.getKey()).getUrl(),200,300), lettore.getDaLeggere().get(pairs.getKey())));
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
		    }
		}
		    
	    for (int i = 0; i < bottoniDaLeggere.size(); i++)
	    {
			bottoniDaLeggere.get(i).setPreferredSize(new Dimension(200,300));
			bottoniDaLeggere.get(i).setBorder(BorderFactory.createLineBorder(Color.black,3));
			if (i == 0)
				bottoniDaLeggere.get(i).setBounds(20, 20+ jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniDaLeggere.get(i).getPreferredSize().getWidth(), (int)bottoniDaLeggere.get(i).getPreferredSize().getHeight());
			else
				bottoniDaLeggere.get(i).setBounds(15 + bottoniDaLeggere.get(i-1).getX() + (int)bottoniDaLeggere.get(i-1).getPreferredSize().getWidth(), 20 + jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniDaLeggere.get(i).getPreferredSize().getWidth(), (int)bottoniDaLeggere.get(i).getPreferredSize().getHeight());
			
			bottoniDaLeggere.get(i).addActionListener(listener);
			
			add(bottoniDaLeggere.get(i));
	    }
		repaint();
	}
	
	public void caricaBottonePerSeguire(int Y, Lettore lettore)
	{		
		bottoneSegui = new BottoneUtente("Following        ", 20, Color.WHITE, lettore);
		
		bottoneSegui.setBounds(bottoneCronologia.getX(), Y+20, (int)bottoneSegui.getPreferredSize().getWidth(), (int)bottoneSegui.getPreferredSize().getHeight());
	
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
				bottonePreferiti.setForeground(Color.WHITE);
				bottoneFollows.setForeground(Color.DARK_GRAY);
				bottoneFollower.setForeground(Color.WHITE);
				try
				{
					prendiFollows();
				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (source == bottoneFollower)   //BOTTONE FOLLOWER
			{
				aggiungiFollowers = true;
				bottonePreferiti.setForeground(Color.WHITE);
				bottoneFollows.setForeground(Color.WHITE);
				bottoneFollower.setForeground(Color.DARK_GRAY);
				try
				{
					prendiFollowers();
				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (source == bottonePreferiti)   //BOTTONE PREFERITI
			{
				aggiungi = true;
				bottonePreferiti.setForeground(Color.DARK_GRAY);
				bottoneFollows.setForeground(Color.WHITE);
				bottoneFollower.setForeground(Color.WHITE);
				prendiPreferiti();
			}
			else if (source == bottoneDaLeggere)   //BOTTONE DA LEGGERE
			{
				aggiungiDaLeggere = true;
				bottonePreferiti.setForeground(Color.WHITE);
				bottoneFollows.setForeground(Color.WHITE);
				bottoneFollower.setForeground(Color.WHITE);
				bottoneDaLeggere.setForeground(Color.DARK_GRAY);
				prendiDaLeggere();
			}
			else if (source == bottoneCronologia)   //BOTTONE CRONOLOGIA
			{
				
			}
			
			for (BottoneFumetto bottoneFumetto : bottoniFumettiPreferiti)
			{
				if (source == bottoneFumetto)
				{
					try
					{
						panel.PremiPerFumetto(bottoneFumetto.getFumetto());
					} catch (MalformedURLException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		if (aggiungi)
			for (BottoneFumetto bottone : bottoniFumettiPreferiti)
				g.drawImage(bottone.getImageScaled(), 0,0, this);
		if (aggiungiFollows)
			for (BottoneUtente bottone : bottoniFollows)
				g.drawImage(bottone.getImageScaled(), 0,0, this);
		if (aggiungiFollowers)
			for (BottoneUtente bottone : bottoniFollowers)
				g.drawImage(bottone.getImageScaled(), 0,0, this);
		if (aggiungiDaLeggere)
			for (BottoneFumetto bottone : bottoniDaLeggere)
				g.drawImage(bottone.getImageScaled(), 0,0, this);
		super.paintComponent(g);
	}
	
}
