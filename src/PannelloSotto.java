import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;


public class PannelloSotto extends JPanel
{
	ImageIcon image = new ImageIcon("/home/eliana/Interfacce grafiche ed eventi/javaWorkspace/INGSW/src/image/play.png");
	ImageIcon imageNext = new ImageIcon("/home/eliana/Interfacce grafiche ed eventi/javaWorkspace/INGSW/src/image/next.png");
	ImageIcon imagePrev = new ImageIcon("/home/eliana/Interfacce grafiche ed eventi/javaWorkspace/INGSW/src/image/prev-icon.png");
	ImageIcon imageNext2 = new ImageIcon("/home/eliana/Interfacce grafiche ed eventi/javaWorkspace/INGSW/src/image/next2.png");
	ImageIcon imagePrev2 = new ImageIcon("/home/eliana/Interfacce grafiche ed eventi/javaWorkspace/INGSW/src/image/prev2.png");
	Image scaledImage = null;
	
	JButton play = new JButton();
	JButton next = new JButton();
	JButton next2 = new JButton();
	JButton prev = new JButton();
	JButton prev2 = new JButton();
	
	public PannelloSotto() 
	{
//		int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//		
//		setBounds(180, 30, larghezza-200, altezza-50);
		
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 50));
		
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
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
		
		//NEXTNEXT
		imageScaled = imageNext2.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		imageNext2.setImage(imageScaled);
		
		next2.setIcon(imageNext2);
		next2.setPressedIcon(imageNext2);
		next2.setBorderPainted(false);
		next2.setFocusPainted(false);
		next2.setBackground(getBackground());
		next2.setBounds((int)getPreferredSize().getWidth()/2 + imageNext.getImage().getWidth(null)*4, (int)getPreferredSize().getHeight()/4, imageNext2.getIconWidth(), imageNext2.getIconHeight());
		
		//PREV
		imageScaled = imagePrev.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		imagePrev.setImage(imageScaled);
		
		prev.setIcon(imagePrev);
		prev.setPressedIcon(imagePrev);
		prev.setBorderPainted(false);
		prev.setFocusPainted(false);
		prev.setBackground(getBackground());
		prev.setBounds((int)getPreferredSize().getWidth()/2 - image.getImage().getWidth(null)*2, (int)getPreferredSize().getHeight()/4, imagePrev.getIconWidth(), imagePrev.getIconHeight());
		
		//PREVPREV
		imageScaled = imagePrev2.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		imagePrev2.setImage(imageScaled);
		
		prev2.setIcon(imagePrev2);
		prev2.setPressedIcon(imagePrev2);
		prev2.setBorderPainted(false);
		prev2.setFocusPainted(false);
		prev2.setBackground(getBackground());
		prev2.setBounds((int)getPreferredSize().getWidth()/2 - imagePrev.getImage().getWidth(null)*4, (int)getPreferredSize().getHeight()/4, imagePrev2.getIconWidth(), imagePrev2.getIconHeight());
		
		add(play);
		add(next);
		add(next2);
		add(prev);
		add(prev2);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
//		g.drawImage(image, 0,0, this);
	}
}
