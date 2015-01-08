package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

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
	
	private Lettore lettore;
	private ImageIcon imageFumetto;
	private Image scaledImage;
	private File file;
	private JLabel forImage;
	
	public PannelloProfilo(Lettore lettore, PannelloCentrale pannelloCentrale)
	{
		super();
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		MyListener listener = new MyListener();
		
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
		nome.setBounds(30 + forImage.getX() + (int)forImage.getPreferredSize().getWidth(), 10, (int)nome.getPreferredSize().getWidth(), (int)nome.getPreferredSize().getHeight());
		add(nome);
		nomeAccount = new Text(lettore.getNome(), 35, Color.WHITE);
		nomeAccount.setBounds(30 + forImage.getX() + (int)forImage.getPreferredSize().getWidth(), 10 + nome.getY() + (int)nome.getPreferredSize().getHeight(), (int)nomeAccount.getPreferredSize().getWidth(), (int)nomeAccount.getPreferredSize().getHeight());
		add(nomeAccount);
		
		bottoneFollows = new MyButton("Follows", 20, Color.WHITE);
		bottoneFollows.setBounds(forImage.getX(), 10+forImage.getY()+(int)forImage.getPreferredSize().getHeight(), (int)bottoneFollows.getPreferredSize().getWidth(),(int)bottoneFollows.getPreferredSize().getHeight());
		add(bottoneFollows);
		bottoneFollows.addActionListener(listener);
		
		bottoneFollower = new MyButton("Follower", 20, Color.WHITE);
		bottoneFollower.setBounds(25+bottoneFollows.getX()+(int)bottoneFollows.getPreferredSize().getWidth(), 10 + forImage.getY() + (int)forImage.getPreferredSize().getHeight(), (int)bottoneFollower.getPreferredSize().getWidth(),(int)bottoneFollower.getPreferredSize().getHeight());
		add(bottoneFollower);
		bottoneFollower.addActionListener(listener);
		
		bottonePreferiti = new MyButton("Preferiti", 20, Color.WHITE);
		bottonePreferiti.setBounds(25+bottoneFollower.getX()+(int)bottoneFollower.getPreferredSize().getWidth(), 10 + forImage.getY() + (int)forImage.getPreferredSize().getHeight(), (int)bottonePreferiti.getPreferredSize().getWidth(),(int)bottonePreferiti.getPreferredSize().getHeight());
		add(bottonePreferiti);
		bottonePreferiti.addActionListener(listener);
		
		bottoneDaLeggere = new MyButton("Da Leggere", 20, Color.WHITE);
		bottoneDaLeggere.setBounds(25+bottonePreferiti.getX()+(int)bottonePreferiti.getPreferredSize().getWidth(), 10 + forImage.getY() + (int)forImage.getPreferredSize().getHeight(), (int)bottoneDaLeggere.getPreferredSize().getWidth(),(int)bottoneDaLeggere.getPreferredSize().getHeight());
		add(bottoneDaLeggere);
		bottoneDaLeggere.addActionListener(listener);
		
		bottoneCronologia = new MyButton("Cronologia", 20, Color.WHITE);
		bottoneCronologia.setBounds(25+bottoneDaLeggere.getX()+(int)bottoneDaLeggere.getPreferredSize().getWidth(), 10 + forImage.getY() + (int)forImage.getPreferredSize().getHeight(), (int)bottoneCronologia.getPreferredSize().getWidth(),(int)bottoneCronologia.getPreferredSize().getHeight());
		add(bottoneCronologia);
		bottoneCronologia.addActionListener(listener);
		
		jseparator = (new JSeparator(JSeparator.HORIZONTAL));
		jseparator.setBackground(Color.BLACK);
		jseparator.setForeground(Color.BLACK);
		jseparator.setBounds(0, bottoneFollows.getY()+(int)bottoneFollows.getPreferredSize().getHeight(), (int)getPreferredSize().getWidth(), 12);
		add(jseparator);
	}
	
	@Override
	public void paintComponents(Graphics g)
	{
		super.paintComponents(g);
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
	
	private class MyListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			if (source == bottoneFollows)   //BOTTONE FOLLOWS
			{
				
			}
			else if (source == bottoneFollower)   //BOTTONE FOLLOWER
			{
				
			}
			else if (source == bottonePreferiti)   //BOTTONE PREFERITI
			{
				
			}
			else if (source == bottoneDaLeggere)   //BOTTONE DA LEGGERE
			{
				
			}
			else if (source == bottoneCronologia)   //BOTTONE CRONOLOGIA
			{
				
			}
		}
		
	}
}
