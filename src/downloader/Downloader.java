package downloader;

import java.awt.Image;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public abstract class Downloader {
	
	protected static final int NUMERO_DOWNLOADER = 4;

	protected CyclicBarrier barriera;

	protected String[] urlImmagini;
	protected Image[] immagini;
	
	protected int indiceUrl;
	protected int immaginiDaScaricare;
	
	protected ImageDownloader[] dowloaders;
		
	
	public boolean downloadConcluso(){
		
		if(immaginiDaScaricare == 0) return true;
			return false;
	}
	
	public Image getImmagineScaricata(int i) throws ScaricamentoImmagineIncompletoException{
		if(immagini[i] == null) throw new ScaricamentoImmagineIncompletoException(urlImmagini[i]);
		
		return immagini[i];
	}
	
	protected  Downloader() {
		barriera =null;
		dowloaders = new ImageDownloader[NUMERO_DOWNLOADER];
		indiceUrl = 0;
		immaginiDaScaricare = 0;
		for(int i = 0; i < NUMERO_DOWNLOADER; i++){
			dowloaders[i] = new ImageDownloader(this);
		}
	
	}
	protected void scaricaImmagini(String[] urls, Image[] images){
		
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
	protected void scaricaImmagini(){
		
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
	
	int await() throws InterruptedException, BrokenBarrierException{
		return barriera.await();
	}

	void inserisciImmagine(Image immagine,int indice) {
		immagini[indice] = immagine;
	}
	
	void scaricaProssimeImmagini(){
		while(!downloadConcluso()){
			scaricaImmagini();
		}
	}
	
	
	
	public static void main(String[] args) {
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
	}
	
}
