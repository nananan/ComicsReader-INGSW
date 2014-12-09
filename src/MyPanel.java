import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class MyPanel extends JPanel
{
	MyButton button = new MyButton("Discover");
	MyButton buttonTopRead = new MyButton("Top Read");
	MyButton buttonTopRated = new MyButton("Top Rated Comics");
	MyButton buttonUtentsRated = new MyButton("Utents Rated");
	MyButton buttonFavorites = new MyButton("Favorites Comics");
	MyButton buttonToRead = new MyButton("To Read");
	MyButton buttonChronology = new MyButton("Chronology");
	MyButton buttonOffline = new MyButton("Offline");
	
	JPanel pannelloDestro = new JPanel();
	JPanel pannelloSinistro = new JPanel();
	JPanel pannelloSotto = new JPanel();
	JPanel pannelloSopra = new JPanel();
	
	
	public MyPanel() 
	{
		super();
		this.setBackground(new Color(137,130,130));
		
		pannelloSopra.setBackground(new Color(91,84,84));
		pannelloSopra.setPreferredSize(new Dimension(10, 30));
		pannelloSopra.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new BorderLayout());
		
		pannelloSotto.setBackground(new Color(91,84,84));
		pannelloSotto.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 50));
		pannelloSotto.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new BorderLayout());
		pannelloSotto.setLayout(null);
		
		pannelloDestro.setBackground(new Color(91,84,84));
		pannelloDestro.setPreferredSize(new Dimension(200, 0));
		pannelloDestro.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new BorderLayout());
		
		pannelloSinistro.setBackground(new Color(91,84,84));
		pannelloSinistro.setPreferredSize(new Dimension(180, 0));
		pannelloSinistro.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new BorderLayout());
		pannelloSinistro.setLayout(null);
		
		this.add(pannelloDestro,BorderLayout.EAST);
		this.add(pannelloSinistro,BorderLayout.WEST);
		this.add(pannelloSotto, BorderLayout.SOUTH);
		this.add(pannelloSopra, BorderLayout.NORTH);
		
		button.setDimension(pannelloSinistro, 200);
		pannelloSinistro.add(button);
		
		buttonTopRead.setDimension(pannelloSinistro, button.getY()+25);
		pannelloSinistro.add(buttonTopRead);
		  
		buttonTopRated.setDimension(pannelloSinistro, buttonTopRead.getY()+25);
		pannelloSinistro.add(buttonTopRated);

		buttonUtentsRated.setDimension(pannelloSinistro, buttonTopRated.getY()+25);
		pannelloSinistro.add(buttonUtentsRated);

		buttonFavorites.setDimension(pannelloSinistro, buttonUtentsRated.getY()+25);
		pannelloSinistro.add(buttonFavorites);
		
		buttonToRead.setDimension(pannelloSinistro, buttonFavorites.getY()+25);
		pannelloSinistro.add(buttonToRead);
		
		buttonChronology.setDimension(pannelloSinistro, buttonToRead.getY()+25);
		pannelloSinistro.add(buttonChronology);

		
		buttonOffline.setPreferredSize(new Dimension(100,20));
		int X = (int)pannelloSotto.getPreferredSize().getWidth() - (int)buttonOffline.getPreferredSize().getWidth();
		int Y = ((int)pannelloSotto.getPreferredSize().getHeight()/2) - ((int)buttonOffline.getPreferredSize().getHeight()/2);
		buttonOffline.setBounds(X, Y, 100,20);
		pannelloSotto.add(buttonOffline);
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
	}
}
