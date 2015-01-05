package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TabellaLettore {
	
	private ResultSet cursoreLettore;
	final private String QUERY_LETTORE = "SELECT * FROM utente";
//	private String idFacebookCorrente;

	public TabellaLettore() throws SQLException{
	
		cursoreLettore = DataBase.getStatement().executeQuery(QUERY_LETTORE);
	}
	
	public TabellaLettore(ResultSet cursore){
		cursoreLettore = cursore;
	}
	
	public TabellaLettore getFollows() throws SQLException{
		
		String query = "SELECT id_facebook, nome, url_foto "
				+ "FROM utente ,segue "
				+ "WHERE utente_follower=\""+getIdFacebook()+"\" and utente_follow = id_facebook;";
		
		ResultSet cursoreFollows = DataBase.getStatement().executeQuery(query);
		
		return new TabellaLettore(cursoreFollows);
	}
	
	public TabellaLettore getFollower() throws SQLException {
		
		String query = "SELECT id_facebook, nome, url_foto "
				+ "FROM utente ,segue "
				+ "WHERE utente_follow=\""+getIdFacebook()+"\" and utente_follower = id_facebook;";
		
		ResultSet cursoreFollows = DataBase.getStatement().executeQuery(query);
		
		return new TabellaLettore(cursoreFollows);
	}
	
	public TabellaFumetto getPreferiti() throws SQLException{
		String query =" SELECT nome, autore, artista,trama,completa,occindentale,url_compertina_primo_volume,"
				+ "valutazione_percentuale,numero_letture"
				+ "FROM fumetto as f , preferiti as p"
				+ "WHERE p.utente =\""+getIdFacebook()+"\" and p.nome_fumetto = f.nome;";
				
		ResultSet cursorePreferiti = DataBase.getStatement().executeQuery(query);
		return new TabellaFumetto(cursorePreferiti);
	}
	
	public TabellaFumetto getDaLeggere() throws SQLException{
	
		String query = "SELECT nome, autore, artista,trama,completa,occindentale,url_compertina_primo_volume,"
				+ "valutazione_percentuale,numero_letture"
				+ "FROM fumetto as f, da_leggere as d"
				+ "WHERE d.utente =\""+getIdFacebook()+"\" and d.nome_fumetto = f.nome;";
		ResultSet cursoreDaLeggere = DataBase.getStatement().executeQuery(query);
		return new TabellaFumetto(cursoreDaLeggere );
	}
	
	public TabellaFumetto getCronologia() throws SQLException{
		
		String query = "SELECT nome, autore, artista,trama,completa,occindentale,url_compertina_primo_volume,"
				+ "valutazione_percentuale,numero_letture"
				+ "FROM fumetto as f, letture_recenti as r"
				+ "WHERE r.utente =\""+getIdFacebook()+"\" and r.nome_fumetto = f.nome"
				+ "ORDER BY data_lettura; ";
		ResultSet cursoreDaLeggere = DataBase.getStatement().executeQuery(query);
		return new TabellaFumetto(cursoreDaLeggere );
	}
	//TODO prova con array
	public void aggiungiPreferiti(String nomeFumetto) throws SQLException{
		
		String query = "INSERT INTO preferiti(utente,nome_fumetto) values(\""+getIdFacebook()
				+"\",\""+nomeFumetto+"\");";
		DataBase.getStatement().executeUpdate(query);	
	}
	
	public void aggiungiFollow(String follow) throws SQLException{
		
		String query = "INSERT INTO segue(utente_follower,utente_follow)valus (\""+getIdFacebook()
				+"\",\""+follow+"\");";
		DataBase.getStatement().executeUpdate(query);	
	}
	
	public void aggiungiDaLeggere(String nomeFumetto) throws SQLException{
		
		String query = "INSERT INTO da_leggere(utente,nome_fumetto) values(\""+getIdFacebook()
				+"\",\""+nomeFumetto+"\");";
		DataBase.getStatement().executeUpdate(query);	
	}
	
	public void aggiungiCronologia(String nomeFumetto) throws SQLException{
		//TODO continuare
	}
	public String getIdFacebook() throws SQLException {
		
		return cursoreLettore.getString(1);
	}

	public String getNome() throws SQLException {
		
		return cursoreLettore.getString(2);
	}

	public String getUrlFoto() throws SQLException {
		
		return cursoreLettore.getString(3);
	}
public static void main(String[] args) {
	String id="12415";
	String nomeFumetto="Naruto";
	
	String query = "INSERT INTO preferiti(utente,nome_fumetto) values(\""+id
			+"\",\""+nomeFumetto+"\");";
	System.out.println(query);
}
}
