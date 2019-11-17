package com.mobilekipyonetim.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;


import com.mobilekipyonetim.R;
import com.mobilekipyonetim.activity.OrderListActivity;
import com.mobilekipyonetim.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetOrderListTask extends AsyncTask<String, Void, String> {
	Activity activity;
	private ProgressDialog dialog;
	
	 ArrayList<String> arrayList;
	
	public GetOrderListTask(Activity activity) {
		super();
		this.activity = activity;
		dialog = new ProgressDialog(activity);
	}

	
	@Override
	protected void onPreExecute() {
	// TODO i18n
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
	 
	// setListAdapter must not be called at doInBackground()
	// since it would be executed in separate Thread
//setListAdapter(new ArrayAdapter<String>(ListActivityTest.class, R.layout.simplerow, listAdapter));
		//arrayList.add(objec;t)
		List<String> list = new ArrayList<String>();
		ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
		String[] strArray=null;
			try {
//			JSONObject  reader = new JSONObject(unused);
//			JSONObject sys  = reader.getJSONObject("lat");
//			String country = sys.getString("country");
			
				String id="";
				String ilce="";
			String mahalle="";
			String sokak="";
			String adres="";
			String bina="";
			String lat="";
			String lng="";
			String	description="";
			
			JSONArray a = new JSONArray(unused);
			HashMap<String, String> map = new HashMap<String, String>();
			for(int i=0;i<a.length();i++){
			JSONObject b = a.getJSONObject(i);
			if(b.get("id")!=null){
				Integer test = (Integer)b.get("id");
			    id  = test.toString();
			}
			if(b.has("ilce")&& !b.get("ilce").toString().equals("null"))
				ilce = (String)b.get("ilce");
			
			if(b.has("mahalle")&& !b.get("mahalle").toString().equals("null"))
				mahalle = (String)b.get("mahalle");
			
			if(b.has("sokak")&& !b.get("sokak").toString().equals("null"))
				sokak = (String)b.get("sokak");
			
			if(b.has("address")&& !b.get("address").toString().equals("null"))
				adres = (String)b.get("address");
			
			//if((b.get("bina")!=null)&&(b.get("bina").toString()))
			if(b.has("bina")&& !b.get("bina").toString().equals("null"))
				bina = (String)b.get("bina");

			if(b.has("lat"))
				lat = (String)b.get("lat");
			
			if(b.has("lng"))
				lng = (String)b.get("lng");

			if(b.has("description") ) {
				String value  =b.getString("description");
				if(!value.equals("null"))
					adres = value;
			}
//			list.add(id);
//			list.add(ilce);

			//strArray = list.toArray(new String[list.size()]);
			
			map = new HashMap<String, String>();
			map.put("id", id);
			map.put("ilce", ilce);
			map.put("mahalle", mahalle);
			map.put("sokak", sokak);
			map.put("bina", bina);
			map.put("adress", adres);
			map.put("lat", lat);
			map.put("lng", lng);
				map.put("description", description);

			orderList.add(map);
			}
			
			System.out.println("");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//listAdapter = new ArrayAdapter(ListActivityTest.this, R.layout.simplerow, strArray);  
	   
		//

		ListAdapter listAdapter = new SimpleAdapter(
            activity, orderList,
            R.layout.simplerow, new String[] { "id",
                    "adress", }, new int[] {
                    R.id.id, R.id.rowTextView });

	OrderListActivity activity2 = (OrderListActivity)activity;
	activity2.mainListView.setAdapter( listAdapter );   
	activity2.orderList =orderList;

	if (dialog.isShowing()) {
	    dialog.dismiss();
	//}
	//}
		System.out.println("unused "+unused);
	}



}
public String getValue(JSONObject b, String key){
	try {
		Object object = b.get(key);
		if(object==null)
			return "";
		return (String)object;
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "";
	}
}
}
