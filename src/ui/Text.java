package ui;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextArea;


public class Text extends JTextArea
{
	Font font;
	
	public Text(String text, int grandezzaFont, Color color) 
	{
		super();
		font = new Font("Caladea", Font.BOLD, grandezzaFont);
		setText(text);
		setEditable(false);
		setOpaque(false);
		setForeground(color);
		setFont(font);
	}
	
	public Text(String text) 
	{
		super();
		font = new Font("Caladea", Font.BOLD, 12);
		setText(text);
		setEditable(false);
		setOpaque(false);
		setForeground(Color.WHITE);
		setFont(font);
	}
}
