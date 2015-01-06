package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.xml.crypto.Data;

import technicalService.DataBase;


public class PannelloSinistra extends JPanel
{
	MyButton button = new MyButton("Discover");
	MyButton buttonTopRead = new MyButton("Top Read");
	MyButton buttonTopRated = new MyButton("Top Rated Comics");
	MyButton buttonUtentsRated = new MyButton("Utents Rated");
	MyButton buttonFavorites = new MyButton("Favorites Comics");
	MyButton buttonToRead = new MyButton("To Read");
	MyButton buttonChronology = new MyButton("Chronology");	
	MyButton buttonFiltra = new MyButton("Filtra");
	
	MyButton buttonManga = new MyButton("Manga");
	MyButton buttonFumetti = new MyButton("Fumetti");
	
	boolean filtraggio = false;
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 6;
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	PannelloCentrale pannelloCentrale = null;
	
	public PannelloSinistra(final PannelloCentrale pannelloCentro, final MyPanel panel, final JPanel pannelloFiltraggio) 
	{
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension(larghezza, 0));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		pannelloCentrale = pannelloCentro;
		
		button.setDimension(this, pannelloCentro, altezza/4);
		add(button);
		
		buttonTopRead.setDimension(this, pannelloCentro, button.getY()+25);
		add(buttonTopRead);
		  
		buttonTopRated.setDimension(this, pannelloCentro, buttonTopRead.getY()+25);
		add(buttonTopRated);

		buttonUtentsRated.setDimension(this, pannelloCentro, buttonTopRated.getY()+25);
		add(buttonUtentsRated);

		buttonFavorites.setDimension(this, pannelloCentro, buttonUtentsRated.getY()+25);
		add(buttonFavorites);
		
		buttonToRead.setDimension(this, pannelloCentro, buttonFavorites.getY()+25);
		add(buttonToRead);
		
		buttonChronology.setDimension(this, pannelloCentro, buttonToRead.getY()+25);
		add(buttonChronology);
		
		buttonFiltra.setDimension(this, pannelloCentro, buttonChronology.getY()+25);
		add(buttonFiltra);
		
		
//		final PannelloFiltraggio pannelloFiltraggio = new PannelloFiltraggio(pannelloCentro, panel);
		
		buttonManga.setDimension(this, pannelloCentro, buttonFiltra.getY()+30);
		buttonFumetti.setDimension(this, pannelloCentro, buttonManga.getY()+25);

		buttonFiltra.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				filtraggio = true;
				panel.Premi();
			}
		 });
		
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				super.mouseReleased(e);
				try {
					try {
						DataBase.connect();
						//TODO VEDERE PERCHE IL MOUSELISTENER DISCONNETTE IL DATABASE
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					panel.PremiPerDiscover();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		 });
	}
	
	public boolean getFiltraggio()
	{
		return filtraggio;
	}
	
	public void setFiltraggio(boolean filtraggio)
	{
		this.filtraggio = filtraggio;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		g.drawImage(image, 0,0, this);
	}
	
}
