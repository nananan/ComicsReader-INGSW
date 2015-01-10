package downloader;

public class ScaricatoreImmagini extends Thread {
	private Downloader downloader;
	
	ScaricatoreImmagini(Downloader donnloader) {
		this.downloader = donnloader;
	}
	
	@Override
	public void run() {
		while(!downloader.downloadConcluso()){
			downloader.scaricaProssimeImmagini();
		}
	}
}
