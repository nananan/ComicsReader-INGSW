package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import domain.Lettore;

public class BottoneFollow extends JPanel
{
	private static final int FOLLOW = 0;
	private static final int FOLLOWING = 1;

	private Lettore lettore;
	private Lettore lettoreCorrente;
	private File file;
	private BottoneUtente bottoneUtente;
	private MyButton bottonePerFollow;
	private int stato;
	private MyListener listener;
	
	public BottoneFollow(Lettore lettoreFoll, int larghezza, int stato, Lettore lettoreCorrente, int altezzaImmagine, MyPanel panel) throws IOException
	{
		super();
		this.setPreferredSize(new Dimension(larghezza, altezzaImmagine+10));
		this.setBackground(Color.GRAY);
		this.setLayout(null);
		
		this.lettore = lettoreFoll;
		this.lettoreCorrente = lettoreCorrente;
		this.stato = stato;
		
		listener = new MyListener();
		
		bottoneUtente = new BottoneUtente(getURL(lettore.getUrlFoto(), 150, 150), lettore, panel);
		bottoneUtente.setPreferredSize(new Dimension(150, 150));
		bottoneUtente.setBorder(BorderFactory.createLineBorder(Color.black,2));
		bottoneUtente.setBounds(10, 10, 150, 150);
		add(bottoneUtente);
		
		Text nomeUtente = new Text(lettore.getNome(), 18, Color.WHITE);
		nomeUtente.setBounds(10+bottoneUtente.getX() + (int)bottoneUtente.getPreferredSize().getWidth(), bottoneUtente.getY(), (int)nomeUtente.getPreferredSize().getWidth(), (int)nomeUtente.getPreferredSize().getHeight());
		add(nomeUtente);
		
		Text numeroFollow = new Text(new Integer(lettore.getNumeroFollowAltroLettore()).toString()+ " Follow", 16, Color.WHITE);
		numeroFollow.setBounds(10+bottoneUtente.getX() + (int)bottoneUtente.getPreferredSize().getWidth(), nomeUtente.getY()+(int)nomeUtente.getPreferredSize().getHeight(), (int)numeroFollow.getPreferredSize().getWidth(), (int)numeroFollow.getPreferredSize().getHeight());
		add(numeroFollow);
		
		Text numeroFollower = new Text(new Integer(lettore.getNumeroFollowerAltroLettore()).toString()+ " Follower", 16, Color.WHITE);
		numeroFollower.setBounds(10+bottoneUtente.getX() + (int)bottoneUtente.getPreferredSize().getWidth(), numeroFollow.getY()+(int)numeroFollow.getPreferredSize().getHeight(), (int)numeroFollower.getPreferredSize().getWidth(), (int)numeroFollower.getPreferredSize().getHeight());
		add(numeroFollower);
		
		creaStato(stato);
		
	}
	
	public Image getURL(String stringa, int w, int h) throws IOException
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
		Image scaledImageFumetto = bufferedImage.getScaledInstance(w, h, bufferedImage.SCALE_AREA_AVERAGING);
		
		file.deleteOnExit();
		
		return scaledImageFumetto;
	}
	
	public Image getImage()
	{
		return bottoneUtente.getImageScaled();
	}
	
	public void cambiaStato(int stato)
	{
		this.stato = stato;
		if (stato == FOLLOWING)
		{
		
				lettoreCorrente.segui(lettore);
		}
		else if (stato == FOLLOW)
		{
			
				lettoreCorrente.nonSeguire(lettore);
	
		}
		creaStato(stato);
	}
	
	private void creaStato(int stato)
	{
		if(bottonePerFollow != null)
			remove(bottonePerFollow);
		
		if (stato == FOLLOWING)
		{
			bottonePerFollow = new MyButton("Following", 18, Color.WHITE);
			bottonePerFollow.addMouseMotionListener(new MouseMotionAdapter() {
			      public void mouseMoved(MouseEvent event) {
			    	  bottonePerFollow.setText("Unfollow");
			      }
			    });
			bottonePerFollow.addMouseListener(new MouseAdapter() {
			      public void mouseExited(MouseEvent event) {
			    	  bottonePerFollow.setText("Following");
			        }
			      });
		}
		else if (stato == FOLLOW)
			bottonePerFollow = new MyButton("Follow", 18, Color.WHITE);
		
		bottonePerFollow.setBounds((int)this.getPreferredSize().getWidth() - 100, (int)bottoneUtente.getPreferredSize().getHeight()/3, (int)bottonePerFollow.getPreferredSize().getWidth(), (int)bottonePerFollow.getPreferredSize().getHeight());
		bottonePerFollow.addActionListener(listener);
		add(bottonePerFollow);
		
		repaint();
	}
	
	@Override
	public void paintComponents(Graphics g)
	{
		super.paintComponents(g);
		g.drawImage(this.getImage(), 0, 0, this);
	}
	
	private class MyListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			
			if(source == bottonePerFollow)
			{
				if (stato == FOLLOWING)
				{
					cambiaStato(FOLLOW);
					creaStato(FOLLOW);
				}
				else
				{
					cambiaStato(FOLLOWING);
					creaStato(FOLLOWING);
				}
			}
		}
	}
	
}
