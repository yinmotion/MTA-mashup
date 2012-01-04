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
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PlatformActivity extends ListActivity {
	private static final String TAG = "PlatformActivity";
	private ImageView platform;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.platform);
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.app_title);
        
        //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        
//        platform = (ImageView)findViewById(R.id.platform_bg);
//        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.platform_slide);
//        slideIn.setFillAfter(true);
//        platform.startAnimation(slideIn);
        
        Document _doc = ((LineStatusData)getApplication()).getStatusData();

      //Needed for the listItems
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

		NodeList subway = _doc.getElementsByTagName("subway");
		Element line = (Element)subway.item(0);
		NodeList nodes = line.getElementsByTagName("line");
		
		
		for (int i = 0; i < nodes.getLength(); i++) {							
			HashMap<String, String> map = new HashMap<String, String>();	
			
			Element e = (Element)nodes.item(i);
			map.put("name", XMLfunctions.getValue(e, "name"));
        	map.put("status", "Status: " + XMLfunctions.getValue(e, "status"));
        	mylist.add(map);			
		}		
       
        ListAdapter adapter = new SimpleAdapter(this, mylist , R.layout.platform, 
                        new String[] { "name", "status" }, 
                        new int[] { R.id.item_title, R.id.item_subtitle });
        
        setListAdapter(adapter);
        
        //getListView().setCacheColorHint(0);
        
        final ListView lv = getListView();
        lv.setCacheColorHint(0);
        lv.setBackgroundResource(R.drawable.platform);
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

}
