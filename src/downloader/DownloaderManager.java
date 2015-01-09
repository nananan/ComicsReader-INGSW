package downloader;

import java.awt.Image;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class DownloaderManager {
	
	private static final int NUMERO_DOWNLOADER = 4;

	private static DownloaderManager istanza;

	private CyclicBarrier barriera;

	private String[] urlImmagini;
	private Image[] immagini;
	
	private int indiceUrl;
	private int immaginiDaScaricare;
	
	private ImageDownloader[] dowloaders;
	
	
	private DownloaderManager(){
		barriera =null;
		dowloaders = new ImageDownloader[NUMERO_DOWNLOADER];
		indiceUrl = 0;
		immaginiDaScaricare = 0;
		for(int i = 0; i < NUMERO_DOWNLOADER; i++){
			dowloaders[i] = new ImageDownloader(this);
		}
	
	}
	
	public static DownloaderManager getDownloaderManager(){
		if(istanza == null)
			istanza = new DownloaderManager();
		return istanza;
	}
	
	public boolean downloadConcluso(){
		if(immaginiDaScaricare == 0) return true;
		return false;
	}
	int await() throws InterruptedException, BrokenBarrierException{
		return barriera.await();
	}
	
	private void scaricaImmagini(){
		
		int cont=0;
		int numero = NUMERO_DOWNLOADER;
		
		if(NUMERO_DOWNLOADER > immaginiDaScaricare) numero = immaginiDaScaricare;
		
		for(int i = 0; i < numero; i++){
			dowloaders[i] = new ImageDownloader(this);
		}
		
		barriera = new CyclicBarrier(numero+1);
		
		for(;cont < NUMERO_DOWNLOADER && indiceUrl < urlImmagini.length;indiceUrl ++,cont++){
			
			dowloaders[cont].setUrl(urlImmagini[indiceUrl],indiceUrl);
			dowloaders[cont].start();
		}
		try {
			barriera.await();
			barriera.reset();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		immaginiDaScaricare -=cont;
	}
	
	public void scaricaCopertine(String[] urlCopertine, Image[] copertine){
		scaricaImmagini(urlCopertine, copertine);
		ScaricatoreImmagini secondImageDownloader = new ScaricatoreImmagini();
		secondImageDownloader.start();
	}
	
	public void scaricaPagineCapitolo(String urlCapitolo, Image[] pagine, int numeroPagine, 
			int numeroPrimaPagine){
		
		String[] urlsPagine = new String[numeroPagine];
		
		for(int i = 0; i < numeroPagine; i++){
			urlsPagine[i] = urlCapitolo +"/"+ (i+1);
		}
		scaricaImmagini(urlsPagine, pagine);
		ScaricatoreImmagini secondImageDownloader = new ScaricatoreImmagini();
		secondImageDownloader.start();
	}
	
	void scaricaProssimeImmagini(){
		while(!downloadConcluso()){
			scaricaImmagini();
		}
	}
	private void scaricaImmagini(String[] urls, Image[] images){
		
		urlImmagini = urls;
		immagini = images;
		immaginiDaScaricare = urls.length;
		int numero = NUMERO_DOWNLOADER;
		
		for(int i = 0; i < numero; i++){
			dowloaders[i] = new ImageDownloader(this);
		}
		
		if(NUMERO_DOWNLOADER > urls.length) numero = urls.length;
		barriera = new CyclicBarrier(numero + 1);
		for(;indiceUrl < NUMERO_DOWNLOADER && indiceUrl < urls.length;indiceUrl ++){
			
			dowloaders[indiceUrl].setUrl(urls[indiceUrl],indiceUrl);
			dowloaders[indiceUrl].start();
		}
		try {
			barriera.await();
			barriera.reset();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		immaginiDaScaricare -=indiceUrl;
//		System.out.println(immaginiDaScaricare);
	}
	
	void inserisciImmagine(Image immagine,int indice) {
		immagini[indice] = immagine;
	}
//	
//	public static void main(String[] args) {
//
//		
//		String urlCapitolo = "http://5.196.65.101/~ComicsReader/Fumetti/Death%20Note/Volume%2001/1";
//		int numeroPagine = 49;
//		Image[] pagine = new Image[numeroPagine];
//		long startTime = System.currentTimeMillis();		
//		DownloaderManager.getDownloaderManager().scaricaPagineCapitolo(urlCapitolo, pagine, numeroPagine, 1);
//		for(int  i = 0;i<4;i++)
//		System.out.println(pagine[i]);
//
//	
//		 while(!DownloaderManager.getDownloaderManager().downloadConcluso())
//			 for(Image image : pagine){
//				 System.out.println(image);
//		 }
//		long endTime = System.currentTimeMillis();		
//
//		 long seconds = (endTime - startTime) / 1000;
//		 System.out.println(seconds);
//	}
		
		
}

