package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;



public class TabellaGeneri
{
	private String queryGeneri = "SELECT * FROM genere";
	private String queryNumeroGeneri = "Select count(*) from genere";
	
	private ResultSet tuplaGenereResultSet;
	private ResultSet numeroGeneri;
	private String[] generi;
	
	private String ultimoFumetto;
	private String[] generiFumetto;
	
	public TabellaGeneri() throws SQLException{

		tuplaGenereResultSet = DataBase.getStatement().executeQuery(queryGeneri+";");
		numeroGeneri = DataBase.getStatement().executeQuery(queryNumeroGeneri+";");
		numeroGeneri.next();
	}
	
	public String[] getGeneri() throws SQLException{
		
		if(generi != null) return generi;
		
		generi = new String[numeroGeneri.getInt(1)];
		
		for(int i=0;tuplaGenereResultSet.next();i++)
		{
			generi[i] = tuplaGenereResultSet.getString(1);
		}
		
		return generi;
	}
	
	public String[] getGeneri(String nomeFumetto) throws SQLException{
		
		if(nomeFumetto == ultimoFumetto ) return generiFumetto;
		
		ultimoFumetto = nomeFumetto;
		System.out.println(nomeFumetto);
		String queryGeneriFumetto = "SELECT * FROM genere_fumetto where nome_fumetto=\""+nomeFumetto+"\";";
		String queryNumeroGeneriFumetto = "Select count(*) from genere_fumetto  where nome_fumetto=\""+nomeFumetto+"\";";
		
		ResultSet tuplaGenereResultSet =DataBase.getStatement().executeQuery(queryGeneriFumetto);
		ResultSet numeroGeneri=DataBase.getStatement().executeQuery(queryNumeroGeneriFumetto);
		numeroGeneri.next();

		generiFumetto= new String[numeroGeneri.getInt(1)];
		
		for(int i=0;tuplaGenereResultSet.next();i++)
		{
			generiFumetto[i] = tuplaGenereResultSet.getString(2);
		}
			
		return generiFumetto;
	}
}
