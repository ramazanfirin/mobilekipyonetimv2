package com.mobilekipyonetim.listener;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mobilekipyonetim.application.MyApplication;
import com.mobilekipyonetim.service.SendLocationTask;

public  class MyLocationListener implements LocationListener {

	public String orderId;
	public String url;
	Location destination;
	Context context;
		
	
	public MyLocationListener() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public MyLocationListener(String orderId, String url, Location destination,
                              LocationManager locationManager) {
		super();
		this.orderId = orderId;
		this.url = url;
		this.destination = destination;
	}


	@Override
    public void onLocationChanged(Location loc) {
		try {
			MyApplication myApplication = (MyApplication)context.getApplicationContext() ; myApplication.getLocationListener();
			if(loc.distanceTo(destination)>100){
			    
				new SendLocationTask().execute(url+"/"+orderId+"/"+ String.valueOf(loc.getLatitude())+"/"+ String.valueOf(loc.getLongitude()));
				Log.i("mobilEkip", "koordinat gonderildi "+loc.getLatitude()+" "+loc.getLongitude());
				Toast.makeText(context.getApplicationContext(),loc.getLatitude()+" "+loc.getLongitude() +" hata payi "+loc.getAccuracy(), Toast.LENGTH_LONG).show();
			}else{
				Log.i("mobilEkip", "hedefe ulasildi");
				Toast.makeText(context.getApplicationContext(),"hedefe ulasildi", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.putExtra("orderId", orderId);
				intent.setAction("com.mobil.ekip.yonetim.receiver.TaskCompletedReceiver");
				context.sendBroadcast(intent);
			}
		} catch (Exception e) {
			Toast.makeText(context.getApplicationContext(),"Koordinat gonderirken hata: "+e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

    @Override
    public void onProviderDisabled(String provider) {
       // called when the GPS provider is turned off (user turning off the GPS on the phone)
    }

    @Override
    public void onProviderEnabled(String provider) {
       // called when the GPS provider is turned on (user turning on the GPS on the phone)
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
       // called when the status of the GPS provider changes
    }
    
    
    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}



	public Context getContext() {
		return context;
	}



	public void setContext(Context context) {
		this.context = context;
	}

	
}