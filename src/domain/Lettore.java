package domain;


import java.util.HashMap;
import java.util.LinkedHashMap;

import technicalService.GestoreDataBase;
import technicalService.TuplaFumetto;
import technicalService.TuplaLettore;

public class Lettore {
	
	private String idFacebook;
	private String nome;
	private String urlFoto;
	private int numFollow;
	private int numFollower;
	
	private HashMap<String, Lettore> follower;
	private HashMap<String, Lettore> follows;
	private HashMap<String, Fumetto> preferiti;
	private HashMap<String, Fumetto> daLeggere;
	private LinkedHashMap<String, Fumetto> cronologia;
	
	private TuplaLettore tuplaLettore;
	private GestoreDataBase gestoreDB = GestoreDataBase.getIstanza();
	
	public Lettore(String idFacebook,String nome, String url, int numFollow, int numFollower){
				
		this.idFacebook =idFacebook;
		this.nome = nome;
		urlFoto = url;
		this.numFollow = numFollow;
		this.numFollower  = numFollower;
		
		this.tuplaLettore = gestoreDB.creaTuplaLettore(nome);
		this.tuplaLettore.prossima();
		
		follower = null;
		follows = null;
		preferiti = null;
		daLeggere = null;
		cronologia = null;
	}
	
	public Lettore(TuplaLettore tuplaLettore){
		
		idFacebook = tuplaLettore.getIdFacebook();
		nome = tuplaLettore.getNome();
		urlFoto = tuplaLettore.getUrlFoto();
		numFollow = tuplaLettore.getNumFollows();
		numFollower = tuplaLettore.getNumFollower();
		this.tuplaLettore = gestoreDB.creaTuplaLettore(nome);
		this.tuplaLettore.prossima();
		
		follower = null;
		follows = null;
		preferiti = null;
		daLeggere = null;
		cronologia = null;
	}
	
	public void caricaFollows(){
		
		if(follows != null) return;
		
		follows = new HashMap<>();
		
	
		TuplaLettore tuplaFollows = tuplaLettore.getFollows(idFacebook);
		while(tuplaFollows.prossima())
		{	
			String idFacebook = tuplaFollows.getIdFacebook();
			String nome = tuplaFollows.getNome();
			String urlFoto = tuplaFollows.getUrlFoto();
			int numFollow = tuplaFollows.getNumFollows();
			int numFollower = tuplaFollows.getNumFollower();
			follows.put(idFacebook,new Lettore(idFacebook,nome,urlFoto, numFollow, numFollower));
			
		}
		tuplaFollows.close();
	}

	public void caricaFollower(){
		//TODO migliorare il caso in cui follower.size() > tuplaLettore.getNumFollower
		if(follower == null)
			follower = new HashMap<>();
				
		tuplaLettore = gestoreDB.creaTuplaLettore(idFacebook);
		tuplaLettore.prossima();
		if(follower.size() == tuplaLettore.getNumFollower()) return;
		
		TuplaLettore tuplaFollower = tuplaLettore.getFollower(idFacebook);
		
		if(follower.size() > tuplaLettore.getNumFollower()) follower = new HashMap<>();

		while(tuplaFollower.prossima())
		{
			String idFacebook = tuplaFollower.getIdFacebook();
			String nome = tuplaFollower.getNome();
			String urlFoto = tuplaFollower.getUrlFoto();
			int numFollow = tuplaFollower.getNumFollows();
			int numFollower = tuplaFollower.getNumFollower();
			follower.put(idFacebook,new Lettore(idFacebook,nome,urlFoto, numFollow, numFollower));
		}
	}

	public void caricaPreferiti(){
		
		if(preferiti != null) return ; 
			
		preferiti = new HashMap<>();   
		
		TuplaFumetto tuplaFumetto = tuplaLettore.getPreferiti(idFacebook);
		
		while(tuplaFumetto.prossima())
		{
			Fumetto fumetto = new Fumetto(tuplaFumetto);
			preferiti.put(fumetto.getNome(), fumetto);
		}
	}
	public void caricaDaLeggere(){
		
		if(daLeggere != null) return;
		
		daLeggere = new HashMap<>();
		
		TuplaFumetto tuplaFumetto =tuplaLettore.getDaLeggere(idFacebook);
		
		while(tuplaFumetto.prossima()){
			Fumetto fumetto = new Fumetto(tuplaFumetto);
			daLeggere.put(fumetto.getNome(), fumetto);
		}
	}
	public void caricaCronologia(){
	
		if(cronologia != null) return;
		
		cronologia = new LinkedHashMap<>();
	
		TuplaFumetto tuplaFumetto = tuplaLettore.getCronologia(idFacebook);
		while(tuplaFumetto.prossima()){
			Fumetto fumetto = new Fumetto(tuplaFumetto, tuplaFumetto.getData());
			String nome_data = fumetto.getNome()+fumetto.getData();
			cronologia.put(nome_data, fumetto);
		}	
	}
	
	public void vota(Fumetto fumetto, int valutazione)
	{
		gestoreDB.aggiungiValutazione(this.idFacebook, fumetto.getNome(), valutazione);
	}	

	public boolean inserisciPreferiti(Fumetto fumetto){
		
		if(preferiti == null) caricaPreferiti();
		
		if(preferiti.containsKey(fumetto.getNome())) return false;
		preferiti.put(fumetto.getNome(),fumetto);
		gestoreDB.aggiungiPreferiti(idFacebook,fumetto.getNome());
		return true;
	}
	
	public boolean rimuoviPreferiti(Fumetto fumetto){
		
		if(preferiti == null) caricaPreferiti();
		
		if(!preferiti.containsKey(fumetto.getNome()))return false;
		
		preferiti.remove(fumetto.getNome());
		gestoreDB.rimuoviPreferiti(idFacebook,fumetto.getNome());
		return true;
	}
	public boolean inserisciDaLeggere(Fumetto fumetto){
		
		if(daLeggere == null) caricaDaLeggere();
		
		if(daLeggere.containsKey(fumetto.getNome())) return false;
		daLeggere.put(fumetto.getNome(),fumetto);
		gestoreDB.aggiungiDaLeggere(idFacebook,fumetto.getNome());
		return true;	
	}
	
	public boolean rimuoviDaLeggere(Fumetto fumetto){
		
		if(daLeggere == null) caricaDaLeggere();
		
		if(!daLeggere.containsKey(fumetto.getNome())) return false;
		
		daLeggere.remove(fumetto.getNome());
		gestoreDB.rimuoviDaLeggere(idFacebook,fumetto.getNome());
		return true;
	}
	
	public boolean inserisciCronologia(Fumetto fumetto)
	{
		if(AppManager.getStatoApp() == AppManager.SESSIONE_PRIVATA)return false;
		
		if(cronologia == null) caricaCronologia();
		
		if(cronologia.containsKey(fumetto.getData())) return false;
		cronologia.put(fumetto.getData(),fumetto);
		gestoreDB.aggiungiCronologia(idFacebook,fumetto.getNome());
		return true;	
	}

	public boolean segui(Lettore lettore){
		
		if(follows == null) caricaFollows();
		
		if(!follows.containsKey(lettore.idFacebook))
		{
			follows.put(lettore.idFacebook, lettore);
			gestoreDB.aggiungiFollow(idFacebook,lettore.idFacebook);
			return true;
		}
		return false;
	}
	
	public boolean nonSeguire(Lettore lettore){
		
		if(follows == null) caricaFollows();
		
		if(follows.containsKey(lettore.idFacebook))
		{
			follows.remove(lettore.idFacebook);
			gestoreDB.rimuoviFollow(idFacebook,lettore.idFacebook);
			return true;
		}
		return false;
	}
	
	public boolean eFollows(Lettore lettore)
	{
		if (follows.containsKey(lettore.idFacebook))
			return true;
		return false;
	}
	
	public boolean eInPreferiti(Fumetto fumetto)
	{
		caricaPreferiti();
		if (preferiti.containsKey(fumetto.getNome()))
			return true;
		return false;
	}
	
	public boolean eInDaLeggere(Fumetto fumetto)
	{
		caricaDaLeggere();
		if (daLeggere.containsKey(fumetto.getNome()))
			return true;
		return false;
	}
	
	public Fumetto getPrimoCronologia(Lettore lettore)
	{
		TuplaFumetto tuplaFumetto = gestoreDB.getPrimoFumettoCronologia(lettore);
		
		if (tuplaFumetto.prossima())
		{
			Fumetto fumetto = new Fumetto(tuplaFumetto);
			return fumetto;
		}
		return null;
	}
	
	public int getNumFollow() {
		caricaFollows();
		return numFollow = follows.size();
	}

	public int getNumFollower() {
	
		caricaFollower();
		return numFollower = follower.size();
	}

	public String getIdFacebook() {
		return idFacebook;
	}

	public String getNome() {
		return nome;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public TuplaLettore getTuplaLettore() {
		return tuplaLettore;
	}

	public HashMap<String, Fumetto> getPreferiti() {
		caricaPreferiti();
		return preferiti;
	}
	public HashMap<String, Lettore> getFollower(){
		caricaFollower();
		return follower;
	}

	public HashMap<String, Lettore> getFollows(){
		if(follows == null)caricaFollows();
		return follows;
	}

	public HashMap<String, Fumetto> getDaLeggere() {
		caricaDaLeggere();
		return daLeggere;
	}

	public HashMap<String, Fumetto> getCronologia() {
		caricaCronologia();
		return cronologia;
	}
	public int getNumeroFollowerAltroLettore()
	{
		return numFollower;
	}
	
	public int getNumeroFollowAltroLettore()
	{
		return numFollow;
	}
	

	public static void main(String[] args) 
	{
//		TuplaLettore tuplaLettore;
//		try
//		{
//			DataBase.connect();
//			tuplaLettore = new TuplaLettore();
//			HashMap<String, Lettore> lettori = new HashMap<>();
//			while(tuplaLettore.nextLettore())
//			{
//				Lettore lettore = new Lettore(tuplaLettore);
//				lettori.put(tuplaLettore.getIdFacebook(),lettore);
//			}
//			
//			Lettore manuel = lettori.get("1590013667");
//			Lettore eliana = lettori.get("100000001659558");
//			Lettore mario = lettori.get("100002870129213");
//			
//			manuel.segui(eliana);
//			manuel.segui(mario);
//			System.out.println();
//			System.out.println("Manuel Follows "
//					+manuel.getFollows().size());
//			System.out.println("Eliana Follower "+ 
//					eliana.getFollower().size());
//			
//			mario.segui(eliana);
//			
//		
//			Fumetto death = new Fumetto(TuplaFumetto.generaTuplaFumetto("Death Note"));
//			manuel.inserisciDaLeggere(death);
//			
//			System.out.println(manuel.getDaLeggere().size());
//			
//			manuel.rimuoviDaLeggere(death);
//			
//			System.out.println(manuel.getDaLeggere().size());
//
//			DataBase.disconnect();
//			
//			
//
//		} catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	
	
	
}
