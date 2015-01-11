package downloader;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class DownloaderSuccessivi extends Thread {
	
	private int maxIndice;
	private int indiceCorrente;
	private PagineDownloader pagineDownloaderManager;
	
	public DownloaderSuccessivi(int minIndice, int maxIndice, 
			PagineDownloader paginaDownloaderManager) {
		
		this.maxIndice = maxIndice;
		this.pagineDownloaderManager = paginaDownloaderManager;
		indiceCorrente = minIndice;
	}
	
	public void scarica() throws IOException{
		
		URL urlPagina = new URL(pagineDownloaderManager.getUrlsPagine()[indiceCorrente]);
		Image pagina = ImageIO.read(urlPagina);
		pagineDownloaderManager.inserisciPagine(pagina,indiceCorrente);
		
	}
	
	@Override
	public void run() {
		while(indiceCorrente <= maxIndice && pagineDownloaderManager.stato != pagineDownloaderManager.COMPLETATO){
			try {
				pagineDownloaderManager.verificaPausaDownloader();
				scarica();
				indiceCorrente++;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pagineDownloaderManager.incrementaDownloaderConclusi();	
	}
}
