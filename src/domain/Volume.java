package domain;


import java.awt.Image;
import java.sql.SQLException;

import technicalService.GestoreDataBase;
import technicalService.TuplaCapitolo;
import technicalService.TuplaVolume;

public class Volume {
	
	private String nome;
	private int numero;
	private Image copertina;
	private String urlCopertina;
	private String nomeFumetto;
	
	private Capitolo[] capitoli;
	private int numeroCapitoli;
	private GestoreDataBase gestoreDB =GestoreDataBase.getIstanza();
	
	public Volume(TuplaVolume tuplaVolume) {
		
		nome = tuplaVolume.getNome();
		numero = tuplaVolume.getNumero();
		urlCopertina = tuplaVolume.getUrlCopertina();
		nomeFumetto = tuplaVolume.getNomeFumetto();	
		numeroCapitoli = 0;
		capitoli = null;
	}
	
	public void caricaCapitoli() {
		
		if(numeroCapitoli !=0) return;
		
		numeroCapitoli = gestoreDB.getNumeroCapitoli(nomeFumetto,numero);
		
		TuplaCapitolo tuplaCapitolo = gestoreDB.creaTuplaCapitolo(nomeFumetto,numero);
		int nPrimoC=gestoreDB.primoCapitoloVolume(nomeFumetto,numero);
		
		capitoli = new Capitolo[numeroCapitoli];
		while(tuplaCapitolo.prossima())
		{		
			Capitolo capitolo = new Capitolo(tuplaCapitolo);
			capitoli[capitolo.getNumero()-nPrimoC] = capitolo;
		}
		
		
	}
	public void setCopertina(Image copertina){
		this.copertina = copertina;
	}
	public String getNomeFumetto() {
		return nomeFumetto;
	}
	
	public int getNumeroCapitoli() {
		return numeroCapitoli;
	}
	
	@Override
	public int hashCode() {
		return numero;
	}
	
	public String getNome() {
		return nome;
	}

	public int getNumero() {
		return numero;
	}

	public Image getCopertina() {
		return copertina;
	}

	public String getUrlCopertina() {
		return urlCopertina;
	}
	
	public Capitolo[] getCapitoli(){
		return capitoli;
	}
}
