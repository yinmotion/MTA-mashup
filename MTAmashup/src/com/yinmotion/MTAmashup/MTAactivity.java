/**
 * 
 */
package com.yinmotion.MTAmashup;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Element;

import com.yinmotion.MTAmashup.PlatformActivity.DownLineListAdapter;
import com.yinmotion.MTAmashup.PlatformActivity.UpLineListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * @author yi.liu
 *
 */
public class MTAactivity extends Activity {

	public static final String TAG = "MTAactivity";
	private View wallView;
	private XMLloaderParser xmlTask;
	private Timer loaderTimer;
	private ArrayList<Element> aUps;
	private ArrayList<Element> aDowns;

	/**
	 * 
	 */
	public MTAactivity() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(R.layout.main_activity);
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.app_title);
        
        xmlTask = new XMLloaderParser();
        
        loaderTimer = new Timer();
        loaderTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				loaderTimerMethod();
			}
		}, 2000, 1000);
        
//        loadingFlipper = (ViewFlipper)findViewById(R.id.loading_data);
//        loadingFlipper.startFlipping();
//        
//        loadingFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
//        loadingFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
//        
//        ImageView loader_anim = (ImageView) findViewById(R.id.loader_anim);
//        loaderAnim = (AnimationDrawable) loader_anim.getDrawable();
//        
//        //load XML data ONLY after loader animation is ready 
//        loader_anim.post(new Runnable(){
//        	@Override
//        	public void run(){
//        		if(loaderAnim!=null)
//        		loaderAnim.start();
//        		Log.v(TAG, "loaderAnim");
//        		
//        		xmlTask.execute();
//        		//new LoadXMLTask().execute();
//        	}
//        });
        
        xmlTask.execute();
        //transToWall();
	}
	
	protected void loaderTimerMethod() {
		// TODO Auto-generated method stub
    	this.runOnUiThread(checkDataLoaded);
	}
    
    private Runnable checkDataLoaded = new Runnable() {
		@Override
		public void run() {
			if(xmlTask.getDataLoaded() == true){
				loaderTimer.cancel();
//				ImageView greenlt = (ImageView)findViewById(R.id.splash_greenlight);
//		        greenlt.setVisibility(0);
//		        loaderAnim.stop();
//		        loadingFlipper.stopFlipping();
//		        loadingFlipper.removeAllViews();
		       
		        
		        setLineStatusData();
//		        slideTrainIn();
		        
		        ProgressBar bar = (ProgressBar)findViewById(R.id.mainProgressBar);
		        bar.clearAnimation();
		        
		        transToWall();
		        //startPlatformAct();
			}
		}
	};
	
	private void setLineStatusData() {
		// TODO Auto-generated method stub
		//Log.v(TAG, "setLineStatusData");
		((LineStatusData)getApplication()).setStatusData(xmlTask.getDoc());
	}
	
	@Override
    public void onWindowFocusChanged(boolean hasFocus){
    	super.onWindowFocusChanged(hasFocus);
    	//Log.v(TAG, "hasFocus = "+loaderAnim);
    	
    	if(hasFocus){
//    		loaderAnim.start();
    		
    	}
    }
	

//	private void slideTrainIn() {
//		//Log.v(TAG, "slideTrainIn");
//		ImageView train = (ImageView)findViewById(R.id.splash_train);
//		Animation trainSlide = AnimationUtils.loadAnimation(this, R.anim.splash_train_slide);
//		//trainSlide.setFillAfter(true);
//		trainSlide.setAnimationListener(new AnimationListener() {
//			
//			@Override
//			public void onAnimationStart(Animation animation) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				// TODO Auto-generated method stub
//				startPlatformAct();
//			}
//		});
//		train.startAnimation(trainSlide);
//		//trainSlide.setFillAfter(true);
//	}
    
//    protected void startPlatformAct() {
//		// TODO Auto-generated method stub
//    	Intent intent = new Intent(this, PlatformActivity.class);
//    	startActivity(intent);
//    	
//    	//overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//	}
	
	protected void transToWall(){
		FrameLayout platform = (FrameLayout)findViewById(R.id.main_container);
		Animation platformOut = AnimationUtils.loadAnimation(this, R.anim.platform_out);
		
		platformOut.setFillAfter(true);
		platform.startAnimation(platformOut);
		
		ViewGroup mainView = (ViewGroup) findViewById(R.id.MTA_main); 
        
        wallView = LayoutInflater.from(getBaseContext()).inflate(R.layout.platform_wall, null);
        
        mainView.addView(wallView);        
		
		FrameLayout platformWall = (FrameLayout)findViewById(R.id.platform_wall);
		Animation platformWallIn = AnimationUtils.loadAnimation(this, R.anim.platform_wall_in);
		
		platformWallIn.setFillAfter(true);
		platformWall.startAnimation(platformWallIn);
		
		addStatusBoards();
	}
	
	protected void addStatusBoards(){
		TextView tv = (TextView) findViewById(R.id.app_title_timestamp);
        //Log.v(TAG, "title : "+tv);
        tv.setText("@"+((LineStatusData)getApplication()).getTimestamp());
        
        ViewGroup wall_container = (ViewGroup) findViewById(R.id.platform_container); 
        
        View upView = LayoutInflater.from(getBaseContext()).inflate(R.layout.board_ups, null);
        
        wall_container.addView(upView);        
        
        aUps = ((LineStatusData)getApplication()).getUps();
        
        GridView upsGrid = (GridView) findViewById(R.id.ups_grid); 
        upsGrid.setAdapter(new UpLineListAdapter());
        
        //
        	
        View downView = LayoutInflater.from(getBaseContext()).inflate(R.layout.board_downs, null);
        wall_container.addView(downView);
        aDowns = ((LineStatusData) getApplication()).getDowns();
        
        GridView downsGrid = (GridView) findViewById(R.id.downs_grid);
        downsGrid.setAdapter(new DownLineListAdapter());
        
		Animation boardDrop = AnimationUtils.loadAnimation(this, R.anim.board_drop);
		
		boardDrop.setFillAfter(true);
		wall_container.startAnimation(boardDrop);
		
		boardDrop.setAnimationListener(new AnimationListener() {
			
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
				//slideTrainIn();
			}
		});
	}
	
	protected void slideTrainIn(){
		ImageView train = (ImageView)findViewById(R.id.splash_train);
		Animation trainSlide = AnimationUtils.loadAnimation(this, R.anim.splash_train_slide);
		trainSlide.setFillAfter(true);
		train.startAnimation(trainSlide);
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
