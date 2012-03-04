package com.yinmotion.MTAmashup;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.R.integer;
import android.app.Application;
import android.graphics.Bitmap;
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
	private ArrayList<Integer> aMainMenu;
	private String status = "";
	
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
	
	public ArrayList<Integer> getMainMenu(){
		if(aMainMenu==null){
			aMainMenu = new ArrayList<Integer>();
			
			aMainMenu.add(0, R.drawable.menu_icons_refresh);
			aMainMenu.add(1, R.drawable.menu_icons_setting);
			aMainMenu.add(2, R.drawable.menu_icons_share);
		}
		
		return aMainMenu;
	}
	
	public String getAllStatus(){
		
		if(status == ""){
			status += "Ups:\n";	
			for(int i=0; i<aUps.size(); i++){
				Element line = aUps.get(i);
				String time = XMLfunctions.getValue(line, "Time");
				if(time!=""){
					time = " updated @"+ time + " " +XMLfunctions.getValue(line, "Date");
				}
				status += XMLfunctions.getValue(line, "name") + " Line : "+ XMLfunctions.getValue(line, "status") + time + ".\n";
			}
			status += "\nDowns:\n";
			for(int i=0; i<aDowns.size(); i++){
				Element line = aDowns.get(i);
				status += XMLfunctions.getValue(line, "name") + " Line : "+ XMLfunctions.getValue(line, "status") + " updated @"+XMLfunctions.getValue(line, "Time")+" "+XMLfunctions.getValue(line, "Date") + ".\n";
			}
		}
		return status;
	}
}
