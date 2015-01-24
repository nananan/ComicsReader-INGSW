package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class MyButton extends JButton
{
	Font font = new Font("Caladea", Font.HANGING_BASELINE, 14);
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

	protected boolean premo;
	
	public MyButton(String text, final Color colorePress) 
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
			setFont(new Font("Caladea", Font.HANGING_BASELINE, 10));
		
		addMouseMotionListener(new MouseMotionAdapter() {
		      public void mouseMoved(MouseEvent event) {
	    		  MyButton.this.setForeground(colorePress);
		      }
		    });
	    addMouseListener(new MouseAdapter() {
	      public void mouseExited(MouseEvent event) {
	    	  if (!premo)
	    		  MyButton.this.setForeground(Color.WHITE);
	        }
	      public void mouseReleased(MouseEvent event) {
	    	  premo = true;
	    	  MyButton.this.setForeground(colorePress);
	        }

	    });
	}

	public MyButton(String text, int larghezza, final Color colore, final Color colorePress) 
	{
		super(text);
		setBackground(colore);
		setBorderPainted(false);
		setFocusPainted(false);
		setForeground(Color.WHITE);
		setContentAreaFilled(false);
		setFont(new Font("Caladea", Font.HANGING_BASELINE, larghezza));
		
		addMouseMotionListener(new MouseMotionAdapter() {
		      public void mouseMoved(MouseEvent event) {
	    		  MyButton.this.setForeground(colorePress);
		      }
		    });
	    addMouseListener(new MouseAdapter() {
	      public void mouseExited(MouseEvent event) {
	    	  if (!premo)
	    		  MyButton.this.setForeground(Color.WHITE);
	        }
	      public void mouseReleased(MouseEvent event) {
	    	  premo = true;
	    	  MyButton.this.setForeground(colorePress);
	        }
	    });
	}
	
	public void setPremuto()
	{
		premo=false;
	}
}
