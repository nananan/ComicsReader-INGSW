package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TabellaFumetto {
	
	private ResultSet cursoreFumetto;
	private String nomeFumettoCorrente;
	final private String QUERY_FUMETTO = "SELECT * FROM fumetto;";
	
	public TabellaFumetto() throws SQLException {
		
		cursoreFumetto = DataBase.getStatement().executeQuery(QUERY_FUMETTO);	
	}
	
	public TabellaFumetto(String fumetto) throws SQLException{
		
		String query = "SELECT * FROM fumetto where nome =\""+fumetto+"\";";
		cursoreFumetto = DataBase.getStatement().executeQuery(query);
		cursoreFumetto.next();
	}
	
	public TabellaFumetto(ArrayList<String> arrayDiFiltri, String filtro) throws SQLException{
		
		int èOccidentale = 1, èCompleto = 0;
		if (arrayDiFiltri.get(0).equals("Orientale"))
			èOccidentale = 0;
		if (arrayDiFiltri.get(1).equals("Completo"))
			èCompleto = 1;
		
			String query = "Select * from fumetto where nome = "
				+ "(SELECT nome_fumetto FROM genere_fumetto where nome_fumetto IN "
				+ "(select nome from fumetto where occidentale =\""+èOccidentale+"\""
				+ "and completa =\""+èCompleto+"\")"
				+ "and genere =\""+filtro+"\" );";
		
			cursoreFumetto = DataBase.getStatement().executeQuery(query);
			
	}
	
	public TabellaFumetto(String tipoDaCercare, String testoDaCercare) throws SQLException
	{
		if (tipoDaCercare.equals("Fumetto"))
			tipoDaCercare = "Nome";
		
		String query = "Select * from fumetto where " 
						+ tipoDaCercare + "= \"" + testoDaCercare + "\";";
		
		cursoreFumetto = DataBase.getStatement().executeQuery(query);

	}
	
	public TabellaFumetto(ResultSet cursorePreferiti) {
		cursoreFumetto = cursorePreferiti;
	}

	public boolean nextFumetto() throws SQLException {
		
		return cursoreFumetto.next();
	}
	
	public String getNome() throws SQLException {
		
		return nomeFumettoCorrente = cursoreFumetto.getString(1);
	}

	public String getAutore() throws SQLException{
		
		return cursoreFumetto.getString(2);
	}
	public String getArtista() throws SQLException {
		return cursoreFumetto.getString(3);
	}
	public String getDescrizione() throws SQLException{
		
		return cursoreFumetto.getString(4);
	}
	
	public boolean getECompleto() throws SQLException{
		
		return cursoreFumetto.getBoolean(5);
	}
	
	public boolean getEOccidentale() throws SQLException {
		
		return cursoreFumetto.getBoolean(6);
	}
	public String getUrlCopertina() throws SQLException{
		
		return cursoreFumetto.getString(7);
	}
	
	public double getValutazioneMedia() throws SQLException{
		
		return cursoreFumetto.getDouble(8);
	}
	
	public int getNumeroLetture() throws SQLException{
		
		return cursoreFumetto.getInt(9);
	}
	
	public void close() throws SQLException{
		cursoreFumetto.close();
	}

	
}
