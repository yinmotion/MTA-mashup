package com.yinmotion.MTAmashup;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class AddNotifyActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Log.v("AddNotifyActivity", "onCreate");
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        
        setContentView(R.layout.addnotify_activity);
//        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.app_title);
        
        
        View addNotify = (View) findViewById(R.id.AddNotification);
        
        addNotify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startSetNotify();
				
			}
		});
        
	}
	
	protected void startSetNotify() {
		// TODO Auto-generated method stub
		Intent setAlertIntent = new Intent(this, SetNotifyActivity.class);
		startActivity(setAlertIntent);
	}

	public AddNotifyActivity() {
		// TODO Auto-generated constructor stub
	}

}
