package domain;

import java.net.URL;
import java.sql.SQLException;

import technicalService.TabellaLettore;

public class Lettore {
	
	private String idFacebook;
	private String nome;
	private String urlFoto;
	
	private TabellaLettore tuplaLettore;
	

	Lettore(TabellaLettore tuplaLettore) throws SQLException{
		
		this.tuplaLettore = tuplaLettore;
		
		idFacebook = tuplaLettore.getIdFacebook();
		nome = tuplaLettore.getNome();
		urlFoto = tuplaLettore.getUrlFoto();
	}
	
}
