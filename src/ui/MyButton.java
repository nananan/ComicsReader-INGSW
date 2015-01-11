package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class MyButton extends JButton
{
	Font font = new Font("Caladea", Font.BOLD, 14);
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	
	public MyButton(String text) 
	{
		super();
		setText(text);
		setBackground(new Color(91,84,84));
		setBorderPainted(false);
		setFocusPainted(false);
		setForeground(Color.WHITE);
		setContentAreaFilled(false);
		
		if (larghezza > 1000)
			setFont(font);
		else
			setFont(new Font("Caladea", Font.BOLD, 10));
	}

	public MyButton(String text, int larghezza, Color colore) 
	{
		super(text);
//		setText(text);
//		setBackground(colore);
		setBackground(new Color(200, 200, 200));
//		setBorderPainted(false);
//		setFocusPainted(false);
		setForeground(Color.WHITE);
//		setContentAreaFilled(false);
//		setFont(new Font("Caladea", Font.BOLD, larghezza));
	}
	
	public void setDimension(JPanel panel, JPanel centro, int Y)
	{
		setPreferredSize(new Dimension((int) panel.getPreferredSize().getWidth()-centro.getInsets().bottom,20));
		setBounds(panel.getInsets().bottom, Y, (int) panel.getPreferredSize().getWidth()-centro.getInsets().bottom,20);
	}
	
}
