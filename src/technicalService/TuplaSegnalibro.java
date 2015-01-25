package technicalService;

import java.sql.SQLException;

public class TuplaSegnalibro extends Tupla {

	public TuplaSegnalibro(String query) {
		super(query);
		// TODO Auto-generated constructor stub
	}
	
	
	public String getLettore(){
		try {
			return cursore.getString("utente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String getFumetto(){
		try {
			return cursore.getString("nome_fumetto");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int getVolume(){
		try {
			return cursore.getInt("numero_volume");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getCapitolo(){
		try {
			return cursore.getInt("numero_capitolo");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getPagina(){
		try {
			return cursore.getInt("numero_pagina");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
