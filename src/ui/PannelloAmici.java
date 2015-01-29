package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JSeparator;

import domain.AppManager;
import domain.Lettore;

public class PannelloAmici extends JPanel
{
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private MyPanel panel;
	private Lettore lettore = AppManager.getLettore();
	private ArrayList<BottoneFollow> listaBottoniFollowCorrente;
	private ArrayList<JSeparator> listaJSeparator;
	
	private int larghezzaBottone;
	private Text textAmici;
	
	public PannelloAmici(final MyPanel panel, int larghezzaPannello) throws IOException
	{
		super();	
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(larghezza-larghezzaPannello*2, altezza));
		setLayout(null);
		this.panel = panel;
		
		this.larghezzaBottone = larghezzaPannello - 100;
		
		textAmici = new Text("Amici", 32, Color.DARK_GRAY);
		textAmici.setBounds(10, 10, (int)textAmici.getPreferredSize().getWidth(), 
				(int)textAmici.getPreferredSize().getHeight());
		add(textAmici);
		
		listaBottoniFollowCorrente = new ArrayList<>();
		listaJSeparator = new ArrayList<>();
		
		Iterator it = lettore.getAmici().entrySet().iterator();

		while (it.hasNext())
	    {
	        Map.Entry pairs = (Map.Entry)it.next();

        	BottoneFollow bottone = new BottoneFollow(lettore.getAmici().get(pairs.getKey()), larghezzaBottone, 0, this.lettore, 150, this.panel);
			listaBottoniFollowCorrente.add(bottone);
	    }

	    int larg = 0;
		for (int i = 0; i < listaBottoniFollowCorrente.size(); i++)
		{
			if (i == 0)
				listaBottoniFollowCorrente.get(i).setBounds(5 , 5 + textAmici.getY()+
						(int)textAmici.getPreferredSize().getHeight(), 
						(int)listaBottoniFollowCorrente.get(i).getPreferredSize().getWidth(), 
						(int)listaBottoniFollowCorrente.get(i).getPreferredSize().getHeight());
			else
				listaBottoniFollowCorrente.get(i).setBounds(listaBottoniFollowCorrente.get(i-1).getX(),
						10+listaBottoniFollowCorrente.get(i-1).getY() + 
						(int)listaBottoniFollowCorrente.get(i-1).getPreferredSize().getHeight(), 
						(int)listaBottoniFollowCorrente.get(i).getPreferredSize().getWidth(),
						(int)listaBottoniFollowCorrente.get(i).getPreferredSize().getHeight());
			
			this.add(listaBottoniFollowCorrente.get(i));
			larg += listaBottoniFollowCorrente.get(i).getPreferredSize().getHeight();
			
			if (i != listaBottoniFollowCorrente.size()-1)
			{
				listaJSeparator.add(new JSeparator(JSeparator.HORIZONTAL));
				listaJSeparator.get(i).setBackground(Color.BLACK);
				listaJSeparator.get(i).setForeground(Color.GRAY);
				listaJSeparator.get(i).setPreferredSize(new Dimension((int)this.getPreferredSize().getHeight(),
						(int)listaJSeparator.get(i).getPreferredSize().getHeight()));
				listaJSeparator.get(i).setBounds(10, 10+listaBottoniFollowCorrente.get(i).getY()+
						(int)listaBottoniFollowCorrente.get(i).getPreferredSize().getHeight(), 
						(int)this.getPreferredSize().getWidth()-13, (int)listaJSeparator.get(i).getPreferredSize().getHeight());
				this.add(listaJSeparator.get(i));
				
			}
			
			if (larg >= this.getPreferredSize().getHeight())
				this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(),
						larg + (int)listaBottoniFollowCorrente.get(listaBottoniFollowCorrente.size()-1).getPreferredSize().getHeight()/2));
			
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		for (BottoneFollow bottoneFollow : listaBottoniFollowCorrente)
		{
			g.drawImage(bottoneFollow.getScaledImage(), 0, 0, this);
		}
		super.paintComponent(g);
	}
}
