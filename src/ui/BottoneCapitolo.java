package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import domain.Volume;

public class BottoneCapitolo extends MyButton
{
	private Volume volume;
	private int numeroCapitolo;
	
	public BottoneCapitolo(String nome, int larghezza, Color color, final Volume volume, final int numeroCapitolo, final MyPanel panel)
	{
		super(nome, larghezza, color);
		
		this.volume = volume;
		this.numeroCapitolo = numeroCapitolo;
		
		this.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("premo bottone");
				panel.PremiPerCapitolo(volume, numeroCapitolo);
			}
		});
		
	}
	
	public Volume getVolume()
	{
		return volume;
	}

	public int getNumeroCapitolo()
	{
		return numeroCapitolo;
	}
	
//	@Override
//	public void paintComponents(Graphics g)
//	{
//		super.paintComponents(g);
//	}
	
}
