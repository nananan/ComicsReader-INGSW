package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.xml.crypto.Data;

import technicalService.DataBase;


public class PannelloSinistra extends JPanel
{
	MyPanel panel;
	
	MyButton buttonDiscover = new MyButton("Scopri");
	MyButton buttonTopRead = new MyButton("Top Read");
	MyButton buttonTopRated = new MyButton("Top Rated Comics");
	MyButton buttonUtentsRated = new MyButton("Utents Rated");
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 6;
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	PannelloCentrale pannelloCentrale = null;
	
	public PannelloSinistra(final PannelloCentrale pannelloCentro, final MyPanel panel, final JPanel pannelloFiltraggio) 
	{
		super();
		this.panel = panel;
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension(larghezza, 0));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		pannelloCentrale = pannelloCentro;
		
		buttonDiscover.setBounds(5, 10, (int)buttonDiscover.getPreferredSize().getWidth(), (int)buttonDiscover.getPreferredSize().getHeight());
		add(buttonDiscover);
		
		buttonTopRead.setBounds(buttonDiscover.getX(), 3+buttonDiscover.getY()+(int)buttonDiscover.getPreferredSize().getHeight(), (int)buttonTopRead.getPreferredSize().getWidth(), (int)buttonTopRead.getPreferredSize().getHeight());
		add(buttonTopRead);
		  
		buttonTopRated.setBounds(buttonDiscover.getX(), 3+buttonTopRead.getY()+(int)buttonTopRead.getPreferredSize().getHeight(), (int)buttonTopRated.getPreferredSize().getWidth(), (int)buttonTopRated.getPreferredSize().getHeight());
		add(buttonTopRated);

		buttonUtentsRated.setBounds(buttonDiscover.getX(), 3+buttonTopRated.getY()+(int)buttonTopRated.getPreferredSize().getHeight(), (int)buttonUtentsRated.getPreferredSize().getWidth(), (int)buttonUtentsRated.getPreferredSize().getHeight());
		add(buttonUtentsRated);
		
		MyListener listener = new MyListener();
		
		buttonDiscover.addActionListener(listener);
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
			
			try {
				DataBase.connect();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			if (source == buttonDiscover)
			{
				try {
					panel.PremiPerDiscover();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
}
