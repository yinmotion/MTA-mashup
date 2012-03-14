package com.yinmotion.MTAmashup;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

import android.content.Context;
import android.view.GestureDetector.OnGestureListener;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.Window;


public class MapActivity extends com.google.android.maps.MapActivity implements OnGestureListener, OnDoubleTapListener {
	
	public static final String TAG = "MapActivity";
	
	private MapController mc;

	private LocationManager mlocManager;

	private MyLocationListener mlocListener;

	public boolean gpsEnabled = true;

	private List<Overlay> mapOverlays;

	private MapOverlay mapOverlay;

	private boolean markerAdded;


	private ExtendedMapView mapView;

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	    setContentView(R.layout.map_activity);
	    
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.app_title);
	    
	    
	    mapView = (ExtendedMapView) findViewById(R.id.mapview);
	    
	    mapView.setBuiltInZoomControls(true);
	    
	    mapView.showContextMenu();
	    //mapView.setTraffic(true);
	    Log.v(TAG, "mapView = "+mapView);
	    
	    mc = this.mapView.getController();  
	    mc.setZoom(17);  
	    
	    mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    
	    mlocListener = new MyLocationListener();
	    
	    mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
	    //LocationManager.NETWORK_PROVIDER
	    Log.v(TAG, "onCreate ");
	    
	    mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.map_marker);
	    mapOverlay = new MapOverlay(drawable, this);
	  //Get the current location in start-up
	   
		Location loc = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(loc!=null){
			  setCurrLocation((int)(loc.getLatitude()*1E6), (int)(loc.getLongitude()*1E6));
			  
			  GeoPoint point = new GeoPoint((int)(loc.getLatitude()*1E6), (int)(loc.getLongitude()*1E6));
			  addMarker(point);
		}
	  
	}
	
	private void addMarker(GeoPoint point){
		if(!markerAdded){
			OverlayItem item = new OverlayItem(point, "I'M HERE :-)", "Latitude:"+point.getLatitudeE6()*1E6+"\nLongitude:"+point.getLongitudeE6()*1E6);
			mapOverlay.addOverlay(item);
			mapOverlays.add(mapOverlay);
			markerAdded = true;
		}else{
			//TODO update marker position
			
		}
	}
	
	private void setCurrLocation(int lat, int lng){
		GeoPoint point = new GeoPoint(lat, lng);
		addMarker(point);
		
		mc.animateTo(point);
	}
	
	
	public class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {

			   // definitely need what's below
			   int lat = (int) (location.getLatitude() * 1E6);
			   int lng = (int) (location.getLongitude() * 1E6);
			   
			   Log.v(TAG, "lat = "+lat + " lng = "+lng);
			   setCurrLocation(lat, lng);
			   
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Log.v(TAG, "GPS disabled");
			
			gpsEnabled  = false;
			mlocManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Log.v(TAG, "GPS enabled");	
			/*
			Location loc;
			if(gpsEnabled){
				loc = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}else{
				loc = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
			setCurrLocation((int)(loc.getLatitude()*1E6), (int)(loc.getLongitude()*1E6));
			*/
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
	}
	
	@Override
	public boolean onDoubleTap(MotionEvent e){
		int x = (int)e.getX(), y = (int)e.getY();;  
	     Projection p = mapView.getProjection();  
	     mc.animateTo(p.fromPixels(x, y)); // zoom in to a point you tapped   
	     mc.zoomIn();
	     return true;  
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
