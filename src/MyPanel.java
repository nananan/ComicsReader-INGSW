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
	PannelloCentrale pannelloCentro = new PannelloCentrale();
	PannelloSotto pannelloSotto = new PannelloSotto();
	PannelloSopra pannelloSopra = new PannelloSopra(pannelloCentro, this);
	PannelloDestro pannelloDestro = new PannelloDestro();
	PannelloFiltraggio pannelloFiltraggio = new PannelloFiltraggio(pannelloCentro, this);
	PannelloSinistra pannelloSinistro = new PannelloSinistra(pannelloCentro, this, pannelloFiltraggio);
	
	private Image image = null;
	
	public MyPanel() 
	{
		super();
//		this.setBackground(new Color(137,130,130));
		this.setLayout(new BorderLayout());
		
//		this.add(pannelloFiltraggio,BorderLayout.WEST);
		
		this.add(pannelloDestro,BorderLayout.EAST);
		this.add(pannelloSinistro,BorderLayout.WEST);
		this.add(pannelloSotto, BorderLayout.SOUTH);
		this.add(pannelloSopra, BorderLayout.NORTH);
		this.add(pannelloCentro, BorderLayout.CENTER);
		
		pannelloSotto.setVisible(false);
//		pannelloFiltraggio.setVisible(false);
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

	public void Premi()
	{
		pannelloSinistro.setVisible(false);
		this.add(pannelloFiltraggio,BorderLayout.WEST);
		pannelloFiltraggio.setVisible(true);
		repaint();
	}
	
	public void PremiPerPannelloSinistro()
	{
		pannelloFiltraggio.setVisible(false);
		this.add(pannelloSinistro,BorderLayout.WEST);
		pannelloSinistro.setVisible(true);
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		pannelloCentro.getGraphics().drawImage(image, 0,0, pannelloCentro);
//		System.out.println("ciao");
		
	}
}
