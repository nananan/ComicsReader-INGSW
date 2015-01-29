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
}
