import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class FrameLogin extends JFrame
{
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3;
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4;
	
	Login p = new Login(larghezza, altezza);
	public FrameLogin()
	{
		super();
		this.setTitle("Login for Comics Reader");
		this.setVisible(true);
		this.setContentPane(p);
		this.setSize(larghezza, altezza);
		this.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3, larghezza, altezza);
		System.out.println((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 + " "+ (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public Login getPanel()
	{
		return p;
	}
}
