package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TabellaLettore {
	
	private ResultSet cursoreLettore;
	final private String QUERY_LETTORE = "SELECT * FROM utente";
	private String idFacebookCorrente;

	public TabellaLettore() throws SQLException{
	
		cursoreLettore = DataBase.getStatement().executeQuery(QUERY_LETTORE);
	}
	public TabellaLettore(String id) throws SQLException{
		
		String query = "SELECT * FROM utente WHERE id_facebook=\""+id+"\";";
		cursoreLettore = DataBase.getStatement().executeQuery(query);
		idFacebookCorrente = id;

	}
	public TabellaLettore(ResultSet cursore){
		cursoreLettore = cursore;
	}
	public TabellaLettore getFollows() throws SQLException{
		
		String query = "SELECT id_facebook, nome, url_foto,numFollows, numFollower "
				+ "FROM utente ,segue "
				+ "WHERE utente_follower=\""+idFacebookCorrente+"\" and utente_follow = id_facebook;";
		
		ResultSet cursoreFollows = DataBase.getStatement().executeQuery(query);
		
		return new TabellaLettore(cursoreFollows);
	}
	
	public TabellaLettore getFollower() throws SQLException {
		
		String query = "SELECT id_facebook, nome, url_foto,numFollows, numFollower "
				+ "FROM utente ,segue "
				+ "WHERE utente_follow=\""+idFacebookCorrente+"\" and utente_follower = id_facebook;";
		
		ResultSet cursoreFollows = DataBase.getStatement().executeQuery(query);
		
		return new TabellaLettore(cursoreFollows);
	}
	
	public TabellaFumetto getPreferiti() throws SQLException{
		String query =" SELECT nome, autore, artista,trama,completa,occidentale,url_copertina_primo_volume,"
				+ "valutazione_media,numero_letture "
				+ "FROM fumetto as f , preferiti as p "
				+ "WHERE p.utente =\""+idFacebookCorrente+"\" and p.nome_fumetto = f.nome;";
				
		ResultSet cursorePreferiti = DataBase.getStatement().executeQuery(query);
		return new TabellaFumetto(cursorePreferiti);
	}
	
	public TabellaFumetto getDaLeggere() throws SQLException{
	
		String query = "SELECT nome, autore, artista,trama,completa,occidentale,url_copertina_primo_volume,"
				+ "valutazione_media,numero_letture  "
				+ "FROM fumetto as f, da_leggere as d "
				+ "WHERE d.utente =\""+idFacebookCorrente+"\" and d.nome_fumetto = f.nome;";
		ResultSet cursoreDaLeggere = DataBase.getStatement().executeQuery(query);
		return new TabellaFumetto(cursoreDaLeggere );
	}
	
	public TabellaFumetto getCronologia() throws SQLException{
		//TODO rivedere
		String query = "SELECT nome, autore, artista,trama,completa,occidentale,url_copertina_primo_volume,"
				+ "valutazione_media,numero_letture "
				+ "FROM fumetto as f, letture_recenti as r "
				+ "WHERE r.utente =\""+idFacebookCorrente+"\" and r.nome_fumetto = f.nome"
				+ "ORDER BY data_lettura DESC; ";
		ResultSet cursoreDaLeggere = DataBase.getStatement().executeQuery(query);
		return new TabellaFumetto(cursoreDaLeggere );
	}
	
	public int getValutazione(String nomeFumetto) throws SQLException{
		String query = "SELECT valutazione"
				+ "FROM valuta"
				+ "WHERE utente =\""+idFacebookCorrente+"\" and d.nome_fumetto =\""+nomeFumetto+"\";";
		ResultSet valutazione = DataBase.getStatement().executeQuery(query);
		valutazione.next();
		return valutazione.getInt(1);
	}
	//TODO prova con array
	public void aggiungiPreferiti(String nomeFumetto) throws SQLException{
		
		String query = "INSERT INTO preferiti(utente,nome_fumetto) values(\""+idFacebookCorrente
				+"\",\""+nomeFumetto+"\");";
		DataBase.getStatement().executeUpdate(query);	
	}

	public void aggiungiFollow(String follow) throws SQLException{
		
		String query = "INSERT INTO segue(utente_follower,utente_follow)values (\""+idFacebookCorrente
				+"\",\""+follow+"\");";
		DataBase.getStatement().executeUpdate(query);	
	}
	
	public void aggiungiDaLeggere(String nomeFumetto) throws SQLException{
		
		String query = "INSERT INTO da_leggere(utente,nome_fumetto) values(\""+idFacebookCorrente
				+"\",\""+nomeFumetto+"\");";
		DataBase.getStatement().executeUpdate(query);	
	}
	
	public void aggiungiCronologia(String nomeFumetto) {
		//TODO inserire la data della lettura;
//		String query = "INSERT INTO letture_recenti(utente,nome_fumetto,data_lettura) values(\""+getIdFacebook()
//				+"\",\""+nomeFumetto+"\",'"+Data"');";
//		DataBase.getStatement().executeUpdate(query);	
	}
	
	public void aggiungiValutazione(String nomeFumetto, int valutazione)throws ValoreNonCorrettoException, SQLException {
		
		if(valutazione > 6 || valutazione < 0) throw new ValoreNonCorrettoException();
		//TODO chiamare la procedura per la valutazione dei fumetti
		
		String query = "call aggiungiValutazione(\""+idFacebookCorrente+"\",\""+nomeFumetto+"\","
				+valutazione+");";
		DataBase.getStatement().execute(query);
	}
	
	public void aggiungiSegnalibro(String nomeFumetto,int numeroVolume, int numeroCapitolo, int numeroPagina) throws SQLException{
		String query = "call agginugiSegnalibro(\""+getIdFacebook()+"\",\""+nomeFumetto+"\","
				+numeroVolume+","+numeroCapitolo+","+numeroPagina+");";
		DataBase.getStatement().execute(query);
	}	
	
	public boolean nextLettore() throws SQLException{
		
		return cursoreLettore.next();
	}
	public String getIdFacebook() throws SQLException {
		
		return idFacebookCorrente=cursoreLettore.getString(1);
	}

	public String getNome() throws SQLException {
		
		return cursoreLettore.getString(2);
	}

	public String getUrlFoto() throws SQLException {
		
		return cursoreLettore.getString(3);
	}
	public int getNumFollows() throws SQLException{
		
		return cursoreLettore.getInt(4);
	}
	
	public int getNumFollower() throws SQLException{
		
		return cursoreLettore.getInt(5);
	}
	public void close() throws SQLException {
	
		cursoreLettore.close();
	}
	
	public void aggiorna(String idLettore) throws SQLException{
		
		String queryAggiorna = "SELECT * FROM utente WHERE  id_facebook=\""+idLettore
				+"\";";
		idFacebookCorrente=idLettore;
		cursoreLettore=DataBase.getStatement().executeQuery(queryAggiorna);
		cursoreLettore.next();
	}
	
	public void rimuoviFollow(String idFollow) throws SQLException{
		
		String rimuoviFollow ="DELETE FROM segue WHERE utente_follow=\""+idFollow
				+"\" and utente_follower=\""+idFacebookCorrente+"\";";
		DataBase.getStatement().executeUpdate(rimuoviFollow);	

	}

	public void rimuoviPreferiti(String nomeFumetto) throws SQLException{
		
		String rimuoviPreferito = "DELETE FROM preferiti WHERE utente=\""+idFacebookCorrente+"\" "
				+ "and nome_fumetto=\""+nomeFumetto+"\";";
		DataBase.getStatement().executeUpdate(rimuoviPreferito);	
	}
	
	public void rimuoviDaLeggere(String nomeFumetto) throws SQLException{
		
		String rimuoviDaLeggere = "DELETE FROM da_leggere WHERE utente=\""+idFacebookCorrente+"\" "
				+ "and nome_fumetto=\""+nomeFumetto+"\";";
		DataBase.getStatement().executeUpdate(rimuoviDaLeggere);
	}
	public static void main(String[] args) {
		String idFacebookCorrente = "1590013667";
		String nomeFumetto = "Death Note";
		String rimuoviPreferito = "DELETE FROM preferiti WHERE utente=\""+idFacebookCorrente+"\" "
				+ "and nome_fumetto=\""+nomeFumetto+"\";";
		
		System.out.println(rimuoviPreferito);
	}

}
