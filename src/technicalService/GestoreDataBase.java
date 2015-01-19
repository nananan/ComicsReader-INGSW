package technicalService;
import java.sql.*;
import java.util.Calendar;

public class GestoreDataBase {
	
	private static final String USER = "manueliana";
	private static final String PASS = "ciaosonoricca";
   	private static final String CONNECTION_URL = "jdbc:mariadb://5.196.65.101:5061/comics_reader";
    private static final String DRIVER = "org.mariadb.jdbc.Driver"; 
    private static Connection connection;
    private static Statement statement;
    private static GestoreDataBase istanza;
    
    public static GestoreDataBase getIstanza(){
    	if(istanza == null)
    		istanza = new GestoreDataBase();
    	return istanza;
    }
    private GestoreDataBase(){
    	connetti();
    }
    
	public static void connetti(){
    	
    	try {
			Class.forName(DRIVER);
			connection= DriverManager.getConnection(CONNECTION_URL, USER, PASS);
			statement = connection.createStatement(); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    
    }
    public static boolean eConnesso(){
    	try {
			return !connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    public static void disconnetti(){
    	
    	try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
    public static Connection getConnection() {
	
    	return connection;
	}

	public static Statement getStatement(){
		
		try {
			statement.close();
			statement = connection.createStatement();
			return statement;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TuplaFumetto creaTuplaFumetto(int indiceRiga){
		String queryFumetti = "SELECT nome,url_copertina_primo_volume,autore,artista, "
				+ "completa,occidentale,valutazione_media, numero_Letture FROM fumetto LIMIT "+indiceRiga+","+8+";";
		return new TuplaFumetto(queryFumetti);
	}
	
	public TuplaFumetto creaTuplaFumetto(String fumetto){
		String query =" SELECT nome,url_copertina_primo_volume,autore,artista, "
				+ "completa,occidentale,valutazione_media, numero_Letture FROM fumetto where nome =\""+fumetto+"\";";
		return new TuplaFumetto(query);
	}
	public TuplaFumetto creaTuplaFumettoPerArtista(String artista){
		String query =" SELECT nome,url_copertina_primo_volume,autore,artista, "
				+ "completa,occidentale,valutazione_media, numero_Letture FROM fumetto where artista =\""+artista+"\";";
		return new TuplaFumetto(query);
	}
	
	public TuplaFumetto creaTuplaFumettoPerAutpre(String autore){
		String query =" SELECT nome,url_copertina_primo_volume,autore,artista, "
				+ "completa,occidentale,valutazione_media, numero_Letture FROM fumetto where autore =\""+autore+"\";";
		return new TuplaFumetto(query);
	}
	
	public TuplaVolume creaTuplaVolume(String fumetto){
		String query = "SELECT  nome_fumetto,numero,nome,url_copertina FROM volume WHERE nome_fumetto =\""
				+fumetto+"\";";
		return new TuplaVolume(query);
	}
	
	public TuplaCapitolo creaTuplaCapitolo(String fumetto, int volume){
		String query = "SELECT nome_fumetto,numero_volume,numero,titolo,numero_pagine,url_capitolo "
				+ "FROM capitolo "
				+ "WHERE nome_fumetto = \""+fumetto+"\" and numero_volume = "+ volume +";";
		return new TuplaCapitolo(query);
	}
	
	public TuplaLettore creaTuplaLettore(String idFacebook){
		String query = "SELECT id_facebook, nome, url_foto,numFollows,numFollower "
				+ "FROM utente "
				+ "WHERE id_facebook = \""+idFacebook+"\""; 
		return new TuplaLettore(query);
	}
	public String getTramaFumetto(String fumetto) {
		String query = " SELECT trama FROM fumetto WHERE nome =\""+fumetto+"\";";
		try {
			ResultSet r = getStatement().executeQuery(query);
			r.next();
			return r.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
	public int primoCapitoloVolume(String nome, int numero) {
		String query = "SELECT numero "
				+ "FROM capitolo "
				+ "WHERE nome_fumetto = \""+nome+"\" and numero_volume = "+ numero +" ;";		
		try {
			System.out.println(query);
			ResultSet r = getStatement().executeQuery(query);
			r.next();
			return r.getInt("numero");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	public String[] getGeneri(){
		String query ="SELECT  nome FROM genere";
		String[] generi=null;
		try {
			generi = new String[getNumeroGeneri()];
			ResultSet cursore = getStatement().executeQuery(query);
			for(int i=0;cursore.next();i++)
			{
				generi[i] = cursore.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return generi;
	}
	public String[] getGeneri(String fumetto){
		String query ="SELECT  genere FROM genere_fumetto WHERE nome_fumetto =\""+fumetto+"\";";
		String[] generi=null;
		try {
			generi = new String[getNumeroGeneri(fumetto)];
			ResultSet cursore = getStatement().executeQuery(query);
			for(int i=0;cursore.next();i++)
			{
				generi[i] = cursore.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return generi;
	}
	public int getNumeroFumetti() {
		String query = "SELECT count(*) FROM fumetto";
		
		try {
			ResultSet r = getStatement().executeQuery(query);
			r.next();
			return r.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public int getNumeroVolumi(String fumetto){
		String query = "SELECT count(*) FROM volume WHERE nome_fumetto = \""+fumetto+"\"";

		try {
			ResultSet r = getStatement().executeQuery(query);
			r.next();
			return r.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public int getNumeroCapitoli(String fumetto, int volume){
		String query = "SELECT count(*) FROM capitolo WHERE nome_fumetto = \""+fumetto+"\" and numero_volume="+volume+";";

		try {
			ResultSet r = getStatement().executeQuery(query);
			r.next();
			return r.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public int getNumeroGeneri(){
		String query = "SELECT count(*) FROM genere";

		try {
			ResultSet r = getStatement().executeQuery(query);
			r.next();
			return r.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public int getNumeroGeneri(String fumetto){
		String query = "SELECT count(*) FROM genere_fumetto WHERE nome_fumetto = \""+fumetto+"\"";
		try {
			ResultSet r = getStatement().executeQuery(query);
			r.next();
			return r.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	public int getValutazione(String lettore, String nomeFumetto) {
		String query = "SELECT valutazione"
				+ "FROM valuta"
				+ "WHERE utente =\""+lettore+"\" and d.nome_fumetto =\""+nomeFumetto+"\";";
		try {
			ResultSet v = GestoreDataBase.getStatement().executeQuery(query);
			v.next();
			return v.getInt("valutazione");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public TuplaFumetto piuVotati()
	{
		String query = "SELECT nome,url_copertina_primo_volume,autore,artista,completa,occidentale,valutazione_media, numero_Letture FROM fumetto ORDER BY valutazione_media DESC LIMIT 8;";
		return new TuplaFumetto(query);
	}
	
	public TuplaFumetto piuLetti()
	{
		String query = "SELECT nome,url_copertina_primo_volume,autore,artista,completa,occidentale,valutazione_media, numero_Letture FROM fumetto ORDER BY numero_letture DESC LIMIT 8;";
		return new TuplaFumetto(query);
	}
	
	public void aggiungiPreferiti(String lettore,String nomeFumetto){
		
		String query = "INSERT INTO preferiti(utente,nome_fumetto) values(\""+lettore
				+"\",\""+nomeFumetto+"\");";
		try {
			GestoreDataBase.getStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public void aggiungiFollow(String lettore,String follow){
		
		String query = "INSERT INTO segue(utente_follower,utente_follow)values (\""+lettore
				+"\",\""+follow+"\");";
		try {
			GestoreDataBase.getStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void aggiungiDaLeggere(String lettore,String nomeFumetto){
		
		String query = "INSERT INTO da_leggere(utente,nome_fumetto) values(\""+lettore
				+"\",\""+nomeFumetto+"\");";
		try {
			GestoreDataBase.getStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void aggiungiCronologia(String lettore, String nomeFumetto) {
		Calendar cal = Calendar.getInstance();
		
		int month = cal.get(Calendar.MONTH)+1;
		String dataLettura = cal.get(Calendar.YEAR)+"-"+month+"-"+cal.get(Calendar.DAY_OF_MONTH);
		
		String procedure = "{call add_cronologia(?,?,?)}";
		CallableStatement callableStatement = null;
		
		try {
			 callableStatement = connection.prepareCall(procedure);
			 callableStatement.setString(1, lettore);
			 callableStatement.setString(2, nomeFumetto);
			 callableStatement.setString(3, dataLettura);
			 callableStatement.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void aggiungiValutazione(String lettore,String nomeFumetto, int valutazione){
		
		String query = "call valutazione(\""+lettore+"\",\""+nomeFumetto+"\","
				+valutazione+");";
		try {
			GestoreDataBase.getStatement().execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void aggiungiSegnalibro(String lettore,String nomeFumetto,int numeroVolume, int numeroCapitolo, int numeroPagina){
//		String query = "call agginugiSegnalibro(\""+lettore+"\",\""+nomeFumetto+"\","
//				+numeroVolume+","+numeroCapitolo+","+numeroPagina+");";
//		DataBase.getStatement().execute(query);
	}	
	
	public void rimuoviFollow(String lettore,String idFollow){
		
		String rimuoviFollow ="DELETE FROM segue WHERE utente_follow=\""+idFollow
				+"\" and utente_follower=\""+lettore+"\";";
		try {
			GestoreDataBase.getStatement().executeUpdate(rimuoviFollow);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public void rimuoviPreferiti(String lettore,String nomeFumetto){
		
		String rimuoviPreferito = "DELETE FROM preferiti WHERE utente=\""+lettore+"\" "
				+ "and nome_fumetto=\""+nomeFumetto+"\";";
		try {
			GestoreDataBase.getStatement().executeUpdate(rimuoviPreferito);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void rimuoviDaLeggere(String lettore,String nomeFumetto){
		
		try{
			String rimuoviDaLeggere = "DELETE FROM da_leggere WHERE utente=\""+lettore+"\" "
				+ "and nome_fumetto=\""+nomeFumetto+"\";";
			GestoreDataBase.getStatement().executeUpdate(rimuoviDaLeggere);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
//		
			
//			DataBase.connetti();
//			TabellaFumetto f = new TabellaFumetto();
//			f.nextFumetto();
//			System.out.println(f.getECompleto());
//			f.nextFumetto();
//			System.out.println(f.getECompleto());
//
//
//			DataBase.disconnetti();
//			
	}
}
