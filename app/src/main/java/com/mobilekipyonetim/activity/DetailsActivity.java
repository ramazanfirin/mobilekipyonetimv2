package com.mobilekipyonetim.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import com.mobilekipyonetim.R;

import java.util.HashMap;

public class DetailsActivity extends BaseActivity //implements ConnectionCallbacks, OnConnectionFailedListener
{
	
	EditText ilce;
	EditText mahalle;
	EditText sokak;
	EditText bina;
	EditText address;
	TextView lat;
	TextView lng;
	TextView id;
	TextView description;
	Button button;
	
	//String url = "http://192.168.56.1:8080/Navigator/rest/order";
	//private LocationClient locationClient;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);  
		setContentView(R.layout.details);
		Intent i = getIntent();
		
		HashMap<String, String> map =(HashMap<String, String>)i.getExtras().get("order");
		
		ilce = (EditText) findViewById(R.id.ilce );
		ilce.setText(map.get("ilce"));
		
		mahalle = (EditText) findViewById( R.id.mahalle );
		mahalle.setText(map.get("mahalle"));
		
		sokak = (EditText) findViewById( R.id.sokak );
		sokak.setText(map.get("sokak"));
		
		bina = (EditText) findViewById( R.id.bina );
		bina.setText(map.get("bina"));
		
		address = (EditText) findViewById( R.id.address );
		address.setText(map.get("adress"));
		
		lat = (TextView) findViewById( R.id.lat );
		lat.setText(map.get("lat"));
		
		lng = (TextView) findViewById( R.id.lng );
		lng.setText(map.get("lng"));
		
		id = (TextView) findViewById( R.id.id );
		id.setText(map.get("id"));

		description = (TextView) findViewById( R.id.details );
		description.setText(map.get("description"));

		button = (Button) findViewById( R.id.button1 );
		button.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View arg0) {
               
            	 String latValue = lat.getText().toString();
            	 String lngValue = lng.getText().toString();
            	 
            	 String orderId = id.getText().toString();
            	
            	 Location dest = new Location("Destination2");
            	 dest.setLatitude(Double.valueOf(latValue));
            	 dest.setLongitude(Double.valueOf(lngValue));
            	
            	 /*
            	 Location location = locationManager.getLastKnownLocation(provider);
            	 if (location != null) {
            		  //mylistener.onLocationChanged(location);
       		  	} else {
	       			  // leads to the settings because there is no last known location
	       			  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	       			  startActivity(intent);
       		     }
            	 */
            	 //String uri2 ="route66://?daddr="+latValue+","+lngValue;
            	 //Toast.makeText(getApplicationContext(),uri2, Toast.LENGTH_LONG).show();
            	 getMyApplication().startTracking(orderId, dest);
            	 
            	 if(getApplication().getString(R.string.emulator).equals("true")){
            		 String uri ="http://maps.google.com/maps?ll="+latValue+",-"+lngValue;
            		 Intent newIntent = new Intent("android.intent.action.VIEW");
            		 newIntent.setData(Uri.parse(uri));
            		 Intent chooser = Intent.createChooser(newIntent, "Select app:");
            		 //newIntent.setClass(context, AndroidMobilePushApp.class);
            		 //newIntent.putExtras(intent.getExtras());
            		 //newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            		 startActivity(chooser);
            	 }else{
            		 //String uri ="route66://?daddr="+latValue+","+lngValue;
					 String uri ="http://maps.google.com/maps?f=d&daddr="+latValue+","+lngValue;
            		 //uri ="route66://?daddr=41.0249,29.1226";
//            		 Intent intent = new Intent(Intent.ACTION_VIEW);
//            		 intent.setData(Uri.parse(uri));
//            		 startActivity(intent);
            		 Toast.makeText(getApplicationContext(),uri, Toast.LENGTH_LONG).show();
            		 
            		 try {
						startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse(uri)) );
					} catch (Exception e) {
						// TODO Auto-generated catch block
						 Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
            		 }
                 
                
            	  //locationManager.requestLocationUpdates(provider, 200, 1, mylistener);
            	 	
             }
     });
	Button button2 = (Button) findViewById( R.id.button2 );
	button2.setVisibility(View.INVISIBLE);
	button2.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			/*
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			if (locationManager != null) {
                Location location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                	double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                	Toast.makeText(getApplicationContext(),latitude +" "+longitude+" "+location.getAccuracy(), Toast.LENGTH_LONG).show();
                    

                }
            }
            */
		}
	
	});
	
	final int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	if (result != ConnectionResult.SUCCESS) {
		Toast.makeText(this, "Google Play service is not available (status=" + result + ")", Toast.LENGTH_LONG).show();
		finish();
	}

	//locationClient = new LocationClient(this, this, this);
}
/*
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		

		Location loc = locationClient.getLastLocation();
		Log.d("XXX", "location=" + loc.toString());
		Toast.makeText(this, "Connected "+loc.toString(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected", Toast.LENGTH_LONG).show();
		
	}
*/
	}
