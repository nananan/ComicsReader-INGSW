package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class GestoreJSON
{
	private JSONArray amiciJSON;
	private JSONObject utenteJSON;
	private LoginFacebook login = LoginFacebook.getIstanza();
	
	public GestoreJSON(String url_friends, String url_user)
	{
		inizializzaFriendsJSON(url_friends);
		inizializzaUserJSON(url_user);
	}
	public String[] getIdAmici(){
		String[] amici = new String[amiciJSON.length()];
		for (int i = 0; i < amiciJSON.length(); i++)
		{
			try{
				amici[i]=amiciJSON.getJSONObject(i).getString("id");
			} catch (JSONException e){
				e.printStackTrace();
			}
		}
		return amici;
	}
	public String getNomeUtente(){
		try{
			return utenteJSON.getString("name");
		} catch (JSONException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String getIdUtente(){
		try{
			return utenteJSON.getString("id");
		} catch (JSONException e){
			e.printStackTrace();
		}
		return null;
	}

	private void inizializzaFriendsJSON(String url_friends)
	{
	    	try{
		    	InputStream is = new URL(url_friends).openStream();
		    	BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			    String jsonText = readAll(rd);
			    JSONObject json = new JSONObject(jsonText);
			    is.close();
			    amiciJSON= json.getJSONArray("data");
		    }catch (JSONException e){
				e.printStackTrace();
		    } catch (MalformedURLException e){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
	    }
	  
	private void inizializzaUserJSON(String url_user)
	 {
	    	try{
		    	InputStream is = new URL(url_user).openStream();
		    	BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			    String jsonText = readAll(rd);
			    JSONObject json = new JSONObject(jsonText);
			    is.close();
			    utenteJSON= json;
		    }catch (JSONException e){
				e.printStackTrace();
		    } catch (MalformedURLException e){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
	    }
	   
	private String readAll(Reader rd) throws IOException {
			
		    StringBuilder sb = new StringBuilder();
		    
		    int cp;
		    
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		    
		}
  
}
