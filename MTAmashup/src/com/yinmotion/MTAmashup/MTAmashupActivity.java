package com.yinmotion.MTAmashup;

import org.w3c.dom.Document;

import com.yinmotion.MTAmashup.R.string;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.Window;

public class MTAmashupActivity extends Activity {
    /** Called when the activity is first created. */
	public static final String PATH_MTA_STATUS = "http://www.mta.info/status/serviceStatus.txt";
	public static final String TAG = "MTAmashupActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String xml = XMLfunctions.getXML();
        Document doc = XMLfunctions.XMLfromString(xml);
          
        Log.v(TAG, "xml = "+xml);
        //int numResults = XMLfunctions.numResults(doc);
    }
    
    
    
}