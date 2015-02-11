package ui;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class FrameLogin extends JFrame
{
	final int costanteDivisione = 2;
	
	int larghezza = 300;
	int altezza = 450;
	Login p = new Login(larghezza, altezza);
	public FrameLogin()
	{
		super();
		this.setTitle("Login for Comics Reader");
		this.setContentPane(p);
		this.setSize(larghezza, altezza);
		this.setBounds(altezza, larghezza-200, larghezza, altezza);
		
		this.setUndecorated(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public Login getPanel()
	{
		return p;
	}
}
