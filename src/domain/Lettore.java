package domain;

import java.sql.SQLException;
import java.util.HashMap;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

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
		
		follower = null;
		follows = null;
		preferiti = null;
		daLeggere = null;
		cronologia = null;
	}
	
	public Lettore(TabellaLettore tuplaLettore) throws SQLException{
		
		idFacebook = tuplaLettore.getIdFacebook();
		nome = tuplaLettore.getNome();
		urlFoto = tuplaLettore.getUrlFoto();
		numFollow = tuplaLettore.getNumFollows();
		numFollower = tuplaLettore.getNumFollower();
		
		this.tuplaLettore = new TabellaLettore(idFacebook);
		this.tuplaLettore.nextLettore();
		
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
		//TODO migliorare il caso in cui follower.size() > tuplaLettore.getNumFollower
		if(follower == null)
			follower = new HashMap<>();
				
		tuplaLettore.aggiorna(idFacebook);

		if(follower.size() == tuplaLettore.getNumFollower()) return;
		
	
		TabellaLettore tuplaFollower = tuplaLettore.getFollower();
		
		if(follower.size() > tuplaLettore.getNumFollower()) follower = new HashMap();

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
		
		if(follows == null) caricaFollows();
		
		if(!follows.containsKey(lettore.idFacebook))
		{
			follows.put(lettore.idFacebook, lettore);
			tuplaLettore.aggiungiFollow(lettore.idFacebook);
			return true;
		}
		return false;
	}
	public boolean nonSeguire(Lettore lettore) throws SQLException{
		
		if(follows == null) caricaFollows();
		
		if(follows.containsKey(lettore.idFacebook))
		{
			follows.remove(lettore.idFacebook);
			tuplaLettore.rimuoviFollow(lettore.idFacebook);
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
	public HashMap<String, Lettore> getFollower() throws SQLException {
		caricaFollower();
		return follower;
	}

	public HashMap<String, Lettore> getFollows() throws SQLException {
		if(follows == null)caricaFollows();
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
			Lettore mario = lettori.get("100002870129213");
			
			manuel.segui(eliana);
			manuel.segui(mario);
			System.out.println();
			System.out.println("Manuel Follows "
					+manuel.getFollows().size());
			System.out.println("Eliana Follower "+ 
					eliana.getFollower().size());
			
			mario.segui(eliana);
			
			System.out.println("Prima del caricamento ,Eliana Follower "+ 
					eliana.numFollower);
			eliana.caricaFollower();
			System.out.println("Dopo del caricamento ,Eliana Follower "+ 
					eliana.numFollower);
			System.out.println(manuel.nonSeguire(eliana));
			
			System.out.println(eliana.getFollower().size());
			
			DataBase.disconnect();
			
			

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
