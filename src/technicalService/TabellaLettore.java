package technicalService;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TabellaLettore {
	
	private ResultSet cursoreLettore;
	final private String QUERY_LETTORE = "SELECT * FROM utente";

	public TabellaLettore() throws SQLException{
	
		cursoreLettore = DataBase.getStatement().executeQuery(QUERY_LETTORE);
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

}
