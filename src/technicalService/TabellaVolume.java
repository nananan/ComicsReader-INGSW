package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TabellaVolume {
	
	private ResultSet cursoreVolume;
	private String QUERY_VOLUMI;
	
	public TabellaVolume(String nomeFumetto) throws SQLException {
		
		QUERY_VOLUMI = "SELECT numero,nome,url_copertina FROM volume WHERE nome_fumetto='"+nomeFumetto+"';";
		cursoreVolume = DataBase.getStatement().executeQuery(QUERY_VOLUMI);
		
	}
	public boolean nextVolume() throws SQLException {
		
		return cursoreVolume.next();
	}
	
	public String getNome() throws SQLException {
		
		return cursoreVolume.getString(2);
	}
	
	public int getNumero() throws SQLException {
	
		return cursoreVolume.getInt(1);
	}
	
	public String getUrlCopertina() throws SQLException {
		
		return cursoreVolume.getString(3);
	}
	public void close() throws SQLException {
		cursoreVolume.close();
	}
	public int getNumeroVolumi(String nomeFumetto) throws SQLException {
		
		String queryNumeroVolumi = "SELECT count(*) FROM volume WHERE nome_fumetto='"+nomeFumetto+"';";
		ResultSet r=DataBase.getStatement().executeQuery(queryNumeroVolumi);
		r.next();
		int numero = r.getInt(1);
		r.close();
		return numero;
	
	}
}
