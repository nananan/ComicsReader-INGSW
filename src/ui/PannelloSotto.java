package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import domain.VisualizzatoreCapitoli;


public class PannelloSotto extends JPanel
{
	private ImageIcon image = new ImageIcon("image/play.png");
	private ImageIcon imageNext = new ImageIcon("image/next.png");
	private ImageIcon imagePrev = new ImageIcon("image/prev-icon.png");
	private Image scaledImage = null;
	
	private JButton play = new JButton();
	private JButton next = new JButton();
	private JButton prev = new JButton();
	
	private MyPanel panel;
	
	private MyListener listener;
	
	public PannelloSotto(final MyPanel panel) 
	{
//		int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//		
//		setBounds(180, 30, larghezza-200, altezza-50);
		
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 50));
		
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		this.panel = panel;
		listener = new MyListener();
		
		//PLAY
		Image imageScaled = image.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		image.setImage(imageScaled);
		
		play.setIcon(image);
		play.setPressedIcon(image);
		play.setBorderPainted(false);
		play.setFocusPainted(false);
		play.setBackground(getBackground());
		play.setBounds((int)getPreferredSize().getWidth()/2, (int)getPreferredSize().getHeight()/4, image.getIconWidth(), image.getIconHeight());
		
		//NEXT
		imageScaled = imageNext.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		imageNext.setImage(imageScaled);
		
		next.setIcon(imageNext);
		next.setPressedIcon(imageNext);
		next.setBorderPainted(false);
		next.setFocusPainted(false);
		next.setBackground(getBackground());
		next.setBounds((int)getPreferredSize().getWidth()/2 + image.getImage().getWidth(null)*2, (int)getPreferredSize().getHeight()/4, imageNext.getIconWidth(), imageNext.getIconHeight());
		
		//PREV
		imageScaled = imagePrev.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		imagePrev.setImage(imageScaled);
		
		prev.setIcon(imagePrev);
		prev.setPressedIcon(imagePrev);
		prev.setBorderPainted(false);
		prev.setFocusPainted(false);
		prev.setBackground(getBackground());
		prev.setBounds((int)getPreferredSize().getWidth()/2 - image.getImage().getWidth(null)*2, (int)getPreferredSize().getHeight()/4, imagePrev.getIconWidth(), imagePrev.getIconHeight());

		next.addActionListener(listener);
		prev.addActionListener(listener);

		add(play);
		add(next);
		add(prev);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		g.drawImage(image, 0,0, this);
	}
	
	private class MyListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if (source == next) 
				panel.premiPerPaginaSuccessiva();
			else if (source == prev) 
				panel.premiPerPaginaPrecedente();
		}
	}
}
