package ui;

import javax.swing.JPanel;

public class ThreadCaricamento extends Thread
{
	private Caricabile pannello;
	private PannelloLoading loading;
	
	public ThreadCaricamento(Caricabile pane){
		pannello=pane;
		loading = PannelloLoading.getIstanza();
	}
	
	@Override
	public void run()
	{
		 while(true){
			 pannello.avviaCaricamento();
			 
		 }
	}
}
