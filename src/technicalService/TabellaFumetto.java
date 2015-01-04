package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TabellaFumetto {
	
	private ResultSet cursoreFumetto;
	private String nomeFumettoCorrente;
	final private String QUERY_FUMETTO = "SELECT * FROM fumetto";
	
	public TabellaFumetto() throws SQLException {
		
		cursoreFumetto = DataBase.getStatement().executeQuery(QUERY_FUMETTO);	
	}
	
	public boolean nextFumetto() throws SQLException {
		
		return cursoreFumetto.next();
	}
	
	public String getNome() throws SQLException {
		
		return nomeFumettoCorrente = cursoreFumetto.getString(1);
	}
	
//	TODO Migliorare getGeneri
//	public String[] getGeneri() throws SQLException{
//	
//		final String QUERY_GENERI = "SELECT genere FROM genere_fumetto WHERE nome_fumetto =" +nomeFumettoCorrente+";";
//		ResultSet risualtatoQuery = DataBase.getStatement().executeQuery(QUERY_GENERI);
//		String[] generi = new String[10];
//		
//		for(int i= 0;risualtatoQuery.next();i++) {
//			generi[i]=risualtatoQuery.getString(1);
//		}
//		return generi;
//	}
//	
	
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
