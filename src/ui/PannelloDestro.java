package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import domain.AppManager;
import domain.Lettore;


public class PannelloDestro extends JPanel
{
	int larghezza = ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3) - 50 ;
	
	Lettore lettore;	
	
	public PannelloDestro()
	{
		super();
		this.setBackground(new Color(91,84,84));
		this.setPreferredSize(new Dimension(larghezza, 0));
		
		lettore = AppManager.getLettore();
		
		Iterator it = lettore.getFollows().entrySet().iterator();
	    while (it.hasNext())
	    {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(	lettore.getFollows().get(pairs.getKey()));
	        Iterator iter = lettore.getFollows().get(pairs.getKey()).getCronologia().entrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pair = (Map.Entry)it.next();
		        if (lettore.getCronologia().get(pair.getKey()) != null)
		        	System.out.println(lettore.getCronologia().get(pair.getKey()));
		    }
	        
	    }
		
	}
	
	@Override
	public void paintComponents(Graphics g) 
	{
		super.paintComponents(g);
	}
}
