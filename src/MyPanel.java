import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;


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
	JPanel pannelloSopra = new JPanel();
	
	PannelloCentrale pannelloCentro = new PannelloCentrale();
	PannelloSotto pannelloSotto = new PannelloSotto();
	private Image image = null;
	
	public MyPanel() 
	{
		super();
//		this.setBackground(new Color(137,130,130));
		this.setLayout(new BorderLayout());
		
		pannelloSopra.setBackground(new Color(91,84,84));
		pannelloSopra.setPreferredSize(new Dimension(10, 30));
		pannelloSopra.setBorder(BorderFactory.createLineBorder(Color.black,1));
		
		pannelloDestro.setBackground(new Color(91,84,84));
		pannelloDestro.setPreferredSize(new Dimension(200, 0));
		pannelloDestro.setBorder(BorderFactory.createLineBorder(Color.black,1));
		
		pannelloSinistro.setBackground(new Color(91,84,84));
		pannelloSinistro.setPreferredSize(new Dimension(180, 0));
		pannelloSinistro.setBorder(BorderFactory.createLineBorder(Color.black,1));
		pannelloSinistro.setLayout(null);
		
		this.add(pannelloDestro,BorderLayout.EAST);
		this.add(pannelloSinistro,BorderLayout.WEST);
		this.add(pannelloSotto, BorderLayout.SOUTH);
		this.add(pannelloSopra, BorderLayout.NORTH);
		this.add(pannelloCentro, BorderLayout.CENTER);
		
		button.setDimension(pannelloSinistro, pannelloCentro, 200);
		pannelloSinistro.add(button);
		
		buttonTopRead.setDimension(pannelloSinistro, pannelloCentro, button.getY()+25);
		pannelloSinistro.add(buttonTopRead);
		  
		buttonTopRated.setDimension(pannelloSinistro, pannelloCentro, buttonTopRead.getY()+25);
		pannelloSinistro.add(buttonTopRated);

		buttonUtentsRated.setDimension(pannelloSinistro, pannelloCentro, buttonTopRated.getY()+25);
		pannelloSinistro.add(buttonUtentsRated);

		buttonFavorites.setDimension(pannelloSinistro, pannelloCentro, buttonUtentsRated.getY()+25);
		pannelloSinistro.add(buttonFavorites);
		
		buttonToRead.setDimension(pannelloSinistro, pannelloCentro, buttonFavorites.getY()+25);
		pannelloSinistro.add(buttonToRead);
		
		buttonChronology.setDimension(pannelloSinistro, pannelloCentro, buttonToRead.getY()+25);
		pannelloSinistro.add(buttonChronology);

		buttonOffline.setPreferredSize(new Dimension(100,20));
		int X = (int)pannelloSotto.getPreferredSize().getWidth() - (int)buttonOffline.getPreferredSize().getWidth();
		int Y = ((int)pannelloSotto.getPreferredSize().getHeight()/2) - ((int)buttonOffline.getPreferredSize().getHeight()/2);
		
		System.out.println(X -pannelloCentro.getInsets().bottom);
		
		buttonOffline.setBounds(X - pannelloCentro.getInsets().bottom, Y, (int)buttonOffline.getPreferredSize().getWidth()-pannelloCentro.getInsets().bottom,20);
		pannelloSotto.add(buttonOffline);
				
//		JMenu menu = new JMenu("ciao");
//		
//		JMenuBar menuBar = new JMenuBar();
//		
//		menuBar.add(menu);
//		
//		menuBar.setBounds(0, 0, 10, 30);
//		this.add(menuBar);
		
		
//		setVisible(true);
//		updateUI();
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		pannelloCentro.getGraphics().drawImage(image, 0,0, pannelloCentro);
//		System.out.println("ciao");
		
	}
}
