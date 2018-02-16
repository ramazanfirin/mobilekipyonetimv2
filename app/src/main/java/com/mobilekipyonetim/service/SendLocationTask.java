package com.mobilekipyonetim.service;

import android.os.AsyncTask;
import android.util.Log;

import com.mobilekipyonetim.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SendLocationTask extends AsyncTask<String, Void, String> {
	
	private List<String> itemsList;
	 ArrayList<String> arrayList;
	 private String temp;

	@Override
	protected void onPreExecute() {
	
	}

	@Override
	protected String doInBackground(String...arg) {
		InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(arg[0]));
 
            // receive response as inputStream
            if(httpResponse.getEntity() ==null)
            	return "";
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
	 

	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
	}
