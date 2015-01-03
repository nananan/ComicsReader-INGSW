package gui;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class FrameLogin extends JFrame
{
	final int costanteDivisione = 2;
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / costanteDivisione;
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / (costanteDivisione+1);
	
	Login p = new Login(larghezza, altezza);
	public FrameLogin()
	{
		super();
		this.setTitle("Login for Comics Reader");
		this.setContentPane(p);
		this.setSize(larghezza, altezza);
		this.setBounds(larghezza-(int)p.getPreferredSize().getWidth()/2, altezza, larghezza, altezza);
//		System.out.println((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/(costanteDivisione-1) + " "+ (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/(costanteDivisione-1));
		
		this.setUndecorated(true);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public Login getPanel()
	{
		return p;
	}
}
