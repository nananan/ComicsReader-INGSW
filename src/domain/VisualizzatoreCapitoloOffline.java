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

//	public static void main(String[] args) throws SQLException, MalformedURLException, ClassNotFoundException {
//		
//		GestoreDataBase genereBase = GestoreDataBase.getIstanza();
//		TuplaFumetto tuplaF = genereBase.creaTuplaFumetto("666 Satan");
//		tuplaF.prossima();
//		Fumetto fumetto = new Fumetto(tuplaF);
//		fumetto.apriFumetto();
//		Volume[] volumi = fumetto.getVolumi();
//		volumi[0].caricaCapitoli();
//		Capitolo[] capitoli = volumi[0].getCapitoli();
//		System.out.println(capitoli.length);
//		double startTime = System.currentTimeMillis();		
//
//		VisualizzatoreCapitoli visualizzatoreCapitoli = VisualizzatoreCapitoli.getVisualizzatoreCapitoli();
//		visualizzatoreCapitoli.visualizzaCapitoli(capitoli, 1, 1);
//		double endTime = System.currentTimeMillis();		
//
//		System.out.println(visualizzatoreCapitoli.visualizzaPaginaCorrente());
//		System.out.println((endTime - startTime)/1000);
//		visualizzatoreCapitoli.capitoloSuccessivo();
//		
//		GestoreDataBase.disconnetti();
//
//		
//	}	
}
