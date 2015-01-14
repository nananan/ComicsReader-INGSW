package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TabellaFumetto
{
	private ResultSet cursoreFumetto;
	
	private final static int MAX_NUMERO_TUPLE = 8;
	
	static private String queryFumetti = "SELECT nome,url_copertina_primo_volume,autore,artista, "
			+ "completa,occidentale,valutazione_media, numero_Letture FROM fumetto LIMIT ";
	
	static private String queryTrama = "SELECT trama FROM fumetto WHERE nome = \""; 
	
	static private String queryFumetto = "SELECT * FROM fumetto where nome =\"";

	private static String queryNumeroFumetti = "SELECT count(*) FROM fumetto;";

	static private String queryFumettiFiltri = "SELECT nome,url_copertina_primo_volume,autore,artista, "
			+ "completa,occidentale,valutazione_media, numero_Letture FROM fumetto as f, genere_fumetto as g "
			+ "WHERE ";
	private static int numeroTupleGenerate;
	
	static public TabellaFumetto generaProssimeTupleFumetto(){
		
		TabellaFumetto tupla = new TabellaFumetto(queryFumetti + numeroTupleGenerate + "," + MAX_NUMERO_TUPLE+";");
		numeroTupleGenerate +=MAX_NUMERO_TUPLE;
		return tupla; 
	
	}

	static public TabellaFumetto generaProssimeTupleFumetto(int indice){
	
		TabellaFumetto tupla = new TabellaFumetto(queryFumetti + indice + "," + MAX_NUMERO_TUPLE+";");
		return tupla; 	
	}

	static public TabellaFumetto generaTuplaFumetto(String nome){
	
		return new TabellaFumetto(queryFumetto +nome+"\";");
	}

	public String getDescrizione(String nomeFumetto){ 
	
		TabellaFumetto tupla = new TabellaFumetto(queryTrama+nomeFumetto+"\";");
		return tupla.getTrama();
	}
	
	public static int getNumeroFumetti()
	{
		try
		{
			ResultSet numeroFumetti  =DataBase.getStatement().executeQuery(queryNumeroFumetti);
			numeroFumetti.next();
			return numeroFumetti.getInt(1);
		} catch (SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public static TabellaFumetto generaFumettiPerFiltri(String[] strings,
			int statoFumetto, int provenienzaFumetto)
	{
		String query = queryFumettiFiltri;
		return null;
	}
	public TabellaFumetto(String query)
	{
		try
		{
			cursoreFumetto = DataBase.getStatement().executeQuery(query);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean nextFumetto() {
		
		try
		{
			return cursoreFumetto.next();
		} catch (SQLException e)
		{			
			e.printStackTrace();
			return false;
		}
	}
	public String getNome() {
		
		try
		{
			return  cursoreFumetto.getString("nome");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String getAutore(){
		
		try
		{
			return cursoreFumetto.getString("autore");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public String getArtista() {
		try
		{
			return cursoreFumetto.getString("artista");
		} catch (SQLException e)
		{
			e.printStackTrace();			
			return null;

		}
	
	}
		
	public boolean getECompleto(){
		
		try
		{
			return cursoreFumetto.getBoolean("completa");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		}
	}
	
	public boolean getEOccidentale() {
		
		try
		{
			return cursoreFumetto.getBoolean("occidentale");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;

		}
	}
	public String getUrlCopertina(){
		
		try
		{
			return cursoreFumetto.getString("url_copertina_primo_volume");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public double getValutazioneMedia(){
		
		try
		{
			return cursoreFumetto.getDouble("valutazione_media");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return 0.0;

		}
		
	}
	
	public int getNumeroLetture(){
		
		try
		{
			return cursoreFumetto.getInt("numero_letture");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	public void close() throws SQLException{
		cursoreFumetto.close();
	}

	public String getTrama()
	{
		try
		{	nextFumetto();
			return cursoreFumetto.getString("trama");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public boolean previousFumetto()
	{
		try
		{
			return cursoreFumetto.previous();
		} catch (SQLException e)
		{	
			e.printStackTrace();
			return false;
		}
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
	
	public static TabellaFumetto generaFumettiPerArtista(String artista)
	{
		String query = "Select nome,url_copertina_primo_volume,autore,artista, "
			+ "completa,occidentale,valutazione_media, numero_Letture from fumetto where artista = \"" + artista + "\";";
		
		return new TabellaFumetto(query);
	}

	public static TabellaFumetto generaFumettiPerAutore(String autore)
	{
		String query = "Select nome,url_copertina_primo_volume,autore,artista, "
			+ "completa,occidentale,valutazione_media, numero_Letture from fumetto where autore = \"" + autore + "\";";
		
		return new TabellaFumetto(query);
	}
	
	public TabellaFumetto generaFumettiPerNomeFumetto(String nomeFumetto)
	{
		String query = "Select nome,url_copertina_primo_volume,autore,artista, "
			+ "completa,occidentale,valutazione_media, numero_Letture from fumetto where nome = \"" + nomeFumetto + "\";";
		
		return new TabellaFumetto(query);
	}
	
}
