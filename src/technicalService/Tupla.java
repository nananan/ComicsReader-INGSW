package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Tupla {
	
	protected ResultSet cursore;
	
	public Tupla(String query){
		try {
			cursore = DataBase.getStatement().executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			cursore.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean prossima(){
		try {
			return cursore.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean precedente(){
		try {
			return cursore.previous();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
			
		}
	}
}
