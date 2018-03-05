package com.mobilekipyonetim.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.mobilekipyonetim.R;
import com.mobilekipyonetim.application.MyApplication;
import com.mobilekipyonetim.service.BackgroundTask;
import com.mobilekipyonetim.service.GCMRegisterCheckTask;
import com.mobilekipyonetim.service.GCMRegisterTask;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends TabActivity {
	
	public ListView mainListView;
	 public ArrayList<HashMap<String, String>> orderList;
	 
	 public String getSENDER_ID(){
		  return getApplication().getString(R.string.sender);
	  }
	 
	 public String getServerUrl() {
			return getApplication().getString(R.string.serverUrl);
		}
	 
	 public String getDeviceId(){
		  return getMyApplication().getDeviceId();
	  }
	 
	 public MyApplication getMyApplication(){
		  return (MyApplication) getApplication();
	  }
//
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.main, menu);
			return true;
		}
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == R.id.regControl) {
				new GCMRegisterCheckTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,getServerUrl()+"/hello/checkRegister/"+getDeviceId());
				//new BackgroundTask().execute();
				//new BackgroundTask().executeOnExecutor();
			}
			
			if (item.getItemId() == R.id.register) {
				String asd=getServerUrl()+"/hello/RegisterId/"+getDeviceId();
				new GCMRegisterTask(getApplication(),getSENDER_ID()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,asd);
			}
			return super.onOptionsItemSelected(item);
		}

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost();
     // Android tab
     		Intent intentAndroid = new Intent().setClass(this, ManuelSelectionActivity.class);
     		TabSpec tabSpecAndroid = tabHost
     			.newTabSpec("Secim")
     			.setIndicator("Manuel Secim")
     			.setContent(intentAndroid);

     		// Apple tab
     		Intent intentApple = new Intent().setClass(this, OrderListActivity.class);
     		TabSpec tabSpecApple = tabHost
     			.newTabSpec("Apple")
     			.setIndicator("Gorev Listesi")
     			.setContent(intentApple);
     		
   		tabHost.addTab(tabSpecAndroid);
    		tabHost.addTab(tabSpecApple);

    		
    		//set Windows tab as default (zero based)
//    		tabHost.setCurrentTab(1);	
	}   
	
}


