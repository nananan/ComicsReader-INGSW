package ui;

public class ThreadPagina extends Thread {
	private PannelloVisualizzatore pannello;
	
	public ThreadPagina(PannelloVisualizzatore pannello) {
		this.pannello=pannello;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(pannello.getImmagineCorrente()!=null){
				pannello.visualizzaPaginaCorrente();
				break;
			}
		}
	}
}
