package domain;


import technicalService.GestoreDataBase;
import technicalService.TuplaLettore;

public class AppManager
{
	public static final int SESSIONE_PRIVATA = 2;
	public static final int SESSIONE_OFFLINE = 1;
	public static final int SESSIONE_ONLINE = 0;
	private static	int statoApp;
	
	private static Lettore utenteLettore;
	private static GestoreDataBase gestoreDB = GestoreDataBase.getIstanza();
	
	public static void effettuaLogin(String idLettore, String nome, String url,String[] idAmici)
	{
		gestoreDB = GestoreDataBase.getIstanza();
		GestoreDataBase.connetti();
		gestoreDB.aggiungiUtente(idLettore,nome ,url);
		TuplaLettore tuplaLettore = GestoreDataBase.getIstanza().creaTuplaLettore(idLettore);
		tuplaLettore.prossima();
		utenteLettore = new Lettore(tuplaLettore);
		utenteLettore.setIdAmiciFB(idAmici);
		statoApp = SESSIONE_ONLINE;
	}
	public static void setSessionePrivata()
	{
		statoApp = SESSIONE_PRIVATA;
	}

	public static void setSessioneOffline()
	{
		statoApp = SESSIONE_OFFLINE;
	}
	
	public static void setSessioneOnline()
	{
		statoApp = SESSIONE_ONLINE;
	}
	
	public static int getStatoApp()
	{
		return statoApp;
	}
	
	public static Lettore getLettore()
	{
		return utenteLettore;
	}
	
	
}
