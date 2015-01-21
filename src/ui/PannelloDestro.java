package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.AppManager;
import domain.Fumetto;
import domain.Lettore;


public class PannelloDestro extends JPanel
{
	int larghezza = ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3) - 50 ;
	
	private Lettore lettore;	
	
	private ArrayList<JButton> copertineCronologiaFollow;
	
	public PannelloDestro()
	{
		super();
		this.setBackground(new Color(91,84,84));
		this.setPreferredSize(new Dimension(larghezza, 0));
		
		lettore = AppManager.getLettore();
		
		copertineCronologiaFollow = new ArrayList<>();
		
		Iterator it = lettore.getFollows().entrySet().iterator();
	    while (it.hasNext())
	    {
	       Map.Entry pairs = (Map.Entry)it.next();
	       Fumetto fumettoPrimo = lettore.getPrimoCronologia(lettore.getFollows().get(pairs.getKey()));
	       if (fumettoPrimo != null)
	       {
	    	   copertineCronologiaFollow.add(new JButton(new ImageIcon(fumettoPrimo.getCopertina())));
	       }
	        
	    }
	    
	    for (int i = 0; i < copertineCronologiaFollow.size(); i++)
		{
			copertineCronologiaFollow.get(i).setBounds(10, 10, 
					(int)copertineCronologiaFollow.get(i).getPreferredSize().getWidth(), 
					(int)copertineCronologiaFollow.get(i).getPreferredSize().getHeight());
			
			add(copertineCronologiaFollow.get(i));
		}
		
	}
	
	@Override
	public void paintComponents(Graphics g) 
	{
		super.paintComponents(g);
	}
}
