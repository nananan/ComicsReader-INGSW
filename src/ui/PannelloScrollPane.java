package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import technicalService.GestoreDataBase;

public class PannelloScrollPane extends JScrollPane
{
	private JPanel panel;
	
	public PannelloScrollPane(JPanel panel, int aggiungi, Color color) 
	{
		super(panel);
		this.setPreferredSize(new Dimension((int)panel.getPreferredSize().getWidth()+aggiungi,
				442));
		this.panel = panel;
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(color,1));
		
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getVerticalScrollBar().setUnitIncrement(15);
		this.getVerticalScrollBar().setValueIsAdjusting(true);
		this.getVerticalScrollBar().setUI(new MyScrollBarUI().setColor(color));
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	public JPanel getPanel()
	{
		return panel;
	}	
	
	@Override
	public void paintComponents(Graphics g)
	{
		super.paintComponents(g);
	}
	
}
