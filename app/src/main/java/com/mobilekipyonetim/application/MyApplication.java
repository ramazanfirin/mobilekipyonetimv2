package com.mobilekipyonetim.application;

import android.app.Application;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.mobilekipyonetim.R;
import com.mobilekipyonetim.listener.MyLocationListener;
import com.mobilekipyonetim.service.SendLocationTask;
import com.mobilekipyonetim.util.*;

public class MyApplication extends Application {
	MyLocationListener locationListener = new MyLocationListener();
	String currentOrderId;
	String currentOrderStatus;
	String url;
	String provider;
	String deviceId;
	//public LocationManager locationManager;
	
	public MyApplication() {
		super();
		Log.i("application", "olustu");
//		 Criteria criteria = new Criteria();
//		  criteria.setAccuracy(Criteria.ACCURACY_COARSE);	//default
//		  criteria.setCostAllowed(false); 
//		  LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		  provider = locationManager.getBestProvider(criteria, false);
//		  url = getApplicationContext().getString(R.string.sender);
	}
	
	public void prepare(){
		  Criteria criteria;
		 criteria = new Criteria();
		  criteria.setAccuracy(Criteria.ACCURACY_COARSE);	//default
		  criteria.setCostAllowed(false); 
		  LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		  provider = locationManager.getBestProvider(criteria, false);
		  setUrl(getString(R.string.serverUrl));
		  setProvider(provider);
	}
	
	public void startTracking(String orderId, Location destination){
	/*
			Log.i("mobilEkip", "tracking basladi");
		prepare();
		locationListener = new MyLocationListener(); 
		  MyLocationListener listener = getLocationListener();
		  listener.setOrderId(orderId);
		  listener.setUrl(url+"/order");
		  listener.setDestination(destination);
		  listener.setContext(getApplicationContext());
		  //listener.setLocationManager(locationManager);
//		  LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, listener);
		  new SendLocationTask().execute(url+"/order"+"/"+orderId+"/started");
		  setCurrentOrderId(orderId);
		  setCurrentOrderStatus(Util.ORDER_STATUS_STARTED);
		  Toast.makeText(getApplicationContext(),"Data gonderimi basladi", Toast.LENGTH_LONG).show();
		  //Location location =locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//		  if(location!=null){
//			  listener.onLocationChanged(location);
//		  }else{
//			  location =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//			  if(location!=null){
//				  listener.onLocationChanged(location);
//			  }
//		  }
		  
	*/
	}
	  
	public void stopTracking(){
		  Log.i("mobilEkip", "tracking sonlandirildi");
//		  MyLocationListener listener = getLocationListener();
//		  LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		  locationManager.removeUpdates(listener);
//		  locationListener = null;
		  setCurrentOrderId("");
		  setCurrentOrderStatus(Util.ORDER_STATUS_CANCELLED);
	  }
	  public void cancelTraking(String orderId){
		  Log.i("mobilEkip", "tracking iptal edildi");
//		  MyLocationListener listener = getLocationListener();
//		  LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		  locationManager.removeUpdates(listener);
//		  locationListener = null;
		  new SendLocationTask().execute(url+"/order"+"/"+orderId+"/cancelled");
		  setCurrentOrderId("");
		  setCurrentOrderStatus(Util.ORDER_STATUS_COMPLETED);
	  }
	  public void completedTraking(String orderId){
		  Log.i("mobilEkip", "tracking tamamlandi");
//		  MyLocationListener listener = getLocationListener();
//		  LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		  locationManager.removeUpdates(listener);
//		  locationListener = null;
		  new SendLocationTask().execute(url+"/order"+"/"+orderId+"/completed");
		  setCurrentOrderId("");
		  setCurrentOrderStatus("");
	  }
	
	
	
	
	public MyLocationListener getLocationListener() {
		return locationListener;
	}
	public void setLocationListener(MyLocationListener locationListener) {
		this.locationListener = locationListener;
	}
	public String getCurrentOrderId() {
		return currentOrderId;
	}
	public void setCurrentOrderId(String currentOrderId) {
		this.currentOrderId = currentOrderId;
	}
	public String getCurrentOrderStatus() {
		return currentOrderStatus;
	}
	public void setCurrentOrderStatus(String currentOrderStatus) {
		this.currentOrderStatus = currentOrderStatus;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
//	public LocationManager getLocationManager() {
//		return locationManager;
//	}
//	public void setLocationManager(LocationManager locationManager) {
//		this.locationManager = locationManager;
//	}
	public String getDeviceId() {
		if(deviceId==null || deviceId.equals("")){
			try {
				WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = manager.getConnectionInfo();
				deviceId = info.getMacAddress().replace(":", "");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				deviceId="";
			}
		}return deviceId;
	}
	public void setDeviceId(String wifiMac) {
		this.deviceId = wifiMac;
	}

}
