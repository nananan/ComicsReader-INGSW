package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class PannelloDestro extends JPanel
{
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3;
	
	public PannelloDestro()
	{
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension(larghezza, 0));
		setBorder(BorderFactory.createLineBorder(Color.black,1));
	}
	
	@Override
	public void paintComponents(Graphics g) 
	{
		super.paintComponents(g);
	}
}
