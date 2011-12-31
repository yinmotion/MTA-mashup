package com.yinmotion.MTAmashup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.yinmotion.MTAmashup.R.string;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MTAmashupActivity extends Activity {
    /** Called when the activity is first created. */
	public static final String PATH_MTA_STATUS = "http://www.mta.info/status/serviceStatus.txt";
	public static final String TAG = "MTAmashupActivity";
	private AnimationDrawable loaderAnim;
	private ViewFlipper loadingFlipper;
	private Timer loaderTimer;
	public boolean dataLoaded;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        loaderTimer = new Timer();
        loaderTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				loaderTimerMethod();
			}
		}, 5000, 1000);
        
        loadingFlipper = (ViewFlipper)findViewById(R.id.loading_data);
        loadingFlipper.startFlipping();
        
        loadingFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        loadingFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        
        ImageView loader_anim = (ImageView) findViewById(R.id.loader_anim);
        loaderAnim = (AnimationDrawable) loader_anim.getDrawable();
        
        //load XML data ONLY after loader animation is ready 
        loader_anim.post(new Runnable(){
        	@Override
        	public void run(){
        		if(loaderAnim!=null)
        		loaderAnim.start();
        		Log.v(TAG, "loaderAnim");
        		
        		new LoadXMLTask().execute();
        	}
        });
    }
    
    protected void loaderTimerMethod() {
		// TODO Auto-generated method stub
    	this.runOnUiThread(checkDataLoaded);
	}
    
    private Runnable checkDataLoaded = new Runnable() {
		@Override
		public void run() {
			if(dataLoaded == true){
				loaderTimer.cancel();
				ImageView greenlt = (ImageView)findViewById(R.id.splash_greenlight);
		        greenlt.setVisibility(0);
		        loaderAnim.stop();
		        loadingFlipper.stopFlipping();
		        loadingFlipper.removeAllViews();
		        
		        slideTrainIn();
		        //startPlatformAct();
			}
		}
	};

	@Override
    public void onWindowFocusChanged(boolean hasFocus){
    	super.onWindowFocusChanged(hasFocus);
    	Log.v(TAG, "hasFocus = "+loaderAnim);
    	
    	if(hasFocus){
//    		loaderAnim.start();
    		
    	}
    }
	
	private void slideTrainIn() {
		ImageView train = (ImageView)findViewById(R.id.splash_train);
		Animation trainSlide = AnimationUtils.loadAnimation(this, R.anim.splash_train_slide);
		//trainSlide.setFillAfter(true);
		trainSlide.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				startPlatformAct();
			}
		});
		train.startAnimation(trainSlide);
	}
    
    protected void startPlatformAct() {
		// TODO Auto-generated method stub
    	Intent intent = new Intent(this, PlatformActivity.class);
    	startActivity(intent);
    	
    	//overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	public class LoadXMLTask extends AsyncTask<URL, Integer, String>{
    	protected String doInBackground(URL... urls) {
    		String line = null;

			try {
				
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(PATH_MTA_STATUS);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				line = EntityUtils.toString(httpEntity);
				
			} catch (UnsupportedEncodingException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			} catch (MalformedURLException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			} catch (IOException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			}
			
			return line;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String line) {
	        Log.v(TAG, "xml line = "+line);
            //showDialog("Downloaded " + result + " bytes");
	        dataLoaded = true;
        }
    }
    
}