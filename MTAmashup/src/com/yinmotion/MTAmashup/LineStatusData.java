package com.yinmotion.MTAmashup;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Application;
import android.util.Log;

/*
 * Store status data
 * Extends Application to make it available across Activities 
 */
public class LineStatusData extends Application {
	private static final String TAG = "LineStatusData";
	private Document _doc;
	private ArrayList<Element> aUps;
	private ArrayList<Element> aDowns;
	
	public void setStatusData(Document doc){
		_doc = doc;
		sortByStatus();
		//Log.v("LineStatusData", _doc.toString());
	}
	
	public Document getStatusData(){
		return _doc;
	}
	
	public void sortByStatus(){
		aUps = new ArrayList<Element>();
		aDowns = new ArrayList<Element>();
		
		NodeList subway = _doc.getElementsByTagName("subway");
		Element line = (Element)subway.item(0);
		NodeList nodes = line.getElementsByTagName("line");

		
		for (int i = 0; i < nodes.getLength(); i++) {
			Element e = (Element) nodes.item(i);
			
			Log.v(TAG, "status = "+ (XMLfunctions.getValue(e, "status")));
			if(XMLfunctions.getValue(e, "status").equals("GOOD SERVICE")){
				aUps.add(e);
			}else{
				aDowns.add(e);
			}
		}
	}
	
	public ArrayList<Element> getUps(){
		return aUps;
	}
	
	public ArrayList<Element> getDowns(){
		return aDowns;
	}
	
	public String getTimestamp(){
		
		NodeList eTime = (NodeList)_doc.getElementsByTagName("timestamp");
		Log.v(TAG, "eTime = "+XMLfunctions.getElementValue(eTime.item(0)));
		return XMLfunctions.getElementValue(eTime.item(0));
	}
}
