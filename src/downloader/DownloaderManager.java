package downloader;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;

public class DownloaderManager {
	
	private static DownloaderManager istanza;
	private Lock lock = new ReentrantLock();
	private final Condition conditionFine = lock.newCondition();
	private final Condition condition = lock.newCondition();

	private String[] urlImmagini;
	private Image[] immagini;
	
	private int numeroCongelati;
	private int size;
	private int numeroUltimaPronta;
	private int numeroPronte;
	private ImageDownloader donwloader1;
	private ImageDownloader donwloader2;
	private ImageDownloader donwloader3;
	private ImageDownloader donwloader4;
	
	private DownloaderManager(){
		numeroUltimaPronta = -1;
		numeroCongelati = 0;
		size = 0;
		urlImmagini = null;
		immagini = null;
		numeroPronte = 0;
		
		donwloader1 = new ImageDownloader(this,0);
		donwloader2 = new ImageDownloader(this,1);
		donwloader3 = new ImageDownloader(this,2);
		donwloader4 = new ImageDownloader(this,3);
		
		donwloader1.start();
		donwloader2.start();
		donwloader3.start();
		donwloader4.start();
	
	}
	
	public static DownloaderManager getDownloaderManager(){
		if(istanza == null)
			istanza = new DownloaderManager();
		return istanza;
	}
	
	public void scaricaImmagini(String[] urls){
		lock.lock();
		size = urls.length;
		urlImmagini = urls;
		immagini = new Image[size];
		donwloader1.setIndice(0);
		donwloader1.setIndice(1);
		donwloader1.setIndice(2);
		donwloader1.setIndice(3);
		if(numeroCongelati>0)
			conditionFine.signal();
		numeroCongelati = 0;
		lock.unlock();
	}
	
	public String[] getUrlImmagini() {
		return urlImmagini;
	}
	public Image[] getImmagini() {
		return immagini;
	}
	public int getSize() {
		return size;
	}
	
	void scarica(int indice, boolean controllo) throws IOException{
		lock.lock();
		while(indice >= size){
			try {
				numeroCongelati++;
				conditionFine.await();
				
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(controllo== true)
			try {
				condition.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		
//		URL url = new URL(urlImmagini[indice]);
//		immagini[indice] = ImageIO.read(url);
		System.out.println(indice ); 
		numeroPronte++;
		lock.unlock();
		
	}
	public Image prossimaImmagine(){
		return null;
	}

	public static void main(String[] args) {
		DownloaderManager d= DownloaderManager.getDownloaderManager();
		d.scaricaImmagini(new String[12]);
		
	}
	
}
