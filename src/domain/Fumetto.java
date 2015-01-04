package domain;


import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import technicalService.TabellaFumetto;
import technicalService.TabellaVolume;

public class Fumetto {
	
	private String nome;
	private String autore;
	private String artista;
	private String descrizione;
	private boolean ecompleto;	
	private boolean occidentale;
	private Genere[] generi;
	private ImageIcon copertina = null;
	private String url;
	private double valutazioneMedia;
	private int numeroLetture;
	
	private Volume[] volumi;
	private int numeroVolumi;
	private TabellaVolume tuplaVolume;
	
	public Fumetto(String nome, String autore, String artista, String descrizione,
			boolean ecompleto, boolean occidentale, String url, double valutazione,
			int numeroLetture){
		
		this.nome = nome;
		this.autore = autore;
		this.artista = artista;
		this.descrizione = descrizione;
		this.ecompleto = ecompleto;
		this.occidentale = occidentale;
		this.url = url;
		this.valutazioneMedia= valutazione;
		this.numeroLetture = numeroLetture;
		copertina = new ImageIcon(url);	

		
	}
	public Fumetto(TabellaFumetto tuplaFumetto) throws SQLException, MalformedURLException{
		
		nome = tuplaFumetto.getNome();
		autore = tuplaFumetto.getAutore();
		artista = tuplaFumetto.getArtista();
		descrizione = tuplaFumetto.getDescrizione();
		ecompleto = tuplaFumetto.getECompleto();
		occidentale = tuplaFumetto.getEOccidentale();
		url = tuplaFumetto.getUrlCopertina();
		valutazioneMedia = tuplaFumetto.getValutazioneMedia();
		numeroLetture = tuplaFumetto.getNumeroLetture(); 
		//TODO inizializzare generi...
		copertina = new ImageIcon(new URL(url).toString());
		generi = null;
		tuplaVolume = new TabellaVolume(nome);
		numeroVolumi = 0;
		volumi = null;
		
	}
	
	public void caricaVolumi() throws SQLException, MalformedURLException{
		
		if(numeroVolumi == tuplaVolume.getNumeroVolumi()) return;
		
		numeroVolumi = tuplaVolume.getNumeroVolumi();
		
		if(volumi != null)
			tuplaVolume.aggiorna();
		
		volumi = new Volume[numeroVolumi];
		
		while(tuplaVolume.nextVolume())
		{
			Volume volume = new Volume (tuplaVolume);
			volumi[volume.getNumero()-1]= volume;
		}
	
	}

	public String getNome() {
		return nome;
	}

	public String getAutore() {
		return autore;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public boolean isEcompleto() {
		return ecompleto;
	}

	public boolean isOccidentale() {
		return occidentale;
	}

	public Genere[] getGeneri() {
		return generi;
	}

	public ImageIcon getCopertina() {
		return copertina;
	}

	public String getUrl() {
		return url;
	}

	public double getValutazione_media() {
		return valutazioneMedia;
	}

	public int getNumero_letture() {
		return numeroLetture;
	}

	public Volume[] getVolumi() {
		return volumi;
	}

	public String getArtista() {
		return artista;
	}
}
