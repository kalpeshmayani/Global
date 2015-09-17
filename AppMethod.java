package com.kpinfotech.global;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AppMethod {
	
	public static final String PREFS_NAME = "AppName";
	public static ProgressDialog p_Dialog;

	
	// to check whether internet is connected or not
	public static boolean isNetworkConnected(Activity activity) {
		ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	
	// display toast
	public static void showToast(Activity activity, String msg) {
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
	
	
	// show progress
	public static void showProgressDialog(Activity activity, String msg) {
		p_Dialog = new ProgressDialog(activity);
		p_Dialog.setMessage(msg);
		p_Dialog.setCanceledOnTouchOutside(false);
		p_Dialog.show();
	}

	
	// dismiss progress
	public static void dismissProgressDialog(Activity activity) {
		if (p_Dialog != null && p_Dialog.isShowing())
			p_Dialog.dismiss();
	}
	

	// string preference
	public static boolean setStringPreference(Activity activity, String key, String value) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	public static String getStringPreference(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String value = settings.getString(key, "");
		return value;
	}
	

	// int preference
	public static boolean setIntegerPreference(Activity activity, String key, int value) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	public static int getIntegerPreference(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		int value = settings.getInt(key, -1);
		return value;
	}
	
	
	// boolean preference
	public static boolean setBooleanPreference(Activity activity, String key, Boolean value) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
	public static Boolean getBooleanPreference(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Boolean value = settings.getBoolean(key, false);
		return value;
	}

    
	// to check whether all digit are same or not
	public static Boolean isAllDigitSame(Activity activity, String number) {
        String[] no = number.split("");
        Integer[] noArray = new Integer[(no.length-1)];

        for(int i = 0; i<noArray.length; i++) {
            noArray[i] = Integer.parseInt(no[i+1]);
        }

        boolean flag = true;
        Integer first = noArray[0];
        for(int i = 1; i < noArray.length && flag; i++)
        {
            if (noArray[i] != first) flag = false;
        }
        return flag;
    }
	
	
	// set custom font
    public static Typeface SetFontAwesomeFonts(Activity activity) {
		Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fontawesome_webfont.ttf");
		return typeface;
    }
    
    
    // get proper listview size within scrollview
    public static void getListViewSize(ListView myListView)
	{
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
	}
    
    
    // open file with in-built app
    public static void openFile(Activity activity, String filename)
	{

		File openFile = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "Test Saviour" + File.separator
				+ filename);

		Uri uri = Uri.fromFile(openFile);

		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (openFile.toString().contains(".doc")
				|| openFile.toString().contains(".docx")) {
			// Word document
			intent.setDataAndType(uri, "application/msword");
		} else if (openFile.toString().contains(".pdf")) {
			// PDF file
			intent.setDataAndType(uri, "application/pdf");
		} else if (openFile.toString().contains(".ppt")
				|| openFile.toString().contains(".pptx")) {
			// Powerpoint file
			intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		} else if (openFile.toString().contains(".xls")
				|| openFile.toString().contains(".xlsx")) {
			// Excel file
			intent.setDataAndType(uri, "application/vnd.ms-excel");
		} else if (openFile.toString().contains(".zip")
				|| openFile.toString().contains(".rar")) {
			// WAV audio file
			intent.setDataAndType(uri, "application/zip");
		} else if (openFile.toString().contains(".rtf")) {
			// RTF file
			intent.setDataAndType(uri, "application/rtf");
		} else if (openFile.toString().contains(".wav")
				|| openFile.toString().contains(".mp3")) {
			// WAV audio file
			intent.setDataAndType(uri, "audio/x-wav");
		} else if (openFile.toString().contains(".gif")) {
			// GIF file
			intent.setDataAndType(uri, "image/gif");
		} else if (openFile.toString().contains(".jpg")
				|| openFile.toString().contains(".jpeg")
				|| openFile.toString().contains(".png")) {
			// JPG file
			intent.setDataAndType(uri, "image/jpeg");
		} else if (openFile.toString().contains(".txt")) {
			// Text file
			intent.setDataAndType(uri, "text/plain");
		} else if (openFile.toString().contains(".3gp")
				|| openFile.toString().contains(".mpg")
				|| openFile.toString().contains(".mpeg")
				|| openFile.toString().contains(".mpe")
				|| openFile.toString().contains(".mp4")
				|| openFile.toString().contains(".avi")) {
			// Video files
			intent.setDataAndType(uri, "video/*");
		} else {
			// if you want you can also define the intent type for any other
			// file

			// additionally use else clause below, to manage other unknown
			// extensions
			// in this case, Android will show all applications installed on the
			// device
			// so you can choose which application to use
			intent.setDataAndType(uri, "*/*");
		}

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(intent);
	}
    
	
	// check whether string is valid or not
	public static Boolean isStringValid(String text) {
		if(text != null && !text.equalsIgnoreCase("null") && !text.equalsIgnoreCase(""))
			return true;
		else
			return false;
	}
    
	
	// return valid string else "-"
    public static String getString(String text) {
		if(text != null && !text.equalsIgnoreCase("null") && !text.equalsIgnoreCase(""))
			return text;
		else
			return "-";
	}
    
    
    // return Age based on param Day, Month and year
	public static int calculateAge(int selectedDay, int selectedMonth, int selectedYear) {
		GregorianCalendar cal = new GregorianCalendar();
		int y, m, d, age;

		y = cal.get(Calendar.YEAR);
		m = (cal.get(Calendar.MONTH) + 1);
		d = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(selectedYear, selectedMonth, selectedDay);
		
		age = y - cal.get(Calendar.YEAR);
		if ((m < cal.get(Calendar.MONTH))
				|| ((m == cal.get(Calendar.MONTH)) && (d < cal.get(Calendar.DAY_OF_MONTH)))) {
			--age;
		}
		
		if (age < 0)
			throw new IllegalArgumentException("Age < 0");
		
		return age;
	}
	
	
	// get time difference in (HH:MM) format between two times 
	public static String getTimeDifference(String stime, String etime) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		String tDiffer = null;

		try {
			Date startDate = simpleDateFormat.parse(stime);
			Date endDate = simpleDateFormat.parse(etime);

			long difference = endDate.getTime() - startDate.getTime();
			if (difference < 0) {
				Date dateMax = simpleDateFormat.parse("24:00");
				Date dateMin = simpleDateFormat.parse("00:00");
				difference = (dateMax.getTime() - startDate.getTime()) + (endDate.getTime() - dateMin.getTime());
			}
			
			int days = (int) (difference / (1000 * 60 * 60 * 24));
			int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
			int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
			Log.i("log_tag", "Hours: " + hours + ", Mins: " + min);

			tDiffer = String.format("%02d", hours) + ":" + String.format("%02d", min);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return tDiffer;
	}
	
	
	// get current date and time (16-09-2015 18:27)
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
    }

}