package domain;

import java.awt.Image;

import technicalService.GestoreDataBase;
import domain.Capitolo;
import downloader.DownloaderPagine;

public class VisualizzatoreCapitoli {
	
	private Image[][] pagine;
	
	private int indicePaginaCentrale;
	private int indiceCapitoloPaginaCentrale;
	private String fumettoDaVisualizzare;
	private int volumeDaVisualizzare;
	
	private DownloaderPagine downloaderPagine;
	private static VisualizzatoreCapitoli istanza;
	
	
	private VisualizzatoreCapitoli() {
		downloaderPagine= new DownloaderPagine();
	}

	public static VisualizzatoreCapitoli getIstanza(){
		if(istanza == null)
			istanza = new VisualizzatoreCapitoli();
		return istanza;
	}
	public void mettiSegnalibro(){
		GestoreDataBase.getIstanza().aggiungiSegnalibro(AppManager.getLettore().getIdFacebook(), fumettoDaVisualizzare, 
				volumeDaVisualizzare, indiceCapitoloPaginaCentrale+1, indicePaginaCentrale+1);
	}
	public void visualizzaCapitoli(Volume volume,int numeroCapitoloPagina, int numeroPaginaIniziale){
		
		indiceCapitoloPaginaCentrale =numeroCapitoloPagina-1;
		indicePaginaCentrale = numeroPaginaIniziale-1;
		
		Capitolo[] capitoliDaVisualizzare = volume.getCapitoli();
		fumettoDaVisualizzare = volume.getNomeFumetto();
		volumeDaVisualizzare = volume.getNumero();
		String url = volume.getUrlCopertina();
		String[] stringhe = url.toString().split("/Copertina");
		
		pagine = new Image[capitoliDaVisualizzare.length][]; 
		
		for(int i = 0;i < capitoliDaVisualizzare.length;i++)
		{
			pagine[i] = new Image[capitoliDaVisualizzare[i].getNumeroPagine()];
		}
		
		downloaderPagine.iniziaScaricamento(pagine,stringhe[0],indiceCapitoloPaginaCentrale,indicePaginaCentrale);
	}
	public int numeroPagina(){
		return indicePaginaCentrale+1;
	}
	public Image visualizzaPaginaCorrente(){
		return pagine[indiceCapitoloPaginaCentrale][indicePaginaCentrale];
	}
	
	public void paginaSuccessiva(){
		if(haPaginaSuccessiva()){
			downloaderPagine.scaricaPagineSuccessive();
			indicePaginaCentrale=downloaderPagine.getIndicePaginaCentrale();
			indiceCapitoloPaginaCentrale=downloaderPagine.getIndiceCapitoloPaginaCentrale();
		}
	}
	
	public void paginaPrecedente(){
		if(haPaginaPrecedente()){
			downloaderPagine.scaricaPaginaPrecedenti();
			indicePaginaCentrale = downloaderPagine.getIndicePaginaCentrale();
			indiceCapitoloPaginaCentrale = downloaderPagine.getIndiceCapitoloPaginaCentrale();
		}
	}

	public void capitoloSuccessivo(){
		if(haCapitoloSuccessivo()){
			downloaderPagine.scaricaPagineCapitoloSuccessivo();
			indicePaginaCentrale = downloaderPagine.getIndicePaginaCentrale();
			indiceCapitoloPaginaCentrale = downloaderPagine.getIndiceCapitoloPaginaCentrale();
		}
	}
	
	public void capitoloPrecedente(){
		if(haCapitoloPrecedente()){
			downloaderPagine.scaricaPagineCapitoloPrecedente();
			indicePaginaCentrale = downloaderPagine.getIndicePaginaCentrale();
			indiceCapitoloPaginaCentrale = downloaderPagine.getIndiceCapitoloPaginaCentrale();
		}
	}
	
	
	public boolean haPaginaSuccessiva(){
		if(indicePaginaCentrale+1==pagine[indiceCapitoloPaginaCentrale].length && 
				!haCapitoloSuccessivo())
			return false;
		return true;
	}
	
	public boolean haPaginaPrecedente(){
		if(indicePaginaCentrale == 0 && !haCapitoloPrecedente())
			return false;
		return true;
	}
	
	public boolean haCapitoloSuccessivo(){
		if(indiceCapitoloPaginaCentrale+1==pagine.length)
			return false;
		return true;
	}
	
	public boolean haCapitoloPrecedente(){
		if(indiceCapitoloPaginaCentrale == 0)
			return false;
		return true;
	}
}
