package downloader;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;


public class CopertineDowloader extends Thread
{
	private CopertinaDowloaderManager manager = CopertinaDowloaderManager.getCopertinaDowloader();
	private String urlCopertina1;
	private String urlCopertina2;
	private int indiceCopertina1;
	private int indiceCopertina2;
	
	
	public CopertineDowloader(int i)
	{
		indiceCopertina1 = i;
		indiceCopertina2 = i+4;
	}

	@Override
	public void run()
	{
			scarica();
			manager.barrieraWait();
		
	}
	
	public void scarica() {
		
		urlCopertina1 = manager.urlCopertine[indiceCopertina1];
		urlCopertina2 = manager.urlCopertine[indiceCopertina2];
		
		try
		{	
			if(urlCopertina1 != null){
				Image pagina1 = ImageIO.read(new URL(urlCopertina1));
				manager.inserisciPagine(pagina1,indiceCopertina1);
			}
			if(urlCopertina2 != null){
				Image pagina2 = ImageIO.read(new URL(urlCopertina2));
				manager.inserisciPagine(pagina2,indiceCopertina2);
			}
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
