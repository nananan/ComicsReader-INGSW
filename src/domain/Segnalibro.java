package domain;

import technicalService.TuplaSegnalibro;

public class Segnalibro {
	
	private String fumetto;
	private String lettore;
	private int volume;
	private int capitolo;
	private int pagina;
	
	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getCapitolo() {
		return capitolo;
	}

	public void setCapitolo(int capitolo) {
		this.capitolo = capitolo;
	}

	public String getFumetto() {
		return fumetto;
	}

	public String getLettore() {
		return lettore;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public Segnalibro(TuplaSegnalibro tuplaSegnalibro){
		fumetto = tuplaSegnalibro.getFumetto();
		lettore = tuplaSegnalibro.getLettore();
		volume = tuplaSegnalibro.getVolume();
		capitolo = tuplaSegnalibro.getCapitolo();
		pagina = tuplaSegnalibro.getPagina();
	}
}
