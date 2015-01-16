package technicalService;

import java.sql.SQLException;


public class TuplaVolume extends Tupla{
	
	public TuplaVolume(String query) {
		super(query);
	}
	
	public String getNomeFumetto() {
		
		try
		{
			return cursore.getString("nome_fumetto");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public int getNumero() {
	
		try
		{
			return cursore.getInt("numero");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	public String getNome() {
		
		try
		{
			return cursore.getString("nome");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String getUrlCopertina()  {
		
		try
		{
			return cursore.getString("url_copertina");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
