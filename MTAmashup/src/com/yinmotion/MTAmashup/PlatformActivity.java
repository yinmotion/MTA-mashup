package com.yinmotion.MTAmashup;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlatformActivity extends Activity {
	private static final String TAG = "PlatformActivity";
	private ArrayList<Element> aUps;
	private ArrayList<Element> aDowns;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        
        
        try
    	{
    		// this is the code that I am surrounding in the try/catch block
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.platform);
    		
    	}
    	catch (Exception e)
    	{
    		// this is the line of code that sends a real error message to the log
    		Log.e("ERROR", "ERROR IN CODE: " + e.toString());
     
    		// this is the line that prints out the location in
    		// the code where the error occurred.
    		e.printStackTrace();
    	}
        
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.app_title);
        TextView tv = (TextView) findViewById(R.id.app_title_timestamp);
        //Log.v(TAG, "title : "+tv);
        tv.setText("@"+((LineStatusData)getApplication()).getTimestamp());
        
        ViewGroup platform = (ViewGroup) findViewById(R.id.platform_container); 
        
        View upView = LayoutInflater.from(getBaseContext()).inflate(R.layout.board_ups, null);
        
        platform.addView(upView);        
        
        aUps = ((LineStatusData)getApplication()).getUps();
        
        GridView upsGrid = (GridView) findViewById(R.id.ups_grid); 
        upsGrid.setAdapter(new UpLineListAdapter());
        
        //
        	
        View downView = LayoutInflater.from(getBaseContext()).inflate(R.layout.board_downs, null);
        platform.addView(downView);
        aDowns = ((LineStatusData) getApplication()).getDowns();
        
        GridView downsGrid = (GridView) findViewById(R.id.downs_grid);
        downsGrid.setAdapter(new DownLineListAdapter());
        /*
        //Set listview background 
        final ListView lv = getListView();
        lv.setCacheColorHint(0);
        lv.setBackgroundResource(R.drawable.platform);
        */
//        lv.setTextFilterEnabled(true);	
//        lv.setOnItemClickListener(new OnItemClickListener() {
//        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        		
//        		@SuppressWarnings("unchecked")
//				HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(position);	        		
//        		Toast.makeText(Main.this, "ID '" + o.get("id") + "' was clicked.", Toast.LENGTH_LONG).show(); 
//
//			}
//		});
    }
	
	public class UpLineListAdapter extends BaseAdapter{
		public UpLineListAdapter(){
			
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return aUps.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return aUps.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v;
	        if(convertView==null){
	            LayoutInflater li = getLayoutInflater();
	            v = li.inflate(R.layout.line_sign, null);
	            ImageView iv = (ImageView)v.findViewById(R.id.lineSign);
	            Element line = aUps.get(position);
	            //tv.setText(XMLfunctions.getValue(line, "name"));
	            int imgId = getResources().getIdentifier("line_"+XMLfunctions.getValue(line, "name").toLowerCase(), "drawable", "com.yinmotion.MTAmashup");
	            Log.v(TAG, "id : "+"line_"+XMLfunctions.getValue(line, "name"));
	            iv.setImageResource(imgId);

	        }
	        else
	        {
	            v = convertView;
	        }
	        return v;

		}
		
	}
	
	public class DownLineListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return aDowns.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return aDowns.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v;
	        if(convertView==null){
	            LayoutInflater li = getLayoutInflater();
	            v = li.inflate(R.layout.line_sign, null);
	            ImageView iv = (ImageView)v.findViewById(R.id.lineSign);
	            Element line = aDowns.get(position);
	            //tv.setText(XMLfunctions.getValue(line, "name"));
	            int imgId = getResources().getIdentifier("line_"+XMLfunctions.getValue(line, "name").toLowerCase(), "drawable", "com.yinmotion.MTAmashup");
	            Log.v(TAG, "id : "+"line_"+XMLfunctions.getValue(line, "name"));
	            iv.setImageResource(imgId);

	        }
	        else
	        {
	            v = convertView;
	        }
	        return v;
		}
		
	}

}
