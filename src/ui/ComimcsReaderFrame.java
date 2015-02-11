package ui;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComimcsReaderFrame extends JFrame implements Runnable
{
	private int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private MyPanel mediatore;
	public ComimcsReaderFrame()
	{
		super();
		this.setTitle("ComicsReader");
		this.setSize(larghezza, altezza);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	@Override
	public void setVisible(boolean b)
	{
		mediatore.setLettore();
		this.setContentPane(mediatore);
		super.setVisible(b);
	}
	
	@Override
	public void run()
	{
		mediatore= new MyPanel();

	}
}
