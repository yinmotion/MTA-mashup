/**
 * 
 */
package com.yinmotion.MTAmashup;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Element;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewDebug.FlagToString;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	private static final String SCREEN_PLATFORM_WALL = "screen_platform_wall";
	private static final String SCREEN_SPLASH = "screen_splash";
	private View wallView;
	private XMLloaderParser xmlTask;
	private Timer loaderTimer;
	private ArrayList<Element> aUps;
	private ArrayList<Element> aDowns;
	
	private GridView menuGrid;
	private ArrayList<Integer> aMainMenu;
	private boolean menuEnabled = true;
	
	private String currScreen = SCREEN_SPLASH;
	private boolean isRefreshing = false;
	
	private SensorManager sensorMgr;
	
	protected float accelLast;
	protected float accelCurrent;
	protected float accel;
	protected long lastUpdate;
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
		        addStatusBoards();
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
		if(currScreen==SCREEN_PLATFORM_WALL){
			Log.v(TAG, "hide preloader");
			ProgressBar refreshLoader = (ProgressBar)findViewById(R.id.refreshLoader);
			refreshLoader.setVisibility(View.INVISIBLE);
			
			return;
		}
		
		currScreen = SCREEN_PLATFORM_WALL;
		
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
	
	}
	
	protected void addStatusBoards(){
		TextView tv = (TextView) findViewById(R.id.app_title_timestamp);
        //Log.v(TAG, "title : "+tv);
        tv.setText("@"+((LineStatusData)getApplication()).getTimestamp());
        
        //Inflate ups list view
        ViewGroup wall_container = (ViewGroup) findViewById(R.id.platform_container); 
        
        View upView = LayoutInflater.from(getBaseContext()).inflate(R.layout.board_ups, null);
        wall_container.addView(upView);        
        aUps = ((LineStatusData)getApplication()).getUps();
        
        GridView upsGrid = (GridView) findViewById(R.id.ups_grid); 
        upsGrid.setAdapter(new UpLineListAdapter());
        upsGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				Element line = aUps.get(position);
				int iconId = getResources().getIdentifier("line_"+XMLfunctions.getValue(line, "name").toLowerCase(), "drawable", "com.yinmotion.MTAmashup");
				showUpStatusDetail(iconId, line);
			}
		});
        
        //Inflate downs list view
        View downView = LayoutInflater.from(getBaseContext()).inflate(R.layout.board_downs, null);
        wall_container.addView(downView);
        aDowns = ((LineStatusData) getApplication()).getDowns();
        
        GridView downsGrid = (GridView) findViewById(R.id.downs_grid);
        downsGrid.setAdapter(new DownLineListAdapter());
        downsGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				Element line = aDowns.get(position);
				int iconId = getResources().getIdentifier("line_"+XMLfunctions.getValue(line, "name").toLowerCase(), "drawable", "com.yinmotion.MTAmashup");
				showDownStatusDetail(iconId, line);
			}
		});
        
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
				onBoardSlideIn();
			}
		});
	}
	
	private void onBoardSlideIn() {
		isRefreshing = false;
		
		slideTrainIn();
		addMenu();
		addShakeListener();
	}
	
	private void addShakeListener() {
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorMgr.registerListener(sensorListener, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		accel = 0.00f;
	    accelCurrent = SensorManager.GRAVITY_EARTH;
	    accelLast = SensorManager.GRAVITY_EARTH;
	}
	
	public final SensorEventListener sensorListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if(isRefreshing || isRanting) return;
			
			long curTime = System.currentTimeMillis();
		    // only allow one update every 100ms.
			    if ((curTime - lastUpdate) > 100) {
			    	
			    	lastUpdate = curTime;
					float x = event.values[0];
					float y = event.values[1];
					float z = event.values[2];
					
					accelLast = accelCurrent;
					accelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
				    float delta = accelCurrent - accelLast;
				    accel = accel * 0.9f + delta; // perform low-cut filter
				    
				    if(accel>6){
				    	//Log.v(TAG, "shake!!!");
				    	onShaking();
				    }
		    }
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	};
	private boolean isDownDetailsShow;
	private AlertDialog downdetail;
	private boolean isRanting;
	private Element currDetailsLine;
	
	protected void onShaking() {
		Log.v(TAG, "onShaking");
		if(downdetail!= null && downdetail.isShowing()){
			downdetail.cancel();
			rantIt();
		}else{
			isRanting = false;
			refresh();
		}
	}
	
	@Override
	protected void onResume() {
		Log.v(TAG, "onResume");
	    super.onResume();
	    isRanting = false;
	    if(sensorMgr!=null){
	    	sensorMgr.registerListener(sensorListener, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	    }
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(sensorMgr!=null){
			sensorMgr.unregisterListener(sensorListener, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
		}
	}
	
	protected void addMenu() {
		// TODO Auto-generated method stub
		menuEnabled = true;
		
		if(menuGrid!=null){
			menuGrid.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
			return;
		}
		aMainMenu = ((LineStatusData)getApplication()).getMainMenu();
		menuGrid = (GridView) findViewById(R.id.main_menu);
		menuGrid.setAdapter(new MenuListAdapter());
		menuGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					refresh();
				break;
				
				case 1:
					openOptionsMenu();
				break;
				
				case 2:
					shareIt();
				break;
				
				default:
					break;
				}
			}
		});
		
		menuGrid.setVisibility(0);
	}
	
	public class MenuListAdapter extends BaseAdapter{
		
		public MenuListAdapter(){
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return aMainMenu.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			
			
	        if(convertView==null){     	
	            LayoutInflater li = getLayoutInflater();
	            v = li.inflate(R.layout.menu_item, null);
	            ImageView iv = (ImageView)v.findViewById(R.id.main_menu_icon);
	            final Integer i = aMainMenu.get(position);
	            iv.setImageResource(i);
	        }
	        else
	        {
	            v = convertView;
	        }
	        return v;
		}
	}

	private void refresh() {
		// TODO Auto-generated method stub
		isRefreshing = true;
		
		slideTrainOut();
		slideBoardOut();
		ProgressBar refreshLoader = (ProgressBar)findViewById(R.id.refreshLoader);
		refreshLoader.setVisibility(View.VISIBLE);
		
		Animation fadeout = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
		fadeout.setFillAfter(true);		
		menuGrid.startAnimation(fadeout);
		menuEnabled = false;
		
		loadXMLData();
	}

	private void slideBoardOut(){
		Animation boardBack = AnimationUtils.loadAnimation(this, R.anim.board_back);
		
		boardBack.setFillAfter(true);
		final ViewGroup wall_container = (ViewGroup) findViewById(R.id.platform_container);
		wall_container.startAnimation(boardBack);
		wall_container.setEnabled(false);
		boardBack.setAnimationListener(new AnimationListener() {
			
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
				wall_container.removeAllViews();
			}
		});
	}

	protected void slideTrainIn(){
		ImageView train = (ImageView)findViewById(R.id.splash_train);
		Animation trainSlide = AnimationUtils.loadAnimation(this, R.anim.splash_train_slide);
		trainSlide.setFillAfter(true);
		train.startAnimation(trainSlide);
	}
	
	protected void slideTrainOut(){
		ImageView train = (ImageView)findViewById(R.id.splash_train);
		Animation trainSlide = AnimationUtils.loadAnimation(this, R.anim.splash_train_slide_out);
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
	            
//	            
//	            iv.setOnClickListener(new View.OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						showDownStatusDetail(imgId, line);
//						
//					}
//				});
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
		currDetailsLine = line;
		
		downdetail = new AlertDialog.Builder(this).
				setTitle(XMLfunctions.getValue(line, "status")).
				setMessage(Html.fromHtml(XMLfunctions.getValue(line, "plannedworkheadline"))).
				setIcon(iconId).
				setPositiveButton("RANT!#?@", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						rantIt();
					}
				}).
				setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						isDownDetailsShow = false;
					}
				}).create();
		
		downdetail.show();
		
		isDownDetailsShow = true;
	}

	protected void rantIt() {
		isRanting = true;
		String body = "WTF?#! @"+ XMLfunctions.getValue(currDetailsLine, "Time")+" "+XMLfunctions.getValue(currDetailsLine, "Date")+", " +XMLfunctions.getValue(currDetailsLine, "name")+" train has "+ XMLfunctions.getValue(currDetailsLine, "status").toLowerCase() + ". Details:"+XMLfunctions.getValue(currDetailsLine, "plannedworkheadline");
		String subject = XMLfunctions.getValue(currDetailsLine, "name")+" Line has "+ XMLfunctions.getValue(currDetailsLine, "status").toLowerCase();
		String chooserHeader = "Rant via";
		
		doShareIntent(body, subject, chooserHeader);
	}
	
	protected void shareIt(){
		
		String subject = "MTA service status @"+((LineStatusData)getApplication()).getTimestamp();
		String chooserHeader = "Share via";
		
		doShareIntent(((LineStatusData)getApplication()).getAllStatus(), subject, chooserHeader);
	}
	
	protected void doShareIntent(String body, String subject, String chooserHeader){
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
		startActivity(Intent.createChooser(sharingIntent, chooserHeader));
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.about:
			startAbout();
			return true;
			
		case R.id.alert:
			return true;
			
		case R.id.update_setting:
			return true;
			
		case R.id.exit:
			finish();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
			
		}
		
	}

	private void startAbout() {
		Intent aboutIntent = new Intent(this, AboutActivity.class);
		aboutIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(aboutIntent);
		// TODO Auto-generated method stub
		
	}
}
