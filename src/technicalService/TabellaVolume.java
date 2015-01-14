package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TabellaVolume {
	
	private ResultSet cursoreVolume;
	private String QUERY_VOLUMI;
	private String nomeFumettoCorrente;
	
	public TabellaVolume(String nomeFumetto) throws SQLException {
		QUERY_VOLUMI = "SELECT nome_fumetto,numero,nome,url_copertina  FROM volume WHERE nome_fumetto='"+nomeFumetto+"';";
		cursoreVolume = DataBase.getStatement().executeQuery(QUERY_VOLUMI);
		this.nomeFumettoCorrente = nomeFumetto;
		
	}
	
	public boolean nextVolume() {
		
		try
		{
			return cursoreVolume.next();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public String getNomeFumetto(){
		
		try
		{
			return nomeFumettoCorrente = cursoreVolume.getString("nome_fumetto");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public int getNumero(){
	
		try
		{
			return cursoreVolume.getInt("numero");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public String getNome() {
		
		try
		{
			return cursoreVolume.getString("nome");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String getUrlCopertina() {
		
		try
		{
			return cursoreVolume.getString("url_copertina");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
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
