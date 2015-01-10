package downloader;

import java.awt.Image;

public class CopertineDownloader extends Downloader{

	private static CopertineDownloader istanza;
	
	public static CopertineDownloader getDownloader(){
		if(istanza == null)
			istanza = new CopertineDownloader();
		return istanza;
	}
	
	public void scaricaCopertine(String[] urlCopertine, Image[] copertine){
		scaricaImmagini(urlCopertine, copertine);
		ScaricatoreImmagini secondImageDownloader = new ScaricatoreImmagini(this);
		secondImageDownloader.start();
	}
}
