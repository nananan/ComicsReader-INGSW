package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;


public class Text extends JLabel
{
	Font font;
	
	public Text(String text, int grandezzaFont, final Color color) 
	{
		super();
		font = new Font("Caladea", Font.HANGING_BASELINE, grandezzaFont);
		setText(text);
		setOpaque(false);
		setForeground(color);
		setFont(font);
		
	}
	
	public Text(String text) 
	{
		super();
		font = new Font("Caladea", Font.HANGING_BASELINE, 12);
		setText(text);
		setOpaque(false);
		setForeground(Color.WHITE);
		setFont(font);
	}
}
