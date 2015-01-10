package downloader;

import java.awt.Image;

public class PagineDownloader extends Downloader{
	
	private static PagineDownloader istanza;
	
	public static PagineDownloader getDownloader(){
		if(istanza == null)
			istanza = new PagineDownloader();
		return istanza;
	}
	public void scaricaPagineCapitolo(String urlCapitolo, Image[] pagine, int numeroPagine, 
			int numeroPrimaPagine){
		
		String[] urlsPagine = new String[numeroPagine];
		
		for(int i = 0; i < numeroPagine; i++){
			urlsPagine[i] = urlCapitolo +"/"+ (i+1);
		}
		scaricaImmagini(urlsPagine, pagine);
		ScaricatoreImmagini secondImageDownloader = new ScaricatoreImmagini(this);
		secondImageDownloader.start();
	}
}
