package ui;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;

import technicalService.GestoreDataBase;


public class Main {

	public static void main(String[] args) throws IOException 
	{
		
		
			JFrame f = new FrameLogin();
			GestoreDataBase.connetti();
			while (!((FrameLogin) f).getPanel().pareCheHaInseritoTutto())
			{
				System.out.println("non devo fare niente");
			}
			f.setVisible(false);
			f = new MyFrame(((FrameLogin) f).getPanel().getLettore());
			f.repaint();
			GestoreDataBase.disconnetti();
	
		
		
	}

}
