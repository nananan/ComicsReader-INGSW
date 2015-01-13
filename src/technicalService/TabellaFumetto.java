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
	
	public TabellaFumetto(String filtro, String tipoFumetto, String statoFumetto) throws SQLException{
		
		int èOccidentale = 1, èCompleto = 1;
		if (tipoFumetto.equals("Orientale"))
			èOccidentale = 0;
		if (statoFumetto.equals("Incompleto"))
			èCompleto = 0;
		
		if (tipoFumetto.equals(""))
		{
			String query = "Select * from fumetto where nome = "
					+ "(SELECT nome_fumetto FROM genere_fumetto where nome_fumetto IN "
					+ "(select nome from fumetto where completa =\""+èCompleto+"\""
					+ "and genere =\""+filtro+"\" ));";
			
				System.out.println(query);
				
				cursoreFumetto = DataBase.getStatement().executeQuery(query);
		}
		else if (statoFumetto.equals(""))
		{
			String query = "Select * from fumetto where nome = "
					+ "(SELECT nome_fumetto FROM genere_fumetto where nome_fumetto IN "
					+ "(select nome from fumetto where occidentale =\""+èOccidentale+"\""
					+ "and genere =\""+filtro+"\" ));";
			
				System.out.println(query);
				
				cursoreFumetto = DataBase.getStatement().executeQuery(query);
		}
		else
		{
			String query = "Select * from fumetto where nome = "
				+ "(SELECT nome_fumetto FROM genere_fumetto where nome_fumetto IN "
				+ "(select nome from fumetto where occidentale =\""+èOccidentale+"\""
				+ "and completa =\""+èCompleto+"\""
				+ "and genere =\""+filtro+"\" ));";
		
			System.out.println(query);
			
			cursoreFumetto = DataBase.getStatement().executeQuery(query);
		}
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
