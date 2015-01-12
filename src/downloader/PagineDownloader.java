package downloader;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PagineDownloader {
	
	protected static final int MAX_NUMERO_DOWNLOADER_INIZIALI = 4;
	protected static final int MAX_NUMERO_DOWNLOADER_SUCCESSIVI = 4;
	
	static final int PRONTO = 0;
	static final int DOWNLOADING = 1;
	static final int PAUSA = 2;
	static final int COMPLETATO = 3;
	
	Lock lock = new ReentrantLock();
	Condition condizioneDiPausa = lock.newCondition();
	
	int stato;
	
	int numeroDownloadCompletato;
	
	protected CyclicBarrier barriereDownloaderIniziali;

	protected String[] urlsPagine;
	protected Image[] pagine;
	
	protected int indiceUrl;

	protected DownloaderIniziali[] dowloadersIniziali;
	protected DownloaderSuccessivi[] downloaderSuccessivi;
	
	int numeroDownloaderIniziali;
	int numeroDonwloaderSuccessivi;
	private int downloaderInPausa;
	
	
	
	public PagineDownloader(String urlCapitolo, Image[] pagine, int numeroPagine, 
			int numeroPrimaPagine) throws ImmagineNonPresenteException{
		
		urlsPagine = new String[numeroPagine];
		this.pagine = pagine;
		stato = PRONTO;
		downloaderInPausa = 0;
		
		for(int i = 0; i < numeroPagine; i++){
			urlsPagine[i] = urlCapitolo +"/"+ (i+1);
		}
		
		numeroDownloaderIniziali = MAX_NUMERO_DOWNLOADER_INIZIALI;
		if(numeroPagine < MAX_NUMERO_DOWNLOADER_INIZIALI) numeroDownloaderIniziali = numeroPagine;
		
		barriereDownloaderIniziali = new CyclicBarrier(numeroDownloaderIniziali + 1);
		
		dowloadersIniziali = new DownloaderIniziali[numeroDownloaderIniziali];
		for(int i = 0; i < numeroDownloaderIniziali; i++, indiceUrl++){
			dowloadersIniziali[i] = new DownloaderIniziali(indiceUrl, this, urlsPagine[indiceUrl]);
		}
				
		int minIndice = 4;
		int maxIndice = numeroPagine;
		
		numeroDonwloaderSuccessivi = MAX_NUMERO_DOWNLOADER_SUCCESSIVI;
		
		while ((maxIndice - minIndice + 1) / numeroDonwloaderSuccessivi == 0)
			numeroDonwloaderSuccessivi--;
		
		int fettaIndici = (maxIndice - minIndice + 1) / numeroDonwloaderSuccessivi;
		
		downloaderSuccessivi = new DownloaderSuccessivi[numeroDonwloaderSuccessivi];
		for(int i = 0; i < numeroDonwloaderSuccessivi - 1; i++){
//			System.out.println("*****************************************************");
//			System.out.println("DonwloaderSuccessivo numero "+i);
			downloaderSuccessivi[i] = new DownloaderSuccessivi(minIndice + i * fettaIndici,
					minIndice + (i + 1)* fettaIndici -1,this);
			System.out.println("");
		}
		downloaderSuccessivi[numeroDonwloaderSuccessivi - 1] = 
				new DownloaderSuccessivi(minIndice + (numeroDonwloaderSuccessivi - 1)*fettaIndici,
						maxIndice -1,this);
		
	}
	
	
	public void iniziaDownload(){
		
		avviaDownloaderIniziali();
		avviaDowloaderSuccessivi();
		
	}
	
	public void restartDownload(){
		lock.lock();
		stato = DOWNLOADING;
		if(downloaderInPausa >0){
			condizioneDiPausa.signalAll();
			downloaderInPausa = 0;		
		}
		lock.unlock();
	}
	
	public void stopDownload(){
		stato = PAUSA;
	}
	
	public boolean dowloadECompleto(){
		if(stato == COMPLETATO) return true;
		else if(numeroDownloadCompletato == numeroDonwloaderSuccessivi){
			stato = COMPLETATO;
			return true;
		}
		return false;
	}
	
	public String[] getUrlsPagine() {
		// TODO Auto-generated method stub
		return urlsPagine;
	}
	
	public void chiudiDownload(){
		stato=COMPLETATO;
	}
	
	public Image getImmagineScaricata(int i) throws ScaricamentoImmagineIncompletoException{
		if(pagine[i] == null) throw new ScaricamentoImmagineIncompletoException(urlsPagine[i]);
		
		return pagine[i];
	}
	
	private void avviaDownloaderIniziali(){
		for(int i = 0; i < numeroDownloaderIniziali; i++){
			dowloadersIniziali[i].start();
		}
		try {
			barriereDownloaderIniziali.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stato = DOWNLOADING;

	}
	
	private void avviaDowloaderSuccessivi(){
		for(int i = 0; i < numeroDonwloaderSuccessivi; i++)
			downloaderSuccessivi[i].start();
	}
	
	CyclicBarrier getBarrieraDownloaderIniziali(){
		
		return barriereDownloaderIniziali;
	}
	
	void incrementaDownloaderConclusi(){
	
			numeroDownloadCompletato++;
		
	}
	
	void inserisciPagine(Image immagine,int indice) {
		System.out.println(pagine);
		pagine[indice] = immagine;
	}
	
	void completaDownload(){
			stato = COMPLETATO;
	}
	
	void verificaPausaDownloader(){
		lock.lock();
		try{
			while(stato == PAUSA){
				downloaderInPausa++;
				condizioneDiPausa.await();
					
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}

	void risvegliaDownloader() {
		if(stato == DOWNLOADING && downloaderInPausa >0){
			condizioneDiPausa.signalAll();
			downloaderInPausa = 0;		
		}
	}
	
	int getNumeroDownloaderSuccessivi() {
		return numeroDonwloaderSuccessivi;
	}
	
	public static void main(String[] args) throws ImmagineNonPresenteException {
			
		String urlCapitolo = "http://5.196.65.101/~ComicsReader/Fumetti/Death%20Note/Volume%2001/1";
		int numeroPagine = 49;

		Image[] pagine = new Image[numeroPagine];

		PagineDownloader downloader = new PagineDownloader(urlCapitolo, pagine, numeroPagine, 1);
		

		downloader.iniziaDownload();
		
		
		downloader.stopDownload();
		
       downloader.restartDownload();
		
		while(!downloader.dowloadECompleto()){
			for(Image i : pagine)
				System.out.println(i);
		}
		
	}



	}
