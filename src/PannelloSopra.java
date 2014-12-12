import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class PannelloSopra extends JPanel
{
	MyButton buttonOffline = new MyButton("Offline");
	
	public PannelloSopra(JPanel pannelloCentro) 
	{
		
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 30));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		buttonOffline.setPreferredSize(new Dimension(100,20));
		int X = (int)getPreferredSize().getWidth() - (int)buttonOffline.getPreferredSize().getWidth();
		int Y = ((int)getPreferredSize().getHeight()/2) - ((int)buttonOffline.getPreferredSize().getHeight()/2);
				
		buttonOffline.setBounds(X - pannelloCentro.getInsets().bottom, Y, (int)buttonOffline.getPreferredSize().getWidth()-pannelloCentro.getInsets().bottom,20);
		add(buttonOffline);
		
		buttonOffline.addMouseListener(new MouseAdapter() 
		 {
			public void mouseClicked(MouseEvent e)
			{
				System.out.println("premo");
				repaint();
			}
			//public void 
		 });
		 
	}
	
	@Override
	public void paintComponents(Graphics g) 
	{
		super.paintComponents(g);
	}
}
