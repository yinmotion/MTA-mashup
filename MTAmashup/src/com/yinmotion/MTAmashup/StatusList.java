/**
 * 
 */
package com.yinmotion.MTAmashup;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;

/**
 * @author yi.liu
 *
 */
public class StatusList extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status_list);
		
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		
	}

}
