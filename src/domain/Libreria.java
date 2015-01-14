package domain;
import java.awt.Image;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import downloader.CopertinaDowloaderManager;
import technicalService.DataBase;
import technicalService.TabellaFumetto;


public class Libreria
{
	private static final int MAX_NUMERO_FUMETTI = 8;

	private static Libreria istanza;
	private static LinkedHashMap<String, Fumetto> fumetti;
	private CopertinaDowloaderManager downloaderManager;
	private TabellaFumetto tuplaFumetto;
	
	private Fumetto[] fumettiCorrenti;
	
	private int indiceUltimoFumetto;

	private int numeroFumetti;
	
	
	private Libreria(){
		downloaderManager = CopertinaDowloaderManager.getCopertinaDowloader();
		fumetti = new LinkedHashMap<>();
		indiceUltimoFumetto = 0;
		
	}
	
	public static Libreria getLibreria(){
		if(istanza == null)
			istanza =new Libreria();
		return istanza;
	}
	
	public	Fumetto getFumetto(String nomeFumetto){
		Fumetto fumetto = fumetti.get(nomeFumetto);
		if(fumetto == null)
			fumetto= new Fumetto(TabellaFumetto.generaTuplaFumetto(nomeFumetto));
		return fumetto;
	}
	
	public void caricaFumetti(){
		
		fumettiCorrenti = new Fumetto[MAX_NUMERO_FUMETTI];
		String[] urls = new String[MAX_NUMERO_FUMETTI];
		Image[] copertina = new Image[MAX_NUMERO_FUMETTI];
		int cont;
		for(cont =0; tuplaFumetto.nextFumetto(); cont++){
			fumettiCorrenti[cont] = new Fumetto(tuplaFumetto);
			urls[cont] = tuplaFumetto.getUrlCopertina();
		}
		downloaderManager.scarica(urls, copertina);
		for(int i = 0; i < cont;i++){
			fumettiCorrenti[i].setImage(copertina[i]);
			fumetti.put(fumettiCorrenti[i].getNome(), fumettiCorrenti[i]);
		}
	}
	
	public void caricaFumetti(TabellaFumetto tuplaFumetto, Fumetto[] fumettiCorrenti){
		
		String[] urls = new String[MAX_NUMERO_FUMETTI];
		Image[] copertina = new Image[MAX_NUMERO_FUMETTI];
		int cont;
		for(cont =0; tuplaFumetto.nextFumetto(); cont++){
			fumettiCorrenti[cont] = new Fumetto(tuplaFumetto);
			urls[cont] = tuplaFumetto.getUrlCopertina();
		}
		downloaderManager.scarica(urls, copertina);
		for(int i = 0; i < cont;i++){
			fumettiCorrenti[i].setImage(copertina[i]);
			fumetti.put(fumettiCorrenti[i].getNome(), fumettiCorrenti[i]);
		}
	}
	
	public boolean giaCaricati(){
		
		tuplaFumetto = TabellaFumetto.generaProssimeTupleFumetto(indiceUltimoFumetto);
		tuplaFumetto.nextFumetto();
		String nome=tuplaFumetto.getNome();
		tuplaFumetto.previousFumetto();
		return fumetti.containsKey(nome);	
	}
	
	public boolean fumettiSuccessivi() throws SQLException{
		
		if(indiceUltimoFumetto == numeroFumetti)
		indiceUltimoFumetto+=MAX_NUMERO_FUMETTI;
		if(giaCaricati()){
			for(int i = 0; tuplaFumetto.nextFumetto();i++)
				fumettiCorrenti[i] = fumetti.get(tuplaFumetto.getNome());
		}
		else{
			caricaFumetti();	
		}
		return true;
	}
	
	public Fumetto[] fumettiCorrenti(){
		if(fumettiCorrenti == null){
			tuplaFumetto = TabellaFumetto.generaProssimeTupleFumetto(indiceUltimoFumetto);
			caricaFumetti();
		}
		return fumettiCorrenti;
	}
	
	public boolean fumettiPrecedenti(){
		if(indiceUltimoFumetto==0)return false;
		indiceUltimoFumetto-=MAX_NUMERO_FUMETTI;
		if(giaCaricati()){
			for(int i = 0; tuplaFumetto.nextFumetto();i++)
				fumettiCorrenti[i] = fumetti.get(tuplaFumetto.getNome());
		}
		return true;
	}
	
	public Fumetto[] caricaFumettiPerFiltri(ArrayList<String> generi,
			int statoFumetto, int provenienzaFumetto){
		tuplaFumetto = TabellaFumetto.generaFumettiPerFiltri((String[])generi.toArray(),
				statoFumetto,provenienzaFumetto);
		return null;
	}
	
	public Fumetto caricaFumettiPerNome(String nomeFumetto){
		return getFumetto(nomeFumetto);
	}
	
	public Fumetto[] caricaFumettiPerAutore(String autore){
		
		Fumetto[] fumetti = new Fumetto[MAX_NUMERO_FUMETTI];
		TabellaFumetto tuplaFumetto = TabellaFumetto.generaFumettiPerAutore(autore);
		
		caricaFumetti(tuplaFumetto, fumetti);
		return fumetti;
	}

	public Fumetto[] caricaFumettiPerArtista(String artista){
			
		Fumetto[] fumetti = new Fumetto[MAX_NUMERO_FUMETTI];
		TabellaFumetto tuplaFumetto = TabellaFumetto.generaFumettiPerArtista(artista);
		
		caricaFumetti(tuplaFumetto, fumetti);
		System.out.println(fumetti[0]);
		return fumetti;
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException, MalformedURLException
	{
		DataBase.connect();
		double start = System.currentTimeMillis();
		Libreria catalogo = Libreria.getLibreria();
		
		Fumetto[] fumetti = catalogo.fumettiCorrenti();
		double end = System.currentTimeMillis();
		System.out.println((end-start)/1000);

		start = System.currentTimeMillis();
		fumetti[0].apriFumetto();


		end = System.currentTimeMillis();
		System.out.println((end-start)/1000);
		start = System.currentTimeMillis();
		fumetti[0].caricaProssimeCopertine();


		end = System.currentTimeMillis();
		System.out.println((end-start)/1000);

		DataBase.disconnect();
	}
}
