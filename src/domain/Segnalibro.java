package domain;

import technicalService.TuplaSegnalibro;

public class Segnalibro {
	
	private String fumetto;
	private String lettore;
	private Volume volume;
	private int capitolo;
	private int pagina;
	
	public Volume getVolume() {
		return volume;
	}

	public void setVolume(Volume volume) {
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

	public int getPagina()
	{
		return pagina;
	}
	
	public Segnalibro(TuplaSegnalibro tuplaSegnalibro, Fumetto fumetto){
		this.fumetto = tuplaSegnalibro.getFumetto();
		lettore = tuplaSegnalibro.getLettore();
		volume = fumetto.getNumeroVolume(tuplaSegnalibro.getVolume());
		capitolo = tuplaSegnalibro.getCapitolo();
		pagina = tuplaSegnalibro.getPagina();
	}
}
