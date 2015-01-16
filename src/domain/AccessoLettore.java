package domain;


import technicalService.DataBase;
import technicalService.TuplaLettore;

public class AccessoLettore
{
	private Lettore lettore;
	private TuplaLettore tuplaLettore;
	private String idLettore;
	
	public AccessoLettore(String idLettore)
	{
		this.idLettore = idLettore;
		DataBase.connetti();
		tuplaLettore = new TuplaLettore(idLettore);
		tuplaLettore.prossima();
		lettore = new Lettore(tuplaLettore);
			
		
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
	public TuplaLettore getTuplaLettore()
	{
		return tuplaLettore;
	}
	public String getIdLettore()
	{
		return idLettore;
	}
}
