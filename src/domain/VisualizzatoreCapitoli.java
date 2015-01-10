package domain;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import downloader.PagineDownloader;
import downloader.ScaricamentoImmagineIncompletoException;

public class VisualizzatoreCapitoli {
	
	private static final String PATH_GIF = "image/loading.gif";
	private final PagineDownloader downloader = PagineDownloader.getDownloader();

	private static VisualizzatoreCapitoli istanza;
	
	private  static Image gifCaricamento; 

	private Capitolo[] capitoliDaLeggere;
	private Capitolo capitoloCorrente;
	private Image[] pagineCapitoloCorrente;
	private int numeroPaginaCorrente;
	private int indiceCapitoloCorrente;
	
	
	private VisualizzatoreCapitoli(){
		try {		
			istanza = null;
			gifCaricamento = ImageIO.read(new File(PATH_GIF));
			capitoliDaLeggere = null;
			capitoloCorrente = null;
			pagineCapitoloCorrente = null;
			numeroPaginaCorrente = 0;
			indiceCapitoloCorrente = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static VisualizzatoreCapitoli getVisualizzatoreCapitoli(){
		if(istanza == null){
			istanza = new VisualizzatoreCapitoli();
		}
		return istanza;
	}
	
	public void visualizzaCapitolo(Capitolo[] capitoli,int numeroCapitoloDaLeggere, int primaPaginaDaVisualizzare){
		
	
		capitoliDaLeggere = capitoli;
		indiceCapitoloCorrente = numeroCapitoloDaLeggere - capitoliDaLeggere[0].getNumero();
		capitoloCorrente = capitoliDaLeggere[indiceCapitoloCorrente];
		capitoloCorrente.setPagine();
		pagineCapitoloCorrente = capitoloCorrente.getPagine();
		
		numeroPaginaCorrente = primaPaginaDaVisualizzare;
		
		downloader.scaricaPagineCapitolo(capitoloCorrente.getUrlCapitolo(), pagineCapitoloCorrente, 
				capitoloCorrente.getNumeroPagine(), numeroPaginaCorrente);		
	}
	
	public Image visualizzaPaginaCorrente(){
		
			try {
				Image pagina = downloader.getImmagineScaricata(numeroPaginaCorrente);
				numeroPaginaCorrente++;
				return pagina;
			} catch (ScaricamentoImmagineIncompletoException e) {
				//TODO inizializzare gif
				return gifCaricamento;
			}
	}
	
	public boolean haPaginaSuccessiva(){
		if(numeroPaginaCorrente  == capitoloCorrente.getNumeroPagine()) return false;
		return true;
	}
	
	public boolean haPaginaPrecedente(){
		if(numeroPaginaCorrente == 1) return false;
		return true;
	}
	
	public boolean paginaSuccesiva(){
		boolean boolRitorno;
		if(boolRitorno = haPaginaSuccessiva()){
			numeroPaginaCorrente++;
		}
		else if(haCapitoloSuccessivo())
			capitoloSuccessivo();
		return boolRitorno;
	}
	
	public boolean paginaPrecedente(){
		boolean boolRitorno;
		if(boolRitorno = haPaginaPrecedente()){
			numeroPaginaCorrente--;
		}
		else if(haCapitoloPrecedente())
			capitoloPrecedente();
		return boolRitorno;
	}
	
	public boolean haCapitoloSuccessivo(){
		if(indiceCapitoloCorrente + 1 == capitoliDaLeggere.length) return false;
		return true;
	}
	
	public boolean haCapitoloPrecedente(){
		if(indiceCapitoloCorrente == 0)return false;
		return true;
	}
	
	public boolean capitoloSuccessivo(){
		boolean boolRitorno;
		if(boolRitorno=haCapitoloSuccessivo()){
			indiceCapitoloCorrente++;
			capitoloCorrente = capitoliDaLeggere[indiceCapitoloCorrente];
			pagineCapitoloCorrente = capitoloCorrente.getPagine();
			numeroPaginaCorrente = 1;
			
			downloader.scaricaPagineCapitolo(capitoloCorrente.getUrlCapitolo(), pagineCapitoloCorrente, 
					capitoloCorrente.getNumeroPagine(), numeroPaginaCorrente);	
		}
		return boolRitorno;
	}
	
	public boolean capitoloPrecedente(){
		boolean boolRitorno;
		if(boolRitorno=haCapitoloPrecedente()){
			indiceCapitoloCorrente--;
			capitoloCorrente = capitoliDaLeggere[indiceCapitoloCorrente];
			pagineCapitoloCorrente = capitoloCorrente.getPagine();
			numeroPaginaCorrente = 1;
			downloader.scaricaPagineCapitolo(capitoloCorrente.getUrlCapitolo(), pagineCapitoloCorrente, 
					capitoloCorrente.getNumeroPagine(), numeroPaginaCorrente);			
		}
		return boolRitorno;		
	}
}
