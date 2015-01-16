package technicalService;

import java.sql.SQLException;

public class TuplaFumetto extends Tupla{
	
	
	public TuplaFumetto(String query) {
		super(query);
		// TODO Auto-generated constructor stub
	}
	
	public String getNome() {
		
		try{
			return  cursore.getString("nome");
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public String getAutore(){
		
		try{
			return cursore.getString("autore");
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String getArtista() {
		try{
			return cursore.getString("artista");
		} catch (SQLException e){
			e.printStackTrace();			
			return null;
		}
	}
		
	public boolean getECompleto(){
		
		try{
			return cursore.getBoolean("completa");
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getEOccidentale() {
		
		try{
			return cursore.getBoolean("occidentale");
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	public String getUrlCopertina(){
		
		try{
			return cursore.getString("url_copertina_primo_volume");
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public double getValutazioneMedia(){
		
		try{
			return cursore.getDouble("valutazione_media");
		} catch (SQLException e){
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public int getNumeroLetture(){
		
		try{
			return cursore.getInt("numero_letture");
		} catch (SQLException e){
			e.printStackTrace();
			return 0;
		}
	}
	

	public String getTrama()
	{
		try{
			return cursore.getString("trama");
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public static TuplaFumetto generaFumettiPerFiltri(String[] array,
			int statoFumetto, int provenienzaFumetto) {
		// TODO Auto-generated method stub
		return null;
	}
}
