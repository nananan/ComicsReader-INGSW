package ui;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MyFrame extends JFrame
{
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public MyFrame(String name) throws IOException
	{
		super();
		JPanel p = new MyPanel(name);
		this.setTitle("MyFrame");
//		this.setUndecorated(true);
		this.setVisible(true);
		this.setContentPane(p);
		this.setSize(larghezza, altezza);
		System.out.println(larghezza+" "+altezza);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

}
