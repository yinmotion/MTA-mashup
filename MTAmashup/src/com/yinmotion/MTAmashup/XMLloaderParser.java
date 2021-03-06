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

import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author yi
 *
 */
public class XMLloaderParser extends AsyncTask<URL, Integer, String> {

	private static final String TAG = "XMLloaderParser";
	private static final String PATH_MTA_STATUS = "http://www.mta.info/status/serviceStatus.txt";
	//private static final String PATH_MTA_STATUS = "http://yinmotion.com/mta_status/status.xml";
	private boolean _dataLoaded = false;
	private String _line;
	private Document _doc;
	
	@Override
	protected String doInBackground(URL... urls) {
		// TODO Auto-generated method stub
		_line = null;
		
		try {
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(PATH_MTA_STATUS);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			_line = EntityUtils.toString(httpEntity);
			
			XMLfromString();
			
		} catch (UnsupportedEncodingException e) {
			_line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (MalformedURLException e) {
			_line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (IOException e) {
			_line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		}
		
		
		return _line;
	}
	
	 protected void onProgressUpdate(Integer... progress) {
         //setProgressPercent(progress[0]);
     }

     protected void onPostExecute(String line) {
	     //Log.v(TAG, "xml line = "+line);
         //showDialog("Downloaded " + result + " bytes");
	     
    	 //XMLfromString();
	      _dataLoaded = true;
     }
     
     public boolean getDataLoaded(){
    	 return _dataLoaded;
     }
     
     public Document getDoc(){
    	 return _doc;
     }
     
     public void XMLfromString(){
 		
 		_doc = null;
 		
 		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         try {
         	
 			DocumentBuilder db = dbf.newDocumentBuilder();
 			
 			InputSource is = new InputSource();
 	        is.setCharacterStream(new StringReader(_line));
 	       _doc = db.parse(is); 
 	        
 		} catch (ParserConfigurationException e) {
 			System.out.println("XML parse error: " + e.getMessage());
 			
 		} catch (SAXException e) {
 			System.out.println("Wrong XML file structure: " + e.getMessage());
             
 		} catch (IOException e) {
 			System.out.println("I/O exeption: " + e.getMessage());
 			
 		} 
         
 	}

}
