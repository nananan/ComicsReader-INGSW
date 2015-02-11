package web;

public class ErroreAutenticazioneException extends Exception
{
private static final long serialVersionUID = 1L;
	
	public ErroreAutenticazioneException(){
		super("Errore Login");
	}
}
