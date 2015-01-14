package downloader;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class CopertineVolumiDowloader extends Thread
{
	private CopertinaVolumeDownloaderManager manager = CopertinaVolumeDownloaderManager.getCopertinaDowloader();
	private int indice;
	private String urlCopertina;
	
	public CopertineVolumiDowloader(int i)
	{
		indice = i;
		urlCopertina = manager.urlCopertine[indice];
	}
	@Override
	public void run()
	{	
		scarica();
		manager.barrieraWait();
	}
	public void scarica() {
		if(urlCopertina != null){
			Image pagina1;
			try
			{	
				pagina1 = ImageIO.read(new URL(urlCopertina));
				manager.inserisciPagine(pagina1,indice);
			
			} catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
