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
	
	public PannelloScrollPane(JPanel panel, File file) 
	{
		super(panel);
		this.panel = panel;
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(Color.black,3));
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
