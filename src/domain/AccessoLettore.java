package domain;

import java.sql.SQLException;

import sun.applet.Main;
import sun.security.action.GetLongAction;
import technicalService.DataBase;
import technicalService.TabellaLettore;

public class AccessoLettore
{
	private Lettore lettore;
	private TabellaLettore tuplaLettore;
	private String idLettore;
	
	public AccessoLettore(String idLettore)
	{
		this.idLettore = idLettore;
		try
		{	DataBase.connect();
			tuplaLettore = new TabellaLettore(idLettore);
			tuplaLettore.nextLettore();
			lettore = new Lettore(tuplaLettore);
			
			
		} catch (SQLException e)
		{
			System.err.println("UTENTE NON PRESENTE");
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args)
	{
		AccessoLettore accessoLettore = new AccessoLettore("1590013667");
		System.out.println("*********************Lettore****************************");
		Lettore l = accessoLettore.getLettore();
		System.out.println(l.getNome());
		System.out.println(l.getIdFacebook());
		System.out.println(l.getUrlFoto());
		System.out.println("********************************************************");
	}
	public Lettore getLettore()
	{
		return lettore;
	}
	public TabellaLettore getTuplaLettore()
	{
		return tuplaLettore;
	}
	public String getIdLettore()
	{
		return idLettore;
	}
}
