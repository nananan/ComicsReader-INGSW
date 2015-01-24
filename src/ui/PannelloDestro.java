package ui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import technicalService.GestoreDataBase;
import domain.AppManager;
import domain.Fumetto;
import domain.Lettore;
import domain.Libreria;


public class PannelloDestro extends JPanel
{
	int larghezza = ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3) - 40;
	
	private Lettore lettore;
	private MyPanel panel;
	private Libreria libreria = Libreria.getIstanza();
	
	private ArrayList<BottoneFumetto> copertineCronologiaFollow;
	private ArrayList<Lettore> nomeFollow;

	private ArrayList<JSeparator> listaJSeparator;

	private JTextArea nomeFumetto;
	
	
	public PannelloDestro(final MyPanel panel)
	{
		super();
		this.setBackground(new Color(91,84,84));
		this.setPreferredSize(new Dimension(larghezza, 0));
		this.setLayout(null);
		
		GestoreDataBase.connetti();
		
		lettore = AppManager.getLettore();
		this.panel = panel;
		
		copertineCronologiaFollow = new ArrayList<>();
		nomeFollow = new ArrayList<>();
		listaJSeparator = new ArrayList<>();
		
		Iterator it = lettore.getFollows().entrySet().iterator();
	    while (it.hasNext())
	    {
	       Map.Entry pairs = (Map.Entry)it.next();
	       Fumetto fumettoPrimo = lettore.getPrimoCronologia(lettore.getFollows().get(pairs.getKey()));
	       if (fumettoPrimo != null)
		   {
	    	   fumettoPrimo = libreria.getFumetto(lettore.getPrimoCronologia(lettore.getFollows().get(pairs.getKey())).getNome());
	    	   copertineCronologiaFollow.add(new BottoneFumetto(fumettoPrimo.getCopertina(), fumettoPrimo, 80, 100, panel));
	    	   nomeFollow.add(lettore.getFollows().get(pairs.getKey()));
	   		}
	    }
	    
	    int altezza = 0;
	    for (int i = 0; i < copertineCronologiaFollow.size(); i++)
		{
	    	MyButton nome = new MyButton(nomeFollow.get(i).getNome()+" ha letto", new Color(35,148,188));
	    	nome.setFont(new Font("Caladea", Font.HANGING_BASELINE, 11));
	    	nome.setBackground(this.getBackground());
	    	nome.setBorderPainted(false);
	    	nome.setForeground(Color.WHITE);
	    	nome.setContentAreaFilled(false);
	    	
	    	final int j = i;
	    	nome.addMouseListener(new MouseAdapter()
			{
	    		@Override
	    		public void mouseReleased(MouseEvent e)
	    		{
	    			super.mouseReleased(e);
	    			panel.premiPerAvereProfiloDiAltroUtente(nomeFollow.get(j));
	    		}
			});
	    	
	    	if (i == 0)
	    	{
	    		nome.setBounds(-5, 10, (int)nome.getPreferredSize().getWidth(), 
	    				(int)nome.getPreferredSize().getHeight());
		    	
	    		copertineCronologiaFollow.get(i).setPreferredSize(new Dimension(80, 100));
				copertineCronologiaFollow.get(i).setBounds(10, 5+nome.getY()+(int)nome.getPreferredSize().getHeight(), 
						(int)copertineCronologiaFollow.get(i).getPreferredSize().getWidth(), 
						(int)copertineCronologiaFollow.get(i).getPreferredSize().getHeight());
				
				
				nomeFumetto = new JTextArea(copertineCronologiaFollow.get(i).getFumetto().getNome());
				nomeFumetto.setFont(new Font("Caladea", Font.HANGING_BASELINE, 12));
				nomeFumetto.setForeground(Color.WHITE);
				nomeFumetto.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-100, 50));
				nomeFumetto.setBackground(this.getBackground());
				nomeFumetto.setEditable(false);
				nomeFumetto.setLineWrap(true);
				nomeFumetto.setWrapStyleWord(true);
				nomeFumetto.setBounds(5+copertineCronologiaFollow.get(i).getX()+(int)copertineCronologiaFollow.get(i).getPreferredSize().getWidth(), 
						5+copertineCronologiaFollow.get(i).getY(), (int)nomeFumetto.getPreferredSize().getWidth(), 
						(int)nomeFumetto.getPreferredSize().getHeight());
	    	}
	    	else
	    	{
	    		nome.setBounds(-5, 10+copertineCronologiaFollow.get(i-1).getY()
	    				+ (int)copertineCronologiaFollow.get(i-1).getPreferredSize().getHeight(),
	    				(int)nome.getPreferredSize().getWidth(), 
	    				(int)nome.getPreferredSize().getHeight());
	    		
		    	copertineCronologiaFollow.get(i).setPreferredSize(new Dimension(80, 100));
				copertineCronologiaFollow.get(i).setBounds(10, 5+nome.getY()+(int)nome.getPreferredSize().getHeight(), 
						(int)copertineCronologiaFollow.get(i).getPreferredSize().getWidth(), 
						(int)copertineCronologiaFollow.get(i).getPreferredSize().getHeight());
				
				
				nomeFumetto = new JTextArea(copertineCronologiaFollow.get(i).getFumetto().getNome());
				nomeFumetto.setFont(new Font("Caladea", Font.HANGING_BASELINE, 14));
				nomeFumetto.setForeground(Color.WHITE);
				nomeFumetto.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-100, 50));
				nomeFumetto.setBackground(this.getBackground());
				nomeFumetto.setEditable(false);
				nomeFumetto.setLineWrap(true);
				nomeFumetto.setWrapStyleWord(true);
				nomeFumetto.setBounds(5+copertineCronologiaFollow.get(i).getX()+(int)copertineCronologiaFollow.get(i).getPreferredSize().getWidth(), 
						5+copertineCronologiaFollow.get(i).getY(), (int)nomeFumetto.getPreferredSize().getWidth(), 
						(int)nomeFumetto.getPreferredSize().getHeight());
	    	}
			add(copertineCronologiaFollow.get(i));
			add(nome);
			add(nomeFumetto);
			
			if (i != copertineCronologiaFollow.size()-1)
			{
				listaJSeparator.add(new JSeparator(JSeparator.HORIZONTAL));
				listaJSeparator.get(i).setBackground(Color.BLACK);
				listaJSeparator.get(i).setForeground(Color.GRAY);
				listaJSeparator.get(i).setPreferredSize(new Dimension((int)this.getPreferredSize().getHeight(),
						(int)listaJSeparator.get(i).getPreferredSize().getHeight()));
				listaJSeparator.get(i).setBounds(10, 5+copertineCronologiaFollow.get(i).getY()+
						(int)copertineCronologiaFollow.get(i).getPreferredSize().getHeight(), 
						(int)this.getPreferredSize().getWidth()-13, (int)listaJSeparator.get(i).getPreferredSize().getHeight());
				add(listaJSeparator.get(i));
			}
			altezza += copertineCronologiaFollow.get(i).getHeight();
			if (altezza > this.getPreferredSize().getHeight())
				this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(), 
						(int)this.getPreferredSize().getHeight()+altezza));
		}
	}
	
	@Override
	public void paintComponents(Graphics g) 
	{
		for (BottoneFumetto bottoneFumetto : copertineCronologiaFollow)
		{
			g.drawImage(bottoneFumetto.getImageScaled(), 0, 0, this);
		}
		super.paintComponents(g);
	}
}
