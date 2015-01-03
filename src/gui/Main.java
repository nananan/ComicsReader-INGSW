package gui;
import java.io.IOException;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) throws IOException 
	{
		JFrame f = new FrameLogin();
//		while (!((FrameLogin) f).getPanel().pareCheHaInseritoTutto())
//		{
////			System.out.println("non devo fare niente");
//		}
//		f.setVisible(false);
		f = new MyFrame(((FrameLogin) f).getPanel().getTextName());
		f.repaint();
	}

}
