package domain;


import java.awt.Image;
import java.net.MalformedURLException;
import java.sql.SQLException;
import downloader.CopertinaVolumeDownloaderManager;
import technicalService.DataBase;
import technicalService.TuplaFumetto;
import technicalService.TuplaVolume;

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
	private static DataBase gestoreDB = DataBase.getIstanza();
		
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
	
	public Fumetto(TuplaFumetto tuplaFumetto){
		
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

	public void apriFumetto(){
		
		if(numeroVolumi != 0) return;
		
		descrizione = gestoreDB.getTramaFumetto(nome);
		generi = gestoreDB.getGeneri(nome);
		numeroVolumi =  gestoreDB.getNumeroVolumi(nome);
		
		if(numeroVolumi ==0 )return;
		
		volumi = new Volume[numeroVolumi];
		
		String[] urls = new String[numeroVolumi];
		copertineVolumi = new Image[numeroVolumi];
		
		TuplaVolume tuplaVolume= gestoreDB.creaTuplaVolume(nome);
		
		for(int i=0;tuplaVolume.prossima();i++)
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
	
	static public String[] getTuttiGeneri(){
		
		return gestoreDB.getGeneri();
	}
	
	public String[] getGeneri(){
		
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

	public void setCopertina(Image image)
	{
		copertina = image;
	}
}
