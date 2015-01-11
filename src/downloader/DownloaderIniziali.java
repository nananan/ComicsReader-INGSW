package downloader;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BrokenBarrierException;

import javax.imageio.ImageIO;

public class DownloaderIniziali extends Thread{
	
	private int indiceImmagine;
	private PagineDownloader pagineDownloaderManager;
	private URL urlPagina;
	
	public DownloaderIniziali(int indiceUrl, PagineDownloader pagineDownloaderManager,
			String urlPagina) throws ImmagineNonPresenteException{
		
		try {
			indiceImmagine = indiceUrl;
			this.pagineDownloaderManager = pagineDownloaderManager;
			this.urlPagina = new URL(urlPagina);
		} catch (MalformedURLException e) {
			new ImmagineNonPresenteException(urlPagina);
		}
	}
	
	public void scarica() throws IOException{
		Image pagina = ImageIO.read(urlPagina);
		pagineDownloaderManager.inserisciPagine(pagina,indiceImmagine);
	}
	
	@Override
	public void run() {
		try {
			scarica();
			pagineDownloaderManager.getBarrieraDownloaderIniziali().await();
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
}
