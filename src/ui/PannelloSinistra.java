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
	
	MyButton buttonDiscover = new MyButton("Discover");
	MyButton buttonTopRead = new MyButton("Top Read");
	MyButton buttonTopRated = new MyButton("Top Rated Comics");
	MyButton buttonUtentsRated = new MyButton("Utents Rated");
	MyButton buttonFiltra = new MyButton("Filtra");
	
	MyButton buttonManga = new MyButton("Manga");
	MyButton buttonFumetti = new MyButton("Fumetti");
	
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
		
		buttonDiscover.setDimension(this, pannelloCentro, altezza/4);
		add(buttonDiscover);
		
		buttonTopRead.setDimension(this, pannelloCentro, buttonDiscover.getY()+25);
		add(buttonTopRead);
		  
		buttonTopRated.setDimension(this, pannelloCentro, buttonTopRead.getY()+25);
		add(buttonTopRated);

		buttonUtentsRated.setDimension(this, pannelloCentro, buttonTopRated.getY()+25);
		add(buttonUtentsRated);
		
//		buttonFiltra.setDimension(this, pannelloCentro, buttonChronology.getY()+25);
//		add(buttonFiltra);
		
		buttonManga.setDimension(this, pannelloCentro, buttonFiltra.getY()+30);
		buttonFumetti.setDimension(this, pannelloCentro, buttonManga.getY()+25);

		MyListener listener = new MyListener();
		
		buttonFiltra.addActionListener(listener);
		
		buttonDiscover.addActionListener(listener);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		g.drawImage(image, 0,0, this);
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
			else if (source == buttonFiltra)
			{
				panel.PremiPerFiltraggio();
			}
		}

	}
	
}
