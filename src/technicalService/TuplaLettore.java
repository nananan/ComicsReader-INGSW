package technicalService;

import java.sql.SQLException;

public class TuplaLettore extends Tupla{
	private String utenteCorrente;
	public TuplaLettore(String query) {
		super(query);
	}

	public TuplaLettore getFollows() {
		
		String query = "SELECT id_facebook, nome, url_foto,numFollows, numFollower "
				+ "FROM utente ,segue "
				+ "WHERE utente_follower=\""+utenteCorrente+"\" and utente_follow = id_facebook;";		
		
		return new TuplaLettore(query);
	}
	
	public TuplaLettore getFollower(){
		
		String query = "SELECT id_facebook, nome, url_foto,numFollows, numFollower "
				+ "FROM utente ,segue "
				+ "WHERE utente_follow=\""+utenteCorrente+"\" and utente_follower = id_facebook;";
				
		return new TuplaLettore(query);
	}
	
	public TuplaFumetto getPreferiti() {
		String query =" SELECT f.nome, f.autore, f.artista,f.completa,f.occidentale,f.url_copertina_primo_volume,"
				+ "f.valutazione_media,f.numero_letture "
				+ "FROM fumetto as f , preferiti as p "
				+ "WHERE p.utente =\""+utenteCorrente+"\" and p.nome_fumetto = f.nome;";
				
		return new TuplaFumetto(query);
	}
	
	public TuplaFumetto getDaLeggere(){
	
		String query = "SELECT f.nome, f.autore, f.artista,f.completa,f.occidentale,f.url_copertina_primo_volume,"
				+ "f.valutazione_media,f.numero_letture  "
				+ "FROM fumetto as f, da_leggere as d "
				+ "WHERE d.utente =\""+utenteCorrente+"\" and d.nome_fumetto = f.nome;";
		return new TuplaFumetto(query );
	}
	
	public TuplaFumetto getCronologia(){
		String query = "SELECT f.nome, f.autore, f.artista,f.completa,f.occidentale,f.url_copertina_primo_volume,"
				+ "f.valutazione_media,f.numero_letture "
				+ "FROM fumetto as f, letture_recenti as r "
				+ "WHERE r.utente =\""+utenteCorrente+"\" and r.nome_fumetto = f.nome"
				+ "ORDER BY data_lettura DESC; ";
		return new TuplaFumetto(query );
	}
	
	public String getIdFacebook() {
		
		try {
			return utenteCorrente=cursore.getString("id_facebook");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getNome(){
		
		try {
			return cursore.getString("nome");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getUrlFoto() {
		
		try {
			return cursore.getString("url_foto");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public int getNumFollows(){
		
		try {
			return cursore.getInt("numFollows");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int getNumFollower(){
		
		try {
			return cursore.getInt("numFollower");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static void main(String[] args) {
		String idFacebookCorrente = "1590013667";
		String nomeFumetto = "Death Note";
		String rimuoviPreferito = "DELETE FROM preferiti WHERE utente=\""+idFacebookCorrente+"\" "
				+ "and nome_fumetto=\""+nomeFumetto+"\";";
		
		System.out.println(rimuoviPreferito);
	}

}
