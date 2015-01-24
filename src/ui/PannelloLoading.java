package ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PannelloLoading extends JPanel implements Runnable
{
	private PannelloCentrale panel;
	
	public PannelloLoading(PannelloCentrale pannelloCentrale)
	{
//		setBackground(Color.GRAY);
//		setPreferredSize(new Dimension(600, 400));
//		this.setBorder(BorderFactory.createLineBorder(Color.black,1));
//		setLayout(null);
		
		this.panel = pannelloCentrale;
		
		ImageIcon loading = new ImageIcon("image/ajax-loader.gif");
		add(new JLabel("loading... ", loading, JLabel.CENTER));
	}
	
	@Override
	public void paintComponents(Graphics g)
	{
		super.paintComponents(g);
	}

	@Override
	public void run()
	{	
		panel.setBackground(Color.GREEN);
		repaint();
	}
	
//	public static void main(String[] args) throws Exception {
//		JFrame frame = new JFrame("Test");
//
//		PannelloLoading panel = new PannelloLoading();
//		frame.setContentPane(panel);
//
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(400, 300);
//		frame.setVisible(true);
//	}
}
