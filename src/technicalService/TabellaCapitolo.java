package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TabellaCapitolo {
	
	private ResultSet cursoreCapitolo;
	private String queryCapitolo;
	
	public TabellaCapitolo(String nomeFumetto, int numero) throws SQLException {
		queryCapitolo = "SELECT * FROM capitolo WHERE nome_fumetto='"+nomeFumetto+"' and numero_volume='"+numero+"';";
		cursoreCapitolo = DataBase.getStatement().executeQuery(queryCapitolo);
	}


}

