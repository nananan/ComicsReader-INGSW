package domain;

import java.sql.SQLException;
import java.util.HashMap;

import technicalService.DataBase;
import technicalService.TabellaLettore;

public class Lettore {
	
	private String idFacebook;
	private String nome;
	private String urlFoto;
	private int numFollow;
	private int numFollower;
	private TabellaLettore tuplaLettore;
	
	private HashMap<String, Lettore> follower;
	private HashMap<String, Lettore> follows;
	private HashMap<String, Fumetto> preferiti;
	private HashMap<String, Fumetto> daLeggere;
	private HashMap<String, Fumetto> cronologia;
	
	public Lettore(String idFacebook,String nome, String url, int numFollow, int numFollower){
				
		this.idFacebook =idFacebook;
		this.nome = nome;
		urlFoto = url;
		this.numFollow = numFollow;
		this.numFollower  = numFollower;
		
		tuplaLettore = null;
		follower = null;
		follows = null;
		preferiti = null;
		daLeggere = null;
		cronologia = null;
	}
	
	public Lettore(TabellaLettore tuplaLettore) throws SQLException{
		
		this.tuplaLettore = tuplaLettore;
		
		idFacebook = tuplaLettore.getIdFacebook();
		nome = tuplaLettore.getNome();
		urlFoto = tuplaLettore.getUrlFoto();
		numFollow = tuplaLettore.getNumFollows();
		numFollower = tuplaLettore.getNumFollower();
		
		follower = null;
		follows = null;
		preferiti = null;
		daLeggere = null;
		cronologia = null;
	}
	
	public void caricaFollows() throws SQLException{
		
		if(follows != null) return;
		
		follows = new HashMap<>();
		
	
		TabellaLettore tuplaFollows = tuplaLettore.getFollows();
		
		while(tuplaFollows.nextLettore())
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

	public void caricaFollower() throws SQLException{
		
		if(follower == null)
			follower = new HashMap<>();
		
		if(follower.size() == tuplaLettore.getNumFollower()) return;
		tuplaLettore.nextLettore();
		TabellaLettore tuplaFollower = tuplaLettore.getFollower();
		
		while(tuplaFollower.nextLettore())
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
		
	}
	public void caricaDaLeggere(){
		
	}
	
	public boolean segui(Lettore lettore) throws SQLException{
		
		if(follows.containsKey(lettore.nome))
		{
			follows.put(lettore.nome, lettore);
			tuplaLettore.aggiungiFollow(lettore.idFacebook);
			return true;
		}
		return false;
	}
	
	public void caricaCronologia(){
		//TODO aggiustare prima getCronologia e aggiungiCronologia in TabellaLettore
	}
	public int getNumFollow() {
		return numFollow = follows.size();
	}

	public int getNumFollower() {
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

	public TabellaLettore getTuplaLettore() {
		return tuplaLettore;
	}

	public HashMap<String, Fumetto> getPreferiti() {
		return preferiti;
	}
	public HashMap<String, Lettore> getFollower() {
		return follower;
	}

	public HashMap<String, Lettore> getFollows() {
		return follows;
	}

	public HashMap<String, Fumetto> getDaLeggere() {
		return daLeggere;
	}

	public HashMap<String, Fumetto> getCronologia() {
		return cronologia;
	}

	public static void main(String[] args) throws ClassNotFoundException
	{
		TabellaLettore tuplaLettore;
		try
		{
			DataBase.connect();
			tuplaLettore = new TabellaLettore();
			HashMap<String, Lettore> lettori = new HashMap<>();
			while(tuplaLettore.nextLettore())
			{
				Lettore lettore = new Lettore(tuplaLettore);
				lettori.put(tuplaLettore.getIdFacebook(),lettore);
			}
			
			Lettore manuel = lettori.get("1590013667");
			Lettore eliana = lettori.get("100000001659558");
			
			System.out.println(manuel.getNome());
//			System.out.println(manuel.segui(eliana));
//			
//			System.out.println(manuel.getFollows().size());
			DataBase.disconnect();
			
			

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
