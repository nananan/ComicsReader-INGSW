package downloader;


import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BrokenBarrierException;

import javax.imageio.ImageIO;

public class ImageDownloader extends Thread{
	
	private Downloader downloader;
	private String urlImmagine;
	private int indiceImmagine;
	
	public ImageDownloader(Downloader manager) {
		downloader = manager;
	}

	@Override
	public void run(){
			try {
				scarica();
				downloader.await();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	public void scarica() throws MalformedURLException, IOException{
		Image immagine = ImageIO.read(new URL(urlImmagine));
		downloader.inserisciImmagine(immagine,indiceImmagine);
	}
	
	public void setUrl(String string, int indiceUrl) {
		urlImmagine = string;
		indiceImmagine = indiceUrl;
	}

}
