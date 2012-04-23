package com.yinmotion.MTAmashup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewManager;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class SetNotifyActivity extends Activity {
	protected static final int TIME_DIALOG_ID = 0;
	protected static final int LINES_DIALOG_ID = 1;
	private static final String TAG = "SetNotifyActivity";
	private static boolean isPM = false;
	private CheckBox alertCheckbox;
	private CheckBox vibrateCheckbox;
	private int hour;
	private int min;
	private TextView txtTimeCode;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(R.layout.setnotify_activity);
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.app_title);
        
        initMenu();
        
        initTimePicker();
        initSubwaylinesPicker();
        
        //
        View turnAlertOn = (View) findViewById(R.id.view_turn_alert_on);
        
        alertCheckbox = (CheckBox) findViewById(R.id.checkbox_alert);
        
        turnAlertOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTurnAlertToggle();
			}
		});
        
        View viewVibrateOn = (View) findViewById(R.id.view_vibrate_on);
        
        vibrateCheckbox = (CheckBox) findViewById(R.id.checkbox_vibrate);
        
        viewVibrateOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onVibrateToggle();
			}
		});
        
        //
        txtTimeCode = (TextView) findViewById(R.id.txt_timecode);
       
        Calendar calender = Calendar.getInstance();
        hour = calender.get(Calendar.HOUR_OF_DAY);
        min = calender.get(Calendar.MINUTE);
        
        updateTimeCode();
       
	}
	
	private void initTimePicker() {
		View timePicker = (View) findViewById(R.id.view_time);
		
		timePicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					
				showDialog(TIME_DIALOG_ID);
			}
		});
	}

	private void initSubwaylinesPicker() {
		// TODO Auto-generated method stub
		View linePicker = (View) findViewById(R.id.view_lines);
		linePicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(LINES_DIALOG_ID);				
			}
		});
	}
	
	protected Dialog onCreateDialog(int id){
		
		switch(id){
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, onTimeSetListener, hour, min, false);
			
		case LINES_DIALOG_ID:
			//return null;
			final ListView lv = new ListView(this);
			//Log.v(TAG, "lv = "+lv);
			String[] linesArray = getResources().getStringArray(R.array.lines);
			List<String> arrayList = Arrays.asList(linesArray);
			lv.setAdapter(new PickLineArrayAdapter(this, R.layout.pickline_item, arrayList));
			lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lv.setItemsCanFocus(false);
			lv.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
			
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
		                long id) {
					onSetLines(lv);
				}
			});
			
			AlertDialog linesDialog = new AlertDialog.Builder(this).
									setTitle(R.string.select_lines).
									setView(lv).
//									setMultiChoiceItems(R.array.lines, null, new DialogInterface.OnMultiChoiceClickListener() {
//										
//										@Override
//										public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//											// TODO Auto-generated method stub
//											
//										}
//									}).
									setNegativeButton(R.string.cancel,
											new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int whichButton) {
											dialog.cancel();
											
										}
									}).
									setPositiveButton(R.string.ok,
					                    new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int whichButton) {
					                    	//lv.getSelectedItem();
					                        onSetLines(lv);
					                    }
					                }).
									create();
			
			
			
			return linesDialog;
//			Dialog linesDialog = new Dialog(this);
//			linesDialog.addContentView((View) findViewById(R.layout.picklines_dialog), null);
//			linesDialog.setTitle(R.string.select_lines);
//			linesDialog.setCancelable(true);
			
		}
		
		return null;
	}
	
	protected void onSetLines(ListView lv) {
		// TODO Auto-generated method stub
		
		SparseBooleanArray selectedItems = lv.getCheckedItemPositions();
		 Log.v(TAG, "selectedItems = "+selectedItems.size());
		 
		 
	    for (int i = 0; i < selectedItems.size(); i++) {
	    	 Log.v(TAG, "lv = " + selectedItems.valueAt(i));
	    	
//	        if (selectedItems.get(i)) {
//	            String item =  lv.getAdapter().getItem(selectedItems.keyAt(i)).toString();
//	            Log.v(TAG, "lv = "+item);
//	        }
	    }
	}

	private OnTimeSetListener onTimeSetListener = new OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			hour = hourOfDay;
			min = minute;
			
			updateTimeCode();
		}
	};

	private void initMenu() {
		View cancel = (View) findViewById(R.id.setnotify_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//TODO show/hide deleteBtn based on edit/add mode 
		View deleteBtn = (View) findViewById(R.id.setnotify_delete);
		ViewManager menu = (ViewManager)deleteBtn.getParent();
		menu.removeView((View) findViewById(R.id.divider_delete));
		menu.removeView(deleteBtn);
	}

	private void updateTimeCode() {
		// TODO Auto-generated method stub
		txtTimeCode.setText(
				new StringBuilder()
				.append(leadZero(formatHour(hour))).append(":")
				.append(leadZero(min))
				.append(" ")
				.append(getAMPM()));
				
	}

	protected void onVibrateToggle() {
		// TODO Auto-generated method stub
		vibrateCheckbox.toggle();
		
	}

	protected void onTurnAlertToggle() {
		// TODO Auto-generated method stub
		alertCheckbox.toggle();
	}

	public SetNotifyActivity() {
		// TODO Auto-generated constructor stub
	}
	
	
	private static String leadZero(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
	
	private static int formatHour(int iHour) {
		int h = iHour;
		if(h>=12){
			h -= 12;
			isPM  = true;
		}else{
			isPM = false;
		}
		
		return h;
	}
	
	private static String getAMPM(){
		if(isPM){
			return "PM";
		}else{
			return "AM";
			
		}
		
	}
	
}
