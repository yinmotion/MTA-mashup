package com.yinmotion.MTAmashup;

import java.util.List;

import android.R.drawable;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

public class PickLineArrayAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<String> lines;
	private ImageView lineIcon;


	public PickLineArrayAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.lines = objects;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public int getCount() {
		
		return this.lines.size();
	}
	
	public String getItem(int index){
		return this.lines.get(index);
	}
	
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;
		if(row==null){
			LayoutInflater inflater = (LayoutInflater) this.getContext()
										.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					
			row = inflater.inflate(R.layout.pickline_item, parent, false);
			row.setFocusable(false);
			row.setFocusableInTouchMode(false);
			row.setPressed(true);
			row.setBackgroundResource(drawable.list_selector_background);
			row.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.v("pick line", "line : ");
					CheckBox box = (CheckBox) v.findViewById(R.id.line_checkbox);
					box.toggle();
					
				}
			});
		}
		
		lineIcon = (ImageView) row.findViewById(R.id.line_icon);
		final int imgId = context.getResources().getIdentifier("line_"+lines.get(position), "drawable", "com.yinmotion.MTAmashup");
		lineIcon.setImageResource(imgId);
		
		return row;
	}
	
	

}
