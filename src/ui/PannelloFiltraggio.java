package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import domain.Fumetto;

public class PannelloFiltraggio extends JPanel
{
	private MyPanel panel;
	private Text textGenere;
	private Text textStato;
	private static Color COLORE = new Color(35,148,188);
	
	private MyButton buttonManga;
	private MyButton buttonFumetti;
	private MyButton clean;
	private MyButton fine;
	
	private String nomiGeneriFiltri[];
	private MyButton bottoniGeneriFiltri[];
	private ArrayList<String> filtri = new ArrayList<>();
	private int tipoFumetto;
	private int statoFumetto;
	
	private int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 7;
	private Text tipo;
	private MyButton buttonStatoCompleto;
	private MyButton buttonStatoIncompleto;
	
	private MyListener listener;
	
	public PannelloFiltraggio(final MyPanel panel, int altezzaPannello) 
	{
		super();
		
		this.panel = panel;
		
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension(larghezza, altezzaPannello));
		setLayout(null);
		
		tipoFumetto = 0;
		statoFumetto = 0;
		
		listener = new MyListener();
		
		tipo = new Text("TIPO", 16, Color.BLACK);
		tipo.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), 20));
		tipo.setBounds(10, 10, (int)tipo.getPreferredSize().getWidth(), (int)tipo.getPreferredSize().getHeight());
		add(tipo);
		
		buttonManga = new MyButton("Orientale", COLORE);
		buttonManga.setBounds(10, 10+tipo.getY()+(int)tipo.getPreferredSize().getHeight(), (int)buttonManga.getPreferredSize().getWidth(), (int)buttonManga.getPreferredSize().getHeight());
		buttonManga.addActionListener(listener);
		add(buttonManga);

		buttonFumetti = new MyButton("Occidentale", COLORE);
		buttonFumetti.setBounds(10, buttonManga.getY()+(int)buttonManga.getPreferredSize().getHeight() -5, (int)buttonFumetti.getPreferredSize().getWidth(), (int)buttonFumetti.getPreferredSize().getHeight());
		buttonFumetti.addActionListener(listener);
		add(buttonFumetti);
		
		textStato = new Text("STATO", 16, Color.BLACK);
		textStato.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), 20));
		textStato.setBounds(10,10 + buttonFumetti.getY()+(int)buttonFumetti.getPreferredSize().getHeight(), (int)textStato.getPreferredSize().getWidth(), (int)textStato.getPreferredSize().getHeight());
		add(textStato);
		
		buttonStatoCompleto = new MyButton("Completo", COLORE);
		buttonStatoCompleto.setBounds(10, 10 + textStato.getY()+(int)textStato.getPreferredSize().getHeight(), (int)buttonStatoCompleto.getPreferredSize().getWidth(), (int)buttonStatoCompleto.getPreferredSize().getHeight());
		buttonStatoCompleto.addActionListener(listener);
		add(buttonStatoCompleto);
		
		buttonStatoIncompleto = new MyButton("Incompleto", COLORE);
		buttonStatoIncompleto.setBounds(10, buttonStatoCompleto.getY()+(int)buttonStatoCompleto.getPreferredSize().getHeight() - 5, (int)buttonStatoIncompleto.getPreferredSize().getWidth(), (int)buttonStatoIncompleto.getPreferredSize().getHeight());
		buttonStatoIncompleto.addActionListener(listener);
		add(buttonStatoIncompleto);
		
		textGenere = new Text("GENERE", 16, Color.BLACK);
		textGenere.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), 20));
		textGenere.setBounds(10,10 + buttonStatoIncompleto.getY()+(int)buttonStatoIncompleto.getPreferredSize().getHeight(), (int)textGenere.getPreferredSize().getWidth(), (int)textGenere.getPreferredSize().getHeight());
		add(textGenere);
		
	
			nomiGeneriFiltri = Fumetto.getTuttiGeneri();
			bottoniGeneriFiltri = new MyButton[nomiGeneriFiltri.length];
		
		for (int i = 0; i < nomiGeneriFiltri.length; i++)
		{
			bottoniGeneriFiltri[i] = new MyButton(nomiGeneriFiltri[i], COLORE);
			
			if (i == 0)
				bottoniGeneriFiltri[i].setBounds(10, 10 + textGenere.getY()+(int)textGenere.getPreferredSize().getHeight(), (int)bottoniGeneriFiltri[i].getPreferredSize().getWidth(), (int)bottoniGeneriFiltri[i].getPreferredSize().getHeight());
			else
				bottoniGeneriFiltri[i].setBounds(10, bottoniGeneriFiltri[i-1].getY()+(int)bottoniGeneriFiltri[i-1].getPreferredSize().getHeight(), (int)bottoniGeneriFiltri[i].getPreferredSize().getWidth(), (int)bottoniGeneriFiltri[i].getPreferredSize().getHeight());
			
			add(bottoniGeneriFiltri[i]);
			
			bottoniGeneriFiltri[i].addActionListener(listener);
			
			if (bottoniGeneriFiltri[i].getY() + (int)bottoniGeneriFiltri[i].getPreferredSize().getHeight() > this.getPreferredSize().getHeight())
				this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(), (int)bottoniGeneriFiltri[i].getPreferredSize().getHeight() + (int)bottoniGeneriFiltri[i].getPreferredSize().getHeight()));
		}
		
		
		//BUTTON FINE, TORNA AL MENU PRINCIPALE
		fine = new MyButton("Fine", COLORE);
		fine.setBounds((int)this.getPreferredSize().getWidth()-(int)fine.getPreferredSize().getWidth() - 10, 
				10 + bottoniGeneriFiltri[bottoniGeneriFiltri.length-1].getY() + 
				(int)bottoniGeneriFiltri[bottoniGeneriFiltri.length-1].getPreferredSize().getHeight() , 
				(int)fine.getPreferredSize().getWidth(),20);
		
		fine.addActionListener(listener);
		add(fine);
		
		//BUTTON PER CANCELLARE I FILTRI
		clean = new MyButton("Clean", COLORE);
		clean.setBounds(this.getInsets().bottom, 10 + bottoniGeneriFiltri[bottoniGeneriFiltri.length-1].getY() + (int)bottoniGeneriFiltri[bottoniGeneriFiltri.length-1].getPreferredSize().getHeight() , (int)clean.getPreferredSize().getWidth()+this.getInsets().bottom,20);
		clean.addActionListener(listener);
		add(clean);
		
		this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(), clean.getY() + (int)clean.getPreferredSize().getHeight()));
		
	}
	
	public ArrayList<String> getArrayDiFiltri()
	{
		return filtri;
	}
	
	public int getTipoFumetto()
	{
		return tipoFumetto;
	}
	
	public int getStatoFumetto()
	{
		return statoFumetto;
	}
	
	private class MyListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if (source == buttonManga)
			{
				if (buttonManga.getForeground() == Color.WHITE)
				{
					tipoFumetto = -1;
					buttonManga.setForeground(new Color(35,148,188));
					buttonFumetti.setForeground(Color.WHITE);
				}
				else
				{
					buttonManga.setForeground(Color.WHITE);
					tipoFumetto = 0;
				}
			}
			else if (source == buttonFumetti)
			{
				if (buttonFumetti.getForeground() == Color.WHITE)
				{
					tipoFumetto = 1;
					buttonFumetti.setForeground(new Color(35,148,188));
					buttonManga.setForeground(Color.WHITE);
				}
				else
				{
					buttonFumetti.setForeground(Color.WHITE);
					tipoFumetto = 0;
				}
			}
			if (source == buttonStatoCompleto)
			{
				if (buttonStatoCompleto.getForeground() == Color.WHITE)
				{
					statoFumetto = 1;
					buttonStatoCompleto.setForeground(new Color(35,148,188));
					buttonStatoIncompleto.setForeground(Color.WHITE);
				}
				else
				{
					statoFumetto = 0;
					buttonStatoCompleto.setForeground(Color.WHITE);
				}
			}
			else if (source == buttonStatoIncompleto)
			{
				if (buttonStatoIncompleto.getForeground() == Color.WHITE)
				{
					statoFumetto = -1;
					buttonStatoIncompleto.setForeground(new Color(35,148,188));
					buttonStatoCompleto.setForeground(Color.WHITE);
				}
				else
				{
					statoFumetto = 0;
					buttonStatoIncompleto.setForeground(Color.WHITE);
				}
			}
			
			
			for (int i = 0; i < bottoniGeneriFiltri.length; i++)
			{
				if (source == bottoniGeneriFiltri[i])
				{
					if (bottoniGeneriFiltri[i].getForeground() == Color.WHITE)
					{
						bottoniGeneriFiltri[i].setForeground(new Color(35,148,188));
						filtri.add(bottoniGeneriFiltri[i].getText());
					}
					else
					{
						filtri.remove(bottoniGeneriFiltri[i].getText());
						bottoniGeneriFiltri[i].setForeground(Color.WHITE);
					}
				}
			}
			
			if (source == clean)
			{
				for (int i = 0; i < bottoniGeneriFiltri.length; i++)
					bottoniGeneriFiltri[i].setForeground(Color.WHITE);
				
				buttonFumetti.setForeground(Color.WHITE);
				buttonManga.setForeground(Color.WHITE);
				buttonStatoCompleto.setForeground(Color.WHITE);
				buttonStatoIncompleto.setForeground(Color.WHITE);
				
				filtri.clear();
			}
			
			if (source == fine)
			{
				panel.PremiPerPannelloSinistro();
			}
			
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	}
}
