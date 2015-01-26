package downloader;

import java.awt.Image;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DownloaderPagine {
	
	private static final int NUMERO_MAX_DOWNLOADER = 10;
	private static final int DISTANZA_CENTRALE_FINALE = 4;
	private static final int DISTANZA_INIZIALE_CENTRALE = 3;
	
	
	private int indiceUltimoScaricatore;
	private int numeroDownloadAttivi;
	private Lock lock;
	private Condition condition;
	
	private Image[][] pagine;
	
	private int indicePaginaCentrale;
	private int indicePaginaIniziale;
	private int indicePaginaFinale;
	
	private int indiceCapitoloPaginaIniziale;
	private int indiceCapitoloPaginaCentrale;
	private int indiceCapitoloPaginaFinale;
	private int numeroPrimoCapitolo;
	private String urlVolumeDaScaricare;
	
	private ScaricatorePagine[] scaricatori;
	private int numeroDownloadConclusi;
	
	public DownloaderPagine(){
		scaricatori = new ScaricatorePagine[NUMERO_MAX_DOWNLOADER];
		lock = new ReentrantLock();
		condition = lock.newCondition();
	}
	
	public void iniziaScaricamento(Image[][] pagine, String urlVolume, int iCapitolo, int iPagina,int numeroPrimoCapitolo) {
		this.pagine = pagine;
		urlVolumeDaScaricare = urlVolume;
		indiceCapitoloPaginaCentrale = iCapitolo;
		indicePaginaCentrale = iPagina;
		indicePaginaFinale = indicePaginaCentrale+DISTANZA_CENTRALE_FINALE;
		indiceCapitoloPaginaFinale = iCapitolo;
		numeroDownloadAttivi=0;
		numeroDownloadConclusi=0;
		this.numeroPrimoCapitolo=numeroPrimoCapitolo;
		if(indicePaginaFinale >= pagine[indiceCapitoloPaginaCentrale].length){
			indicePaginaFinale = indicePaginaFinale - pagine[indiceCapitoloPaginaCentrale].length;
			indiceCapitoloPaginaFinale++;
		}
		indicePaginaIniziale = indicePaginaCentrale-DISTANZA_INIZIALE_CENTRALE;
		indiceCapitoloPaginaIniziale = iCapitolo;
		if(indicePaginaIniziale < 0){
			if(indiceCapitoloPaginaIniziale -1 >= 0)
				indicePaginaIniziale = pagine[indiceCapitoloPaginaIniziale-1].length + indicePaginaIniziale;
			indiceCapitoloPaginaIniziale--;
		}
		int capitoloIniziale = indiceCapitoloPaginaIniziale,capitoloFinale =indiceCapitoloPaginaFinale,capitoloCentrale=indiceCapitoloPaginaCentrale;
		int paginaIniziale=indicePaginaIniziale,paginaCentrale=indicePaginaCentrale,paginaFinale=indicePaginaFinale;
		
		
		int indiceCapitolo=indiceCapitoloPaginaIniziale,indicePagina=indicePaginaIniziale;
		for(indiceUltimoScaricatore=0;indiceUltimoScaricatore<scaricatori.length-2 && haSuccessivo();indiceUltimoScaricatore++){
			if(indiceCapitolo>=0 && indicePagina>=0){
				
				String url = generaUrlPagina(indiceCapitolo, indicePagina);
				scaricatori[indiceUltimoScaricatore]= new ScaricatorePagine(url, this, indiceCapitolo, indicePagina);
				scaricatori[indiceUltimoScaricatore].start();
				numeroDownloadAttivi++;
			}
			indiceUltimoScaricatore=indiceUltimoScaricatore%NUMERO_MAX_DOWNLOADER;
			incrementaIndicePaginaCentrale();
			indiceCapitolo=indiceCapitoloPaginaIniziale; indicePagina = indicePaginaIniziale;
		}
		indiceCapitoloPaginaIniziale = capitoloIniziale;indiceCapitoloPaginaFinale=capitoloFinale;indiceCapitoloPaginaCentrale=capitoloCentrale;
		indicePaginaCentrale = paginaCentrale; indicePaginaFinale = paginaFinale; indicePaginaIniziale = paginaIniziale;
		await();
	}
	
	public String generaUrlPagina(int capitolo, int pagina){
		System.out.println(capitolo +" "+ pagina);
		String url = urlVolumeDaScaricare+"/"+(numeroPrimoCapitolo+capitolo)+"/"+(pagina+1);
		System.out.println(url);
		return url;
	}

	public void scaricaPagineSuccessive(){
		incrementaIndicePaginaCentrale();
		if(indiceCapitoloPaginaFinale >= pagine.length)return;
		
		if(pagine[indiceCapitoloPaginaFinale][indicePaginaFinale]==null){
			String url = generaUrlPagina(indiceCapitoloPaginaFinale, indicePaginaFinale);
			scaricatori[indiceUltimoScaricatore]=new ScaricatorePagine(url, this, indiceCapitoloPaginaFinale, indicePaginaFinale);
			scaricatori[indiceUltimoScaricatore].start();
			indiceUltimoScaricatore++;
		}
		indiceUltimoScaricatore=indiceUltimoScaricatore%NUMERO_MAX_DOWNLOADER;

	}
	public void scaricaPaginaPrecedenti(){
		decrementaIndicePaginaCentrale();
		if(indiceCapitoloPaginaIniziale < 0 || indicePaginaIniziale <0)return;
		if(pagine[indiceCapitoloPaginaIniziale][indicePaginaIniziale]==null){
			String url = generaUrlPagina(indiceCapitoloPaginaIniziale, indicePaginaIniziale);
			scaricatori[indiceUltimoScaricatore]=new ScaricatorePagine(url, this, indiceCapitoloPaginaFinale, indicePaginaFinale);
			scaricatori[indiceUltimoScaricatore].start();
			indiceUltimoScaricatore++;
		}
		indiceUltimoScaricatore=indiceUltimoScaricatore%NUMERO_MAX_DOWNLOADER;
	}
	public void scaricaPagineCapitoloSuccessivo(){
		incrementaIndiceCapitoloPaginaCentrale();
		System.out.println(indiceCapitoloPaginaCentrale);
		for(int i = indicePaginaCentrale;i<=indicePaginaFinale;i++){
			if(pagine[indiceCapitoloPaginaCentrale][i]==null){
				String url = generaUrlPagina(indiceCapitoloPaginaCentrale, i);
				scaricatori[indiceUltimoScaricatore]=new ScaricatorePagine(url, this, indiceCapitoloPaginaCentrale, i);
				scaricatori[indiceUltimoScaricatore].start();
			}
			indiceUltimoScaricatore= (indiceUltimoScaricatore+1)%NUMERO_MAX_DOWNLOADER;
		}
		for(int i = indicePaginaIniziale;i < pagine[indiceCapitoloPaginaIniziale].length;i++){
			if(pagine[indiceCapitoloPaginaIniziale][i]==null){
				String url = generaUrlPagina(indiceCapitoloPaginaIniziale, i);
				scaricatori[indiceUltimoScaricatore]=new ScaricatorePagine(url, this, indiceCapitoloPaginaIniziale, i);
				scaricatori[indiceUltimoScaricatore].start();
			}
			indiceUltimoScaricatore= (indiceUltimoScaricatore+1)%NUMERO_MAX_DOWNLOADER;
		}
	}
	public void scaricaPagineCapitoloPrecedente(){
		decrementoIndiceCapitoloPaginaCentrale();
		for(int i = indicePaginaCentrale;i<=indicePaginaFinale;i++){
			if(pagine[indiceCapitoloPaginaCentrale][i]==null){
				String url = generaUrlPagina(indiceCapitoloPaginaCentrale, i);
				scaricatori[indiceUltimoScaricatore]=new ScaricatorePagine(url, this, indiceCapitoloPaginaCentrale, i);
				scaricatori[indiceUltimoScaricatore].start();
			}
			indiceUltimoScaricatore= (indiceUltimoScaricatore+1)%NUMERO_MAX_DOWNLOADER;
		}
		if(indiceCapitoloPaginaIniziale>=0){
			for(int i = indicePaginaIniziale;i < pagine[indiceCapitoloPaginaIniziale].length;i++){
				if(pagine[indiceCapitoloPaginaIniziale][i]==null){
					String url = generaUrlPagina(indiceCapitoloPaginaIniziale, i);
					scaricatori[indiceUltimoScaricatore]=new ScaricatorePagine(url, this, indiceCapitoloPaginaIniziale, i);
					scaricatori[indiceUltimoScaricatore].start();
				}
				indiceUltimoScaricatore= (indiceUltimoScaricatore+1)%NUMERO_MAX_DOWNLOADER;
			}
		}
	}
	public boolean haSuccessivo(){
		return !(indiceCapitoloPaginaCentrale  ==pagine.length-1 &&
				indicePaginaCentrale == pagine[indiceCapitoloPaginaCentrale].length-1);
	}
	public boolean haPrecedente(){
		return !(indicePaginaCentrale== 0 && 
				indiceCapitoloPaginaCentrale== 0);
	}
	
	private void incrementaIndicePaginaCentrale(){
		if(indiceCapitoloPaginaCentrale == 0 && indicePaginaIniziale==-1){
			indiceCapitoloPaginaIniziale = 0;
			indicePaginaIniziale++;
			indicePaginaFinale++;
			indicePaginaCentrale++;
		}
		else if(indicePaginaCentrale +1== pagine[indiceCapitoloPaginaCentrale].length ){
			indicePaginaCentrale=0;
			indiceCapitoloPaginaCentrale++;
			indicePaginaIniziale++;
			indicePaginaFinale++;
		}
		else if(indiceCapitoloPaginaFinale < pagine.length && indicePaginaFinale+1==pagine[indiceCapitoloPaginaFinale].length){
			indicePaginaFinale =0;
			indiceCapitoloPaginaFinale++;
			indicePaginaIniziale++;
			indicePaginaCentrale++;
		}
		else if(indiceCapitoloPaginaIniziale>=0 && indicePaginaIniziale+1==pagine[indiceCapitoloPaginaIniziale].length){
			indicePaginaIniziale=0;
			indiceCapitoloPaginaIniziale++;
			indicePaginaCentrale++;
			indicePaginaFinale++;
		}	
		else {
			indicePaginaIniziale++;
			indicePaginaFinale++;
			indicePaginaCentrale++;
		}
//		System.out.println("INDICE INIZIALE CAPITOLO: "+indiceCapitoloPaginaIniziale+" INDICE INIZIALE PAGINA : "+indicePaginaIniziale);
//		System.out.println("INDICE CENTRALE CAPITOLO: "+indiceCapitoloPaginaCentrale+" INDICE CENTRALE PAGINA : "+indicePaginaCentrale);
//		System.out.println("INDICE FINALE CAPITOLO: "+indiceCapitoloPaginaFinale+" INDICE FINALE PAGINA : "+indicePaginaFinale);
	}
	private void decrementaIndicePaginaCentrale(){
		if(indicePaginaIniziale <= 0 && indiceCapitoloPaginaIniziale <= 0){
			indicePaginaIniziale--;
			indiceCapitoloPaginaIniziale = -1;
			indicePaginaCentrale--;
			indicePaginaFinale--;
		}
		else if(indicePaginaIniziale == 0 && indiceCapitoloPaginaIniziale >0){
			indicePaginaIniziale = pagine[indiceCapitoloPaginaIniziale-1].length-1;
			indiceCapitoloPaginaIniziale--;
			indicePaginaFinale--;
			indicePaginaCentrale--;
		}
		else if(indicePaginaCentrale == 0){
			indicePaginaCentrale = pagine[indiceCapitoloPaginaCentrale-1].length-1;
			indiceCapitoloPaginaCentrale--;
			indicePaginaFinale--;
			indicePaginaIniziale--;
		}
		else if(indicePaginaFinale == 0){
			indicePaginaFinale = pagine[indiceCapitoloPaginaFinale-1].length-1;
			indiceCapitoloPaginaFinale--;
			indicePaginaIniziale--;
			indicePaginaCentrale--;
		}
		else{
			indicePaginaCentrale--;
			indicePaginaFinale--;
			indicePaginaIniziale--;
		}
//		System.out.println("INDICE INIZIALE CAPITOLO: "+indiceCapitoloPaginaIniziale+" INDICE INIZIALE PAGINA : "+indicePaginaIniziale);
//		System.out.println("INDICE CENTRALE CAPITOLO: "+indiceCapitoloPaginaCentrale+" INDICE CENTRALE PAGINA : "+indicePaginaCentrale);
//		System.out.println("INDICE FINALE CAPITOLO: "+indiceCapitoloPaginaFinale+" INDICE FINALE PAGINA : "+indicePaginaFinale);
	}
	private void incrementaIndiceCapitoloPaginaCentrale(){
			indicePaginaCentrale=0;
			indicePaginaFinale=indicePaginaCentrale+DISTANZA_CENTRALE_FINALE;
			indicePaginaIniziale = pagine[indiceCapitoloPaginaCentrale].length - DISTANZA_INIZIALE_CENTRALE;
			indiceCapitoloPaginaCentrale++;
			indiceCapitoloPaginaFinale=indiceCapitoloPaginaCentrale;
			indiceCapitoloPaginaIniziale =indiceCapitoloPaginaCentrale-1; 
	}
	private void decrementoIndiceCapitoloPaginaCentrale(){

			indicePaginaCentrale=0;
			indicePaginaFinale=indicePaginaCentrale+DISTANZA_CENTRALE_FINALE;
			indicePaginaIniziale = pagine[indiceCapitoloPaginaCentrale].length - DISTANZA_INIZIALE_CENTRALE;
			indiceCapitoloPaginaCentrale--;
			indiceCapitoloPaginaFinale=indiceCapitoloPaginaCentrale;
			indiceCapitoloPaginaIniziale=indiceCapitoloPaginaCentrale-1;
	}
	void inserisciImmagine(Image pagina,int iCapitolo,int iPagina){
		pagine[iCapitolo][iPagina]=pagina;
	}
	
	public static void main(String[] args) {
//		DownloaderPagine dw = new DownloaderPagine();
//		Image[][] pagine = new Image[5][];
//		pagine[0]= new Image[49];
//		pagine[1]= new Image[29];
//		pagine[2]=new Image[26];
//		pagine[3]= new Image[19];
//		pagine[4]= new Image[17];
//		String urlVolume="http://5.196.65.101/~ComicsReader/Fumetti/Death%20Note/Volume%2001";
//		int indiceCapitolo=0;
//		int indicePagina=0;
//		dw.iniziaScaricamento(pagine, urlVolume, indiceCapitolo, indicePagina);
//		
//		dw.scaricaPagineCapitoloSuccessivo();
//	
	}

	void await(){
		lock.lock();
		try{
			numeroDownloadConclusi++;
			if(numeroDownloadConclusi == numeroDownloadAttivi+1)
				condition.signalAll();
			
			while(numeroDownloadConclusi < numeroDownloadAttivi+1){
				condition.await();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	void fatto(){
		lock.lock();
		try{
			numeroDownloadConclusi++;
			if(numeroDownloadConclusi == numeroDownloadAttivi+1)
				condition.signalAll();
		}finally{
			lock.unlock();
		}
	}
	public int getIndiceCapitoloPaginaCentrale() {
		return indiceCapitoloPaginaCentrale;
	}

	public int getIndicePaginaCentrale() {
		return indicePaginaCentrale;
	}
}
