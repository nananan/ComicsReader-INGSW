package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JPanel;

import technicalService.DataBase;
import technicalService.TabellaFumetto;
import domain.Fumetto;

public class PannelloDiscover extends JPanel
{
	private PannelloCentrale pannelloCentrale;
	File file;
	Image imageSfondo = null;
	Image scaledImage = null;
	
	HashMap<String,Fumetto> fumetti = new HashMap<>();
	
	ArrayList<BottoneFumetto> bottoniFumetti = new ArrayList<>();
	
	public PannelloDiscover()
	{
		super();		
	}
	
	public void setPannelloCentrale(PannelloCentrale pannelloCentrale, MyPanel panel) throws IOException
	{
		this.pannelloCentrale = pannelloCentrale;
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension((int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight()));
		setBounds(0, 0, (int)pannelloCentrale.getPreferredSize().getWidth(), (int)pannelloCentrale.getPreferredSize().getHeight());
		setLayout(null);
		
		try {
			DataBase.connect();
			
			TabellaFumetto tupleFumetto = new TabellaFumetto();
			while(tupleFumetto.nextFumetto())
			{
				Fumetto fumetto = new Fumetto(tupleFumetto);
				fumetti.put(fumetto.getNome(), fumetto);
			}
			
			tupleFumetto.close();

			int j=0, i=0;
			for(Entry<String,Fumetto> f : fumetti.entrySet())
			{
				BottoneFumetto bottoneFumetto = new BottoneFumetto(getURL(f.getValue().getUrl()), panel, f.getValue());

				bottoniFumetti.add(bottoneFumetto);
				bottoniFumetti.get(j).setPreferredSize(new Dimension(200,300));
								
				if (j == 0)
					bottoneFumetto.setBounds(10,10, 200,300);
				else
				{
					
					if (j % 4 == 0)
					{
						bottoniFumetti.get(j).setBounds(10,10+(int)bottoniFumetti.get(j-1).getPreferredSize().getHeight()+bottoniFumetti.get(j-1).getY(), 200,300);
						i += bottoniFumetti.get(j).getPreferredSize().getHeight()+10;
					}
					else
						bottoniFumetti.get(j).setBounds(10+(int)bottoniFumetti.get(j-1).getPreferredSize().getWidth()+bottoniFumetti.get(j-1).getX(),10+i, 200,300);
				}
				add(bottoneFumetto);
				
				if (i > (int)getPreferredSize().getHeight())
				{
					setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight()+i+(int)bottoniFumetti.get(j).getPreferredSize().getHeight()));
				}
				
				j++;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				DataBase.disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public Image getURL(String stringa) throws IOException
	{
		URL url = new URL(stringa);
		BufferedImage bufferedImage = ImageIO.read(url);
		
		String stringhe [] = new String[14];
				stringhe = url.toString().split("/");
		
		File dir = (new File("Comics Reader"));
		
		if (dir.exists())
			file = new File(dir.getName()+"/"+stringhe[stringhe.length-1]);
		else
		{
			dir.mkdir();
			file = new File(dir.getName()+"/"+stringhe[stringhe.length-1]);
		}
		
		ImageOutputStream imageOutputStream = new FileImageOutputStream(file); 
		ImageIO.write(bufferedImage, "jpg", imageOutputStream);
		
		bufferedImage = ImageIO.read(url);
		Image scaledImageFumetto = bufferedImage.getScaledInstance(200,300, bufferedImage.SCALE_AREA_AVERAGING);
		
		file.deleteOnExit();
		
		return scaledImageFumetto;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		for (BottoneFumetto bottoneFumetto : bottoniFumetti) 
		{
			g.drawImage(bottoneFumetto.getImageScaled(), 0,0, this);
		}
		super.paintComponent(g);
//		g.drawImage(scaledImage, 0,0, this);
	}
}
