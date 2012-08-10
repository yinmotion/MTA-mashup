package com.yinmotion.MTAmashup;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private GeoPoint _geoPoint; 
	

	public MapOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	
	public void addOverlay(OverlayItem overlay){
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	
	public void setGeoPoint(GeoPoint point){
		_geoPoint = point;
	}
	
	@Override
	protected boolean onTap(int index){
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.setPositiveButton(R.string.to_google_maps, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				openInMaps();
			}

			private void openInMaps() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:"+_geoPoint.getLatitudeE6()/1E6+","+_geoPoint.getLongitudeE6()/1E6+"?z=16"));
				//Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:4.0777171e13,-7.3950142e13"));
				Log.v("MapOverlay", "geo : "+_geoPoint.getLatitudeE6()/1E6);
				mContext.startActivity(intent);
			}
		});
		dialog.show();
		
		return true;
		
	}


}
