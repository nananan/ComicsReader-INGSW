package Swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PannelloDiscover extends JPanel
{
	PannelloCentrale pannelloCentrale = null;
	File file = null;
	Image image = null;
	Image scaledImage = null;
	
	ImageIcon icon = new ImageIcon("");
	JButton fumetto = new JButton(icon); 
	
	public PannelloDiscover()
	{
		super();
//		setFile(pannelloCentrale.getFile());
	}
	
	public void setPannelloCentrale(PannelloCentrale pannelloCentrale) throws IOException
	{
		this.pannelloCentrale = pannelloCentrale;
		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
		setBounds(0, 0, (int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight());
		setBorder(BorderFactory.createLineBorder(Color.black,3));
		setLayout(null);
		
		fumetto.setPreferredSize(new Dimension(200,300));
		fumetto.setBounds(10,10, 200,300);
		icon.setImage(getURL(fumetto.getName()));
		
		add(fumetto);
		System.out.println(fumetto.getName());	
		
		JLabel label = new JLabel(fumetto.getName());
		label.setBounds(10, (int)fumetto.getPreferredSize().getHeight()+10, 100, 100);
		add(label);
		
		try {
			image = ImageIO.read(pannelloCentrale.getFile());
			System.out.println((int)getPreferredSize().getWidth()+","+ (int)getPreferredSize().getHeight());
			scaledImage = image.getScaledInstance((int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight(), Image.SCALE_AREA_AVERAGING);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
//		repaint();
	}
	
	public Image getURL(String stringa) throws IOException
	{
		URL url = new URL("http://5.196.65.101/~ComicsReader/Fumetti/666%20Satan/Copertina.jpg");
		BufferedImage image = ImageIO.read(url);
		
		String stringhe [] = new String[14];
				stringhe = url.toString().split("/");
		
		stringa = stringhe[stringhe.length-2];
//		for (String string : stringhe) {
//			System.out.println(string);
//		}
		
		File dir = (new File("Comics Reader"));
		
		if (dir.exists())
			file = new File(dir.getName()+"/"+stringhe[stringhe.length-1]);
		else
		{
			dir.mkdir();
			file = new File(dir.getName()+"/"+stringhe[stringhe.length-1]);
		}
		
		ImageOutputStream imageOutputStream = new FileImageOutputStream(file); 
		ImageIO.write(image, "jpg", imageOutputStream);
		
		image = ImageIO.read(url);
		scaledImage  = image.getScaledInstance((int)fumetto.getPreferredSize().getWidth(), (int)fumetto.getPreferredSize().getHeight(), Image.SCALE_AREA_AVERAGING);
		
		file.deleteOnExit();
		
		return scaledImage;
	}
	
	//ciao
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(scaledImage, 0,0, this);
	}
}
