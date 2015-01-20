package domain;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;

import downloader.CopertinaDowloaderManager;
import technicalService.GestoreDataBase;
import technicalService.Tupla;
import technicalService.TuplaFumetto;


public class Libreria
{
	private static final int MAX_NUMERO_FUMETTI = 8;

	private static Libreria istanza;
	
	private static LinkedHashMap<String, Fumetto> fumetti;
	private CopertinaDowloaderManager downloaderManager;
	private TuplaFumetto tuplaFumetto;
	
	private GestoreDataBase gestoreDB = GestoreDataBase.getIstanza();
	private Fumetto[] fumettiCorrenti;
	private Fumetto[] fumettiFiltriCorrenti;
	private int indiceUltimoFumetto;
	private int indicePrimoFumetto;
	private int numeroFumetti;
	private int numeroFumettiTotali;
	private int indiceUltimoFumettoFiltrato;
	

	private int statoFumetto;

	private int provenienzaFumetto;

	private ArrayList<String> generiSelezionati;

	private int indicePrimoFumettoFiltrato;
		
	private Libreria(){
		downloaderManager = CopertinaDowloaderManager.getCopertinaDowloader();
		fumetti = new LinkedHashMap<>();
		indiceUltimoFumetto = 0;
		indicePrimoFumetto = 0;
		numeroFumettiTotali = gestoreDB.getNumeroFumetti();
		numeroFumetti = numeroFumettiTotali;
		
	}
	
	public static Libreria getIstanza(){
		if(istanza == null)
			istanza =new Libreria();
		return istanza;
	}
	
	public	Fumetto getFumetto(String nomeFumetto){
		TuplaFumetto tupla = gestoreDB.creaTuplaFumetto(nomeFumetto);
		if(tupla.prossima()){
			Fumetto fumetto = fumetti.get(tupla.getNome());
			if(fumetto == null)
			{
				try{
					fumetto = new Fumetto(tupla);
					Image copertina =ImageIO.read(new URL(fumetto.getUrl()));
					fumetto.setCopertina(copertina);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return fumetto;
		}
		return null;
	}
	
	public void caricaFumetti(){
		
		fumettiCorrenti = new Fumetto[MAX_NUMERO_FUMETTI];
		String[] urls = new String[MAX_NUMERO_FUMETTI];
		Image[] copertina = new Image[MAX_NUMERO_FUMETTI];
		int cont;
		for(cont =0; tuplaFumetto.prossima(); cont++, indiceUltimoFumetto++){
			fumettiCorrenti[cont] = new Fumetto(tuplaFumetto);
			urls[cont] = tuplaFumetto.getUrlCopertina();
		}
		downloaderManager.scarica(urls, copertina);
		for(int i = 0; i < cont;i++){
			fumettiCorrenti[i].setCopertina(copertina[i]);
			fumetti.put(fumettiCorrenti[i].getNome(), fumettiCorrenti[i]);
		}
		numeroFumetti = numeroFumettiTotali;
	}
	
	public void caricaFumetti(TuplaFumetto tuplaFumetto, Fumetto[] fumettiCorrenti){
		
		String[] urls = new String[MAX_NUMERO_FUMETTI];
		Image[] copertina = new Image[MAX_NUMERO_FUMETTI];
		int cont;		
		for(cont =0; tuplaFumetto.prossima(); cont++, indiceUltimoFumetto++){
			fumettiCorrenti[cont] = new Fumetto(tuplaFumetto);
			urls[cont] = tuplaFumetto.getUrlCopertina();
		}
		downloaderManager.scarica(urls,copertina);
		for(int i = 0; i < cont;i++){
			fumettiCorrenti[i].setCopertina(copertina[i]);
			fumetti.put(fumettiCorrenti[i].getNome(), fumettiCorrenti[i]);
		}
		numeroFumetti = numeroFumettiTotali;
	}
	
	public boolean giaCaricati(){
		
		tuplaFumetto = gestoreDB.creaTuplaFumetto(indiceUltimoFumetto);
		System.out.println(indiceUltimoFumetto);
		tuplaFumetto.prossima();
		String nome=tuplaFumetto.getNome();
		tuplaFumetto.precedente();
		System.out.println(nome);
		return fumetti.containsKey(nome);	
	}
	public void caricaFumettiPerFiltri(ArrayList<String> generi,
			int statoFumetto, int provenienzaFumetto){
		indiceUltimoFumettoFiltrato=0;
		this.generiSelezionati = generi;
		this.statoFumetto = statoFumetto;
		this.provenienzaFumetto = provenienzaFumetto;
		TuplaFumetto tupla = gestoreDB.generaFumettiPerFiltri(generi,statoFumetto,provenienzaFumetto,indiceUltimoFumettoFiltrato);
		fumettiFiltriCorrenti = new Fumetto[MAX_NUMERO_FUMETTI];
		int cont=0;
		for(;tupla.prossima();indiceUltimoFumettoFiltrato++,cont++){
			System.out.println(tupla.getNome());
			fumettiFiltriCorrenti[cont]=getFumetto(tupla.getNome());
		}		
	}

	public  void fumettiFiltratiSuccessivi(){
		TuplaFumetto tupla = gestoreDB.generaFumettiPerFiltri(generiSelezionati,statoFumetto,provenienzaFumetto,indiceUltimoFumettoFiltrato);
		int cont=0;
		indicePrimoFumettoFiltrato = indiceUltimoFumettoFiltrato;
		for(;tupla.prossima();indiceUltimoFumettoFiltrato++,cont++){
			fumettiFiltriCorrenti[cont]=getFumetto(tupla.getNome());
		}
	}
	public Fumetto[] fumettiFiltratiCorrente(){
		return fumettiFiltriCorrenti;
	}
	public void fumettiFiltratiPrecedenti(){
		TuplaFumetto tupla = gestoreDB.generaFumettiPerFiltri(generiSelezionati,statoFumetto,provenienzaFumetto,indicePrimoFumetto-MAX_NUMERO_FUMETTI);
		
		indiceUltimoFumettoFiltrato = indicePrimoFumettoFiltrato;
		for(int cont=0;tupla.prossima();indicePrimoFumettoFiltrato++,cont++){
			fumettiFiltriCorrenti[cont]=getFumetto(tupla.getNome());
		}
	}
	public boolean fumettiSuccessivi(){
		if(indiceUltimoFumetto == numeroFumetti) return false;
		indicePrimoFumetto = indiceUltimoFumetto;
		if(giaCaricati()){
			int i = 0;
			for(; tuplaFumetto.prossima();i++,indiceUltimoFumetto++){
				fumettiCorrenti[i] = fumetti.get(tuplaFumetto.getNome());
			}
			for (int j = MAX_NUMERO_FUMETTI - 1; j >= i; j--)
				fumettiCorrenti[j] = null;
		}else{
			caricaFumetti();	
		}
		return true;
	}
	
	public Fumetto[] fumettiCorrenti(){
		numeroFumetti = numeroFumettiTotali;
		if(fumettiCorrenti == null){
			tuplaFumetto = gestoreDB.creaTuplaFumetto(indiceUltimoFumetto);
			caricaFumetti();
		}
		return fumettiCorrenti;
	}
	public boolean ePresente(Fumetto fumetto){
		return fumetti.containsKey(fumetto.getNome());
	}
	public boolean fumettiPrecedenti(){
		if(indicePrimoFumetto==0)return false;	
			
		TuplaFumetto tuplaFumetto = gestoreDB.creaTuplaFumetto(indicePrimoFumetto-MAX_NUMERO_FUMETTI);
					
		indiceUltimoFumetto = indicePrimoFumetto;

		for(int i = 0; tuplaFumetto.prossima();i++,indicePrimoFumetto--){
			fumettiCorrenti[i] = fumetti.get(tuplaFumetto.getNome());
		}
		return true;
	}
	
	public Fumetto[] caricaFumettiPerNome(String nomeFumetto){
		Fumetto[] fumetti =new Fumetto[1];
		fumetti[0] = getFumetto(nomeFumetto);
		return fumetti;
	}
	
	public Fumetto[] caricaFumettiPerAutore(String autore){
		
		Fumetto[] fumetti = new Fumetto[MAX_NUMERO_FUMETTI];
		TuplaFumetto tuplaFumetto = gestoreDB.creaTuplaFumettoPerAutpre(autore);
		
		caricaFumetti(tuplaFumetto, fumetti);
		return fumetti;
	}

	public Fumetto[] caricaFumettiPerArtista(String artista){
			
		Fumetto[] fumetti = new Fumetto[MAX_NUMERO_FUMETTI];
		TuplaFumetto tuplaFumetto = gestoreDB.creaTuplaFumettoPerArtista(artista);
		
		caricaFumetti(tuplaFumetto, fumetti);
		return fumetti;
	}
	
	public Fumetto[] caricaPiuLetti()
	{
		Fumetto[] fumetti = new Fumetto[MAX_NUMERO_FUMETTI];
		TuplaFumetto tuplaFumetto = gestoreDB.piuLetti();
		
		caricaFumetti(tuplaFumetto, fumetti);
		numeroFumetti = MAX_NUMERO_FUMETTI;
		return fumetti;
	}
	
	public Fumetto[] caricaPiuVotati()
	{
		Fumetto[] fumetti = new Fumetto[MAX_NUMERO_FUMETTI];
		TuplaFumetto tuplaFumetto = gestoreDB.piuVotati();
		
		caricaFumetti(tuplaFumetto, fumetti);
		numeroFumetti = MAX_NUMERO_FUMETTI;
		return fumetti;
	}
	
	public boolean haSuccessivo()
	{
		if (indiceUltimoFumetto >= numeroFumetti)
			return false;
		return true;
	}
	
	public boolean haPrecedente()
	{
		if (indicePrimoFumetto == 0)
			return false;
		return true;
	}
	
	public static void main(String[] args){
		GestoreDataBase gestore = GestoreDataBase.getIstanza();
		double start = System.currentTimeMillis();
		Libreria catalogo = Libreria.getIstanza();
		System.out.println("ciao");
		Fumetto[] fumetti = catalogo.fumettiCorrenti();
		double end = System.currentTimeMillis();
		System.out.println((end-start)/1000);
		System.out.println(catalogo.fumettiSuccessivi());
		System.out.println(catalogo.fumettiSuccessivi());
		System.out.println(catalogo.fumettiSuccessivi());
		System.out.println(catalogo.fumettiSuccessivi());
		System.out.println(catalogo.fumettiPrecedenti());
		System.out.println(catalogo.fumettiSuccessivi());
		System.out.println(catalogo.fumettiSuccessivi());
		System.out.println(catalogo.fumettiPrecedenti());
		System.out.println(catalogo.fumettiPrecedenti());
		System.out.println(catalogo.fumettiSuccessivi());
		System.out.println(catalogo.fumettiSuccessivi());
		System.out.println(catalogo.fumettiPrecedenti());
		System.out.println(catalogo.fumettiPrecedenti());
		System.out.println(catalogo.fumettiSuccessivi());
		System.out.println(catalogo.fumettiSuccessivi());
		System.out.println(catalogo.fumettiPrecedenti());
		System.out.println(catalogo.fumettiPrecedenti());
		
	
		GestoreDataBase.disconnetti();
	}
}
