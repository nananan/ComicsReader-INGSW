package downloader;

public class ScaricatoreImmagini extends Thread {
	private static final DownloaderManager downloaderManager = DownloaderManager.getDownloaderManager();
	
	@Override
	public void run() {
		while(!downloaderManager.downloadConcluso()){
			downloaderManager.scaricaProssimeImmagini();
		}
	}
}
