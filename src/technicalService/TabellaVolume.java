package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TabellaVolume {
	
	private ResultSet cursoreVolume;
	private String QUERY_VOLUMI;
	private String nomeFumettoCorrente;
	
	public TabellaVolume(String nomeFumetto) throws SQLException {
		
		QUERY_VOLUMI = "SELECT * FROM volume WHERE nome_fumetto='"+nomeFumetto+"';";
		cursoreVolume = DataBase.getStatement().executeQuery(QUERY_VOLUMI);
		this.nomeFumettoCorrente = nomeFumetto;
		
	}
	public boolean nextVolume() throws SQLException {
		
		return cursoreVolume.next();
	}
	
	public String getNomeFumetto() throws SQLException {
		
		return nomeFumettoCorrente = cursoreVolume.getString(1);
	}
	
	public int getNumero() throws SQLException {
	
		return cursoreVolume.getInt(2);
	}

	public String getNome() throws SQLException {
		
		return cursoreVolume.getString(3);
	}
	
	public String getUrlCopertina() throws SQLException {
		
		return cursoreVolume.getString(4);
	}
	
	public void close() throws SQLException {
		
		cursoreVolume.close();
	}
	
	public int getNumeroVolumi() throws SQLException {
		
		String queryNumeroVolumi = "SELECT count(*) FROM volume WHERE nome_fumetto='"+nomeFumettoCorrente+"';";
		ResultSet r=DataBase.getStatement().executeQuery(queryNumeroVolumi);
		r.next();
		int numero = r.getInt(1);
		r.close();
		return numero;
	
	}
	
	public void aggiorna() throws SQLException {
		cursoreVolume.close();
		cursoreVolume = DataBase.getStatement().executeQuery(QUERY_VOLUMI);

	}
	public static void main(String[] args) {
		try {
			DataBase.connect();
			TabellaVolume tv = new TabellaVolume("666 Satan");
			int numVolumi = tv.getNumeroVolumi();
			System.out.println(numVolumi);
//			System.out.println(tv.getNomeFumetto());
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
