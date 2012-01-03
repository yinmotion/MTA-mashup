package com.yinmotion.MTAmashup;

import org.w3c.dom.Document;

import android.app.Application;

/*
 * Store status data
 * Extends Application to make it available across Activities 
 */
public class LineStatusData extends Application {
	private Document _doc;
	
	public void setStatusData(Document doc){
		_doc = doc;
	}
	
	public Document getStatusData(){
		return _doc;
	}
	
}
