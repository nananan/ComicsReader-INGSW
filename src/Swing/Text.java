package Swing;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextArea;


public class Text extends JTextArea
{
	Font font = new Font("Caladea", Font.BOLD, 12);
	
	public Text(String text) 
	{
		super();
		setText(text);
		setEditable(false);
		setOpaque(false);
		setForeground(Color.WHITE);
		setFont(font);
	}
}
