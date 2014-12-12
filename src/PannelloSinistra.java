import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class PannelloSinistra extends JPanel
{
	MyButton button = new MyButton("Discover");
	MyButton buttonTopRead = new MyButton("Top Read");
	MyButton buttonTopRated = new MyButton("Top Rated Comics");
	MyButton buttonUtentsRated = new MyButton("Utents Rated");
	MyButton buttonFavorites = new MyButton("Favorites Comics");
	MyButton buttonToRead = new MyButton("To Read");
	MyButton buttonChronology = new MyButton("Chronology");	
	
	public PannelloSinistra(JPanel pannelloCentro) 
	{
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension(180, 0));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		button.setDimension(this, pannelloCentro, 200);
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
		
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		g.drawImage(image, 0,0, this);
	}
	
}
