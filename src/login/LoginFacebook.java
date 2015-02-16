package login;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jws.WebParam;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class LoginFacebook
{
	private final static String URL_LOGIN = "https://www.facebook.com/dialog/oauth?client_id=1526250877630357&redirect_uri=http://5.196.65.101/~ComicsReader/facebook/comicsLogin.php&scope=user_friends,user_photos";
	private static String URL_FRIENDS ="https://graph.facebook.com/me/friends?";
	private static String URL_USER ="https://graph.facebook.com/me?";
	
	private WebClient webClient;
    private HtmlPage page1;
    private HtmlPage page2;
    private HtmlForm form;
    private HtmlTextInput email;
    private HtmlPasswordInput pass;
    private HtmlSubmitInput loginBotton;
    
    private static LoginFacebook istanza;
    
    public static LoginFacebook getIstanza(){
    	if(istanza==null)
    		istanza = new LoginFacebook();
    	return istanza;
    }
    public LoginFacebook()
    {
    	try{
    	
    		webClient = new WebClient(BrowserVersion.FIREFOX_24);
	    	webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	    	webClient.getOptions().setThrowExceptionOnScriptError(false);
	    	webClient.getOptions().setJavaScriptEnabled(false);
		    Logger logger = Logger.getLogger ("");
		    logger.setLevel (Level.OFF);
			page1 = webClient.getPage(URL_LOGIN);
			form = page1.getForms().get(0);
			loginBotton = form.getInputByName("login");
			email = form.getInputByName("email");
			pass = form.getInputByName("pass");
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public String  getURLAmiciJSON()
	{
		return URL_FRIENDS;
	}
    
    public String  getURLUtenteJSON()
	{
		return URL_USER;
	}
    public String getURLFoto(String id)
	{
		try{	
			URL url = new URL("http://graph.facebook.com/"+id+"/picture?type=large");
			Page page1 = webClient.getPage(url);
			String urlPhoto = page1.getUrl().toString();
			return urlPhoto;
		} catch (FailingHttpStatusCodeException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
    
    public void setPass(String pass){
    	this.pass.setValueAttribute(pass);
    }
    
    public void setEmail(String email){
    	this.email.setValueAttribute(email);
    }
   
    public void clickLoginBotton() throws ErroreAutenticazioneException{
    	try
		{
			page2=loginBotton.click();
			String accessToken =page2.getBody().getTextContent().replaceFirst("\n", ""); 
			if(!accessToken.contains("access_token")){
				throw new ErroreAutenticazioneException();
			}
			URL_FRIENDS+=accessToken;
			URL_USER+=accessToken;
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
    }

}
