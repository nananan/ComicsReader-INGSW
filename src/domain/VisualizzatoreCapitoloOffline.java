package domain;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VisualizzatoreCapitoloOffline
{
	private static VisualizzatoreCapitoloOffline istanza;
	private int numeroPaginaCorrente;
	private int numeroPagine;
	private File[] pagine;
	
	private VisualizzatoreCapitoloOffline()
	{
		istanza = null;
		numeroPaginaCorrente = 0; 
	}
	
	public static VisualizzatoreCapitoloOffline getVisualizzatoreCapitoli(){
		if(istanza == null){
			istanza = new VisualizzatoreCapitoloOffline();
		}
		return istanza;
	}
	
	public void visualizzaCapitoli(File[] pagine, int primaPaginaDaVisualizzare){
			
//		pagineCapitoloCorrente = capitoloCorrente.getPagine();
		this.pagine = pagine;
		
		numeroPaginaCorrente = primaPaginaDaVisualizzare-1;
		numeroPagine = pagine.length;
	}
	
	public Image visualizzaPaginaCorrente()
	{
		Image pagina = null;
		try
		{
			pagina = ImageIO.read(pagine[numeroPaginaCorrente]);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagina;
	}
	
	public boolean haPaginaSuccessiva(){
		if(numeroPaginaCorrente+1  == numeroPagine) return false;
		return true;
	}
	
	public boolean haPaginaPrecedente(){
		if(numeroPaginaCorrente == 0) return false;
		return true;
	}
	
	public boolean paginaSuccesiva(){
		boolean boolRitorno;
		if(boolRitorno = haPaginaSuccessiva()){
			numeroPaginaCorrente++;
		}
		return boolRitorno;
	}
	
	public boolean paginaPrecedente(){
		boolean boolRitorno;
		if(boolRitorno = haPaginaPrecedente()){
			numeroPaginaCorrente--;
		}
		return boolRitorno;
	}
}
