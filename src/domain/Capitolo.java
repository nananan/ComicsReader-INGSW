package domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import technicalService.TabellaCapitolo;

public class Capitolo {
	
	private int numero;
	private String titolo;
	private String urlCapitolo;
	
	private ImageIcon[] pagine;
	private int numeroPagine;
	
	public Capitolo( TabellaCapitolo tuplaCapitolo) throws SQLException {
		
		numero = tuplaCapitolo.getNumero();
		titolo = tuplaCapitolo.getTitolo();
		urlCapitolo = tuplaCapitolo.getUrlPrimaPagina();
		
		numeroPagine = tuplaCapitolo.getNumeroPagine();
		
	}
	
	public void caricaPagina() throws MalformedURLException{
		
		if(pagine != null) return;
		
		pagine = new ImageIcon[numeroPagine];
		
		for(int pagina = 0; pagina < numeroPagine;pagina++){
			pagine[pagina] = new ImageIcon(new URL(urlCapitolo+(pagina +1)).toString());
		}
		
	}
	public String getUrlCapitolo() {
		return urlCapitolo;
	}

	public ImageIcon[] getPagine() {
		return pagine;
	}

	public int getNumeroPagine() {
		return numeroPagine;
	}

	public int getNumero() {
		return numero;
	}
	public String getTitolo() {
		return titolo;
	}
}

