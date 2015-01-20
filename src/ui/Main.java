package ui;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;

import technicalService.GestoreDataBase;


public class Main extends Thread{

	public static void main(String[] args) throws IOException 
	{
		JFrame f = new FrameLogin();
		while (!((FrameLogin) f).getPanel().pareCheHaInseritoTutto())
		{
			try
			{
				sleep(100);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		f.setVisible(false);
		f = new MyFrame(((FrameLogin) f).getPanel().getLettore());
		f.repaint();
		GestoreDataBase.disconnetti();
	}

}
