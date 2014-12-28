import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MyFrame extends JFrame
{
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public MyFrame()
	{
		super();
		JPanel p = new MyPanel();
		this.setTitle("MyFrame");
		this.setVisible(true);
		this.setContentPane(p);
		this.setSize(larghezza, altezza);
		System.out.println(larghezza+" "+altezza);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

}
