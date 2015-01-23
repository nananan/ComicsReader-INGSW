
package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSeparator;

public class PannelloBottoniProfilo extends JPanel
{
	private ArrayList<JSeparator> listaJSeparator;

	public PannelloBottoniProfilo(int larghezza)
	{
		super();
		this.setBackground(Color.GRAY);
		this.setPreferredSize(new Dimension(larghezza,0));
		this.setLayout(null);

		listaJSeparator = new ArrayList<>();
		
	}
	
	public void setPreferiti_DaLeggere(ArrayList<BottoneFumettoProfilo> bottoni)
	{
		int larghezza = 0;
		for (int i = 0; i < bottoni.size(); i++)
		{
			bottoni.get(i).setLayout(null);
			
			if (i == 0)
			{
				bottoni.get(i).setBounds(30, 5, 
						(int)bottoni.get(i).getPreferredSize().getWidth(), 
						(int)bottoni.get(i).getPreferredSize().getHeight());
				larghezza += bottoni.get(i).getPreferredSize().getHeight();
			}
			else
			{
				if (i % 4 == 0)
				{
					bottoni.get(i).setBounds(30, 10 + bottoni.get(i-1).getY() 
							+ (int)bottoni.get(i-1).getPreferredSize().getHeight(), 
							(int)bottoni.get(i).getPreferredSize().getWidth(), 
							(int)bottoni.get(i).getPreferredSize().getHeight());
					larghezza += bottoni.get(i).getPreferredSize().getHeight();
				}
				else
					bottoni.get(i).setBounds(25 + bottoni.get(i-1).getX() 
							+ (int)bottoni.get(i-1).getPreferredSize().getWidth(),
							bottoni.get(i-1).getY(),
							(int)bottoni.get(i).getPreferredSize().getWidth(), 
							(int)bottoni.get(i).getPreferredSize().getHeight());
			}
		
			add(bottoni.get(i));
			
			if (larghezza > this.getPreferredSize().getHeight())
				this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(),
						larghezza+(int)bottoni.get(bottoni.size()-1).getPreferredSize().getHeight()/2));
			
		}
	}
	
	public void setCronologia(ArrayList<BottoneCronologia> bottoniCronologia)
	{
		int larghezza = 0;
	    for (int i = 0; i < bottoniCronologia.size(); i++)
	    {
	    	if (i == 0)
				bottoniCronologia.get(i).setBounds(30,5, 
						(int)bottoniCronologia.get(i).getPreferredSize().getWidth(), (int)bottoniCronologia.get(i).getPreferredSize().getHeight());
			else
				bottoniCronologia.get(i).setBounds(30, 10 + bottoniCronologia.get(i-1).getY() + (int)bottoniCronologia.get(i-1).getPreferredSize().getHeight(), (int)bottoniCronologia.get(i).getPreferredSize().getWidth(), (int)bottoniCronologia.get(i).getPreferredSize().getHeight());
			
			add(bottoniCronologia.get(i));
			
			larghezza += bottoniCronologia.get(i).getPreferredSize().getHeight();
			if (i != bottoniCronologia.size()-1)
			{
				listaJSeparator.add(new JSeparator(JSeparator.HORIZONTAL));
				listaJSeparator.get(i).setBackground(Color.BLACK);
				listaJSeparator.get(i).setForeground(Color.GRAY);
				listaJSeparator.get(i).setPreferredSize(new Dimension((int)this.getPreferredSize().getHeight(),
						(int)listaJSeparator.get(i).getPreferredSize().getHeight()));
				listaJSeparator.get(i).setBounds(10, 5+bottoniCronologia.get(i).getY()+
						(int)bottoniCronologia.get(i).getPreferredSize().getHeight(), 
						(int)this.getPreferredSize().getWidth()-13, (int)listaJSeparator.get(i).getPreferredSize().getHeight());
				add(listaJSeparator.get(i));
			}
			
			if (larghezza >= this.getPreferredSize().getHeight())
				this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(),
						larghezza + (int)bottoniCronologia.get(bottoniCronologia.size()-1).getPreferredSize().getHeight()/2));
	    }
	}
	
	public void setFollow_Follower(ArrayList<BottoneFollow> bottone)
	{
		int larghezza = 0;
		for (int i = 0; i < bottone.size(); i++)
		{
			if (i == 0)
				bottone.get(i).setBounds(5 , 5, (int)bottone.get(i).getPreferredSize().getWidth(), (int)bottone.get(i).getPreferredSize().getHeight());
			else
				bottone.get(i).setBounds(bottone.get(i-1).getX(), 10+bottone.get(i-1).getY() + 
						(int)bottone.get(i-1).getPreferredSize().getHeight(), (int)bottone.get(i).getPreferredSize().getWidth(),
						(int)bottone.get(i).getPreferredSize().getHeight());
			
			add(bottone.get(i));
			larghezza += bottone.get(i).getPreferredSize().getHeight();
			
			if (i != bottone.size()-1)
			{
				listaJSeparator.add(new JSeparator(JSeparator.HORIZONTAL));
				listaJSeparator.get(i).setBackground(Color.BLACK);
				listaJSeparator.get(i).setForeground(Color.GRAY);
				listaJSeparator.get(i).setPreferredSize(new Dimension((int)this.getPreferredSize().getHeight(),
						(int)listaJSeparator.get(i).getPreferredSize().getHeight()));
				listaJSeparator.get(i).setBounds(10, 10+bottone.get(i).getY()+
						(int)bottone.get(i).getPreferredSize().getHeight(), 
						(int)this.getPreferredSize().getWidth()-13, (int)listaJSeparator.get(i).getPreferredSize().getHeight());
				add(listaJSeparator.get(i));
			}
			
			if (larghezza >= this.getPreferredSize().getHeight())
				this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(),
						larghezza + (int)bottone.get(bottone.size()-1).getPreferredSize().getHeight()/2));
			
		}
	}
    
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}
