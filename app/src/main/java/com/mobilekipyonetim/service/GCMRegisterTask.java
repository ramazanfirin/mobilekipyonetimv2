package com.mobilekipyonetim.service;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mobilekipyonetim.util.GCMUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class GCMRegisterTask extends AsyncTask<String, Void, String> {
    
	private GoogleCloudMessaging gcm;
	private Context context;
	private String SENDER_ID;
	String regid;
	private Activity activity;
	
	
	
	public GCMRegisterTask(Context _context, String _SENDER_ID) {
		super();
		context = _context;
		SENDER_ID = _SENDER_ID;
		//gcm = GoogleCloudMessaging.getInstance(_context);
		//activity = _activity;
	}

	@Override
    protected String doInBackground(String... params) {
            String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regid = gcm.register(SENDER_ID);
                msg = "Device registered, registration id=" + regid;
     
                
               // MyApplication myApplication = (MyApplication)context.getApplicationContext() ;
               // String imei=myApplication.getDeviceId();
                
                
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet(params[0]+"/"+regid);
                HttpResponse httpResponse = httpclient.execute(request);
                if(httpResponse.getStatusLine().getStatusCode()==200){
                	String responseString = new BasicResponseHandler().handleResponse(httpResponse);
                	if("OK".equals(responseString)){
                		GCMUtil.setRegistrationId(context, regid);
                		//Toast.makeText(activity.getBaseContext(),"Kayit Yapildi", Toast.LENGTH_LONG).show();
                		 //publishProgress(1);
                		msg= "Kayit Yapildi";
                	}else{
                		msg="Cihaz Bulunamadi";
                	}
                }else{
                	msg="Hata Olustu. Yeniden Deneyin";
                }
            } catch (Exception ex) {
                msg = "Error :" + ex.getMessage();
                Log.e("hata", ex.getMessage());
            }
            return msg;
        }

		protected void onProgressUpdate(Integer integers) {
		  if(integers == 1) {
		    Toast.makeText(activity.getBaseContext(), "Text", Toast.LENGTH_LONG).show();
		  } 
		}
	
        @Override
        protected void onPostExecute(String msg) {
            //mDisplay.append(msg + "\n");
        	//Log.i("regId", regid);
        	Toast.makeText(context.getApplicationContext(),msg, Toast.LENGTH_LONG).show();
        }

		public Activity getActivity() {
			return activity;
		}

		public void setActivity(Activity activity) {
			this.activity = activity;
		}
    

}
