package domain;

import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;

import technicalService.TabellaVolume;

public class Volume {
	
	private String nome;
	private int numero;
	private Image copertina;
	private String url_copertina;
	
	public Volume(TabellaVolume tuplaVolume) throws SQLException {
		
		nome = tuplaVolume.getNome();
		numero = tuplaVolume.getNumero();
		url_copertina = tuplaVolume.getUrlCopertina();
		copertina = Toolkit.getDefaultToolkit().getImage(url_copertina);	
	}
	
	@Override
	public int hashCode() {
		return numero;
	}
	
	public String getNome() {
		return nome;
	}

	public int getNumero() {
		return numero;
	}

	public Image getCopertina() {
		return copertina;
	}

	public String getUrl_copertina() {
		return url_copertina;
	}
}
