package domain;


import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.org.apache.bcel.internal.generic.IUSHR;

import downloader.CopertinaVolumeDownloaderManager;
import technicalService.TabellaFumetto;
import technicalService.TabellaGeneri;
import technicalService.TabellaVolume;
import technicalService.TabellaFumetto;

public class Fumetto {
	
	private String nome;
	private String autore;
	private String artista;
	private String descrizione;
	private boolean ecompleto;	
	private boolean occidentale;
	private String[] generi;
	private Image copertina = null;
	private String url;
	private double valutazioneMedia;
	private int numeroLetture;
	private Image[] copertineVolumi ;

	private Volume[] volumi;
	private int numeroVolumi;
	private int ultimeCopertineInserite;
	private TabellaVolume tuplaVolume;
	static private TabellaGeneri tupleGenere;
	
	private CopertinaVolumeDownloaderManager dowloaderManager = CopertinaVolumeDownloaderManager.getCopertinaDowloader();
	
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
	}
	
	public Fumetto(TabellaFumetto tuplaFumetto){
		
		nome = tuplaFumetto.getNome();
		autore = tuplaFumetto.getAutore();
		artista = tuplaFumetto.getArtista();
		ecompleto = tuplaFumetto.getECompleto();
		occidentale = tuplaFumetto.getEOccidentale();
		url = tuplaFumetto.getUrlCopertina();
		valutazioneMedia = tuplaFumetto.getValutazioneMedia();
		numeroLetture = tuplaFumetto.getNumeroLetture(); 
				
		generi = null;
		numeroVolumi = 0;
		volumi = null;
		
	}
	
	public double getValutazioneMedia()
	{
		return valutazioneMedia;
	}

	public int getNumeroLetture()
	{
		return numeroLetture;
	}

	public int getNumeroVolumi()
	{
		return numeroVolumi;
	}

	public TabellaVolume getTuplaVolume()
	{
		return tuplaVolume;
	}

	public static TabellaGeneri getTupleGenere()
	{
		return tupleGenere;
	}
	
	public void apriFumetto() throws SQLException, MalformedURLException{
		
		if(numeroVolumi != 0) return;
		
		descrizione = TabellaFumetto.generaTuplaFumetto(nome).getTrama();
		tupleGenere = new TabellaGeneri();
		generi = tupleGenere.getGeneri(nome);
		tuplaVolume = new TabellaVolume(nome);
		numeroVolumi =  tuplaVolume.getNumeroVolumi();
	
		volumi = new Volume[numeroVolumi];
		
		String[] urls = new String[numeroVolumi];
		copertineVolumi = new Image[numeroVolumi];
		
		for(int i=0;tuplaVolume.nextVolume();i++)
		{
			urls[i] = tuplaVolume.getUrlCopertina();
			Volume volume = new Volume (tuplaVolume);
			volumi[volume.getNumero()-1]= volume;
		}
		dowloaderManager.scarica(urls, copertineVolumi,numeroVolumi);
		for(int i =0; i < 4; i++, ultimeCopertineInserite++){
			volumi[i].setCopertina(copertineVolumi[i]);
		}
	}
	
	public void caricaProssimeCopertine(){
		dowloaderManager.continuaDownload();
		for(int i =0; i < dowloaderManager.getNumeroDownloaders();i++,ultimeCopertineInserite++)
			volumi[ultimeCopertineInserite].setCopertina(copertineVolumi[ultimeCopertineInserite]); 
	}
	
	static public String[] getGeneri() throws SQLException{
		if(tupleGenere == null) 
			tupleGenere = new TabellaGeneri();
		
		return tupleGenere.getGeneri();
	}
	
	public String[] getGeneriFumetto() throws SQLException{
		
		return generi;
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

	public Image getCopertina() {
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

	public void setImage(Image image)
	{
		copertina = image;
	}
}
