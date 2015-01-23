//package domain;
//
//import java.awt.Image;
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.sql.SQLException;
//
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//
//import technicalService.GestoreDataBase;
//import technicalService.TuplaFumetto;
//import downloader.ImmagineNonPresenteException;
//import downloader.PagineDownloader;
//import downloader.ScaricamentoImmagineIncompletoException;
//
//public class VisualizzatoreCapitoloOffline
//{
//	private static VisualizzatoreCapitoloOffline istanza;
//	private Image[] pagineCapitoloCorrente;
//	private int numeroPaginaCorrente;
//	private int indiceCapitoloCorrente;
//	
//	private VisualizzatoreCapitoloOffline()
//	{
//		istanza = null;
//		pagineCapitoloCorrente = null;
//		numeroPaginaCorrente = 0; 
//		indiceCapitoloCorrente = 0;
//	}
//	
//	public static VisualizzatoreCapitoloOffline getVisualizzatoreCapitoli(){
//		if(istanza == null){
//			istanza = new VisualizzatoreCapitoloOffline();
//		}
//		return istanza;
//	}
//	
//	public void visualizzaCapitoli(File[] capitoli,int numeroCapitoloDaLeggere, int primaPaginaDaVisualizzare){
//			
//		indiceCapitoloCorrente = numeroCapitoloDaLeggere - capitoliDaLeggere[0].getNumero();
//		capitoloCorrente = capitoliDaLeggere[indiceCapitoloCorrente];
//		capitoloCorrente.setPagine();
//		pagineCapitoloCorrente = capitoloCorrente.getPagine();
//		
//		numeroPaginaCorrente = primaPaginaDaVisualizzare-1;
//		try {
//			paginaDownloaders[indiceCapitoloCorrente] = new PagineDownloader(capitoloCorrente.getUrlCapitolo(),
//					pagineCapitoloCorrente, capitoloCorrente.getNumeroPagine(), numeroPaginaCorrente);
//			paginaDownloaders[indiceCapitoloCorrente].iniziaDownload();
//		} catch (ImmagineNonPresenteException e) {
//			
//			e.printStackTrace();
//		}
//	}
//	
//	public Image visualizzaPaginaCorrente(String filename){
//			System.out.println(numeroPaginaCorrente);
//
//			Image pagina = new ImageIcon(filename)
//					return pagina;
//	}
//	
//	public boolean haPaginaSuccessiva(){
//		if(numeroPaginaCorrente+1  == capitoloCorrente.getNumeroPagine()) return false;
//		return true;
//	}
//	
//	public boolean haPaginaPrecedente(){
//		if(numeroPaginaCorrente == 0) return false;
//		return true;
//	}
//	
//	public boolean paginaSuccesiva(){
//		boolean boolRitorno;
//		if(boolRitorno = haPaginaSuccessiva()){
//			numeroPaginaCorrente++;
//		}
//		else if(haCapitoloSuccessivo())
//			capitoloSuccessivo();
//		return boolRitorno;
//	}
//	
//	public boolean paginaPrecedente(){
//		boolean boolRitorno;
//		if(boolRitorno = haPaginaPrecedente()){
//			numeroPaginaCorrente--;
//		}
//		else if(haCapitoloPrecedente())
//			capitoloPrecedente();
//		return boolRitorno;
//	}
//	
//	public boolean haCapitoloSuccessivo(){
//		if(indiceCapitoloCorrente + 1 == capitoliDaLeggere.length) return false;
//		return true;
//	}
//	
//	public boolean haCapitoloPrecedente(){
//		if(indiceCapitoloCorrente == 0)return false;
//		return true;
//	}
//	
//	public boolean capitoloSuccessivo(){
//		boolean boolRitorno;
//		if(boolRitorno=haCapitoloSuccessivo()){
//			indiceCapitoloCorrente++;
//			capitoloCorrente = capitoliDaLeggere[indiceCapitoloCorrente];
//			pagineCapitoloCorrente = capitoloCorrente.getPagine();
//			numeroPaginaCorrente = 0;
//			cambiaPaginaDownloader(indiceCapitoloCorrente - 1);
//		}
//		return boolRitorno;
//	}
//
//	public boolean capitoloPrecedente(){
//		boolean boolRitorno;
//		if(boolRitorno=haCapitoloPrecedente()){
//			indiceCapitoloCorrente--;
//			capitoloCorrente = capitoliDaLeggere[indiceCapitoloCorrente];
//			pagineCapitoloCorrente = capitoloCorrente.getPagine();
//			numeroPaginaCorrente = 0;
//			cambiaPaginaDownloader(indiceCapitoloCorrente + 1);
//		}
//		return boolRitorno;		
//	}
//	
//	private void cambiaPaginaDownloader(int indicePrecedente){
//		System.out.println("DENTRO CAMBIA PAG :"+indicePrecedente);
//		if(!paginaDownloaders[indicePrecedente].dowloadECompleto())
//			paginaDownloaders[indicePrecedente].stopDownload();
//		if(paginaDownloaders[indiceCapitoloCorrente]== null)
//			try {
//				System.out.println("DENTRO CAMBIA PAG :"+numeroPaginaCorrente);
//				double start = System.currentTimeMillis();
//				paginaDownloaders[indiceCapitoloCorrente]= new PagineDownloader(capitoloCorrente.getUrlCapitolo(),
//						pagineCapitoloCorrente, capitoloCorrente.getNumeroPagine(), numeroPaginaCorrente);
//				paginaDownloaders[indiceCapitoloCorrente].iniziaDownload();
//				double end = System.currentTimeMillis();
//				System.out.println("DENTRO CAMBIA PAG :"+(end -start)/1000 +" sec");
//			} catch (ImmagineNonPresenteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		else if(!paginaDownloaders[indiceCapitoloCorrente].dowloadECompleto())
//			paginaDownloaders[indiceCapitoloCorrente].restartDownload();
//	}
//	public static void main(String[] args) throws SQLException, MalformedURLException, ClassNotFoundException {
//		
//		GestoreDataBase genereBase = GestoreDataBase.getIstanza();
//		TuplaFumetto tuplaF = genereBase.creaTuplaFumetto("666 Satan");
//		tuplaF.prossima();
//		Fumetto fumetto = new Fumetto(tuplaF);
//		fumetto.apriFumetto();
//		Volume[] volumi = fumetto.getVolumi();
//		volumi[0].caricaCapitoli();
//		Capitolo[] capitoli = volumi[0].getCapitoli();
//		System.out.println(capitoli.length);
//		double startTime = System.currentTimeMillis();		
//
//		VisualizzatoreCapitoli visualizzatoreCapitoli = VisualizzatoreCapitoli.getVisualizzatoreCapitoli();
//		visualizzatoreCapitoli.visualizzaCapitoli(capitoli, 1, 1);
//		double endTime = System.currentTimeMillis();		
//
//		System.out.println(visualizzatoreCapitoli.visualizzaPaginaCorrente());
//		System.out.println((endTime - startTime)/1000);
//		visualizzatoreCapitoli.capitoloSuccessivo();
//		
//		GestoreDataBase.disconnetti();
//
//		
//	}	
//}