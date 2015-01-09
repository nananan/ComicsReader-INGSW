package downloader;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.peer.ContainerPeer;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.locks.Condition;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.xml.internal.ws.api.server.Container;

public class ImageDownloader extends Thread{
	
	private DownloaderManager downloaderManager;
	private int indice;
	private boolean controllo;
	
	public ImageDownloader(DownloaderManager manager, int i) {
		downloaderManager = manager;
		indice = i;
		controllo=false;
	}
	public void setIndice(int n){
		indice = n;
	}
	
	@Override
	public void run(){
		
		while(true){
			
			try {
				System.out.println("Indice "+this.indice);
				downloaderManager.scarica(indice,controllo);
				controllo= true;
				indice+=4;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	public boolean isControllo() {
		return controllo;
	}
	public void setControllo(boolean controllo) {
		this.controllo = controllo;
	}

}
