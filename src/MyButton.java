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
		setContentAreaFilled(false);
	}

	public void setDimension(JPanel panel, JPanel centro, int Y)
	{
		setPreferredSize(new Dimension((int) panel.getPreferredSize().getWidth()-centro.getInsets().bottom,20));
		setBounds(panel.getInsets().bottom, Y, (int) panel.getPreferredSize().getWidth()-centro.getInsets().bottom,20);
	}
	
}
