package downloader;

import java.awt.Image;
import java.sql.SQLException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import domain.Fumetto;
import technicalService.GestoreDataBase;
import technicalService.TuplaFumetto;
import technicalService.TuplaFumetto;

public class CopertinaDowloaderManager
{
	private static CopertinaDowloaderManager istanza;
	
	protected static final int MAX_NUMERO_DOWNLOADER = 4;
	
	static final int PRONTO = 0;
	static final int DOWNLOADING = 1;
	static final int PAUSA = 2;
	static final int COMPLETATO = 3;
	
	int stato;
	int dowloaderInPausa=0;
	int completati=0;
	boolean primiDownload =true;
	
	Lock lock = new ReentrantLock();
	protected CyclicBarrier barriereDownloaderIniziali;
	private  CopertineDowloader[] downloader;
	
	String[] urlCopertine;
	Image[] copertine;
	

	public static CopertinaDowloaderManager getCopertinaDowloader()
	{
		if(istanza == null)
			istanza = new CopertinaDowloaderManager();
		return istanza;
	}
	
	public void scarica(String[] url, Image[] copertine)
	{
		lock.lock();
		barriereDownloaderIniziali = new CyclicBarrier(MAX_NUMERO_DOWNLOADER+1);
		this.urlCopertine = url;
		this.copertine = copertine;
		inizializza();
		stato=DOWNLOADING;
		lock.unlock();
	}
	
	private void inizializza()
	{
		downloader=new CopertineDowloader[MAX_NUMERO_DOWNLOADER];
		for(int i = 0; i < MAX_NUMERO_DOWNLOADER; i++){
			downloader[i] = new CopertineDowloader(i);
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
			barriereDownloaderIniziali.await();
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
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
//		DataBase.connetti();
//		String[] urls = new String[8];
//		Image[] copertine = new Image[8];
//		double st = System.currentTimeMillis();
//		TuplaFumetto tupla = d.creaTupleFumetto(0);
//		for(int i = 0;tupla.nextFumetto();i++){
//			urls[i] = tupla.getUrlCopertina();
//			Fumetto fumetto = new Fumetto(tupla);
//		}
//		CopertinaDowloaderManager manager = CopertinaDowloaderManager.getCopertinaDowloader();
//		manager.scarica(urls, copertine);
//		double end = System.currentTimeMillis();
//		for(Image ima:copertine)
//			System.out.println(ima);
//		System.out.println((end-st)/1000);
//		st = System.currentTimeMillis();
//		tupla = TabellaFumetto.generaProssimeTupleFumetto(8);
//		for(int i = 0;tupla.nextFumetto();i++){
//			urls[i] = tupla.getUrlCopertina();
//			Fumetto fumetto = new Fumetto(tupla);
//		}
//		for(int i=0;i<copertine.length;i++)
//			copertine[i]=null;
//		
//		manager.scarica(urls, copertine);
//	
//		for(Image ima:copertine)
//			System.out.println(ima);
//		tupla = TabellaFumetto.generaProssimeTupleFumetto(16);
//		int cont=0;
//		for(int i = 0;tupla.nextFumetto();i++,cont++){
//			urls[i] = tupla.getUrlCopertina();
//			Fumetto fumetto = new Fumetto(tupla);
//		}
//		for(int i = 8-1; i >= cont;--i)
//			urls[i] = null;
//			
//		for(int i=0;i<copertine.length;i++)
//			copertine[i]=null;
//		
//		manager.scarica(urls, copertine);
//		tupla = TabellaFumetto.generaProssimeTupleFumetto(16);

	}
}
