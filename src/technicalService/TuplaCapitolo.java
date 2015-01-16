package technicalService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TuplaCapitolo extends Tupla{
	
	
	public TuplaCapitolo(String query) {
		super(query);
	}
	
	public int getNumero(){
		
		try
		{
			return cursore.getInt("numero");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	public int getNumeroVolume(){
		
		try
		{
			return cursore.getInt("numero_volume");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public String getNomeFumetto(){
		
		try
		{
			return cursore.getString("nome_fumetto");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String getTitolo(){
		
		try
		{
			return cursore.getString(4);
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String getUrlPrimaPagina(){
		try
		{
			return cursore.getString("url_capitolo");
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	public int getNumeroCapitoli(String fumetto, int volume){
		
		try {
			String queryNumeroCapitoli = "SELECT count(*) FROM capitolo WHERE nome_fumetto='"+fumetto+
					"' and numero_volume="+volume+";";
			ResultSet r = DataBase.getStatement().executeQuery(queryNumeroCapitoli);	
			r.next();
			int numero = r.getInt(1);
			r.close();
			return numero;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	

	}
	
	public int getNumeroPagine(){
		
		try {
			return cursore.getInt("numero_pagine");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}

