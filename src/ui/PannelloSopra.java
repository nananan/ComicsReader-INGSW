package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.AccessoLettore;
import domain.Lettore;

public class PannelloSopra extends JMenuBar implements ActionListener, ItemListener
{
	private JMenu buttonOptions;
	private JMenuItem logOut = new JMenuItem("Log out");
	private JMenuItem privateSession = new JMenuItem("Sessione Privata");
	private JMenuItem offlineSession = new JMenuItem("Offline");
	private JMenuItem profilo;
	
	private JTextField textFiltraggio;
	private MyButton buttonFiltra;
	private MyPanel panel;
	
	private JButton bottoneCerca;
	
	private static final String[] stringheFiltri = { "Artista", "Autore", "Fumetto" };

	private JComboBox boxFiltri;
	
	public PannelloSopra(JPanel pannelloCentro, final MyPanel panel, Lettore lettore, PannelloDestro pannelloDestro) 
	{
		this.panel = panel;
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 30));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		buttonOptions = new JMenu(lettore.getNome());
		buttonOptions.setFont(new Font("Caladea", Font.BOLD, 13));
		buttonOptions.setForeground(Color.WHITE);
		buttonOptions.setPreferredSize(new Dimension((int)pannelloDestro.getPreferredSize().getWidth()-pannelloDestro.getInsets().bottom,20));
		int X = (int)getPreferredSize().getWidth() - (int)buttonOptions.getPreferredSize().getWidth();
		int Y = ((int)getPreferredSize().getHeight()/2) - ((int)buttonOptions.getPreferredSize().getHeight()/2);
		
		buttonOptions.setBounds(X - pannelloCentro.getInsets().bottom, Y, (int)pannelloDestro.getPreferredSize().getWidth()-pannelloDestro.getInsets().bottom,20);
		
		profilo = new JMenuItem(lettore.getNome());
		
		impostaButtonJMenu(profilo, pannelloCentro);
		impostaButtonJMenu(logOut, pannelloCentro);
		impostaButtonJMenu(privateSession, pannelloCentro);
		impostaButtonJMenu(offlineSession, pannelloCentro);
		
		buttonOptions.setLayout(null);
		add(buttonOptions);

		buttonOptions.add(profilo);
		buttonOptions.add(privateSession);
		buttonOptions.add(offlineSession);
		buttonOptions.add(logOut);
		
		logOut.setActionCommand("ESC");
		privateSession.setActionCommand("PRIVATE");
		offlineSession.setActionCommand("OFFLINE");
		profilo.setActionCommand("PROFILO");
		
		buttonOptions.getItem(0).addActionListener(this);
		buttonOptions.getItem(1).addActionListener(this);
		buttonOptions.getItem(2).addActionListener(this);
		buttonOptions.getItem(3).addActionListener(this);
		
		buttonFiltra = new MyButton("Filtra");
		buttonFiltra.setBounds(-7, 2, (int)buttonFiltra.getPreferredSize().getWidth(), (int)buttonFiltra.getPreferredSize().getHeight());
		add(buttonFiltra);
		buttonFiltra.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if (buttonFiltra.isEnabled())
					panel.PremiPerFiltraggio();
			}
		});
		
		boxFiltri = new JComboBox(stringheFiltri);
		boxFiltri.setSelectedIndex(2);
		boxFiltri.addActionListener(this);
		boxFiltri.setPreferredSize(new Dimension(150, 20));
		boxFiltri.setBounds(5+buttonFiltra.getX()+(int)buttonFiltra.getPreferredSize().getWidth(), 5, (int)boxFiltri.getPreferredSize().getWidth()+10, (int)boxFiltri.getPreferredSize().getHeight());
		boxFiltri.setBackground(Color.GRAY);
		boxFiltri.setFocusable(false);
		boxFiltri.setFocusCycleRoot(false);
		add(boxFiltri);
		
		textFiltraggio= new JTextField();
		textFiltraggio.setPreferredSize(new Dimension(150, 20));
		textFiltraggio.setBounds(20+boxFiltri.getX()+(int)boxFiltri.getPreferredSize().getWidth(), 5, (int)textFiltraggio.getPreferredSize().getWidth(), (int)textFiltraggio.getPreferredSize().getHeight());
		add(textFiltraggio);
		
		ImageIcon imageCerca = new ImageIcon("image/search.png");
		
		bottoneCerca = new JButton();
		bottoneCerca.setPreferredSize(new Dimension((int)this.getPreferredSize().getHeight()-this.getInsets().bottom*8, (int)this.getPreferredSize().getHeight()-this.getInsets().bottom*9));
		Image imageScaled = imageCerca.getImage().getScaledInstance((int)bottoneCerca.getPreferredSize().getWidth(), (int)bottoneCerca.getPreferredSize().getHeight(), Image.SCALE_AREA_AVERAGING);
		imageCerca.setImage(imageScaled);
		bottoneCerca.setIcon(imageCerca);
		bottoneCerca.setPressedIcon(imageCerca);
		bottoneCerca.setBorderPainted(false);
		bottoneCerca.setFocusPainted(false);
		bottoneCerca.setContentAreaFilled(false);
		bottoneCerca.setBackground(this.getBackground());
		bottoneCerca.setBounds(5+textFiltraggio.getX()+(int)textFiltraggio.getPreferredSize().getWidth(), this.getInsets().bottom*5, (int)bottoneCerca.getPreferredSize().getWidth(), (int)bottoneCerca.getPreferredSize().getHeight());
		add(bottoneCerca);
		
	}
	
	public void setBooleanaPerBottoneFiltro(boolean booleana)
	{
		buttonFiltra.setEnabled(booleana);
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
		else if (e.getActionCommand().equals(profilo.getActionCommand()))
		{
			panel.PremiPerProfiloUtente();
		}		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}
}
