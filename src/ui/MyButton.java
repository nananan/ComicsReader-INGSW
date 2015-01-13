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
		setBackground(colore);
		setBorderPainted(false);
		setFocusPainted(false);
		setForeground(Color.WHITE);
		setContentAreaFilled(false);
		setFont(new Font("Caladea", Font.BOLD, larghezza));
	}
	
}
