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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
		//Toast.makeText(activity.getApplication().getApplicationContext(),"test",  Toast.LENGTH_LONG).show();
	dialog.setMessage("Please wait..");
	dialog.show();
	}
	
	@Override
	protected String doInBackground(String... urlValue) {
		//Toast.makeText(activity.getApplication().getApplicationContext(),"test",  Toast.LENGTH_LONG).show();
		InputStream inputStream = null;
        String result = "";
        try {

			URL url = new URL(urlValue[0]);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			try {
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());

				StringBuffer buffer = new StringBuffer();
				if (in == null) {
					// Nothing to do.
					return null;
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));

				String line;
				while ((line = reader.readLine()) != null) {
					// Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
					// But it does make debugging a *lot* easier if you print out the completed
					// buffer for debugging.
					buffer.append(line + "\n");
				}

				if (buffer.length() == 0) {
					// Stream was empty.  No point in parsing.
					return null;
				}

				return buffer.toString().replace("\\","").replace("n","");



			} finally {
				urlConnection.disconnect();
			}

 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
	return result;
	}
	
	@Override
	protected void onPostExecute(String unused) {
		unused = unused.replace("n","");
		unused = unused.replace("\\","").replace("n","");

			if("OK\n".equals(unused))
				Toast.makeText(activity,"Kayit Yapilmiş", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(activity.getApplicationContext(),"Kayit Yapılmamış", Toast.LENGTH_LONG).show();
			
			if (dialog.isShowing()) {
			    dialog.dismiss();
			}
					
	}


}
