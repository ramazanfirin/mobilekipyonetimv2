package com.mobilekipyonetim.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mobilekipyonetim.util.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

public class GCMRegisterCheckTask extends AsyncTask<String, Void, String> {
	private ProgressDialog dialog ;
	Activity activity;
	
	public GCMRegisterCheckTask(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
	// TODO i18n
		dialog	= new ProgressDialog(activity);
	dialog.setMessage("Please wait..");
	dialog.show();
	}
	
	@Override
	protected String doInBackground(String... url) {
		InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url[0]));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = Util.convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
	return result;
	}
	
	@Override
	protected void onPostExecute(String unused) {
		
			if("OK".equals(unused))
				Toast.makeText(activity,"Kayit Yapilmiş", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(activity.getApplicationContext(),"Kayit Yapılmamış", Toast.LENGTH_LONG).show();
			
			if (dialog.isShowing()) {
			    dialog.dismiss();
			}
					
	}


}
