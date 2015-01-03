package domain;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import technicalService.DataBase;
import technicalService.TabellaFumetto;

public class Libreria {
	
	public static void main(String[] args) {
		 
		try {
			try{
				HashMap<String,Fumetto> fumetti = new HashMap<>();
				DataBase.connect();
				TabellaFumetto tupleFumetto = new TabellaFumetto();
				while(tupleFumetto.nextFumetto()){
					Fumetto fumetto = new Fumetto(tupleFumetto);
					fumetti.put(fumetto.getNome(), fumetto);
				}
				tupleFumetto.close();
				
				for(Entry<String,Fumetto> f : fumetti.entrySet()){
					System.out.println("***********************************************************");
					System.out.println(f.getValue().getNome());
					System.out.println(f.getValue().getAutore());
					System.out.println(f.getValue().getArtista());
					System.out.println(f.getValue().getDescrizione());
					System.out.println(f.getValue().isEcompleto());
					System.out.println(f.getValue().isOccidentale());
					System.out.println(f.getValue().getCopertina());
					

				}
				System.out.println("***********************************************************");
				Fumetto dn= fumetti.get("Death Note");
				dn.visualizzaVolumi();
				Volume[] volumi = dn.getVolumi();
				
				for (Volume volume : volumi) {
					System.out.println("***********************************************************");
					System.out.println(volume.getNome());
					System.out.println(volume.getNumero());
					System.out.println(volume.getUrl_copertina());
					
				}
				System.out.println("***********************************************************");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DataBase.disconnect();
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}