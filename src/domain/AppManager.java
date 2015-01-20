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
	
	public static void effettuaLogin(String idLettore)
	{
		GestoreDataBase.getIstanza();
		GestoreDataBase.connetti();
		TuplaLettore tuplaLettore = GestoreDataBase.getIstanza().creaTuplaLettore(idLettore);
		tuplaLettore.prossima();
		utenteLettore = new Lettore(tuplaLettore);
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
