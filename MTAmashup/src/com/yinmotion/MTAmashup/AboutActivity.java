package com.yinmotion.MTAmashup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class AboutActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.about);
        
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.app_title);
        
        Button closeBtn = (Button) findViewById(R.id.button_ok); 
        closeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	public AboutActivity() {
		// TODO Auto-generated constructor stub
	}
}
