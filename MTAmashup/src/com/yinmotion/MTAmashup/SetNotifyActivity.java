package com.yinmotion.MTAmashup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class SetNotifyActivity extends Activity {
	private CheckBox alertCheckbox;
	private CheckBox vibrateCheckbox;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(R.layout.setnotify_activity);
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.app_title);
        
        View turnAlertOn = (View) findViewById(R.id.view_turn_alert_on);
        
        alertCheckbox = (CheckBox) findViewById(R.id.checkbox_alert);
        
        turnAlertOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTurnAlertToggle();
			}
		});
        
        View viewVibrateOn = (View) findViewById(R.id.view_vibrate_on);
        
        vibrateCheckbox = (CheckBox) findViewById(R.id.checkbox_vibrate);
        
        viewVibrateOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onVibrateToggle();
			}
		});
        
//        View addNotify = (View) findViewById(R.id.AddNotification);
//        
//        addNotify.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
        
	}
	
	protected void onVibrateToggle() {
		// TODO Auto-generated method stub
		vibrateCheckbox.toggle();
		
	}

	protected void onTurnAlertToggle() {
		// TODO Auto-generated method stub
		alertCheckbox.toggle();
	}

	public SetNotifyActivity() {
		// TODO Auto-generated constructor stub
	}

}
