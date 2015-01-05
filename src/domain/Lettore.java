package domain;

import java.sql.SQLException;
import java.util.HashMap;

import technicalService.TabellaLettore;

public class Lettore {
	
	private String idFacebook;
	private String nome;
	private String urlFoto;
	
	private TabellaLettore tuplaLettore;
	
//	private HashMap<String, Lettore> follows;
	private HashMap<String, Fumetto> preferiti;

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

	public Lettore(TabellaLettore tuplaLettore) throws SQLException{
		
		this.tuplaLettore = tuplaLettore;
		
		idFacebook = tuplaLettore.getIdFacebook();
		nome = tuplaLettore.getNome();
		urlFoto = tuplaLettore.getUrlFoto();
	}
	
	
	
	
}
