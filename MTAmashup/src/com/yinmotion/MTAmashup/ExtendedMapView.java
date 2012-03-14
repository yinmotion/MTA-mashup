package com.yinmotion.MTAmashup;

import com.google.android.maps.MapView;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;



public class ExtendedMapView extends MapView {
	  private Context context;
	  private GestureDetector gestureDetector;
	
	
	public ExtendedMapView(Context c, AttributeSet attrs) {
		super(c, attrs);  
		context = c;
		  
	 }
	
	public void init(){
		gestureDetector = new GestureDetector((OnGestureListener) context);  
		gestureDetector.setOnDoubleTapListener((OnDoubleTapListener) context);  
		
	}
	
//	public boolean onTouchEvent(MotionEvent ev) {
//	  if (ev.getAction()==MotionEvent.ACTION_UP) {
//	   //do your thing
//		  Log.v("MapView", "Touch");
//		  return true;
//	  }
//	  return super.onTouchEvent(ev);
//	 }
//	 
//	@Override
//	 public boolean onTouchEvent(MotionEvent ev) {  
//	  if (this.gestureDetector.onTouchEvent(ev))  
//	   return true;  
//	  else  
//	   return super.onTouchEvent(ev);  
//	 } 

}
