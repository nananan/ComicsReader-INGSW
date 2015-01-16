package downloader;

import java.awt.Image;
import java.sql.SQLException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import domain.Fumetto;
import technicalService.DataBase;
import technicalService.TuplaFumetto;
import technicalService.TuplaFumetto;

public class CopertinaVolumeDownloaderManager
{

	private static CopertinaVolumeDownloaderManager istanza;
	
	protected static final int MAX_NUMERO_DOWNLOADER = 4;
	
	static final int PRONTO = 0;
	static final int DOWNLOADING = 1;
	static final int PAUSA = 2;
	static final int COMPLETATO = 3;
	
	int stato;
	int dowloaderInPausa=0;
	int completati=0;
	boolean primiDownload =true;
	int contatore = 0;
	
	Lock lock = new ReentrantLock();
	protected CyclicBarrier barriera;
	private  CopertineVolumiDowloader[] downloader;
	private int numeroVolumiCorrenti;
	String[] urlCopertine;
	Image[] copertine;

	private int numeroDownloader;

	public static CopertinaVolumeDownloaderManager getCopertinaDowloader()
	{
		if(istanza == null)
			istanza = new CopertinaVolumeDownloaderManager();
		return istanza;
	}
	
	public void scarica(String[] url, Image[] copertine, int numeroVolumi)
	{
		lock.lock();
		this.urlCopertine = url;
		this.copertine = copertine;
		numeroVolumiCorrenti =numeroVolumi;
		contatore = 0;
		avviaThread();
		stato=DOWNLOADING;
		lock.unlock();
	}
	public void continuaDownload(){
		avviaThread();
	}
	private void avviaThread()
	{
		numeroDownloader = MAX_NUMERO_DOWNLOADER;
		if(contatore + MAX_NUMERO_DOWNLOADER > numeroVolumiCorrenti) 
			numeroDownloader = numeroVolumiCorrenti - contatore;
		barriera = new CyclicBarrier(numeroDownloader+1); 
		downloader=new CopertineVolumiDowloader[MAX_NUMERO_DOWNLOADER];
		for(int i = 0; i < numeroDownloader && contatore < numeroVolumiCorrenti; i++,contatore++){
			downloader[i] = new CopertineVolumiDowloader(contatore);
			downloader[i].start();
		}
		barrieraWait();		
	}
	
	void inserisciPagine(Image copertina, int indiceCopertina)
	{
		copertine[indiceCopertina] = copertina;
	}
	void barrieraWait()
	{
		try
		{
			barriera.await();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNumeroDownloaders()
	{
		return numeroDownloader;
	}
}
