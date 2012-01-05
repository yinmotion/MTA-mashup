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
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlatformActivity extends Activity {
	private static final String TAG = "PlatformActivity";
	private ArrayList<Element> aUps;
	
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
        
        ViewGroup platform = (ViewGroup) findViewById(R.id.platform_container); 
        
        View upView = LayoutInflater.from(getBaseContext()).inflate(R.layout.board_ups, null);
        
        platform.addView(upView);        
        
        aUps = ((LineStatusData)getApplication()).getUps();
        
        Log.v(TAG, "aUps = "+aUps.size());
        
        GridView upsGrid = (GridView) findViewById(R.id.ups_grid); 
        upsGrid.setAdapter(new LineListAdapter());
        
        
//        ViewGroup upList = (ViewGroup) findViewById(R.id.board_up_list);
//        for(int i = 0; i<aUps.size(); i++){
//        	Element line = aUps.get(i);
//        	
//        	//Element line = (Element) aUps[i];
//        	View lineSign = LayoutInflater.from(getBaseContext()).inflate(R.layout.line_sign, null);
//        	TextView signTxt = (TextView) lineSign.findViewById(R.id.lineName);
//        	signTxt.setText(XMLfunctions.getValue(line, "name"));
//        	
//        	upList.addView(lineSign);
//        }
        
        
//        platform = (ImageView)findViewById(R.id.platform_bg);
//        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.platform_slide);
//        slideIn.setFillAfter(true);
//        platform.startAnimation(slideIn);
        
//        Document _doc = ((LineStatusData)getApplication()).getStatusData();
//
//      //Needed for the listItems
//        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
//
//		NodeList subway = _doc.getElementsByTagName("subway");
//		Element line = (Element)subway.item(0);
//		NodeList nodes = line.getElementsByTagName("line");
//		
//		
//		for (int i = 0; i < nodes.getLength(); i++) {							
//			HashMap<String, String> map = new HashMap<String, String>();	
//			
//			Element e = (Element)nodes.item(i);
//			map.put("name", XMLfunctions.getValue(e, "name"));
//        	map.put("status", "Status: " + XMLfunctions.getValue(e, "status"));
//        	mylist.add(map);			
//		}		
//       
//        ListAdapter adapter = new SimpleAdapter(this, mylist , R.layout.platform, 
//                        new String[] { "name", "status" }, 
//                        new int[] { R.id.item_title, R.id.item_subtitle });
//        
//        setListAdapter(adapter);
//        
        //getListView().setCacheColorHint(0);
        
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
	
	public class LineListAdapter extends BaseAdapter{
		public LineListAdapter(){
			
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
	            TextView tv = (TextView)v.findViewById(R.id.lineName);
	            Element line = aUps.get(position);
	            tv.setText(XMLfunctions.getValue(line, "name"));

	        }
	        else
	        {
	            v = convertView;
	        }
	        return v;

		}
		
	}

}
