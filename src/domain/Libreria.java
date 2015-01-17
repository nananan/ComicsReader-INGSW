package domain;
import java.awt.Image;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
	private int indiceUltimoFumetto;
	private int indicePrimoFumetto;
	private int numeroFumetti;
		
	private Libreria(){
		downloaderManager = CopertinaDowloaderManager.getCopertinaDowloader();
		fumetti = new LinkedHashMap<>();
		indiceUltimoFumetto = 0;
		indicePrimoFumetto = 0;
		numeroFumetti = gestoreDB.getNumeroFumetti();
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
				fumetto = new Fumetto(tupla);
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
		if(fumettiCorrenti == null){
			tuplaFumetto = gestoreDB.creaTuplaFumetto(indiceUltimoFumetto);
			caricaFumetti();
		}
		return fumettiCorrenti;
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
	
	public Fumetto[] caricaFumettiPerFiltri(ArrayList<String> generi,
			int statoFumetto, int provenienzaFumetto){
		tuplaFumetto = TuplaFumetto.generaFumettiPerFiltri((String[])generi.toArray(),
				statoFumetto,provenienzaFumetto);
		return null;
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
		System.out.println(fumetti[0]);
		return fumetti;
	}
	
	public boolean haSuccessivo()
	{
		if (indiceUltimoFumetto == numeroFumetti)
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
