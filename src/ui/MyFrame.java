package ui;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.Lettore;


public class MyFrame extends JFrame
{
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public MyFrame(Lettore lettore) throws IOException
	{
		super();
		JPanel p = new MyPanel(lettore);
		this.setTitle("MyFrame");
//		this.setUndecorated(true);
		this.setVisible(true);
		this.setContentPane(p);
		this.setSize(larghezza, altezza);
		System.out.println(larghezza+" "+altezza);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
//	public void switchTo(final JPanel panel) {
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				if (MyFrame.this.contentPanel.getComponentCount() > 0) {
//					MyFrame.this.contentPanel.removeAll();
//				}
//				MyFrame.this.contentPanel.add(panel, BorderLayout.CENTER);
//				MyFrame.this.contentPanel.updateUI();
//				panel.requestFocus();
//			}
//		});
//	}

}
