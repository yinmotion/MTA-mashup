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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
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
        
        splashIn();

        
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

	}
	
	private void loadXMLData() {
		if(!isNetworkAvailable()){
        	noConnectionWarning();
        	return;
        }
		
		xmlTask = new XMLloaderParser();
        
        loaderTimer = new Timer();
        loaderTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				loaderTimerMethod();
			}
		}, 2000, 1000);
        
        xmlTask.execute();
	}
	
	private void splashIn() {
		FrameLayout platform = (FrameLayout)findViewById(R.id.main_container);
		Animation splashIn = AnimationUtils.loadAnimation(this, R.anim.splash_in);
		
		splashIn.setFillAfter(true);
		splashIn.setAnimationListener(new AnimationListener() {
			
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
				loadXMLData();
			}
		});
		platform.startAnimation(splashIn);
	}
	
	private void noConnectionWarning() {
		Log.v(TAG, "noConnectionWarning");
		// TODO Auto-generated method stub
		AlertDialog alert = new AlertDialog.Builder(this).
								setTitle(R.string.no_internet_connection).
								setMessage(R.string.connect_to_internet).
								setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
							           public void onClick(DialogInterface dialog, int id) {
							                dialog.cancel();
							           }
							       }).create();
		
		alert.show();
	}
	
	protected boolean isNetworkAvailable() {
		ConnectivityManager connectivityMng = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityMng.getActiveNetworkInfo();

		return activeNetworkInfo!=null;
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
				slideTrainIn();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v;
	        if(convertView==null){
	            LayoutInflater li = getLayoutInflater();
	            v = li.inflate(R.layout.line_sign, null);
	            ImageView iv = (ImageView)v.findViewById(R.id.lineSign);
	            final Element line = aUps.get(position);
	            //tv.setText(XMLfunctions.getValue(line, "name"));
	            final int imgId = getResources().getIdentifier("line_"+XMLfunctions.getValue(line, "name").toLowerCase(), "drawable", "com.yinmotion.MTAmashup");
	            //Log.v(TAG, "id : "+"line_"+XMLfunctions.getValue(line, "name"));
	            iv.setImageResource(imgId);
	            
	            iv.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showUpStatusDetail(imgId, line);
						
					}
				});
	        }
	        else
	        {
	            v = convertView;
	        }
	        return v;

		}
		
	}
	
	private void showUpStatusDetail(int iconId, Element line){
		AlertDialog detail = new AlertDialog.Builder(this).
				setTitle(XMLfunctions.getValue(line, "status")).
				//setMessage(R.string.connect_to_internet).
				setIcon(iconId).
				setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       }).create();
		detail.show();
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
	            final Element line = aDowns.get(position);
	            //tv.setText(XMLfunctions.getValue(line, "name"));
	            final int imgId = getResources().getIdentifier("line_"+XMLfunctions.getValue(line, "name").toLowerCase(), "drawable", "com.yinmotion.MTAmashup");
	            Log.v(TAG, "id : "+"line_"+XMLfunctions.getValue(line, "name"));
	            iv.setImageResource(imgId);
	            
	            iv.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showDownStatusDetail(imgId, line);
						
					}
				});
	        }
	        else
	        {
	            v = convertView;
	        }
	        return v;
		}

		
	}
	
	protected void showDownStatusDetail(int iconId, final Element line) {
		//
		AlertDialog downdetail = new AlertDialog.Builder(this).
				setTitle(XMLfunctions.getValue(line, "status")).
				setMessage(Html.fromHtml(XMLfunctions.getValue(line, "plannedworkheadline"))).
				setIcon(iconId).
				setPositiveButton("RANT!#?@", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						rantIt(line);
					}
				}).
				setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).create();
		downdetail.show();
	}

	protected void rantIt(Element line) {
		// 
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		String shareBody = "WTF?#! @"+ XMLfunctions.getValue(line, "Time")+" "+XMLfunctions.getValue(line, "Date")+", " +XMLfunctions.getValue(line, "name")+" train has "+ XMLfunctions.getValue(line, "status").toLowerCase();
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, XMLfunctions.getValue(line, "name")+" train has "+ XMLfunctions.getValue(line, "status").toLowerCase());
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, "Rant via"));
	}
}
