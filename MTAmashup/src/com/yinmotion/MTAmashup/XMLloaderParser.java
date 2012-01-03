/**
 * 
 */
package com.yinmotion.MTAmashup;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author yi
 *
 */
public class XMLloaderParser extends AsyncTask<URL, Integer, String> {

	private static final String TAG = "XMLloaderParser";
	private static final String PATH_MTA_STATUS = "http://www.mta.info/status/serviceStatus.txt";
	private boolean _dataLoaded = false;
	
	@Override
	protected String doInBackground(URL... urls) {
		// TODO Auto-generated method stub
		String line = null;
		
		try {
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(PATH_MTA_STATUS);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			line = EntityUtils.toString(httpEntity);
			
		} catch (UnsupportedEncodingException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (MalformedURLException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (IOException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		}
		
		
		return line;
	}
	
	 protected void onProgressUpdate(Integer... progress) {
         //setProgressPercent(progress[0]);
     }

     protected void onPostExecute(String line) {
	     Log.v(TAG, "xml line = "+line);
         //showDialog("Downloaded " + result + " bytes");
	     
	      _dataLoaded = true;
     }
     
     public boolean getDataLoaded(){
    	 return _dataLoaded;
     }
     
     public Document XMLfromString(String xml){
 		
 		Document doc = null;
 		
 		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         try {
         	
 			DocumentBuilder db = dbf.newDocumentBuilder();
 			
 			InputSource is = new InputSource();
 	        is.setCharacterStream(new StringReader(xml));
 	        doc = db.parse(is); 
 	        
 		} catch (ParserConfigurationException e) {
 			System.out.println("XML parse error: " + e.getMessage());
 			return null;
 		} catch (SAXException e) {
 			System.out.println("Wrong XML file structure: " + e.getMessage());
             return null;
 		} catch (IOException e) {
 			System.out.println("I/O exeption: " + e.getMessage());
 			return null;
 		}
 		       
         return doc;
         
 	}

}
