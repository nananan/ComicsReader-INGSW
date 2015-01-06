package domain;


import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import technicalService.TabellaCapitolo;
import technicalService.TabellaVolume;

public class Volume {
	
	private String nome;
	private int numero;
	private ImageIcon copertina;
	private String urlCopertina;
	private String nomeFumetto;
	
	private TabellaCapitolo tuplaCapitolo;
	private Capitolo[] capitoli;
	private int numeroCapitoli;
	
	public Volume(TabellaVolume tuplaVolume) throws SQLException, MalformedURLException {
		
		nome = tuplaVolume.getNome();
		numero = tuplaVolume.getNumero();
		urlCopertina = tuplaVolume.getUrlCopertina();
		nomeFumetto = tuplaVolume.getNomeFumetto();
		copertina = new ImageIcon(new URL(urlCopertina).toString());
	
		tuplaCapitolo = new TabellaCapitolo(nomeFumetto,numero);
		numeroCapitoli = 0;
		capitoli = null;
	}
	
	public void caricaCapitoli() throws SQLException{
		
		if(numeroCapitoli == tuplaCapitolo.getNumeroCapitoli()) return;
		
		int nPrimoC=tuplaCapitolo.primoCapitoloVolume();
		
		numeroCapitoli= tuplaCapitolo.getNumeroCapitoli();

		if(capitoli==null)
			tuplaCapitolo.aggiorna();
		
		capitoli = new Capitolo[numeroCapitoli];
		while(tuplaCapitolo.nextCapitolo())
		{		
			Capitolo capitolo = new Capitolo(tuplaCapitolo);
			capitoli[capitolo.getNumero()-nPrimoC] = capitolo;
		}
		
		
	}
	
	public String getNomeFumetto() {
		return nomeFumetto;
	}
	
	public TabellaCapitolo getTuplaCapitolo() {
		return tuplaCapitolo;
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

	public ImageIcon getCopertina() {
		return copertina;
	}

	public String getUrlCopertina() {
		return urlCopertina;
	}
	
	public Capitolo[] getCapitoli(){
		return capitoli;
	}
}
