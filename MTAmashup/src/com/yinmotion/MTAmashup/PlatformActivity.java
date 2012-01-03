package com.yinmotion.MTAmashup;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class PlatformActivity extends ListActivity {
	private ImageView platform;

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.platform);
        
        //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        
        platform = (ImageView)findViewById(R.id.platform_bg);
        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.platform_slide);
        slideIn.setFillAfter(true);
        platform.startAnimation(slideIn);
	}
}
