package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TabellaCapitolo {
	
	private ResultSet cursoreCapitolo;
	private String queryCapitolo;
	private String nomeFumettoCorrente;
	private int numeroVolumeCorrente;
	
	public TabellaCapitolo(String nomeFumetto, int numeroVolume) throws SQLException {
		
		queryCapitolo = "SELECT * FROM capitolo WHERE nome_fumetto='"+nomeFumetto+"' and numero_volume='"+numeroVolume+"';";
		cursoreCapitolo = DataBase.getStatement().executeQuery(queryCapitolo);
		nomeFumettoCorrente = nomeFumetto;
		numeroVolumeCorrente = numeroVolume;
	}
	
	
	public boolean nextCapitolo() throws SQLException{
		
		return cursoreCapitolo.next();
	}
	
	public int primoCapitoloVolume() throws SQLException{
		 cursoreCapitolo.next();
		 int n=cursoreCapitolo.getInt(3);
		 cursoreCapitolo.previous();
		 return n;
	}
	public int getNumero() throws SQLException{
		
		return cursoreCapitolo.getInt(3);
	}
	public int getNumeroVolume() throws SQLException{
		
		return numeroVolumeCorrente=cursoreCapitolo.getInt(2);
	}
	
	public String getNomeFumetto() throws SQLException{
		
		return nomeFumettoCorrente=cursoreCapitolo.getString(1);
	}
	
	public String getTitolo() throws SQLException{
		
		return cursoreCapitolo.getString(4);
	}
	
	public String getUrlPrimaPagina() throws SQLException{
		return cursoreCapitolo.getString(6);
	}


	public int getNumeroCapitoli() throws SQLException {
		
		String queryNumeroCapitoli = "SELECT count(*) FROM capitolo WHERE nome_fumetto='"+nomeFumettoCorrente+
				"' and numero_volume="+numeroVolumeCorrente+";";
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
	
	public static void main(String[] args) {
		
	}


	

}

