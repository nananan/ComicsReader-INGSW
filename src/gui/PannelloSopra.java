package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class PannelloSopra extends JMenuBar implements ActionListener, ItemListener
{
	private JMenu buttonOptions = null;
	private JMenuItem logOut = new JMenuItem("Log out");
	private JMenuItem privateSession = new JMenuItem("Sessione Privata");
	private JMenuItem offlineSession = new JMenuItem("Offline");
	MyPanel panel;
	boolean put = false;
	
	
	public PannelloSopra(JPanel pannelloCentro, final MyPanel panel, String name, PannelloDestro pannelloDestro) 
	{
		this.panel = panel;
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 30));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		buttonOptions = new JMenu(name);
		buttonOptions.setFont(new Font("Caladea", Font.BOLD, 13));
		buttonOptions.setForeground(Color.WHITE);
		buttonOptions.setPreferredSize(new Dimension((int)pannelloDestro.getPreferredSize().getWidth()-pannelloDestro.getInsets().bottom,20));
		int X = (int)getPreferredSize().getWidth() - (int)buttonOptions.getPreferredSize().getWidth();
		int Y = ((int)getPreferredSize().getHeight()/2) - ((int)buttonOptions.getPreferredSize().getHeight()/2);
		
//		buttonOptions.setHorizontalAlignment(SwingConstants.CENTER);
		
		buttonOptions.setBounds(X - pannelloCentro.getInsets().bottom, Y, (int)pannelloDestro.getPreferredSize().getWidth()-pannelloDestro.getInsets().bottom,20);
		
		impostaButtonJMenu(logOut, pannelloCentro);
		impostaButtonJMenu(privateSession, pannelloCentro);
		impostaButtonJMenu(offlineSession, pannelloCentro);
		
		buttonOptions.setLayout(null);
		add(buttonOptions);

		buttonOptions.add(privateSession);
		buttonOptions.add(offlineSession);
		buttonOptions.add(logOut);
		
		logOut.setActionCommand("ESC");
		privateSession.setActionCommand("PRIVATE");
		offlineSession.setActionCommand("OFFLINE");
		
		buttonOptions.getItem(0).addActionListener(this);
		buttonOptions.getItem(1).addActionListener(this);
		buttonOptions.getItem(2).addActionListener(this);
		
		
	}
	
	private void impostaButtonJMenu(JMenuItem button, JPanel pannelloCentro)
	{
		button.setBackground(new Color(91,84,84));
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Caladea", Font.BOLD, 13));
		button.setContentAreaFilled(false);
		button.setPreferredSize(new Dimension((int) buttonOptions.getPreferredSize().getWidth()- pannelloCentro.getInsets().bottom*2, 20));
	}
	
	@Override
	public void paintComponents(Graphics g) 
	{
		super.paintComponents(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(logOut.getActionCommand()))
		{
			System.out.println("bye");
			System.exit(0);
		}
		else if (e.getActionCommand().equals(privateSession.getActionCommand()))
		{
			System.out.println("SESSIONE PRIVATA");
		}
		else if (e.getActionCommand().equals(offlineSession.getActionCommand()))
		{
			System.out.println("SESSIONE OFFLINE");
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		
	}
}
