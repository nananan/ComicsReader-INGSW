package ui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PannelloLoading extends JPanel implements Runnable
{
	private static ImageIcon caricamento = new ImageIcon("image/loading.gif");
	private static PannelloLoading istanza;
	private JLabel label;
	private PannelloLoading()
	{
		setLayout(new BorderLayout());
		label = new JLabel(caricamento,JLabel.CENTER);
		add(label,BorderLayout.CENTER);
	
	}
	
	public static PannelloLoading getIstanza(){
		if(istanza==null)
			istanza=new PannelloLoading();		
		return istanza;
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		 super.paintComponent(g);
	}

	@Override
	public void run()
	{
		while(true){
			repaint();
		}
	}
	
}
