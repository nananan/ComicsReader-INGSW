package downloader;

public class ScaricamentoImmagineIncompletoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ScaricamentoImmagineIncompletoException(String urlImmagine) {
		super("Il download dell' immagine con url : "+urlImmagine+
				 " non è ancora terminato");
	}
}