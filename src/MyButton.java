import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;


public class MyButton extends JButton
{
	Font font = new Font("Caladea", Font.BOLD, 14);
	
	public MyButton(String text) 
	{
		super();
		setText(text);
		setBackground(new Color(91,84,84));
		setBorderPainted(false);
		setFocusPainted(false);
		setForeground(Color.WHITE);
		setFont(font);
	}

	public void setDimension(JPanel panel, int Y)
	{
		setPreferredSize(new Dimension((int) panel.getPreferredSize().getWidth()-2,20));
		setBounds(1, Y, (int) panel.getPreferredSize().getWidth()-2,20);
	}
	
}
