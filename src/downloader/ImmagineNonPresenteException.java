package downloader;


public class ImmagineNonPresenteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ImmagineNonPresenteException(String url){
		super("L'immagine con url : "+url+" non è stata trovata sul server" );
	}

}
