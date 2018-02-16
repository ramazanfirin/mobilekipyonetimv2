package com.mobilekipyonetim.activity;


import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;


import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mobilekipyonetim.R;
import com.mobilekipyonetim.application.MyApplication;
import com.mobilekipyonetim.listener.MyLocationListener;
public class BaseActivity extends Activity {
	 


	public LocationManager locationManager;
//	  public MyLocationListener mylistener = new MyLocationListener();
	  public Criteria criteria;
	  public String provider;
	  //String serverUrl;
	  //public String SENDER_ID;
	    
	    GoogleCloudMessaging gcm;
	    String regid;
	    
	    protected String currentOrderId;
	    protected String currentOrderStatus;
	    
	    
	  
	  
	  @Override
		public void onCreate(Bundle savedInstanceState) {
			  super.onCreate(savedInstanceState);  
//			  locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			  criteria = new Criteria();
			  criteria.setAccuracy(Criteria.ACCURACY_COARSE);	//default
			  criteria.setCostAllowed(false); 
			  LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			  provider = locationManager.getBestProvider(criteria, false);
//			  serverUrl=getApplication().getString(R.string.serverUrl);
			  //SENDER_ID = getApplication().getString(R.string.sender);
     		  getMyApplication().setProvider(provider);
			  getMyApplication().setUrl(getServerUrl());
	 }	 
	  
	  public String getServerUrl() {
			return getApplication().getString(R.string.serverUrl);
		}
	  
	  public String getSENDER_ID(){
		  return getApplication().getString(R.string.sender);
	  }
	 
	  public MyApplication getMyApplication(){
		  return (MyApplication) getApplication();
	  }
	  
	  public MyLocationListener getLocationListener(){
		 return getMyApplication().getLocationListener();
	  }
	  
	  public void setGlobalCurrentOrderId(String id){
		  getMyApplication().setCurrentOrderId(id);
	  }
	   
	  public String getGlobalCurrentOrderId(){
		  return getMyApplication().getCurrentOrderId();
	  }
	  
	  public void setGlobalCurrentOrderStatus(String id){
		  getMyApplication().setCurrentOrderStatus(id);
	  }
	   
	  public String getGlobalCurrentOrderStatus(){
		  return getMyApplication().getCurrentOrderStatus();
	  }
	  
	  public String getDeviceId(){
		  return getMyApplication().getDeviceId();
	  }
	   
}
