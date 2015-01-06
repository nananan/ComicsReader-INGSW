package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class PannelloProfilo extends JPanel
{
	Text nomeAccount;
	MyButton bottoneFollows;
	MyButton bottoneFollower;
	MyButton bottonePreferiti;
	MyButton bottoneDaLeggere;
	MyButton bottoneCronologia;
	private JSeparator jseparator;
	
	public PannelloProfilo(String name, PannelloCentrale pannelloCentrale)
	{
		super();
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		MyListener listener = new MyListener();
		
		//TODO modificare nomeUtente quando mettiamo l'immagine
		nomeAccount = new Text(name+"Nome Utente", 35, Color.WHITE);
		nomeAccount.setBounds(30, 10, (int)nomeAccount.getPreferredSize().getWidth(), (int)nomeAccount.getPreferredSize().getHeight());
		add(nomeAccount);
		
		bottoneFollows = new MyButton("Follows", 20, Color.WHITE);
		bottoneFollows.setBounds(nomeAccount.getX(), 20+nomeAccount.getY()+(int)nomeAccount.getPreferredSize().getWidth(), (int)bottoneFollows.getPreferredSize().getWidth(),(int)bottoneFollows.getPreferredSize().getHeight());
		add(bottoneFollows);
		bottoneFollows.addActionListener(listener);
		
		bottoneFollower = new MyButton("Follower", 20, Color.WHITE);
		bottoneFollower.setBounds(25+bottoneFollows.getX()+(int)bottoneFollows.getPreferredSize().getWidth(), 20+nomeAccount.getY()+(int)nomeAccount.getPreferredSize().getWidth(), (int)bottoneFollower.getPreferredSize().getWidth(),(int)bottoneFollower.getPreferredSize().getHeight());
		add(bottoneFollower);
		bottoneFollower.addActionListener(listener);
		
		bottonePreferiti = new MyButton("Preferiti", 20, Color.WHITE);
		bottonePreferiti.setBounds(25+bottoneFollower.getX()+(int)bottoneFollower.getPreferredSize().getWidth(), 20+nomeAccount.getY()+(int)nomeAccount.getPreferredSize().getWidth(), (int)bottonePreferiti.getPreferredSize().getWidth(),(int)bottonePreferiti.getPreferredSize().getHeight());
		add(bottonePreferiti);
		bottonePreferiti.addActionListener(listener);
		
		bottoneDaLeggere = new MyButton("Da Leggere", 20, Color.WHITE);
		bottoneDaLeggere.setBounds(25+bottonePreferiti.getX()+(int)bottonePreferiti.getPreferredSize().getWidth(), 20+nomeAccount.getY()+(int)nomeAccount.getPreferredSize().getWidth(), (int)bottoneDaLeggere.getPreferredSize().getWidth(),(int)bottoneDaLeggere.getPreferredSize().getHeight());
		add(bottoneDaLeggere);
		bottoneDaLeggere.addActionListener(listener);
		
		bottoneCronologia = new MyButton("Cronologia", 20, Color.WHITE);
		bottoneCronologia.setBounds(25+bottoneDaLeggere.getX()+(int)bottoneDaLeggere.getPreferredSize().getWidth(), 20+nomeAccount.getY()+(int)nomeAccount.getPreferredSize().getWidth(), (int)bottoneCronologia.getPreferredSize().getWidth(),(int)bottoneCronologia.getPreferredSize().getHeight());
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
//		g.setColor(Color.GREEN);
//		g.drawLine(100, 100, 400, 100);
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
