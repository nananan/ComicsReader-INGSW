package domain;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import technicalService.TabellaCapitolo;
import technicalService.TabellaVolume;

public class Volume {
	
	private String nome;
	private int numero;
	private ImageIcon copertina;
	private String url_copertina;
	
	private TabellaCapitolo cursoreCapitolo;
	private Capitolo[] capitoli;
	private int numeroCapitolo;
	
	public Volume(TabellaVolume tuplaVolume) throws SQLException, MalformedURLException {
		
		nome = tuplaVolume.getNome();
		numero = tuplaVolume.getNumero();
		url_copertina = tuplaVolume.getUrlCopertina();
		copertina = new ImageIcon(new URL(url_copertina).toString());
		
		cursoreCapitolo = new TabellaCapitolo();
		
		
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

	public ImageIcon getCopertina() {
		return copertina;
	}

	public String getUrl_copertina() {
		return url_copertina;
	}
}
