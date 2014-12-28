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
//		setBackground(new Color(137,130,130));
		setEditable(false);
		setOpaque(false);
//		setBorderPainted(false);
//		setFocusPainted(false);
		setForeground(Color.WHITE);
		setFont(font);
//		setContentAreaFilled(true);
	}
}
