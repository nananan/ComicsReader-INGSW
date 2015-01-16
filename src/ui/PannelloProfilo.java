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
	
	private ArrayList<BottoneFumettoProfilo> bottoniFumettiPreferiti;
	private ArrayList<BottoneFumettoProfilo> bottoniDaLeggere;
	private ArrayList<BottoneFumetto> bottoniCronologia;

	private ArrayList<Lettore> followDelLettoreCorrente;
	private ArrayList<BottoneFollow> listaBottoniFollowCorrente;
	private ArrayList<Lettore> followerDelLettoreCorrente;
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
	
	public PannelloProfilo(Lettore lettore, MyPanel panel, int larghezza)
	{
		super();
		setBackground(Color.GRAY);
		setBorder(BorderFactory.createLineBorder(Color.black,3));
		setPreferredSize(new Dimension(larghezza, (int)this.getPreferredSize().getHeight()));
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
		
		jseparator = new JSeparator(JSeparator.HORIZONTAL);
		jseparator.setBackground(Color.BLACK);
		jseparator.setForeground(Color.BLACK);
		jseparator.setBounds(0, bottoneFollows.getY()+(int)bottoneFollows.getPreferredSize().getHeight(), (int)jseparator.getPreferredSize().getWidth(), 12);
		add(jseparator);
		
		bottoniFumettiPreferiti = new ArrayList<>();
		bottoniDaLeggere = new ArrayList<>();
		bottoniCronologia = new ArrayList<>();
		
		followDelLettoreCorrente = new ArrayList<>();
		listaBottoniFollowCorrente = new ArrayList<>();
		followerDelLettoreCorrente = new ArrayList<>();
		listaBottoniFollowerCorrente = new ArrayList<>();
		
		
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
		for (BottoneFumettoProfilo bottoneFumettoDaLeggere : bottoniDaLeggere)
		{
			if (bottoneFumettoDaLeggere != null)
				remove(bottoneFumettoDaLeggere);
		}
		
		for (int i = 0; i < listaBottoniFollowCorrente.size(); i++)
		{
			if (listaBottoniFollowCorrente.get(i) != null)
				remove(listaBottoniFollowCorrente.get(i));
		}
		
		for (int i = 0; i < listaBottoniFollowerCorrente.size(); i++)
		{
			if (listaBottoniFollowerCorrente.get(i) != null)
				remove(listaBottoniFollowerCorrente.get(i));
		}
		
		if (bottoniFumettiPreferiti.size() == 0)
		{
			Iterator it = lettore.getPreferiti().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
		        try
		        {
		        	bottoniFumettiPreferiti.add(new BottoneFumettoProfilo(panel, getURL(lettore.getPreferiti().get(pairs.getKey()).getUrl(),200,300), lettore.getPreferiti().get(pairs.getKey()), 0, 2, lettore));
		        } catch (IOException e1)
				{
					e1.printStackTrace();
				}
		    }
		}
		    
	    for (int i = 0; i < bottoniFumettiPreferiti.size(); i++)
	    {
			if (i == 0)
				bottoniFumettiPreferiti.get(i).setBounds(20, 20+ jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniFumettiPreferiti.get(i).getPreferredSize().getWidth(), (int)bottoniFumettiPreferiti.get(i).getPreferredSize().getHeight());
			else
				bottoniFumettiPreferiti.get(i).setBounds(15 + bottoniFumettiPreferiti.get(i-1).getX() + (int)bottoniFumettiPreferiti.get(i-1).getPreferredSize().getWidth(), 20 + jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniFumettiPreferiti.get(i).getPreferredSize().getWidth(), (int)bottoniFumettiPreferiti.get(i).getPreferredSize().getHeight());

			bottoniFumettiPreferiti.get(i).addActionListener(listener);
			
			add(bottoniFumettiPreferiti.get(i));
	    }
		repaint();
	}
	
	public void prendiFollows() throws SQLException, IOException
	{
		for (BottoneFumettoProfilo bottoneFumetto : bottoniFumettiPreferiti)
		{
			if (bottoneFumetto != null)
				remove(bottoneFumetto);
		}
		
		for (BottoneFumettoProfilo bottoneFumettoDaLeggere : bottoniDaLeggere)
		{
			if (bottoneFumettoDaLeggere != null)
				remove(bottoneFumettoDaLeggere);
		}
		
		for (int i = 0; i < listaBottoniFollowerCorrente.size(); i++)
		{
			if (listaBottoniFollowerCorrente.get(i) != null)
				remove(listaBottoniFollowerCorrente.get(i));
		}
		
		if (followDelLettoreCorrente.size() == 0)
		{
			Iterator it = lettore.getFollows().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
	
	        	followDelLettoreCorrente.add(lettore.getFollows().get(pairs.getKey()));
		    }
		
			for (int i = 0; i < followDelLettoreCorrente.size(); i++)
			{
				BottoneFollow bottoneFollowCorrente = new BottoneFollow(followDelLettoreCorrente.get(i), larghezza, 1, this.lettore);
				
				listaBottoniFollowCorrente.add(bottoneFollowCorrente);
				
				if (i == 0)
					bottoneFollowCorrente.setBounds(5 + jseparator.getX(), jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)listaBottoniFollowCorrente.get(i).getPreferredSize().getWidth(), (int)listaBottoniFollowCorrente.get(i).getPreferredSize().getHeight());
				else
					bottoneFollowCorrente.setBounds(listaBottoniFollowCorrente.get(i-1).getX(), 5+listaBottoniFollowCorrente.get(i-1).getY() + (int)listaBottoniFollowCorrente.get(i-1).getPreferredSize().getHeight(), (int)listaBottoniFollowCorrente.get(i).getPreferredSize().getWidth(), (int)listaBottoniFollowCorrente.get(i).getPreferredSize().getHeight());
				
				add(listaBottoniFollowCorrente.get(i));
			}
		}
		else
		{
			for (int i = 0; i < followDelLettoreCorrente.size(); i++)
				add(listaBottoniFollowCorrente.get(i));
		}
		repaint();
	}
	
	public void prendiFollowers() throws SQLException, IOException
	{
		for (BottoneFumettoProfilo bottoneFumetto : bottoniFumettiPreferiti)
		{
			if (bottoneFumetto != null)
				remove(bottoneFumetto);
		}
		
		for (BottoneFumettoProfilo bottoneFumettoDaLeggere : bottoniDaLeggere)
		{
			if (bottoneFumettoDaLeggere != null)
				remove(bottoneFumettoDaLeggere);
		}
		
		for (int i = 0; i < listaBottoniFollowCorrente.size(); i++)
		{
			if (listaBottoniFollowCorrente.get(i) != null)
				remove(listaBottoniFollowCorrente.get(i));
		}
		
		if (followerDelLettoreCorrente.size() == 0)
		{
			Iterator it = lettore.getFollower().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
	
	        	followerDelLettoreCorrente.add(lettore.getFollower().get(pairs.getKey()));
		    }
	
			for (int i = 0; i < followerDelLettoreCorrente.size(); i++)
			{
				BottoneFollow bottoneFollowerCorrente = new BottoneFollow(followerDelLettoreCorrente.get(i), larghezza, 0, this.lettore);
				
				listaBottoniFollowerCorrente.add(bottoneFollowerCorrente);
				
				if (i == 0)
					bottoneFollowerCorrente.setBounds(5 + jseparator.getX(), jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)listaBottoniFollowerCorrente.get(i).getPreferredSize().getWidth(), (int)listaBottoniFollowerCorrente.get(i).getPreferredSize().getHeight());
				else
					bottoneFollowerCorrente.setBounds(listaBottoniFollowerCorrente.get(i-1).getX(), 5+listaBottoniFollowerCorrente.get(i-1).getY() + (int)listaBottoniFollowerCorrente.get(i-1).getPreferredSize().getHeight(), (int)listaBottoniFollowerCorrente.get(i).getPreferredSize().getWidth(), (int)listaBottoniFollowerCorrente.get(i).getPreferredSize().getHeight());
				
				add(listaBottoniFollowerCorrente.get(i));
			}
		}
		else
		{
			for (int i = 0; i < followerDelLettoreCorrente.size(); i++)
				add(listaBottoniFollowerCorrente.get(i));
		}
		repaint();
	}
	
	public void prendiDaLeggere()
	{
		for (BottoneFumettoProfilo bottoneFumetto : bottoniFumettiPreferiti)
		{
			if (bottoneFumetto != null)
				remove(bottoneFumetto);
		}
		
		for (int i = 0; i < listaBottoniFollowCorrente.size(); i++)
		{
			if (listaBottoniFollowCorrente.get(i) != null)
				remove(listaBottoniFollowCorrente.get(i));
		}
		
		for (int i = 0; i < listaBottoniFollowerCorrente.size(); i++)
		{
			if (listaBottoniFollowerCorrente.get(i) != null)
				remove(listaBottoniFollowerCorrente.get(i));
		}
		
		if (bottoniDaLeggere.size() == 0)
		{
			Iterator it = lettore.getDaLeggere().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
		        try
		        {
		        	bottoniDaLeggere.add(new BottoneFumettoProfilo(panel, getURL(lettore.getDaLeggere().get(pairs.getKey()).getUrl(),200,300), lettore.getDaLeggere().get(pairs.getKey()), 2, 0, lettore));
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
		    }
		}
		    
	    for (int i = 0; i < bottoniDaLeggere.size(); i++)
	    {
			if (i == 0)
				bottoniDaLeggere.get(i).setBounds(20, 20+ jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniDaLeggere.get(i).getPreferredSize().getWidth(), (int)bottoniDaLeggere.get(i).getPreferredSize().getHeight());
			else
				bottoniDaLeggere.get(i).setBounds(15 + bottoniDaLeggere.get(i-1).getX() + (int)bottoniDaLeggere.get(i-1).getPreferredSize().getWidth(), 20 + jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniDaLeggere.get(i).getPreferredSize().getWidth(), (int)bottoniDaLeggere.get(i).getPreferredSize().getHeight());
			
			bottoniDaLeggere.get(i).addActionListener(listener);
			
			add(bottoniDaLeggere.get(i));
	    }
		repaint();
	}
	
	public void prendiCronologia() throws SQLException
	{
		for (BottoneFumettoProfilo bottoneFumetto : bottoniFumettiPreferiti)
		{
			if (bottoneFumetto != null)
				remove(bottoneFumetto);
		}
		
		for (int i = 0; i < listaBottoniFollowCorrente.size(); i++)
		{
			if (listaBottoniFollowCorrente.get(i) != null)
				remove(listaBottoniFollowCorrente.get(i));
		}
		
		for (int i = 0; i < listaBottoniFollowerCorrente.size(); i++)
		{
			if (listaBottoniFollowerCorrente.get(i) != null)
				remove(listaBottoniFollowerCorrente.get(i));
		}
		
		if (bottoniCronologia.size() == 0)
		{
			Iterator it = lettore.getCronologia().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
	        	try
				{
					bottoniCronologia.add(new BottoneFumetto(getURL(lettore.getCronologia().get(pairs.getKey()).getUrl(),120, 150), lettore.getCronologia().get(pairs.getKey()), 120, 150));
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
		    
	    for (int i = 0; i < bottoniCronologia.size(); i++)
	    {
	    	bottoniCronologia.get(i).setPreferredSize(new Dimension(120,150));
	    	
	    	if (i == 0)
			{
				bottoniCronologia.get(i).setBounds(20, 20+jseparator.getY()+(int)jseparator.getPreferredSize().getHeight(), (int)bottoniCronologia.get(i).getPreferredSize().getWidth(), (int)bottoniCronologia.get(i).getPreferredSize().getHeight());
				
			}
			else
				bottoniCronologia.get(i).setBounds(20, 20 + bottoniCronologia.get(i-1).getY() + (int)bottoniCronologia.get(i-1).getPreferredSize().getHeight(), (int)bottoniCronologia.get(i).getPreferredSize().getWidth(), (int)bottoniCronologia.get(i).getPreferredSize().getHeight());
			
			bottoniCronologia.get(i).addActionListener(listener);
			
			add(bottoniCronologia.get(i));
	    }
		repaint();
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
				bottoneDaLeggere.setForeground(Color.WHITE);

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
				bottonePreferiti.setForeground(Color.WHITE);
				bottoneFollows.setForeground(Color.WHITE);
				bottoneDaLeggere.setForeground(Color.WHITE);
				bottoneFollower.setForeground(Color.DARK_GRAY);
				
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
				bottonePreferiti.setForeground(Color.DARK_GRAY);
				bottoneFollows.setForeground(Color.WHITE);
				bottoneFollower.setForeground(Color.WHITE);
				bottoneDaLeggere.setForeground(Color.WHITE);
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
				aggiungiCronologia = true;
				bottonePreferiti.setForeground(Color.WHITE);
				bottoneFollows.setForeground(Color.WHITE);
				bottoneFollower.setForeground(Color.WHITE);
				bottoneDaLeggere.setForeground(Color.WHITE);
				bottoneCronologia.setForeground(Color.DARK_GRAY);
				try
				{
					prendiCronologia();
				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			for (BottoneFumettoProfilo bottoneFumetto : bottoniFumettiPreferiti)
			{
				if (source == bottoneFumetto)
				{
					panel.PremiPerFumetto(bottoneFumetto.getFumetto(), bottoneFumetto.getImageScaled());
	
				}
			}
			for (BottoneFumetto bottoneFumetto : bottoniCronologia)
			{
				if (source == bottoneFumetto)
				{
						panel.PremiPerFumetto(bottoneFumetto.getFumetto(), bottoneFumetto.getImageScaled());
			
				}
			}
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
				g.drawImage(bottone.getImage(), 0, 0, this);
		if (aggiungiFollowers)
			for (BottoneFollow bottone : listaBottoniFollowerCorrente)
				g.drawImage(bottone.getImage(), 0, 0, this);
		if (aggiungiDaLeggere)
			for (BottoneFumettoProfilo bottone : bottoniDaLeggere)
				g.drawImage(bottone.getImageScaled(), 0,0, this);
		if (aggiungiCronologia)
			for (BottoneFumetto bottone : bottoniCronologia)
				g.drawImage(bottone.getImageScaled(), 0,0, this);
		super.paintComponent(g);
	}
	
}
