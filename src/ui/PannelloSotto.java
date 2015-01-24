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
	private Text indicePagina;
	private ImageIcon imageNext = new ImageIcon("image/next.png");
	private ImageIcon imagePrev = new ImageIcon("image/prev-icon.png");
	
	private JButton next = new JButton();
	private JButton prev = new JButton();
	private MyButton capitoloPrecedente;
	private MyButton capitoloSuccessivo;
	
	private MyPanel panel;
	private int visualizzatore;

	private MyListener listener;
	
	public PannelloSotto(final MyPanel panel, int larghezzaPannelloSinistra, int larghezzaPannelloDestro) 
	{
		int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//		
//		setBounds(180, 30, larghezza-200, altezza-50);
		
		setBackground(new Color(91,84,84));
		setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 50));
		
		setBorder(BorderFactory.createLineBorder(Color.black,1));
		setLayout(null);
		
		this.panel = panel;
		listener = new MyListener();
		
		capitoloPrecedente = new MyButton("Capitolo Precedente", 13, Color.WHITE, new Color(35,148,188));
		capitoloPrecedente.setBounds(larghezzaPannelloSinistra, (int)getPreferredSize().getHeight()/4, 
				(int)capitoloPrecedente.getPreferredSize().getWidth(), (int)capitoloPrecedente.getPreferredSize().getHeight());
		add(capitoloPrecedente);
		capitoloPrecedente.addActionListener(listener);
		
		capitoloSuccessivo = new MyButton("Capitolo Successivo", 13, Color.WHITE, new Color(35,148,188));
		capitoloSuccessivo.setBounds(larghezza - larghezzaPannelloDestro - (int)capitoloSuccessivo.getPreferredSize().getWidth(), 
				(int)getPreferredSize().getHeight()/4, 
				(int)capitoloSuccessivo.getPreferredSize().getWidth(), (int)capitoloSuccessivo.getPreferredSize().getHeight());
		add(capitoloSuccessivo);
		capitoloSuccessivo.addActionListener(listener);
		
		//PLAY
		indicePagina = new Text("Pagina: ", 14, Color.WHITE);
		indicePagina.setBounds((int)getPreferredSize().getWidth()/2, (int)getPreferredSize().getHeight()/4,
				(int)indicePagina.getPreferredSize().getWidth(),
				(int)indicePagina.getPreferredSize().getHeight());
		
		//NEXT
		Image imageScaled = imageNext.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		imageNext.setImage(imageScaled);
		
		next.setIcon(imageNext);
		next.setPressedIcon(imageNext);
		next.setBorderPainted(false);
		next.setFocusPainted(false);
		next.setBackground(getBackground());
		next.setBounds(indicePagina.getX() + (int)indicePagina.getPreferredSize().getWidth()*2, 
				(int)getPreferredSize().getHeight()/4, imageNext.getIconWidth(), imageNext.getIconHeight());
		
		//PREV
		imageScaled = imagePrev.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		imagePrev.setImage(imageScaled);
		
		prev.setIcon(imagePrev);
		prev.setPressedIcon(imagePrev);
		prev.setBorderPainted(false);
		prev.setFocusPainted(false);
		prev.setBackground(getBackground());
		prev.setBounds(indicePagina.getX() - (int)indicePagina.getPreferredSize().getWidth()-20, 
				(int)getPreferredSize().getHeight()/4, imagePrev.getIconWidth(), imagePrev.getIconHeight());

		next.addActionListener(listener);
		prev.addActionListener(listener);

		add(indicePagina);
		add(next);
		add(prev);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	}
	
	public void setVisualizzatore(int visualizzatore)
	{
		this.visualizzatore = visualizzatore;
	}
	
	public int getVisualizzatore()
	{
		return visualizzatore;
	}
	
	public void setPagina(int numeroPagina)
	{
		indicePagina.removeAll();
		indicePagina.setText("Pagina: "+numeroPagina);
		indicePagina.setBounds((int)getPreferredSize().getWidth()/2, (int)getPreferredSize().getHeight()/4,
				(int)indicePagina.getPreferredSize().getWidth(),
				(int)indicePagina.getPreferredSize().getHeight());
		
		repaint();
	}
	
	public void setEnableBottoniSuccPrec(int indice, boolean bool)
	{
		if (indice == 0)
			capitoloPrecedente.setEnabled(bool);
		else
			capitoloSuccessivo.setEnabled(bool);
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
			else if (source == capitoloPrecedente)
				panel.premiPerCapitoloPrecedente();
			else if (source == capitoloSuccessivo)
				panel.premiPerCapitoloSuccessivo();
		}
	}

	public void setEnableBottoniNextPrev(int indice, boolean bool)
	{
		if (indice == 0)
			next.setEnabled(bool);
		else
			prev.setEnabled(bool);
	}

}
