package ui;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.JFrame;

import technicalService.GestoreDataBase;


public class Main extends Thread{

	public static void main(String[] args) throws Exception 
	{
		GestoreDataBase.connetti();
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

	private static void deleteSng(String ng){
		try {
			File temp = File.createTempFile("preferiti",".txt");
			temp.deleteOnExit();

	        BufferedWriter out = new BufferedWriter(new FileWriter(temp));
	        PrintWriter pw = new PrintWriter(out,true);
	        BufferedReader in = new BufferedReader(new FileReader(ng));
	        	        
	        //scandisco le righe del file e non considero la riga che voglio eliminare
	        String linea = in.readLine();
	        while (linea != null) {
				if (!linea.equals(ng+".xml")) {
					pw.println(linea);
				}
				linea = in.readLine();
	       }

	        in.close();
	        out.close();
	        //pw.close();


	        File file= temp;
	        FileOutputStream fos=new FileOutputStream(file);
	        
	    } catch (IOException e) {
	    }
	}
	
}
