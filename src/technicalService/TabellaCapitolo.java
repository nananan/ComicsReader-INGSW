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
	
	
	public boolean nextCapitolo() throws SQLException{
		
		return cursoreCapitolo.next();
	}
	
	public int getNumero() throws SQLException{
		
		return cursoreCapitolo.getInt(3);
	}
	public int getNumeroVolume() throws SQLException{
		
		return cursoreCapitolo.getInt(2);
	}
	
	public String getNomeFumetto() throws SQLException{
		
		return cursoreCapitolo.getString(1);
	}
	
	public String getTitolo() throws SQLException{
		
		return cursoreCapitolo.getString(4);
	}
	
	public String getUrlPrimaPagina() throws SQLException{
		return cursoreCapitolo.getString(6);
	}


	public int getNumeroCapitoli() throws SQLException {
		
		String queryNumeroCapitoli = "SELECT count(*) FROM volume WHERE nome_fumetto='"+getNomeFumetto()+
				"' and numero_volume="+getNumeroVolume()+";";
		ResultSet r=DataBase.getStatement().executeQuery(queryNumeroCapitoli);
		r.next();
		int numero = r.getInt(1);
		r.close();
		return numero;	
	}
	
	public int getNumeroPagine() throws SQLException {
		
		return cursoreCapitolo.getInt(5);
	}

	public void aggiorna() throws SQLException {
		
		cursoreCapitolo.close();
		cursoreCapitolo = DataBase.getStatement().executeQuery(queryCapitolo);
		
	}



	

}

