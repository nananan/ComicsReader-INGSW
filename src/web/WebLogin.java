package web;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.beans.binding.StringExpression;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlBody;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.sun.javafx.binding.StringFormatter;

public class WebLogin
{
	private final static String URL_LOGIN = "https://www.facebook.com/dialog/oauth?client_id=1526250877630357&redirect_uri=http://5.196.65.101/~ComicsReader/facebook/comicsLogin.php&scope=user_friends,user_photos";
	private static String URL_FRIENDS ="https://graph.facebook.com/me/friends?";
	private static String URL_USER ="https://graph.facebook.com/me?";
	private static String URL_USER_PHOTO ="https://graph.facebook.com/me/picture?";
	
	private WebClient webClient;
    private HtmlPage page1;
    private HtmlPage page2;
    private HtmlForm form;
    private HtmlTextInput email;
    private HtmlPasswordInput pass;
    private HtmlSubmitInput loginBotton;
    
    private static WebLogin istanza;
    
    public static WebLogin getIstanza(){
    	if(istanza==null)
    		istanza = new WebLogin();
    	return istanza;
    }
    public WebLogin()
    {
    	try{
    	
    		webClient = new WebClient(BrowserVersion.FIREFOX_24);
	    	webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	    	webClient.getOptions().setThrowExceptionOnScriptError(false);
	    	webClient.getOptions().setJavaScriptEnabled(false);
		    Logger logger = Logger.getLogger ("");
		    logger.setLevel (Level.OFF);
			page1 = webClient.getPage(URL_LOGIN);
			System.out.println(page1);
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
   
    public void clickLoginBotton(){
    	try
		{
			page2=loginBotton.click();
//			page2=webClient.getPage(page1.getUrl());
//			HtmlBody body = (HtmlBody) page2.getBody();
//			System.out.println(body.asXml());
			String accessToken =page2.getBody().getTextContent().replaceFirst("\n", ""); 
			URL_FRIENDS+=accessToken;
			URL_USER+=accessToken;
			URL_USER_PHOTO+=accessToken;
			System.out.println(URL_USER_PHOTO);
//			String url = page2.getUrl().toString();
//			System.out.println(url);
//			HtmlPage pagFac = webClient.getPage(url);
//			System.out.println(form);
//			System.out.println(URL_FRIENDS);
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
    }

}
