package downloader;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ScaricatorePagine extends Thread{
	private String url;
	private boolean downloadConcluso;
	private DownloaderPagine downloaderPagine;
	private Image pagina;
	private int indiceCapitolo;
	private int indicePagina;
	
	public ScaricatorePagine(String url, DownloaderPagine downloaderPagine,int indiceCapitolo,int indiceVolume){
		this.url=url;
		this.downloaderPagine = downloaderPagine;
		this.downloadConcluso = false;
		this.pagina = null;
		this.indiceCapitolo = indiceCapitolo;
		this.indicePagina = indiceVolume;
	}
	
	public void scarica(){
		try {
			pagina = ImageIO.read(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		scarica();
		downloaderPagine.inserisciImmagine(pagina, indiceCapitolo,indicePagina);
		downloaderPagine.fatto();
		downloadConcluso = true;
	}

	public boolean getCompleto() {
		return downloadConcluso;
	}

	public Image getCopertina() {
		return pagina;
	}

	public String getUrl() {
		return url;
	}
}
